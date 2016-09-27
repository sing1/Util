package sing.util;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * @className   AnimationUtils
 * @time        2016/9/26 16:52
 * @author      LiangYx
 * @description 动画工具类
 */
public final class AnimationUtil {

    /**
     * 获取一个旋转动画
     *
     * @param fromDegrees       开始角度
     * @param toDegrees         结束角度
     * @param pivotXType        旋转中心点X轴坐标相对类型
     * @param pivotXValue       旋转中心点X轴坐标
     * @param pivotYType        旋转中心点Y轴坐标相对类型
     * @param pivotYValue       旋转中心点Y轴坐标
     * @param duration          持续时间
     * @param animationListener 动画监听器
     * @return 一个旋转动画
     */
    public static RotateAnimation getRotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue, long duration, AnimationListener animationListener) {
        RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
        rotateAnimation.setDuration(duration);
        if (animationListener != null) {
            rotateAnimation.setAnimationListener(animationListener);
        }
        return rotateAnimation;
    }

    /**
     * 获取一个根据视图自身中心点旋转的动画
     *
     * @param duration          动画持续时间
     * @param animationListener 动画监听器
     * @return 一个根据中心点旋转的动画
     */
    public static RotateAnimation getRotateAnimationByCenter(long duration, AnimationListener animationListener) {
        return getRotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, duration, animationListener);
    }

    /**
     * 获取一个透明度渐变动画
     *
     * @param fromAlpha         开始时的透明度
     * @param toAlpha           结束时的透明度都
     * @param duration          持续时间
     * @param animationListener 动画监听器
     * @return 一个透明度渐变动画
     */
    public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha, long duration, AnimationListener animationListener) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
        alphaAnimation.setDuration(duration);
        if (animationListener != null) {
            alphaAnimation.setAnimationListener(animationListener);
        }
        return alphaAnimation;
    }

    /**
     * 获取一个由完全显示变为不可见的透明度渐变动画
     *
     * @param duration          持续时间
     * @param animationListener 动画监听器
     * @return 一个由完全显示变为不可见的透明度渐变动画
     */
    public static AlphaAnimation getHiddenAlphaAnimation(long duration, AnimationListener animationListener) {
        return getAlphaAnimation(1.0f, 0.0f, duration, animationListener);
    }

    /**
     * 获取一个由不可见变为完全显示的透明度渐变动画
     *
     * @param duration          持续时间
     * @param animationListener 动画监听器
     * @return 一个由不可见变为完全显示的透明度渐变动画
     */
    public static AlphaAnimation getShowAlphaAnimation(long duration, AnimationListener animationListener) {
        return getAlphaAnimation(0.0f, 1.0f, duration, animationListener);
    }

    /**
     * 获取一个缩小动画
     *
     * @param duration
     * @param animationListener
     */
    public static ScaleAnimation getLessenScaleAnimation(long duration, AnimationListener animationListener) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, ScaleAnimation.RELATIVE_TO_SELF, ScaleAnimation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setAnimationListener(animationListener);
        return scaleAnimation;
    }

    /**
     * 获取一个放大动画
     *
     * @param duration
     * @param animationListener
     */
    public static ScaleAnimation getAmplificationAnimation(long duration, AnimationListener animationListener) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, ScaleAnimation.RELATIVE_TO_SELF, ScaleAnimation.RELATIVE_TO_SELF);
        scaleAnimation.setDuration(duration);
        scaleAnimation.setAnimationListener(animationListener);
        return scaleAnimation;
    }

    /**
     * 将给定视图渐渐隐去（view.setVisibility(View.INVISIBLE)）
     *
     * @param view              被处理的视图
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param duration          持续时间，毫秒
     * @param animationListener 动画监听器
     */
    public static void invisibleViewByAlpha(final View view, long duration, final boolean isBanClick, final AnimationListener animationListener) {
        if (view.getVisibility() != View.INVISIBLE) {
            view.setVisibility(View.INVISIBLE);
            AlphaAnimation hiddenAlphaAnimation = getHiddenAlphaAnimation(duration, null);
            hiddenAlphaAnimation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (isBanClick) {
                        view.setClickable(false);
                    }
                    if (animationListener != null) {
                        animationListener.onAnimationStart(animation);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    if (animationListener != null) {
                        animationListener.onAnimationRepeat(animation);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (isBanClick) {
                        view.setClickable(true);
                    }
                    if (animationListener != null) {
                        animationListener.onAnimationEnd(animation);
                    }
                }
            });
            view.startAnimation(hiddenAlphaAnimation);
        }
    }

    /**
     * 将给定视图渐渐隐去最后从界面中移除（view.setVisibility(View.GONE)）
     *
     * @param view              被处理的视图
     * @param duration          持续时间，毫秒
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     */
    public static void goneViewByAlpha(final View view, long duration, final boolean isBanClick, final AnimationListener animationListener) {
        if (view.getVisibility() != View.GONE) {
            view.setVisibility(View.GONE);
            AlphaAnimation hiddenAlphaAnimation = getHiddenAlphaAnimation(duration, null);
            hiddenAlphaAnimation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (isBanClick) {
                        view.setClickable(false);
                    }
                    if (animationListener != null) {
                        animationListener.onAnimationStart(animation);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    if (animationListener != null) {
                        animationListener.onAnimationRepeat(animation);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (isBanClick) {
                        view.setClickable(true);
                    }
                    if (animationListener != null) {
                        animationListener.onAnimationEnd(animation);
                    }
                }
            });
            view.startAnimation(hiddenAlphaAnimation);
        }
    }

    /**
     * 将给定视图渐渐显示出来（view.setVisibility(View.VISIBLE)）
     *
     * @param view              被处理的视图
     * @param duration          持续时间，毫秒
     * @param isBanClick        在执行动画的过程中是否禁止点击
     * @param animationListener 动画监听器
     */
    public static void visibleViewByAlpha(final View view, long duration, final boolean isBanClick, final AnimationListener animationListener) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
            AlphaAnimation showAlphaAnimation = getShowAlphaAnimation(duration, null);
            showAlphaAnimation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    if (isBanClick) {
                        view.setClickable(false);
                    }
                    if (animationListener != null) {
                        animationListener.onAnimationStart(animation);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    if (animationListener != null) {
                        animationListener.onAnimationRepeat(animation);
                    }
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (isBanClick) {
                        view.setClickable(true);
                    }
                    if (animationListener != null) {
                        animationListener.onAnimationEnd(animation);
                    }
                }
            });
            view.startAnimation(showAlphaAnimation);
        }
    }

    /**
     * 视图移动
     *
     * @param view       要移动的视图
     * @param fromXDelta X轴开始坐标
     * @param toXDelta   X轴结束坐标
     * @param fromYDelta Y轴开始坐标
     * @param toYDelta   Y轴结束坐标
     * @param cycles     重复
     * @param duration   持续时间
     * @param isBanClick 在执行动画的过程中是否禁止点击
     */
    public static void translate(final View view, float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, float cycles, long duration, final boolean isBanClick) {
        TranslateAnimation translateAnimation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
        translateAnimation.setDuration(duration);
        if (cycles > 0.0) {
            translateAnimation.setInterpolator(new CycleInterpolator(cycles));
        }
        translateAnimation.setAnimationListener(new AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                if (isBanClick) {
                    view.setClickable(false);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (isBanClick) {
                    view.setClickable(true);
                }
            }
        });
        view.startAnimation(translateAnimation);
    }

    /**
     * 视图摇晃
     *
     * @param view       要摇动的视图
     * @param fromXDelta X轴开始坐标(0.0f)
     * @param toXDelta   X轴结束坐标(10.0f)
     * @param cycles     重复次数
     * @param duration   持续时间
     * @param isBanClick 在执行动画的过程中是否禁止点击
     */
    public static void shake(View view, float fromXDelta, float toXDelta, float cycles, long duration, final boolean isBanClick) {
        translate(view, fromXDelta, toXDelta, 0.0f, 0.0f, cycles, duration, isBanClick);
    }
}