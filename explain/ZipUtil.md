#ZipUitl
##API
######批量压缩文件（夹）
```JAVA
// 要压缩的文件（夹）列表，生成的压缩文件
ZipUitl.zipFiles(Collection<File>, File) throws IOException 
```
######批量压缩文件（夹）
```JAVA
// 要压缩的文件（夹）列表，生成的压缩文件，压缩文件的注释
ZipUitl.zipFiles(Collection<File>, File, String) throws IOException 
```
######解压缩一个文件
```JAVA
// 压缩文件，解压缩的目标目录
ZipUitl.upZipFile(File, String) throws ZipException, IOException
```
######解压文件名包含传入文字的文件 
```JAVA
// 压缩文件,目标文件夹,传入的文件匹配名
ZipUitl.upZipSelectedFile(File, String, String) throws ZipException,IOException 
```
######获得压缩文件内文件列表 
```JAVA
// 压缩文件,压缩文件内文件名称
ZipUitl.getEntriesNames(File) throws IOException 
```
######获得压缩文件内压缩文件对象以取得其属性(返回一个压缩文件列表)
```JAVA
// 压缩文件
ZipUitl.getEntriesEnumeration(File) throws IOException 
```
######取得压缩文件对象的注释
```JAVA
// 压缩文件对象
ZipUitl.getEntryComment(ZipEntry) throws UnsupportedEncodingException 
```
######取得压缩文件对象的名称 
```JAVA
// 压缩文件对象
ZipUitl.getEntryName(ZipEntry) throws UnsupportedEncodingException 
```
######压缩文件
```JAVA
// 需要压缩的文件（夹）,压缩的目的文件,压缩的文件路径
ZipUitl.zipFile(File, ZipOutputStream, String) throws IOException 
```