# SelectorUitl
## 介绍
采用Builder模式，支持纯色、渐变、图片、按下等状态，圆角、部分圆角，默认提供几个常用的方法，可根据自己的需求自己扩展
## API
###### 获取颜色背景，没有点击状态(自己保证参数的正确性)

```JAVA
// context,边框颜色，填充颜色
SelectorUitl.getBgNoPress(Activity context,String strokeColor,String fillColor)
```
###### 获取颜色背景，有点击状态(自己保证参数的正确性)

```JAVA
// context,边框颜色，填充颜色,按下的填充颜色
SelectorUitl.getBgPress(Activity context,String strokeColor,String fillColor, String fillColorPress)
SelectorUitl.getBgPress(Activity context,String strokeColor,String fillColor, String strokeColorPress,String fillColorPress)
```
###### 获取图片背景，有点击状态(自己保证参数的正确性)

```JAVA
// context,默认图片，按下图片
SelectorUitl.getImagePress(Activity context,int drawble,int drawblePress)
SelectorUitl.getHttpImagePress(Activity context,Drawable drawble,Drawable drawblePress)
```
###### 获取四个圆角不同的无点击背景颜色(自己保证参数的正确性)

```JAVA
// context，边框颜色，填充颜色，四周的圆角数组
SelectorUitl.getBgNoPressRadius(Activity context,String strokeColor,String fillColor,float[] radius)
SelectorUitl.getBgPressRadius(Activity context,String strokeColor,String fillColor,String fillColorPress，float[] radius)
```
###### 获取四个圆角不同的有点击背景颜色(自己保证参数的正确性)

```JAVA
// context，边框颜色，填充颜色，按下填充的颜色，四周的圆角数组
SelectorUitl.getBgPressRadius(Activity context,String strokeColor,String fillColor,String fillColorPress，float[] radius)
SelectorUitl.getBgPressRadius(Activity context,String strokeColor,String fillColor,String strokeColorPress,String fillColorPress，float[] radius)
``` 
## DEMO
纯色无点击状态的圆角背景

```JAVA
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
```
图片选择器

```JAVA
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
```
所以颜色选择器和图片选择器一样的，通过getBgNoPress()来获取到颜色的Drawable，在用相同的方法即可，代码比较多，建议写成方法，已默认提供

```JAVA
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
            .addState(STATE_UN_PRESSED,bg1.getColor())//默认颜色
            .addState(STATE_PRESSED,bg2.getColor())//按下颜色
            .build();
    return a.getSelectorImage();
}
```