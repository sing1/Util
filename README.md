### JAVA序列化
#### 1、什么是序列化和反序列化  
* Serialization（序列化）是一种将对象以一连串的字节描述的过程。
* 反序列化deserialization是一种将这些字节重建成一个对象的过程。  

#### 2、什么情况下需要序列化
* 当你想把的内存中的对象保存到一个文件中或者数据库中时候(数据持久化)；
* 利用序列化实现远程通信，即在网络上传送对象的字节序列；

#### 3、如何实现序列化
* 将需要序列化的类实现Serializable接口就可以了，Serializable接口中没有任何方法，可以理解为一个标记，即表明这个类可以序列化.

#### 4、序列化和反序列化例子
* 如果我们想要序列化一个对象，首先要创建某些OutputStream(如FileOutputStream、ByteArrayOutputStream等)，然后将这些OutputStream封装在一个ObjectOutputStream中。这时候，只需要调用writeObject()方法就可以将对象序列化，并将其发送给OutputStream（对象的序列化是基于字节的，不能使用Reader和Writer等基于字符的层次结构）。而反序列的过程（即将一个序列还原成为一个对象），需要将一个InputStream(如FileInputstream、ByteArrayInputStream等)封装在ObjectInputStream内，然后调用readObject()即可。

```JAVA
package com.serialize;  
  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
import java.io.Serializable;  
  
public class Serialize implements Serializable{  
  
    private static final long serialVersionUID = -5211389707739541364L;  
    public int num = 1390;  
  
    public void serialized(){  
        try {  
            FileOutputStream fos = new FileOutputStream("serialize.obj");  
            ObjectOutputStream oos = new ObjectOutputStream(fos);  
            Serialize serialize = new Serialize();  
            oos.writeObject(serialize);  
            oos.flush();  
            oos.close();//只是为了做个例子，真实编程要放到finally下  
            fos.close();  
            System.out.println("序列化结束");  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    public void deserialized(){  
        Serialize serialize = null;  
        try {  
            FileInputStream fis = new FileInputStream("serialize.obj");  
            ObjectInputStream ois = new ObjectInputStream(fis);  
  
            serialize = (Serialize) ois.readObject();  
            ois.close();  
            fis.close();  
            System.out.println("反序列化结束");  
        } catch (ClassNotFoundException | IOException e) {  
            e.printStackTrace();  
        }  
        System.out.println(serialize.num);  
    }  
  
    public static void main(String[] args) {  
        Serialize serialize = new Serialize();  
        serialize.serialized();  
        serialize.deserialized();  
    }  
}  
```
运行结果：

```JAVA  
序列化结束  
反序列化结束  
1390 
```
#### 5、序列化的数据含有那些信息
这里举个例子将上面例子中的serialize.obj的信息读取出来：

```JAVA
package com.serialize;  
  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;  
import java.io.InputStream;  
  
public class ReadSerialize {  
  
    public static void main(String[] args) {  
        try {  
            File file = new File("serialize.obj");  
            InputStream in = new FileInputStream(file);  
            byte buff[] = new byte[1024];  
            int len = 0;  
            while((len = in.read(buff)) !=-1) {  
                for(int i=0;i<len;i++) {  
                    System.out.printf("%02X ",buff[i]);  
                }  
                System.out.println();  
            }  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}  
```
运行结果 

```JAVA 
AC ED 00 05 73     
72 00 17 63 6F 6D 2E 73 65 72 69 61 6C 69 7A 65 2E 53 65 72 69 61 6C 69 7A 65 B7 AD 6C AC 04 0E D0 8C 02 00 01   
49 00 03 6E 75 6D   
78 70  
00 00 05 6E  
```
##### 第一部分是序列化文件头  
> AC ED：STREAM_MAGIC声明使用了序列化协议  
> 00 05：STREAM_VERSION序列化协议版本  
> 73：TC_OBJECT声明这是一个新的对象  

##### 第二部分是序列化类的描述  
> 72：TC_CLASSDESC声明这里开始一个新class  
> 00 17：class名字的长度是23字节  
> 63 6F 6D 2E 73 65 72 69 61 6C 69 7A 65 2E 53 65 72 69 61 6C 69 7A 65:类名（ASCII码：com.serialize.Serialize）    
B7 AD 6C AC 04 0E D0 8C： SerialVersionUID  
> 02：标记号，改值声明改对象支持序列化  
> 00 01：该类所包含的域的个数为1  

##### 第三部分是对象中各个属性项的描述  
> 49：域类型，代表I,表示Int类型（又如：44，查ASCII码表为D，代表Double类型）  
> 00 03：域名字的长度，为3  
> 6E 75 6D: num属性的名称  

##### 第四部分输出该对象父类信息描述，这里没有父类，如果有，则数据格式与第二部分一样  
> 78：TC_ENDBLOCKDATA，对象块接收标志  
> 70：TC_NULL，说明没有其他超类的标志  

##### 第五部分输出对象的属性的实际值，如果属性项是一个对象，那么这里还将序列化这个对象，规则和第2部分一样。  
> 00 00 05 6E：1390的值  

#### 6、序列化前和序列化后的对象的关系  
序列化时深复制，反序列化还原后的对象地址与原来的不同。 
 
```JAVA
package com.serialize;  
  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.IOException;  
import java.io.ObjectInputStream;  
import java.io.ObjectOutputStream;  
import java.io.Serializable;  
  
public class Serialize implements Serializable{  
  
    private static final long serialVersionUID = -5211389707739541364L;  
    public int num = 1390;  
  
    public void checkIsSame() {  
        Serialize serialize1 = new Serialize();  
        try {  
            FileOutputStream fos = new FileOutputStream("serialize.obj");  
            ObjectOutputStream oos = new ObjectOutputStream(fos);  
            oos.writeObject(serialize1);  
            oos.flush();  
            oos.close();//只是为了做个例子，真实编程要放到finally下  
            fos.close();  
            System.out.println("序列化结束");  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        Serialize serialize2 = null;  
        try {  
            FileInputStream fis = new FileInputStream("serialize.obj");  
            ObjectInputStream ois = new ObjectInputStream(fis);  
  
            serialize2 = (Serialize) ois.readObject();  
            ois.close();  
            fis.close();  
            System.out.println("反序列化结束");  
        } catch (ClassNotFoundException | IOException e){  
            e.printStackTrace();  
        }  
        System.out.println("序列化和反序列化的对象相同否？"+(serialize1==serialize2));  
    }  
  
    public static void main(String[] args) {  
        Serialize serialize = new Serialize();  
        serialize.checkIsSame();  
    }  
}  
```
运行结果： 
 
```JAVA
序列化结束  
反序列化结束  
序列化和反序列化的对象相同否？false  
```
序列化前后对象的地址不同了，但是内容是一样的，而且对象中包含的引用也相同。换句话说，通过序列化操作,我们可以实现对任何可Serializable对象的”深度复制（deep copy）"——这意味着我们复制的是整个对象网，而不仅仅是基本对象及其引用。对于同一流的对象，他们的地址是相同，说明他们是同一个对象，但是与其他流的对象地址却不相同。也就说，只要将对象序列化到单一流中，就可以恢复出与我们写出时一样的对象网，而且只要在同一流中，对象都是同一个。

##### 7、破坏单例模式
序列化和反序列化可能会破坏单例。上面的例子就是个很好的证明，为了更形象，在举一个单例模式的序列化的例子

```JAVA
package com.serialize;  
  
import java.io.Serializable;  
  
public class SerSingleton implements Serializable {  
    private static final long serialVersionUID = 1L;  
  
    String name;  
  
    private SerSingleton(){  
        System.out.println("Singleton is create");  
        name="SerSingleton";  
    }  
  
    private static SerSingleton instance = new SerSingleton();  
  
    public static SerSingleton getInstance() {  
        return instance;  
    }  
  
    public static void createString(){  
        System.out.println("createString in Singleton");  
    }  
}  
  
    @Test  
    public void test() throws IOException, ClassNotFoundException {  
        SerSingleton s1= null;  
        SerSingleton s = SerSingleton.getInstance();  
  
        FileOutputStream fos = new FileOutputStream("SerSingleton.obj");  
        ObjectOutputStream oos = new ObjectOutputStream(fos);  
        oos.writeObject(s);  
        oos.flush();  
        oos.close();  
  
        FileInputStream fis = new FileInputStream("SerSingleton.obj");  
        ObjectInputStream ois = new ObjectInputStream(fis);  
        s1 = (SerSingleton)ois.readObject();  
       System.out.println(s==s1);  
    }  
```
运行结果
  
```JAVA
Singleton is create  
false  
```
说明测试代码中的s和s1指向了不同的实例，在反序列化后，生成多个对象实例。稍微修改一下SerSingleton类，如下所示：

```JAVA
package com.serialize;  
  
import java.io.Serializable;  
  
public class SerSingleton implements Serializable {  
    private static final long serialVersionUID = 1L;  
  
    String name;  
  
    private SerSingleton() {  
        System.out.println("Singleton is create");  
        name="SerSingleton";  
    }  
  
    private static SerSingleton instance = new SerSingleton();  
  
    public static SerSingleton getInstance() {  
        return instance;  
    }  
  
    public static void createString(){  
        System.out.println("createString in Singleton");  
    }  
  
    private Object readResolve(){  
        return instance;  
    }  
}  
```
运行测试代码，结果如下 

```JAVA 
Singleton is create  
true   
```
至于为什么，ObjectIputStream.class的源码中有这样一段话
![](http://upload-images.jianshu.io/upload_images/7115680-24e7fc4b8a040dfb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
 
[^quote]:Deserializing an object via readUnshared invalidates the stream handle associated with the returned object. Note that this in itself does not always guarantee that the reference returned by readUnshared is unique; the deserialized object may define a readResolve method which returns an object visible to other parties, or readUnshared may return a Class object or enum constant obtainable elsewhere in the stream or through external means. If the deserialized object defines a readResolve method and the invocation of that method returns an array, then readUnshared returns a shallow clone of that array; this guarantees that the returned
array object is unique and cannot be obtained a second time from an invocation of readObject or readUnshared on the ObjectInputStream, even if the underlying data stream has been manipulated.

一般来说，对单例进行序列化和反序列化的场景并不多见，但如果存在，就要多加注意。
##### 8、序列化ID
序列化 ID 在 Eclipse 下提供了两种生成策略，一个是固定的 1L，一个是随机生成一个不重复的 long 类型数据（实际上是使用 JDK 工具生成），在这里有一个建议，如果没有特殊需求，就是用默认的 1L 就可以，这样可以确保代码一致时反序列化成功。这也可能是造成序列化和反序列化失败的原因，因为不同的序列化id之间不能进行序列化和反序列化。

##### 9、静态变量能否序列化
序列化会忽略静态变量，即序列化不保存静态变量的状态。静态成员属于类级别的，所以不能序列化。即 序列化的是对象的状态不是类的状态。这里的不能序列化的意思，是序列化信息中不包含这个静态成员域。transient后的变量也不能序列化。
###### transient使用小结
> * 一旦变量被transient修饰，变量将不再是对象持久化的一部分，该变量内容在序列化后无法获得访问。
> * transient关键字只能修饰变量，而不能修饰方法和类。注意，本地变量是不能被transient关键字修饰的。变量如果是用户自定义类变量，则该类需要实现Serializable接口。
> * 被transient关键字修饰的变量不再能被序列化，一个静态变量不管是否被transient修饰，均不能被序列化。

#### 总结
* 当父类继承Serializable接口时，所有子类都可以被序列化。
* 子类实现了Serializable接口，父类没有，父类中的属性不能被序列化（不报错，数据不会丢失），但是在子类中的属性仍能正确序列化
* 如果序列化的属性是对象，则这个对象也必须实现Serializable接口，否则会报错。
* 在反序列化时，如果对象的属性有修改或删减，则修改的部分属性会丢失，但不会报错。
* 在反序列化时，如果serialVersionUID被序列化，则反序列化时会失败
* 当一个对象的实例变量引用其他对象，序列化改对象时，也把引用对象进行序列化
* static,transient后的变量不能被序列化
