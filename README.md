在根目录添加 dependencies.gradle 文件
![](http://upload-images.jianshu.io/upload_images/7115680-f40db664565d80e7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
然后在 dependencies.gradle 中配置我们原本在 build.gradle 中依赖的内容  
![](http://upload-images.jianshu.io/upload_images/7115680-d14a427ccc2035ff.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
在 build.gradle 读取配置文件的内容（apply from:"dependencies.gradle"）
￼
![](http://upload-images.jianshu.io/upload_images/7115680-d0f42f083ada97fe.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

最后修改一下 app 目录 中 build.gradle 文件的内容

![](http://upload-images.jianshu.io/upload_images/7115680-21aea9fa893fb957.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
