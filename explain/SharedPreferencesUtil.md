#SharedPreferencesUtil
##说明
这是默认的保存文件的文件名
```JAVA
private static String FILE_NAME = "share_data";
```
必须在使用前初始化，否则会奔溃，最好写在Application的onCreate中
```JAVA
SharedPreferencesUtil.init(Context, String)//自定义的文件名，传""表示使用默认文件名
```
##API
######保存，object的类型支持String、Integer、Boolean、Float、Long、Object
```JAVA
SharedPreferencesUtil.put(String, Object)
```
######取值，需要强转成需要的值
```JAVA
SharedPreferencesUtil.get(String, Object) // key,默认值
```
######移除某个key值已经对应的值
```JAVA
SharedPreferencesUtil.remove(String key) // return true:移除成功
```
######清除所有数据
```JAVA
SharedPreferencesUtil.clear() // return true:移除成功
```
######查询某个key是否已经存在
```JAVA
public static boolean contains(String key) 
```
######返回所有的键值对
```JAVA
public static Map<String, ?> getAll() 
```