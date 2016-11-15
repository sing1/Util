#ImageUtil
  
##API
######获取网落图片资源
```JAVA
ImageUtil.getHttpBitmap(String) // url
```
######获取图片文件的格式
```JAVA
ImageUtil.getImageFormat(String)throws Exception //imgPath
```
######保存图片到SD卡
```JAVA
ImageUtil.saveImage(String,byte[])throws IOException //imagePath,buffer
```
######从SD卡加载图片
```JAVA
ImageUtil.getImageFromLocal(String) //imagePath
```
######获取相应目录中所有图片
```JAVA
ImageUtil.getAllimagefromdir(String) //dir
```
######将图片转换成圆角图片
```JAVA
ImageUtil.getRoundedCornerBitmap(Bitmap,float,int){//bitmap,roundPx,color
```
######图片的缩放方法
```JAVA
ImageUtil.zoomImage(Bitmap){
```
######读取本地图片资源
```JAVA
ImageUtil.getBitmapDrawable(String,Context) //imagePath
```
######把drawable强制转换成Bitmap
```JAVA
ImageUtil.drawableToBitmap(Drawable){
```
######将bitmap转换成bytes
```JAVA
ImageUtil.bitmap2Bytes(Bitmap){
```
######回收位图所占的空间大小
```JAVA
ImageUtil.recyleBitmapMemory(Bitmap){
```