package sing.util;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * 需自己保证参数的正确性！！！
 */
public class SelectorUtil {

    public static final int[] STATE_ACCELERATED = new int[]{android.R.attr.state_accelerated};
    public static final int[] STATE_UN_ACCELERATED = new int[]{-android.R.attr.state_accelerated};
    public static final int[] STATE_ACTIVATED = new int[]{android.R.attr.state_activated};
    public static final int[] STATE_UN_ACTIVATED = new int[]{-android.R.attr.state_activated};
    public static final int[] STATE_ACTIVE = new int[]{android.R.attr.state_active};
    public static final int[] STATE_UN_ACTIVE = new int[]{-android.R.attr.state_active};
    public static final int[] STATE_CHECKABLE = new int[]{android.R.attr.state_checkable};
    public static final int[] STATE_UN_CHECKABLE = new int[]{-android.R.attr.state_checkable};
    public static final int[] STATE_CHECKED = new int[]{android.R.attr.state_checked};
    public static final int[] STATE_UN_CHECKED = new int[]{-android.R.attr.state_checked};
    public static final int[] STATE_DRAG_CAN_ACCEPT = new int[]{android.R.attr.state_drag_can_accept};
    public static final int[] STATE_UN_DRAG_CAN_ACCEPT = new int[]{-android.R.attr.state_drag_can_accept};
    public static final int[] STATE_DRAG_HOVERED = new int[]{android.R.attr.state_drag_hovered};
    public static final int[] STATE_UN_DRAG_HOVERED = new int[]{-android.R.attr.state_drag_hovered};
    public static final int[] STATE_ENABLED = new int[]{android.R.attr.state_enabled};
    public static final int[] STATE_UN_ENABLED = new int[]{-android.R.attr.state_enabled};
    public static final int[] STATE_FIRST = new int[]{android.R.attr.state_first};
    public static final int[] STATE_UN_FIRST = new int[]{-android.R.attr.state_first};
    public static final int[] STATE_FOCUSED = new int[]{android.R.attr.state_focused};
    public static final int[] STATE_UN_FOCUSED = new int[]{-android.R.attr.state_focused};
    public static final int[] STATE_HOVERED = new int[]{android.R.attr.state_hovered};
    public static final int[] STATE_UN_HOVERED = new int[]{-android.R.attr.state_hovered};
    public static final int[] STATE_LAST = new int[]{android.R.attr.state_last};
    public static final int[] STATE_UN_LAST = new int[]{-android.R.attr.state_last};
    public static final int[] STATE_MIDDLE = new int[]{android.R.attr.state_middle};
    public static final int[] STATE_UN_MIDDLE = new int[]{-android.R.attr.state_middle};
    public static final int[] STATE_PRESSED = new int[]{android.R.attr.state_pressed};
    public static final int[] STATE_UN_PRESSED = new int[]{-android.R.attr.state_pressed};
    public static final int[] STATE_SELECTED = new int[]{android.R.attr.state_selected};
    public static final int[] STATE_UN_SELECTED = new int[]{-android.R.attr.state_selected};
    public static final int[] STATE_SINGLE = new int[]{android.R.attr.state_single};
    public static final int[] STATE_UN_SINGLE = new int[]{-android.R.attr.state_single};
    public static final int[] STATE_WINDOW_FOCUSED = new int[]{android.R.attr.state_window_focused};
    public static final int[] STATE_UN_WINDOW_FOCUSED = new int[]{-android.R.attr.state_window_focused};

    public static class Builder {

        private List<int[]> stateList;
        private List<Drawable> drawableList;

        private Activity context;

        public Builder(Activity context) {
            if (stateList == null) {
                stateList = new ArrayList<>();
            }
            if (drawableList == null) {
                drawableList = new ArrayList<>();
            }
            this.context = context;
        }

        private int type;//0 shape ,1 select image ,2 select color
        public Builder setType(int type) {
            this.type = type;
            return this;
        }

        //////////////// Shape画圆
        int strokeWidth = 5; // 3dp 边框宽度

        public Builder setStrokeWidth(int strokeWidth) {
            this.strokeWidth = strokeWidth;
            return this;
        }

        int roundRadius = -1; // 8dp 圆角半径
        public Builder setRoundRadius(int roundRadius) {
            this.roundRadius = roundRadius;
            return this;
        }
//        new float[] {
//                    topLeftRadius, topLeftRadius,
//                    topRightRadius, topRightRadius,
//                    bottomRightRadius, bottomRightRadius,
//                    bottomLeftRadius, bottomLeftRadius
//        }
        float[] cornerRadii; // 圆角半径
        public Builder setCornerRadii(float[] cornerRadii) {
            this.cornerRadii = cornerRadii;
            return this;
        }

        int strokeColor = Color.parseColor("#2E3135");//边框颜色
        public Builder setStrokeColor(String color) {
            this.strokeColor = Color.parseColor(color);
            return this;
        }

        int fillColor = Color.parseColor("#DFDFE0");//内部填充颜色
        public Builder setFillColor(String color) {
            this.fillColor = Color.parseColor(color);
            return this;
        }

        boolean isGradient = false;//是否渐变
        public Builder setIsGradient(boolean isGradient) {
            this.isGradient = isGradient;
            return this;
        }

        int colors[];//分别为开始颜色，中间夜色，结束颜色
        public Builder setColors(int startColor, int centerColor, int endColor) {
            colors = new int[]{startColor, centerColor, endColor};
            return this;
        }

        GradientDrawable.Orientation orientation;// 渐变方向
        public Builder setOrientation(GradientDrawable.Orientation orientation) {
            this.orientation = orientation;
            return this;
        }

        ///////////////// selector 状态
        public Builder addState(int[] state, Drawable drawable) {
            stateList.add(state);
            drawableList.add(drawable);
            return this;
        }

        public SelectorUtil build() { // 构建，返回一个新对象
            return new SelectorUtil(this);
        }
    }

    private SelectorUtil(Builder b) {
        if (b.type == 0) {
            createColor(b);
        } else if (b.type == 1) {
            createSelectorImage(b);
        } else if (b.type == 2) {
            createSelectorColor(b);
        }
    }

    // 颜色背景
    private void createColor(Builder b) {
        if (b.isGradient && b.colors.length == 3) {
            gd = new GradientDrawable(b.orientation, b.colors);
        } else {
            gd = new GradientDrawable();//创建drawable
        }
        gd.setColor(b.fillColor);
        if (b.roundRadius != -1) {
            gd.setCornerRadius(b.roundRadius);
        } else {
            gd.setCornerRadii(b.cornerRadii);
        }
        gd.setStroke(b.strokeWidth, b.strokeColor);
    }

    // 图片按钮
    private void createSelectorImage(Builder b) {
        drawable = new StateListDrawable();
        List<int[]> stateList = b.stateList;
        List<Drawable> drawableList = b.drawableList;
        for (int i = 0; i < stateList.size(); i++) {
            int a = stateList.size();
            drawable.addState(stateList.get(i), drawableList.get(i));
        }
    }

    // 颜色按钮
    private void createSelectorColor(Builder b) {
        drawable = new StateListDrawable();
        List<int[]> stateList = b.stateList;
        List<Drawable> drawableList = b.drawableList;
        for (int i = 0; i < stateList.size(); i++) {
            drawable.addState(stateList.get(i), drawableList.get(i));
        }
    }

    StateListDrawable drawable;
    public StateListDrawable getSelectorImage() {
        return drawable;
    }

    GradientDrawable gd;
    public GradientDrawable getColor() {
        return gd;
    }

    ///////////////////////////////////////////////////////////////
    /**
     * 获取颜色背景，没有点击状态(自己保证参数的正确性)
     */
    public static GradientDrawable getBgNoPress(Activity context,String strokeColor,String fillColor) {
        SelectorUtil a = new SelectorUtil.Builder(context)
                .setType(0)
                .setStrokeWidth(1)
                .setRoundRadius(8)
                .setStrokeColor(strokeColor)
                .setFillColor(fillColor)
                .setIsGradient(false)
                .build();
        return a.getColor();
    }

    /**
     * 获取颜色按钮，有点击状态(自己保证参数的正确性)
     */
    public static StateListDrawable getBgPress(Activity context,String strokeColor,String fillColor, String fillColorPress) {
        return getBgPress(context,strokeColor,fillColor,strokeColor,fillColorPress);//点击状态下边框线一样的颜色
    }
    public static StateListDrawable getBgPress(Activity context,String strokeColor,String fillColor,String strokeColorPress,String fillColorPress) {
        SelectorUtil bg1 = new SelectorUtil.Builder(context)
                .setType(0)
                .setStrokeWidth(1)
                .setRoundRadius(8)
                .setStrokeColor(strokeColor)
                .setFillColor(fillColor)
                .setIsGradient(false)
                .build();
        SelectorUtil bg2 = new SelectorUtil.Builder(context)
                .setType(0)
                .setStrokeWidth(1)
                .setRoundRadius(8)
                .setStrokeColor(strokeColorPress)
                .setFillColor(fillColorPress)
                .setIsGradient(false)
                .build();
        SelectorUtil a = new SelectorUtil.Builder(context)
                .setType(2)
                .addState(STATE_UN_PRESSED,bg1.getColor())
                .addState(STATE_PRESSED,bg2.getColor())
                .build();
        return a.getSelectorImage();
    }

    /**
     * 获取图片按钮，有点击状态(自己保证参数的正确性)
     */
    public static StateListDrawable getImagePress(Activity context,int drawble,int drawblePress) {
        SelectorUtil a = new SelectorUtil.Builder(context)
                .setType(1)
                .addState(STATE_UN_PRESSED,context.getResources().getDrawable(drawble))
                .addState(STATE_PRESSED,context.getResources().getDrawable(drawblePress))
                .addState(STATE_UN_SELECTED,context.getResources().getDrawable(drawble))
                .addState(STATE_SELECTED,context.getResources().getDrawable(drawblePress))
                .addState(STATE_UN_CHECKED,context.getResources().getDrawable(drawble))
                .addState(STATE_CHECKED,context.getResources().getDrawable(drawblePress))
                .build();
        return a.getSelectorImage();
    }
    public static StateListDrawable getHttpImagePress(Activity context,Drawable drawble,Drawable drawblePress) {
        SelectorUtil a = new SelectorUtil.Builder(context)
                .setType(1)
                .addState(STATE_UN_PRESSED,drawble)
                .addState(STATE_PRESSED,drawblePress)
                .addState(STATE_UN_SELECTED,drawble)
                .addState(STATE_SELECTED,drawblePress)
                .addState(STATE_UN_CHECKED,drawble)
                .addState(STATE_CHECKED,drawblePress)
                .build();
        return a.getSelectorImage();
    }

    /**
     * 获取四个圆角不同的无点击背景颜色(自己保证参数的正确性)
     */
    public static GradientDrawable getBgNoPressRadius(Activity context,String strokeColor,String fillColor,float[] radius) {
        SelectorUtil a = new SelectorUtil.Builder(context)
                .setType(0)
                .setStrokeWidth(1)
                .setCornerRadii(radius)
                .setStrokeColor(strokeColor)
                .setFillColor(fillColor)
                .setIsGradient(false)
                .build();
        return a.getColor();
    }

    /**
     * 获取四个圆角不同的有点击背景颜色(自己保证参数的正确性)
     */
    public static StateListDrawable getBgPressRadius(Activity context,String strokeColor,String fillColor ,String fillColorPress,float[] radius) {
        return getBgPressRadius(context,strokeColor,fillColor,strokeColor,fillColorPress,radius);
    }
    public static StateListDrawable getBgPressRadius(Activity context,String strokeColor,String fillColor,String strokeColorPress,String fillColorPress,float[] radius) {
        SelectorUtil bg1 = new SelectorUtil.Builder(context)
                .setType(0)
                .setStrokeWidth(1)
                .setCornerRadii(radius)
                .setStrokeColor(strokeColor)
                .setFillColor(fillColor)
                .setIsGradient(false)
                .build();
        SelectorUtil bg2 = new SelectorUtil.Builder(context)
                .setType(0)
                .setStrokeWidth(1)
                .setCornerRadii(radius)
                .setStrokeColor(strokeColorPress)
                .setFillColor(fillColorPress)
                .setIsGradient(false)
                .build();
        SelectorUtil a = new SelectorUtil.Builder(context)
                .setType(2)
                .addState(STATE_UN_PRESSED,bg1.getColor())
                .addState(STATE_PRESSED,bg2.getColor())
                .build();
        return a.getSelectorImage();
    }
}