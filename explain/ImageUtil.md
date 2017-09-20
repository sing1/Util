# ImageUtil
  
## API
###### 获取网络图片资源
```JAVA
ImageUtil.getHttpBitmap(String) // urlPath
```
###### 获取图片文件的格式
```JAVA
ImageUtil.getImageFormat(String)throws Exception //imgPath
```
###### 保存图片到SD卡
```JAVA
ImageUtil.saveImage(String,byte[])throws IOException //imagePath,buffer
ImageUtil.saveImage(Bitmap,String,String) // bm, fileName, imagePath
```
###### 从SD卡加载图片
```JAVA
ImageUtil.getImageFromLocal(String) //imagePath
```
###### 获取相应目录中所有图片
```JAVA
ImageUtil.getAllimagefromdir(String) //dir
```
###### 将图片转换成圆角图片
```JAVA
ImageUtil.getRoundedCornerBitmap(Bitmap,float,int) //bitmap,roundPx,color
```
###### 图片的缩放方法
```JAVA
ImageUtil.zoomImage(Bitmap)
```
###### 读取本地图片资源
```JAVA
ImageUtil.getBitmapDrawable(String,Context) //imagePath
```
###### 把drawable强制转换成Bitmap
```JAVA
ImageUtil.drawableToBitmap(Drawable)
```
###### 将bitmap转换成bytes
```JAVA
ImageUtil.bitmap2Bytes(Bitmap)
```
###### 回收位图所占的空间大小
```JAVA
ImageUtil.recyleBitmapMemory(Bitmap)
```
###### 将彩色图转换为纯黑白二色
```JAVA
ImageUtil.convertToBlackWhite(Bitmap,int,int) // 转化后的宽高
```
###### 截取scrollview的图片
```JAVA
ImageUtil.getScrollViewBitmap(Context,ScrollView,String) // String为图片存储目录
```
###### 获取http图片的inputStream
```JAVA
ImageUtil.getImageStream(String) // String为图片http路径
```
###### 读取图片的旋转角度
```JAVA
ImageUtil.readPictureDegree(String) // String为图片路径
```
###### 旋转图片
```JAVA
ImageUtil.rotaingImageView(int，Bitmap) // int为图片目前的角度，Bitmap为要旋转的图片
```
###### 获取正确角度的图片（防自动旋转）
```JAVA
ImageUtil.getRightAngleImage(String,int size,String dirPath) // 图片路径，缩略图压缩大小，新生成的图片储存目录
```