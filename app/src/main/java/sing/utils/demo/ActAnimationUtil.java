package sing.utils.demo;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

import sing.util.AnimationUtil;
import sing.utils.BaseActivity;
import sing.utils.R;

public class ActAnimationUtil extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_animation);

    }

    /**
     * 旋转
     */
    public void rotate(View v) {
        RotateAnimation animation = AnimationUtil.getRotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, 1000,null);
        findViewById(R.id.rotate).startAnimation(animation);
    }

    /**
     * 渐变
     */
    public void alpha(View v) {
        AlphaAnimation animation = AnimationUtil.getAlphaAnimation(1.0f, 0.0f,1000,null);
        findViewById(R.id.alpha).startAnimation(animation);
    }

    /**
     * 缩小
     */
    public void lessenScale(View v) {
        ScaleAnimation animation = AnimationUtil.getLessenScaleAnimation(1000,null);
        findViewById(R.id.lessenScale).startAnimation(animation);
    }

    /**
     * 移动
     */
    public void translate(View v) {
        AnimationUtil.translate(findViewById(R.id.translate),0,100,0,100,1,1000,false);
    }

    /**
     * 摇晃
     */
    public void shake(View v) {
        AnimationUtil.shake(findViewById(R.id.shake), 0.0f, 10.0f, 5, 1000, true);
    }
}