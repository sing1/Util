### SharedPreferences存储效率探究
[ ](https://mp.weixin.qq.com/s?__biz=MzIxNjc0ODExMA==&mid=2247484542&idx=1&sn=7d13266b4ca48fe1edd6d55a12a835b9&chksm=97851d5fa0f29449eb649c0945a41de359d446f56cdb8e45363475a515dbad1a24a39cd36c67&mpshare=1&scene=1&srcid=080804Zs7wLh8Zw8cX61ODNP&key=d6b389c30db0c4fd43f6fdb6d11e793492f2f27c5483ebc6a45b299d350034f0f52056958923c955578ae9f6bdcb861b881e7a848c4f634d21dc5e50ea789d9927a7151c3ca56839ab76916a9832058f&ascene=0&uin=MTkyMTMzNzI2MQ%3D%3D&devicetype=iMac+MacBookPro12%2C1+OSX+OSX+10.12.1+build(16B2555)&version=12020810&nettype=WIFI&fontScale=100&pass_ticket=Ke9gbNVTItoG%2FSBGDp7AwuaFWX3lBTLHch60mXG2YuUW%2FC9zIi9PwqB%2Bcwp5yB%2BR)
### 一、前言
　　SharedPreferences（简称SP）是 Android 系统提供的一个以 Key-Value 键值对形式的存储方式。如果需要获取数据，SP 中提供了对应的getXxx() 方法，如果需要存储数据，只需要拿到 Editor 对象，在 Editor 对象中，也提供了对应的 putXxx() 方法，在操作完成之后，调用 commit() 或者 apply() 即可。那么 commit() 和 apply() 有什么区别？

### 二、从文档上看区别
　　SharedPreferences 就是一个接口，其实现类是SharedPreferencesImpl 。关于 commit() 和 apply() 的描述，在 SharedPreferences 中，下面先看看文档中对它的说明。

```JAVA
174 /**
175  * Commit your preferences changes back from this Editor to the
176  * {@link SharedPreferences} object it is editing.  This atomically
177  * performs the requested modifications, replacing whatever is currently
178  * in the SharedPreferences.
179  *
180  * <p>Note that when two editors are modifying preferences at the same
181  * time, the last one to call commit wins.
182  *
183  * <p>If you don't care about the return value and you're
184  * using this from your application's main thread, consider
185  * using {@link #apply} instead.
186  *
187  * @return Returns true if the new values were successfully written
188  * to persistent storage.
189  */
190  boolean commit();
191 
192 /**
193  * Commit your preferences changes back from this Editor to the
194  * {@link SharedPreferences} object it is editing.  This atomically
195  * performs the requested modifications, replacing whatever is currently
196  * in the SharedPreferences.
197  *
198  * <p>Note that when two editors are modifying preferences at the same
199  * time, the last one to call apply wins.
200  *
201  * <p>Unlike {@link #commit}, which writes its preferences out
202  * to persistent storage synchronously, {@link #apply}
203  * commits its changes to the in-memory
204  * {@link SharedPreferences} immediately but starts an
205  * asynchronous commit to disk and you won't be notified of
206  * any failures.  If another editor on this
207  * {@link SharedPreferences} does a regular {@link #commit}
208  * while a {@link #apply} is still outstanding, the
209  * {@link #commit} will block until all async commits are
210  * completed as well as the commit itself.
211  *
212  * <p>As {@link SharedPreferences} instances are singletons within
213  * a process, it's safe to replace any instance of {@link #commit} with
214  * {@link #apply} if you were already ignoring the return value.
215  *
216  * <p>You don't need to worry about Android component
217  * lifecycles and their interaction with <code>apply()</code>
218  * writing to disk.  The framework makes sure in-flight disk
219  * writes from <code>apply()</code> complete before switching
220  * states.
221  *
222  * <p class='note'>The SharedPreferences.Editor interface
223  * isn't expected to be implemented directly.  However, if you
224  * previously did implement it and are now getting errors
225  * about missing <code>apply()</code>, you can simply call
226  * {@link #commit} from <code>apply()</code>.
227  */
228  void apply();
```
从文档中可以看出一些区别：  
> 1、apply() 没有返回值，而 commit() 是有返回值的，返回值标识着是否执行成功。  
> 2、apply() 的操作是原子提交到内存中，然后以异步的方式保存到磁盘上，而 commit() 完全是以同步的方式将数据保存到磁盘上。  
> 3、apply() 因为没有返回值，所以不会提示任何失败。只需要调用即可。  
> 4、无论是 apply() 还是 commit() ，如果同时被操作了，以最后一次操作为准。  

获取SP这个对象的方式，是使用：

```JAVA
Context.getSharedPreferences()
```
所以在同一进程中，SP 对象是以单例的形式存在的，就不需要考虑有冲突的问题。但是因为 apply() 和 commit() 的差异性，如果对提交结果不关心的话，推荐使用 apply() ，如果需要确保保存成功之后，才继续进行后续的操作，推荐使用 commit()。
#### 三、从代码中看区别
　　虽然从文档中，完全就可以了解清楚 SP 中，commit() 和 apply() 的具体区别和使用场景。但是作为一个有情怀的码农，还是需要再往深了一层挖挖，一探究竟。  
　　之前提到，SharedPreferences 接口的实现类是SharedPreferencesImpl 。那么就继续看看 apply() 和 commit() 的具体实现。  
###### apply():

```JAVA
361 public void apply() {
362     final MemoryCommitResult mcr = commitToMemory();
363     final Runnable awaitCommit = new Runnable() {
364             public void run() {
365                 try {
366                     mcr.writtenToDiskLatch.await();
367                 } catch (InterruptedException ignored) {
368                 }
369             }
370         };
371 
372     QueuedWork.add(awaitCommit);
373 
374     Runnable postWriteRunnable = new Runnable() {
375             public void run() {
376                 awaitCommit.run();
377                 QueuedWork.remove(awaitCommit);
378             }
379         };
380 
381     SharedPreferencesImpl.this.enqueueDiskWrite(mcr, postWriteRunnable);
382 
383     // Okay to notify the listeners before it's hit disk
384     // because the listeners should always get the same
385     // SharedPreferences instance back, which has the
386     // changes reflected in memory.
387     notifyListeners(mcr);
388 }
```
###### commit()：

```JAVA
456 public boolean commit() {
457     MemoryCommitResult mcr = commitToMemory();
458     SharedPreferencesImpl.this.enqueueDiskWrite(
459         mcr, null /* sync write on this thread okay */);
460     try {
461         mcr.writtenToDiskLatch.await();
462     } catch (InterruptedException e) {
463         return false;
464     }
465     notifyListeners(mcr);
466     return mcr.writeToDiskResult;
467 }
```

对比发现 commit() 的实现非常的简单，并且在 SP 中，是通过 enqueueDiskWrite() 方法来控制是否是异步操作的。

下面看看 enqueueDiskWrite() 方法的实现。

```JAVA
494 /**
495  * Enqueue an already-committed-to-memory result to be written
496  * to disk.
497  *
498  * They will be written to disk one-at-a-time in the order
499  * that they're enqueued.
500  *
501  * @param postWriteRunnable if non-null, we're being called
502  *   from apply() and this is the runnable to run after
503  *   the write proceeds.  if null (from a regular commit()),
504  *   then we're allowed to do this disk write on the main
505  *   thread (which in addition to reducing allocations and
506  *   creating a background thread, this has the advantage that
507  *   we catch them in userdebug StrictMode reports to convert
508  *   them where possible to apply() ...)
509  */
510 private void enqueueDiskWrite(final MemoryCommitResult mcr,
511                               final Runnable postWriteRunnable) {
512     final Runnable writeToDiskRunnable = new Runnable() {
513             public void run() {
514                 synchronized (mWritingToDiskLock) {
515                     writeToFile(mcr);
516                 }
517                 synchronized (SharedPreferencesImpl.this) {
518                     mDiskWritesInFlight--;
519                 }
520                 if (postWriteRunnable != null) {
521                     postWriteRunnable.run();
522                 }
523             }
524         };
525 
526     final boolean isFromSyncCommit = (postWriteRunnable == null);
527 
528     // Typical #commit() path with fewer allocations, doing a write on
529     // the current thread.
530     if (isFromSyncCommit) {
531         boolean wasEmpty = false;
532         synchronized (SharedPreferencesImpl.this) {
533             wasEmpty = mDiskWritesInFlight == 1;
534         }
535         if (wasEmpty) {
536             writeToDiskRunnable.run();
537             return;
538         }
539     }
540 
541     QueuedWork.singleThreadExecutor().execute(writeToDiskRunnable);
542 }
```
从注释里可以看到，如果 enqueueDiskWrite() 的第二个参数为 null 的话，则会变成同步操作。而正是因为在 commit() 中是同步操作，commit() 才可以拿到操作是否正确的结果。 
 
具体将数据持久化到硬盘上的操作，是调用了 writeToFile() 方法，无非就是一些对文件读写的操作和 XML 的处理，这个就不再这里继续探讨了，有兴趣的可以自己看看源码。  

### 四、从效率上看问题
从 SP 提供的接口上看，get 操作应该只是去获取，这个就像从一个单例的对象中，获取一个数据一样，从效率上看应该是不存在什么损耗的。那么从存储的角度，去分析一下效率的问题。  
这个先上结论，再来分析一下问题。写了一个简单的 demo:  

```JAVA
// A 操作
long start = System.currentTimeMillis();
for (int i = 0; i < 100; i++) {
    SharedPreferences sp = getSharedPreferences("test", 0);
    SharedPreferences.Editor spe = sp.edit();
    spe.putString("spA" + i, "spA" + i);
    spe.commit();
}
long end = System.currentTimeMillis();
Log.e("sp", "A:" + (end - start));

// B 操作
long start2 = System.currentTimeMillis();
SharedPreferences spa = getSharedPreferences("test", 0);
SharedPreferences.Editor edit = spa.edit();
for (int i = 0; i < 100; i++) {
    edit.putString("spB" + i, "spB" + i);
}
edit.commit();
long end2 = System.currentTimeMillis();
Log.e("sp", "B:" + (end2 - start2));
```    
A 操作和 B 操作，在代码逻辑上应该是一样的，都是想 SP 中写入100 次不同字段的数据，区别只是在于，A操作每次都去获取新的 Editor ，而 B 操作是只使用一个 Eidtor 去存储。两个操作都分别执行两次。

```JAVA
08-08 11:54:11.209 25923-25923/sing.sp E/sp: A:772
08-08 11:54:11.219 25923-25923/sing.sp E/sp: A:7
08-08 11:54:11.229 25923-25923/sing.sp E/sp: B:12
08-08 11:54:11.229 25923-25923/sing.sp E/sp: B:2
```
可以看出来，使用 commit() 的方式，如果每次都使用 sp.edit() 方法获取一个新的 Editor 的话，新建和修改的执行效率差了非常的大。也就是说，存储一个从来没有用过的 Key ，和修改一个已经存在的 Key，在效率上是有差别的。  
然后把之前的例子中， commit() 修改成 apply() ，这里就不贴代码了。再来看看执行结果，当然在运行前需要先清空数据。这里把 A 操作和 B 操作分别执行了 4 次。

```JAVA
08-08 13:53:24.716 6552-6552/sing.sp E/sp: A:29
08-08 13:53:24.776 6552-6552/sing.sp E/sp: A:59
08-08 13:53:24.846 6552-6552/sing.sp E/sp: A:72
08-08 13:53:24.916 6552-6552/sing.sp E/sp: A:69
08-08 13:53:24.916 6552-6552/sing.sp E/sp: B:6
08-08 13:53:24.926 6552-6552/sing.sp E/sp: B:3
08-08 13:53:24.926 6552-6552/sing.sp E/sp: B:3
08-08 13:53:24.926 6552-6552/sing.sp E/sp: B:4
```

从执行结果可以发现，使用 apply() 因为是异步操作，基本上是不耗费时间的，效率上都是 OK 的。从这个结论上来看，apply() 影响效率的地方，在 sp.edit() 方法。  
  
那么，再看看 edit() 方法是如何实现的：

```JAVA
275 public Editor edit() {
276     // TODO: remove the need to call awaitLoadedLocked() when
277     // requesting an editor.  will require some work on the
278     // Editor, but then we should be able to do:
279     //
280     //      context.getSharedPreferences(..).edit().putString(..).apply()
281     //
282     // ... all without blocking.
283     synchronized (this) {
284         awaitLoadedLocked();
285     }
286 
287     return new EditorImpl();
288 }
```
可以看出来，在 edit() 中是有 synchronized 这个同步锁来保证线程安全的，纵观 EditorImpl 的实现，可以看到大部分操作都是有同步锁的，但是只锁了 (this) ，也就是只对当前对象有效，而 edit() 方法是每次都会去重新 new 一个 EditorImpl() 这个Eidtor 接口的实现类。所以效率就应该是被这里影响到了。
### 四、结论
* edit() 是有效率影响的，所以不要在循环中去调用此方法，最好将 edit() 方法获取的 Editor 对象方在循环之外，在循环中共用同一个 Editor() 对象进行操作。
* commit() 的时候，「new-key」和「update-key」的效率是有差别的，但是有返回结果。
* apply() 是异步操作，对效率的影响，基本上是 ms 级的，可以忽略不记。
