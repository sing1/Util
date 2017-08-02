SpannableString和String一样是一种字符串类型，不同的是SpannableString可以通过使用其方法setSpan方法实现字符串各种形式风格的显示,重要的是可以指定设置的区间，也就是为字符串指定下标区间内的子字符串设置格式。
```JAVA
/**
 * @param what 设置的格式(前景色、背景色、点击的文本等等)
 * @param start 需要设置格式的子字符串的起始下标
 * @param end 需要设置格式的子字符串的终了下标
 * @param flags 共有四种属性
 *           Spanned.SPAN_INCLUSIVE_EXCLUSIVE,从起始下标到终了下标，包括起始下标
 *           Spanned.SPAN_INCLUSIVE_INCLUSIVE,从起始下标到终了下标，包括起始下标和终了下标
 *           Spanned.SPAN_EXCLUSIVE_EXCLUSIVE,从起始下标到终了下标，不包括起始下标和终了下标
 *           Spanned.SPAN_EXCLUSIVE_INCLUSIVE,从起始下标到终了下标，包括终了下标
 */
setSpan(Object what, int start, int end, int flags);
```
     
###### 设置前景色
![](http://upload-images.jianshu.io/upload_images/7115680-3ed7759116db7990.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```JAVA
SpannableString str = new SpannableString("设置前景色为淡蓝色");
ForegroundColorSpan color = new ForegroundColorSpan(Color.parseColor("#0099EE"));
str.setSpan(color, 6, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
textView.setText(str);
```
###### 设置背景色
![](http://upload-images.jianshu.io/upload_images/7115680-a1601d835392f452.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```JAVA
SpannableString str = new SpannableString("设置背景色为淡绿色");
BackgroundColorSpan color = new BackgroundColorSpan(Color.parseColor("#AC00FF30"));
str.setSpan(color, 6, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
textView.setText(str);
```
###### 设置文字相对大小
![](http://upload-images.jianshu.io/upload_images/7115680-29100a86482fcb64.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```JAVA
SpannableString str = new SpannableString("逐渐变大");
RelativeSizeSpan size01 = new RelativeSizeSpan(1.2f);
RelativeSizeSpan size02 = new RelativeSizeSpan(1.4f);
RelativeSizeSpan size03 = new RelativeSizeSpan(1.6f);
RelativeSizeSpan size04 = new RelativeSizeSpan(1.8f);
str.setSpan(size01, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
str.setSpan(size02, 1, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
str.setSpan(size03, 2, 3, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
str.setSpan(size04, 3, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
textView.setText(str);
```
###### 为文本设置中划线(删除线)
![](http://upload-images.jianshu.io/upload_images/7115680-443e06f1d9ab4792.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```JAVA
SpannableString str = new SpannableString("为文字设置删除线");
StrikethroughSpan span = new StrikethroughSpan();
str.setSpan(span, 5, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
textView.setText(str);
```
###### 为文本设置下划线
![](http://upload-images.jianshu.io/upload_images/7115680-1ae1e139b941bcda.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```JAVA
SpannableString str = new SpannableString("为文字设置下划线");
UnderlineSpan span = new UnderlineSpan();
str.setSpan(span, 5, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
textView.setText(str);
```
###### 设置上标
![](http://upload-images.jianshu.io/upload_images/7115680-1fd1773d2d69738d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```JAVA
SpannableString str = new SpannableString("为文字设置上标");
SuperscriptSpan span = new SuperscriptSpan();
str.setSpan(span, 5, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
textView.setText(str);
 // 被设置为上标的文字大小和文本文字大小一样，结合RelativeSizeSpan设置小字体文本作为上标
```
###### 设置下标
![](http://upload-images.jianshu.io/upload_images/7115680-4e45e69e547c2d46.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```JAVA
SpannableString str = new SpannableString("为文字设置下标");
SubscriptSpan span = new SubscriptSpan();
str.setSpan(span, 5, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
textView.setText(str);
```
###### 为文字设置风格(粗体、斜体、粗斜体) 
![](http://upload-images.jianshu.io/upload_images/7115680-03b6556d237248dc.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```JAVA
SpannableString str = new SpannableString("为文字设置粗体/斜体/粗斜体风格");
StyleSpan styleSpan_B = new StyleSpan(Typeface.BOLD);
StyleSpan styleSpan_I = new StyleSpan(Typeface.ITALIC);
StyleSpan styleSpan_BI = new StyleSpan(Typeface.BOLD_ITALIC);
str.setSpan(styleSpan_B, 5, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
str.setSpan(styleSpan_I, 8, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
str.setSpan(styleSpan_BI, 11, 14, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
textView.setText(str);
```
###### 设置文本图片
![](http://upload-images.jianshu.io/upload_images/7115680-b70d14566020bf4d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```JAVA
SpannableString str = new SpannableString("在文本中添加表情（表情）");
Drawable drawable = ContextCompat.getDrawable(this,R.mipmap.ic_launcher);
drawable.setBounds(0, 0, 42, 42);
ImageSpan imageSpan = new ImageSpan(drawable);
str.setSpan(imageSpan, 6, 8, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
textView.setText(str);
```
###### 设置可点击的文本
![](http://upload-images.jianshu.io/upload_images/7115680-a38c4af3e9421a8b.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```JAVA
SpannableString str = new SpannableString("为文字设置点击事件");
MyClickableSpan span = new MyClickableSpan("'点击事件'");
str.setSpan(span, 5, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
textView.setMovementMethod(LinkMovementMethod.getInstance());//必须设置setMovementMethod方法，否则没有点击相应
textView.setHighlightColor(Color.parseColor("#36969696"));//控制点击是的背景色
textView.setText(str);
// MyClickableSpan类继承至ClickableSpan
class MyClickableSpan extends ClickableSpan {
    private String content;
    public MyClickableSpan(String content) {
        this.content = content;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);//控制是否让可点击文本显示下划线，false不显示下滑写。
    }

    @Override
    public void onClick(View widget) {
        Toast.makeText(MainActivity.this, "点击了" + content, Toast.LENGTH_SHORT).show();
    }
}
```
###### 设置超链接文本
![](http://upload-images.jianshu.io/upload_images/7115680-aa0f1fdea4268cd4.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```JAVA
// URLSpan就是继承自ClickableSpan，重写onClick点击事件，用系统自带浏览器打开链接
SpannableString str = new SpannableString("为文字设置超链接");
URLSpan span = new URLSpan("http://www.jianshu.com/users/dbae9ac95c78");
str.setSpan(span, 5, str.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
textView.setMovementMethod(LinkMovementMethod.getInstance());
textView.setHighlightColor(Color.parseColor("#36969696"));
textView.setText(str);
```
 ###### 小圆点
![](http://upload-images.jianshu.io/upload_images/7115680-294e709c4a0eeeb6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```JAVA
SpannableString str = new SpannableString("我有小圆点");
BulletSpan bs = new BulletSpan(5, 0xFF123123);
str.setSpan(bs, 0, str.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
textView.setText(str);
```
###### 浮雕效果
![](http://upload-images.jianshu.io/upload_images/7115680-9d32a0dfe6141ba0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
```JAVA
SpannableString str = new SpannableString("浮雕效果");
MaskFilter filter = new BlurMaskFilter(5, BlurMaskFilter.Blur.OUTER );
MaskFilterSpan span = new MaskFilterSpan(filter );
str.setSpan(span, 0, str.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE) ;
textView.setText(str);
```
