package sing.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ImageView;

import sing.util.R;

public class RoundImageView extends ImageView {
    private Paint pressPaint;
    private Path borderPath;
    private int width;
    private int height;

    private int pressAlpha;// 按下的透明度
    private int pressColor;// 按下的颜色
    private float radius;// 弧度
    private float leftTopRadius;// 弧度
    private float leftBottomRadius;// 弧度
    private float rightTopRadius;// 弧度
    private float rightBottomRadius;// 弧度
    private float[] radiis;
    private int shapeType;// 类型,0为圆形，1为圆角矩形，2为不规则圆角(即4个圆角弧度不一样)
    private float borderWidth;// 边框宽度
    private int borderColor;// 边框颜色

    public RoundImageView(Context context) {
        this(context,null);
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        //初始化默认值
        pressAlpha = 50;
        pressColor = getResources().getColor(android.R.color.black);
        radius = 8;
        leftTopRadius = 8;
        leftBottomRadius = 8;
        rightTopRadius = 8;
        rightBottomRadius = 8;
        shapeType = 1;
        borderWidth = 0;
        borderColor = getResources().getColor(android.R.color.holo_green_light);

        // 获取控件的属性值
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoundImageView);
            pressColor = array.getColor(R.styleable.RoundImageView_press_color, pressColor);
            pressAlpha = array.getInteger(R.styleable.RoundImageView_press_alpha, pressAlpha);
            radius = array.getDimension(R.styleable.RoundImageView_radius, radius);
            leftTopRadius = array.getDimension(R.styleable.RoundImageView_leftTopRadius, leftTopRadius);
            leftBottomRadius = array.getDimension(R.styleable.RoundImageView_leftBottomRadius, leftBottomRadius);
            rightTopRadius = array.getDimension(R.styleable.RoundImageView_rightTopRadius, rightTopRadius);
            rightBottomRadius = array.getDimension(R.styleable.RoundImageView_rightBottomRadius, rightBottomRadius);
            shapeType = array.getInteger(R.styleable.RoundImageView_shape_type, shapeType);
            borderWidth = array.getDimension(R.styleable.RoundImageView_border_width, borderWidth);
            borderColor = array.getColor(R.styleable.RoundImageView_border_color, borderColor);

            if (shapeType == 1){
                radiis = new float[]{radius,radius,radius,radius,radius,radius,radius,radius};
            }else if (shapeType == 2){
                radiis = new float[]{leftTopRadius, leftTopRadius, rightTopRadius, rightTopRadius, rightBottomRadius, rightBottomRadius, leftBottomRadius, leftBottomRadius};
            }
            array.recycle();
        }

        // 按下的画笔设置
        pressPaint = new Paint();
        pressPaint.setAntiAlias(true);
        pressPaint.setStyle(Paint.Style.FILL);
        pressPaint.setColor(pressColor);
        pressPaint.setAlpha(0);
        pressPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        setClickable(true);
        setDrawingCacheEnabled(true);
        setWillNotDraw(false);

        borderPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable drawable = getDrawable();// 获取当前控件的 drawable
        if (drawable == null) {
            return;
        }
        // 这里 get 回来的宽度和高度是当前控件相对应的宽度和高度（在 xml 设置）
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        // 获取 bitmap，即传入 imageview 的 bitmap
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        drawDrawable(canvas, bitmap);
        drawPress(canvas);
        drawBorder(canvas);
    }

    private void drawDrawable(Canvas canvas, Bitmap bitmap) {
        Paint paint = new Paint();// 画笔
        paint.setColor(0xffffffff);// 颜色设置
        paint.setAntiAlias(true);// 抗锯齿
        //Paint 的 Xfermode，PorterDuff.Mode.SRC_IN 取两层图像的交集部门, 只显示上层图像。
        PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        // 标志
        int saveFlags = Canvas.MATRIX_SAVE_FLAG
                | Canvas.CLIP_SAVE_FLAG
                | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
                | Canvas.FULL_COLOR_LAYER_SAVE_FLAG
                | Canvas.CLIP_TO_LAYER_SAVE_FLAG;
        canvas.saveLayer(0, 0, width, height, null, saveFlags);

        if (shapeType == 0) { // 画遮罩，画出来就是一个和空间大小相匹配的圆
            canvas.drawCircle(width / 2, height / 2, width / 2, paint);
        } else if (shapeType == 1){ // 当ShapeType = 1 时 图片为圆角矩形
            RectF rectf = new RectF(0, 0, getWidth(), getHeight());
            canvas.drawRoundRect(rectf, radius, radius, paint);
        }else if (shapeType == 2){// 当ShapeType = 2 时 图片为不规则矩形
            Path roundPath = new Path();
            roundPath.addRoundRect(new RectF(0, 0, width, height),radiis, Path.Direction.CW);
            canvas.drawPath(roundPath, paint);
        }

        paint.setXfermode(xfermode);

        // 空间的大小 / bitmap 的大小 = bitmap 缩放的倍数
        float scaleWidth = ((float) getWidth()) / bitmap.getWidth();
        float scaleHeight = ((float) getHeight()) / bitmap.getHeight();

        //bitmap 缩放
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        canvas.drawBitmap(bitmap, 0, 0, paint);//将bitmap画在canvas上
        canvas.restore();
    }

    // 画按下去的画
    private void drawPress(Canvas canvas) {
        if (shapeType == 0) {
            canvas.drawCircle(width / 2, height / 2, width / 2, pressPaint);
        } else if (shapeType == 1) {
            RectF rectF = new RectF(0, 0, width, height);
            canvas.drawRoundRect(rectF, radius, radius, pressPaint);
        } else if (shapeType == 2) {// 暂时不提供按下效果
//            RectF rectF = new RectF(0, 0, width, height);
//            canvas.drawRoundRect(rectF, radius, radius, pressPaint);
        }
    }

    // 画边框
    private void drawBorder(Canvas canvas){
        if(borderWidth > 0){
            borderPath.reset();
            final int width = getWidth();
            final int height = getHeight();

            if (shapeType == 0) {
                final float halfBorderWidth = borderWidth * 0.5f;
                final float cx = width * 0.5f;
                final float cy = height * 0.5f;
                final float radius = Math.min(width, height) * 0.5f;
                borderPath.addCircle(cx, cy, radius - halfBorderWidth, Path.Direction.CW);
            } else if (shapeType == 1){// 当ShapeType = 1 时 图片为圆角矩形
                final float halfBorderWidth = borderWidth * 0.35f;//乘以0.5会导致border在圆角处不能包裹原图
                RectF rect = new RectF(halfBorderWidth, halfBorderWidth, width - halfBorderWidth, height - halfBorderWidth);
                borderPath.addRoundRect(rect,radiis, Path.Direction.CW);
            } else if (shapeType == 2){// 当ShapeType = 2 时 图片为不规则矩形
                final float halfBorderWidth = borderWidth * 0.35f;//乘以0.5会导致border在圆角处不能包裹原图 
                RectF rect = new RectF(halfBorderWidth, halfBorderWidth, width - halfBorderWidth, height - halfBorderWidth);
                borderPath.addRoundRect(rect,radiis, Path.Direction.CW);
            }

            Paint paint = new Paint();
            paint.setStrokeWidth(borderWidth);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(borderColor);
            paint.setAntiAlias(true);
            canvas.drawPath(borderPath, paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                pressPaint.setAlpha(pressAlpha);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                pressPaint.setAlpha(0);
                invalidate();
                break;
            default:
                invalidate();
                break;
        }
        return super.onTouchEvent(event);
    }
}