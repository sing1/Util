# FileUtil
  
## API
###### 判断指定路径是否存在，如果不存在，根据参数决定是否新建
```JAVA
FileUtils.isExist(String,boolean) // 指定的文件路径,rue：新建、false：不新建
```
###### 获取文件名，文件构成：当前时间+n位随机数
```JAVA
FileUtils.getFileName(int) //随机位数
```
###### 获取文件的大小
```JAVA
FileUtils.getFileSize(File)throws Exception
```
###### 删除所有文件，包括文件夹
```JAVA
FileUtils.deleteAll(String)
```
###### 复制文件或者文件夹
```JAVA
FileUtils.copy(File,File,boolean)throws IOException // 源文件，目的文件，是否覆盖文件
```
###### 复制单个文件
```JAVA
FileUtils.copySimpleFile(File,File,boolean)throws IOException //源文件，目的文件，是否覆盖
```
###### 获取文件的MD5
```JAVA
FileUtils.getFileMD5(File)
```
###### 获取文件的后缀
```JAVA
FileUtils.getFileSuffix(String)
```
###### 文件重命名
```JAVA
FileUtils.renameDir(String,String) //老文件，新文件
```