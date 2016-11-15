#MobileUtil
##API

###### 获取IP（ipv4）地址
```JAVA
MobileUtil.getLocalIpAddress()
```
###### 获取MAC地址
```JAVA
// <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
MobileUtil.getLocalMacAddress(Context)
```
###### 获取手机移动运营商
```JAVA
// <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
MobileUtil.getProvidersName(Context)
```
######  获取手机接入点名称
```JAVA
MobileUtil.getApnType(Context)
```
######  获得Android手机UA信息
```JAVA
MobileUtil.getUserAgentString(Context)
```
###### Wifi状态切换
```JAVA
// <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
MobileUtil.toggleWiFi(Context)
```
######  通过反射切换移动数据 注意相关权限添加
```JAVA
MobileUtil.switchMobileData(Context)
```
###### 获取移动数据状态
```JAVA
MobileUtil.invokeMethod(Context,String,Object[])throws Exception
```
######  开启、关闭移动数据
```JAVA
MobileUtil.invokeBooleanArgMethod(Context,boolean)throws Exception
```
######  判断手机是否飞行模式
```JAVA
MobileUtil.getAirplaneMode(Context)
```
######  设置手机飞行模式,rue:设置为飞行模式 false:取消飞行模式
```JAVA
MobileUtil.setAirplaneModeOn(Context,boolean)
```
######  设置亮度
```JAVA
MobileUtil.setBrightness(Activity,int)
```
######  获取当前亮度
```JAVA
MobileUtil.getScreenBrightness(Activity)
```
######  停止自动亮度调节
```JAVA
MobileUtil.stopAutoBrightness(Activity)
```
######  开启亮度自动调节
```JAVA
MobileUtil.startAutoBrightness(Activity)
```
######  判断是否开启了自动亮度调节
```JAVA
MobileUtil.isAutoBrightness(ContentResolver)
```
######  获取手机唯一值，不受重装、卸载等影响
```JAVA
MobileUtil.getMyUUID(Activity)
```
######  Gps是否打开
```JAVA
// <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
MobileUtil.isGpsEnabled(Context)
```