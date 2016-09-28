# Util_Log
 
 ![](./app/src/main/res/mipmap-xhdpi/ic_log1.png "")
 ![](./app/src/main/res/mipmap-xhdpi/ic_log2.png "")  
## gradle:
```xml
dependencies {
    ...
    compile 'sing.util:library:1.0.3'
}
```  
##First
You should do this in your Application, otherwise he will be invalid.
```Java
public class MyApplication extends Application {

　　@Override
　　public void onCreate() {
　　　　super.onCreate();
    
　　　　LogUtil.init(true,"LogUtils");//isDebug?,your tag
　　}
}
```
## sample 
```JAVA 
LogUtil.i("log.i()");
LogUtil.d("log.d()");
LogUtil.v("log.v()");
LogUtil.w("log.w()");
LogUtil.e("log.e()");
LogUtil.i("自定义TAG","log.i()");
LogUtil.d("自定义TAG","log.d()");
LogUtil.v("自定义TAG","log.v()");
LogUtil.w("自定义TAG","log.w()");
LogUtil.e("自定义TAG","log.e()"); 
```
