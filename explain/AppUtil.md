# AppUtil  
  
## API
###### 判断Android系统API的版本
```JAVA
AppUtil.getAPIVersion()
```
###### 获取包信息
```JAVA
AppUtil.getPackageInfo(Context)
```
###### 获取应用程序名称
```JAVA
AppUtil.getAppName(Context)
```
###### 获取应用程序版本名称
```JAVA
AppUtil.getVersionName(Context)
```
###### 获取应用程序版本号
```JAVA
AppUtil.getVersionCode(Context)
```
###### 得到包名
```JAVA
AppUtil.getPackageName(Context)
```
###### 获取运行的进程列表
```JAVA
AppUtil.getRunningAppProcesses(Context)
```
###### 根据进程名返回应用程序
```JAVA
AppUtil.getApplicationInfo(Context,String) // processName
```
###### kill进程
```JAVA
AppUtil.killProcesses(Context,int,String) // pid，processName
```
###### 根据进程名获取CPU和内存信息
```JAVA
AppUtil.getMemInfo(String) // processName
```
###### 根据进程ID获取CPU和内存信息
```JAVA
AppUtil.getMemInfo(int) //pid
```
###### 执行命令
```JAVA
AppUtil.runCommand(String[],String) //command,workdirectory
```
###### 运行脚本
```JAVA
AppUtil.runScript(String ) //script
```
###### 应用程序是否获取Root权限
```JAVA
AppUtil.getRootPermission(Context)
```
###### 获取进程运行的信息.
```JAVA
AppUtil.getProcessRunningInfo()
```
###### 获取可用内存.
```JAVA
AppUtil.getAvailMemory(Context)
```
###### 获取总内存.
```JAVA
AppUtil.getTotalMemory(Context)
```
###### 打开并安装文件
```JAVA
AppUtil.installApk(Context,File)
```
###### 卸载程序
```JAVA
AppUtil.uninstallApk(Context,String) //packageName
```
###### 用来判断服务是否运行
```JAVA
AppUtil.isServiceRunning(Context,String) //className
```
###### 停止服务.
```JAVA
AppUtil.stopRunningService(Context,String) //className
```
###### 获取内核数
```JAVA
AppUtil.getNumCores()
```