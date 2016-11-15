#ToastUtil
##说明
必须在使用前初始化，否则会奔溃，最好写在Application的onCreate中
```JAVA
ToastUtil.init(Context)
```
##API
######分别为长弹、短弹
```JAVA
ToastUtil.showShort(String)  

ToastUtil.showShort(int)  

ToastUtil.showLong(String)  

ToastUtil.showLong(int)
```