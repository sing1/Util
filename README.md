单例模式的写法很多，比较靠谱的的写法如下：

```JAVA
public class Elvis {  
    private static boolean flag = false;  
  
    private Elvis(){  
    }  
  
    private  static class SingletonHolder{  
        private static final Elvis INSTANCE = new Elvis();  
    }  
  
    public static Elvis getInstance() {  
        return SingletonHolder.INSTANCE;  
    }  
}  
```
但前提是不会通过反射调用私有的构造器。若通过反射机制来“攻击”单例模式：
 
```JAVA  
public class ElvisReflectAttack {  
    public static void main(String[] args) throws Exception {  
        Class<?> classType = Elvis.class;  
        Constructor<?> c = classType.getDeclaredConstructor(null);  
        c.setAccessible(true);  
        Elvis e1 = (Elvis)c.newInstance();  
        Elvis e2 = Elvis.getInstance();  
        System.out.println(e1==e2);  
    }
}  
```
运行结果：false，通过反射获取构造函数，然后调用setAccessible(true)就可以调用私有的构造函数，所以e1和e2是两个不同的对象，如果要抵御这种攻击，可以修改构造器，让它在被要求创建第二个实例的时候抛出异常：

```JAVA
public class ElvisModified {  
    private static boolean flag = false;  
  
    private ElvisModified(){  
        synchronized(ElvisModified.class)  {  
            if(flag == false){  
                flag = !flag;  
            } else {  
                throw new RuntimeException("单例模式被侵犯！");  
            }  
        }  
    }  
  
    private  static class SingletonHolder{  
        private static final ElvisModified INSTANCE = new ElvisModified();  
    }  
  
    public static ElvisModified getInstance() {  
        return SingletonHolder.INSTANCE;  
    } 
}  
```
测试代码：
 
```JAVA
public class ElvisModifiedReflectAttack {  
    public static void main(String[] args) {  
        try {  
            Class<ElvisModified> classType = ElvisModified.class;  
            Constructor<ElvisModified> c = classType.getDeclaredConstructor(null);  
            c.setAccessible(true);  
            ElvisModified e1 = (ElvisModified)c.newInstance();  
            ElvisModified e2 = ElvisModified.getInstance();  
            System.out.println(e1==e2);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  
```
运行结果：

```JAVA
Exception in thread "main" java.lang.ExceptionInInitializerError  
    ... 
Caused by: java.lang.RuntimeException: 单例模式被侵犯！  
    at com.effective.singleton.ElvisModified.<init>(ElvisModified.java:16)  
    at com.effective.singleton.ElvisModified.<init>(ElvisModified.java:7)  
    at com.effective.singleton.ElvisModified$SingletonHolder.<clinit>(ElvisModified.java:22)  
    ... 2 more  
```
可以看到，成功的阻止了单例模式被破坏。  
从JDK1.5开始，实现Singleton还有新的写法，只需编写一个包含单个元素的枚举类型。推荐写法：

```JAVA
public enum SingletonClass {  
    INSTANCE;  
  
    public void test() {  
        System.out.println("The Test!");  
    }  
}  
```
测试代码：

```JAVA
public class TestMain  {  
    public static void main(String[] args) throws Exception {  
        Class<SingletonClass> classType = SingletonClass.class;  
        Constructor<SingletonClass> c = (Constructor<SingletonClass>) classType.getDeclaredConstructor();  
        c.setAccessible(true);  
        c.newInstance();  
    }
}
```
运行结果：

```JAVA
Exception in thread "main" java.lang.NoSuchMethodException: com.effective.singleton.SingletonClass.<init>()  
    at java.lang.Class.getConstructor0(Unknown Source)  
    at java.lang.Class.getDeclaredConstructor(Unknown Source)  
```
由此可见这种写法也可以防止单例模式被“攻击”。  
单元素的枚举类型已经成为实现Singleton模式的最佳方法。
