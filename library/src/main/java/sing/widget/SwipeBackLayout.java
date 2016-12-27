package sing.widget;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

import sing.util.R;

public class SwipeBackLayout extends FrameLayout {

    private Activity activity;
    private Scroller mScroller;
    /** 上次ACTION_MOVE时的X坐标 */
    private int mLastMotionX;
    /** 屏幕宽度 */
    private int mWidth;
    /** 可滑动的最小X坐标，小于该坐标的滑动不处理 */
    private int mMinX;
    /** 页面边缘的阴影图 */
    private Drawable mLeftShadow;
    /** 页面边缘阴影的宽度默认值 */
    private static final int SHADOW_WIDTH = 16;
    /** 页面边缘阴影的宽度 */
    private int mShadowWidth;
    /** Activity finish标识符 */
    private boolean mIsFinish;

    public SwipeBackLayout(Activity activity) {
        this(activity, null);
    }

    public SwipeBackLayout(Activity activity, AttributeSet attrs) {
        this(activity, attrs, 0);
    }

    public SwipeBackLayout(Activity activity, AttributeSet attrs, int defStyleAttr) {
        super(activity, attrs, defStyleAttr);
        initView(activity);
    }

    private void initView(Activity activity) {
        this.activity = activity;
        mScroller = new Scroller(activity);
        mLeftShadow = getResources().getDrawable(R.drawable.shadow_left);
        int density = (int) activity.getResources().getDisplayMetrics().density;
        mShadowWidth = SHADOW_WIDTH * density;
    }

    /** 绑定Activity */
    public void bindActivity(Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View child = decorView.getChildAt(0);
        decorView.removeView(child);
        addView(child);
        decorView.addView(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:// 主要是记录了屏幕的宽度
                mLastMotionX = (int) event.getX();
                mWidth = getWidth();
                mMinX = mWidth / 10;
                break;
            case MotionEvent.ACTION_MOVE:
                // 分两种情况，①view的x坐标为0，即初始状态，这时只能向右滑动，禁止向左滑动。
                // ②view的坐标大于0，即view的一部分已经划出屏幕（当然是向右滑）。这时，如果继续向右滑则不用多考虑；
                // 如果向左滑，就要假设view向左滑动了x后，如果view左边缘还在屏幕内，则可以继续滑动，否则，view左边
                // 缘可能已经滑出屏幕，这是我们不想看到的，因此我们直接把view滑动到(0,0)位置。
                int rightMovedX = mLastMotionX - (int) event.getX();
                if (getScrollX() + rightMovedX >= 0) {// 左侧即将滑出屏幕
                    scrollTo(0, 0);
                } else if ((int) event.getX() > mMinX) {// 手指处于屏幕边缘时不处理滑动
                    scrollBy(rightMovedX, 0);
                }
                mLastMotionX = (int) event.getX();
                break;
            case MotionEvent.ACTION_UP:
                if (-getScrollX() < mWidth / 2) {//手指释放后，如果滑动距离超过屏幕的一半，就关闭Activity
                    scrollBack();
                    mIsFinish = false;
                } else {// 否则，恢复原来状态。
                    scrollClose();
                    mIsFinish = true;
                }
                break;
        }
        return true;
    }

    /** 滑动返回 */
    private void scrollBack() {
        int startX = getScrollX();
        int dx = -getScrollX();
        mScroller.startScroll(startX, 0, dx, 0, 300);
        invalidate();
    }

    /** 滑动关闭 */
    private void scrollClose() {
        int startX = getScrollX();
        int dx = -getScrollX() - mWidth;
        mScroller.startScroll(startX, 0, dx, 0, 300);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), 0);
            postInvalidate();
        } else if (mIsFinish) {
            activity.finish();
        }
        super.computeScroll();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        drawShadow(canvas);
    }

    /** 绘制边缘的阴影,页面滑出屏幕后左侧添加阴影区域，增加层次感 */
    private void drawShadow(Canvas canvas) {
        canvas.save();// 保存画布当前的状态
        mLeftShadow.setBounds(0, 0, mShadowWidth, getHeight());// 设置drawable的大小范围
        canvas.translate(-mShadowWidth, 0);// 让画布平移一定距离
        mLeftShadow.draw(canvas); // 绘制Drawable
        canvas.restore(); // 恢复画布的状态
    }
}