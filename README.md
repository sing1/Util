### 1.描述:  
* Singleton(单例)是设计模式的一种,为了保证一个类仅有一个实例，并提供一个访问它的全局访问点。  

### 2.主要特点:  
* 单例类确保自己只有一个实例(构造函数私有:不被外部实例化,也不被继承)。  
* 单例类必须自己创建自己的实例。  
* 单例类必须为其他对象提供唯一的实例。  

### 3.实现方法:    
> #### 1、饿汉式单例类(依赖jvm在加载类时创建唯一单例实例)
> > 如果应用程序总是创建并使用单例实例或在创建和运行时开销不大。

> ```JAVA
> public class EagerSingleton {    
>     // jvm保证在任何线程访问uniqueInstance静态变量之前一定先创建了此实例    
>     private static EagerSingleton uniqueInstance = new EagerSingleton();    
>     
>     // 私有的默认构造子，保证外界无法直接实例化    
>     private EagerSingleton() {    
>     }    
>     
>     // 提供全局访问点获取唯一的实例    
>     public static EagerSingleton getInstance() {    
>         return uniqueInstance;    
>     }    
> }  
> ```  
> #### 2、懒汉式单例类  
> > 如果开销比较大，希望用到时才创建就要考虑延迟实例化,或者Singleton的初始化需要某些外部资源(比如网络或存储设备)

> ```JAVA
> public class LazySingleton {    
>     private static LazySingleton uniqueInstance;    
>     
>     private LazySingleton() {    
>     }    
>     
>     public static synchronized LazySingleton getInstance() {
>         if (uniqueInstance == null) {
>             uniqueInstance = new LazySingleton();
>         }
>         return uniqueInstance;
>     }
> }   
> ```
> #### 3、"双检锁"(Double-Checked Lock)
> > 尽量将"加锁"推迟,只在需要时"加锁"(仅适用于Java 5.0 以上版本,volatile保证原子操作)   
> > 如果没有volatile修饰符则可能出现一个线程t1的B操作和另一线程t2的C操作之间对instance的读写没有happens-before，可能会造成的现象是t1的B操作还没有完全构造成功，但t2的C已经看到instance为非空，这样t2就直接返回了未完全构造的instance的引用，t2想对instance进行操作就会出问题.  
> ######volatile 的功能:  
> > * 避免编译器将变量缓存在寄存器里    
> > * 避免编译器调整代码执行的顺序  

> > 优化器在用到这个变量时必须每次都小心地重新读取这个变量的值，而不是使用保存在寄存器里的备份。

> ```JAVA
> public class DoubleCheckedLockingSingleton {    
>     // java中使用双重检查锁定机制,由于Java编译器和JIT的优化的原因系统无法保证我们期望的执行次序。    
>     // 在java5.0修改了内存模型,使用volatile声明的变量可以强制屏蔽编译器和JIT的优化工作    
>     private volatile static DoubleCheckedLockingSingleton uniqueInstance;    
>     
>     private DoubleCheckedLockingSingleton() {    
>     }    
>     
>     public static DoubleCheckedLockingSingleton getInstance() {    
>         if (uniqueInstance == null) {    
>             synchronized (DoubleCheckedLockingSingleton.class) {    
>                 if (uniqueInstance == null) {    
>                     uniqueInstance = new DoubleCheckedLockingSingleton();    
>                 }    
>             }    
>         }    
>         return uniqueInstance;    
>     }    
> }  
> ```
> #### 4、Lazy initialization holder class 满足所有 Double-Checked Locking 满足的条件，并且没有显示的同步操作
> 
> ```JAVA
> public class LazyInitHolderSingleton {    
>     private LazyInitHolderSingleton() {    
>     }    
>     
>     private static class SingletonHolder {    
>         private static final LazyInitHolderSingleton INSTANCE = new LazyInitHolderSingleton();    
>     }    
>     
>     public static LazyInitHolderSingleton getInstance() {    
>         return SingletonHolder.INSTANCE;    
>     }    
> }   
> ```
> > 根据jvm规范，当某对象第一次调用LazyInitHolderSingleton.getInstance()时，LazyInitHolderSingleton类被首次主动使用，jvm对其进行初始化（此时并不会调用LazyInitHolderSingleton()构造方法），然后LazyInitHolderSingleton调用getInstance()方法，该方法中，又首次主动使用了SingletonHolder类，所以要对SingletonHolder类进行初始化，初始化中，INSTANCE常量被赋值时才调用了 LazyInitHolderSingleton的构造方法LazyInitHolderSingleton()，完成了实例化并返回该实例。当再有对象（也许是在别的线程中）再次调用LazyInitHolderSingleton.getInstance()时，因为已经初始化过了，不会再进行初始化步骤，所以直接返回INSTANCE常量即同一个LazyInitHolderSingleton实例。
