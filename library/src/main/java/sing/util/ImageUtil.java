package sing.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.widget.ScrollView;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * 图片相关
 */
public class ImageUtil {

    /**
     * 获取网落图片资源
     */
    public static Bitmap getHttpBitmap(String url) {
        URL myFileURL;
        Bitmap bitmap = null;
        try {
            myFileURL = new URL(url);
            //获得连接
            HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
            //设置超时时间为6000毫秒，0 表示没有时间限制
            conn.setConnectTimeout(6000);
            conn.setDoInput(true);//连接设置获得数据流
            conn.setUseCaches(false);//不使用缓存
            conn.connect();
            InputStream is = conn.getInputStream();//得到数据流
            bitmap = BitmapFactory.decodeStream(is);//解析得到图片
            is.close();//关闭数据流
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    /**
     * 获取图片文件的真实格式信息
     *
     * @param imgPath 图片原文件存放地址
     * @return 图片格式
     * @throws Exception
     */
    public static String getImageFormat(String imgPath) throws Exception {
        String[] filess = imgPath.split("\\\\");
        String[] formats = filess[filess.length - 1].split("\\.");
        return formats[formats.length - 1];
    }

    /**
     * 保存图片到SD卡
     *
     * @param imagePath
     * @param buffer
     * @throws IOException
     */
    public static void saveImage(String imagePath, byte[] buffer) throws IOException {
        File f = new File(imagePath);
        if (f.exists()) {
            return;
        } else {
            File parentFile = f.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(imagePath);
            fos.write(buffer);
            fos.flush();
            fos.close();
        }
    }

    /**
     * 从SD卡加载图片
     *
     * @param imagePath
     * @return
     */
    public static Bitmap getImageFromLocal(String imagePath) {
        File file = new File(imagePath);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            file.setLastModified(System.currentTimeMillis());
            return bitmap;
        }
        return null;
    }

    /**
     * 获取相应目录中所有图片
     */
    public static ArrayList<Bitmap> getAllimagefromdir(String dir) {
        ArrayList<Bitmap> listbitmap = new ArrayList<>();
        File rFile = new File("/sdcard/cyej/" + dir);
        String[] filelist = rFile.list();
        for (int i = 0; i < filelist.length; i++) {
            File readfile = new File(rFile.getAbsolutePath() + "/" + filelist[i]);
            if (readfile.getName().indexOf(".txt") == -1) {
                FileInputStream fis = null;
                try {
                    fis = new FileInputStream(readfile);
                    BitmapDrawable bd = new BitmapDrawable(fis);
                    Bitmap newBitmap = bd.getBitmap();
                    listbitmap.add(newBitmap);
                } catch (IOException e) {
                    LogUtil.e(e.getMessage());
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return listbitmap;
    }

    /**
     * 将图片转换成圆角图片
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx, int color) {
        if (color == -1) {
            color = 0xff424242;// 默认
        }
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        paint.setColor(color);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new android.graphics.PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return output;
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage   ：源图片资源
     * @return
     */
    public static Bitmap zoomImage(Bitmap bgimage) {
        int newHeight = 0;
        if (bgimage.getWidth() > 460) {
            newHeight = bgimage.getHeight() / bgimage.getWidth() * 460;
        } else {
            return bgimage;
        }
        // 获取这个图片的宽和高
        int width = bgimage.getWidth();
        int height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算缩放率，新尺寸除原始尺寸
        float scaleWidth = ((float) 460) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, width, height, matrix, true);
        return bitmap;
    }

    /**
     * 读取本地图片资源
     */
    public static BitmapDrawable getBitmapDrawable(String imagePath, Context context) {
        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(context.getAssets().open(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapDrawable bitmapdraw = new BitmapDrawable(bmp);
        return bitmapdraw;
    }

    /**
     * 把drawable强制转换成Bitmap
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap
                .createBitmap(
                        drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 将bitmap转换成bytes
     */
    public static byte[] bitmap2Bytes(Bitmap bitmap) {
        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        ByteArrayOutputStream bas = new ByteArrayOutputStream(size);
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bas);
            bas.flush();
            bas.close();
            return bas.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 回收位图所占的空间大小
     */
    public static void recyleBitmapMemory(Bitmap bitmap) {
        if (null != bitmap && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    /**
     * 将彩色图转换为纯黑白二色
     * @param bmp
     * @param w 转化后的宽
     * @param h 转化后的高
     */
    public static Bitmap convertToBlackWhite(Bitmap bmp,int w,int h) {
        int width = bmp.getWidth(); // 获取位图的宽
        int height = bmp.getHeight(); // 获取位图的高
        int[] pixels = new int[width * height]; // 通过位图的大小创建像素点数组

        bmp.getPixels(pixels, 0, width, 0, 0, width, height);
        int alpha = 0xFF << 24;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int grey = pixels[width * i + j];
                //分离三原色
                int red = ((grey & 0x00FF0000) >> 16);
                int green = ((grey & 0x0000FF00) >> 8);
                int blue = (grey & 0x000000FF);
                //转化成灰度像素
                grey = (int) (red * 0.3 + green * 0.59 + blue * 0.11);
                grey = alpha | (grey << 16) | (grey << 8) | grey;
                pixels[width * i + j] = grey;
            }
        }
        //新建图片
        Bitmap newBmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        //设置图片数据
        newBmp.setPixels(pixels, 0, width, 0, 0, width, height);

        Bitmap resizeBmp = ThumbnailUtils.extractThumbnail(newBmp, w, h);
        return resizeBmp;
    }

    /**
     * 截取scrollview的图片
     * **/
    public static Bitmap getScrollViewBitmap(Activity context, ScrollView scrollView,String imagePath) {
        int h = 0;
        Bitmap bitmap;
        LogUtil.e(scrollView.getChildCount()+"");
        // 获取listView实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h, Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);
        // 测试输出
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(imagePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            if (null != out) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
        }
        return bitmap;
    }

    /**
     * 获取http图片的 InputStream
     * @param path
     * @return
     * @throws Exception
     */
    public static InputStream getImageStream(String path) throws Exception{
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            return conn.getInputStream();
        }
        return null;
    }

    /**
     * 保存文件
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static void saveImage(Bitmap bm, String fileName,String imagePath) throws IOException {
        File dirFile = new File(imagePath);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }

        File myCaptureFile = new File(dirFile + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }

    /**
     * 读取图片属性：旋转的角度
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /*
     * 旋转图片
     * @param angle
     * @param bitmap
     * @return Bitmap
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 获取正确角度的图片（防自动旋转）
     * @param imagePath 图片路径
     * @param size 缩略图压缩大小，-1为默认值(6)，
     * @param dirPath 新生成的图片储存目录
     * @return
     */
    public static String getRightAngleImage(String imagePath,int size,String dirPath){
        /** 获取图片的旋转角度，有些系统把拍照的图片旋转了，有的没有旋转 */
        int degree = readPictureDegree(imagePath);// 旋转角度
        LogUtil.e("degree",degree+"");

        BitmapFactory.Options opts = new BitmapFactory.Options();//获取缩略图显示到屏幕上
        if (size == -1){
            size = 6;
        }
        opts.inSampleSize = size;

        Bitmap cbitmap = BitmapFactory.decodeFile(imagePath,opts);
        /** 把图片旋转为正的方向 */
        Bitmap newbitmap = rotaingImageView(degree, cbitmap);

        try {
            File dirFile = new File(dirPath);
            if(!dirFile.exists()){
                dirFile.mkdir();
            }

            File myCaptureFile = new File(imagePath);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            newbitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imagePath;
    }
}