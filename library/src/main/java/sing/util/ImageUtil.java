package sing.util;

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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author: LiangYX
 * @ClassName: ImageUtil
 * @date: 2016/11/11 下午4:30
 * @Description: 图片相关
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
     * @param newWidth  ：缩放后宽度
     * @param newHeight ：缩放后高度
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
}