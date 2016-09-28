# Util_Animation
 
 ![](.././app/src/main/res/mipmap-xhdpi/ic_animation.gif "") 
## gradle:
```xml
dependencies {
    ...
    compile 'sing.util:library:1.0.3'
}
```
## sample 
```JAVA
/**
 * Rotate
 */
public void rotate(View v) {
　　RotateAnimation animation = AnimationUtil.getRotateAnimation(0f, 359f, 
            Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f, 1000,null);
　　findViewById(R.id.rotate).startAnimation(animation);
}

/**
 * Alpha
 */
public void alpha(View v) {
　　AlphaAnimation animation = AnimationUtil.getAlphaAnimation(1.0f, 0.0f,1000,null);
　　findViewById(R.id.alpha).startAnimation(animation);
}

/**
 * LessenScale
 */
public void lessenScale(View v) {
　　ScaleAnimation animation = AnimationUtil.getLessenScaleAnimation(1000,null);
　　findViewById(R.id.lessenScale).startAnimation(animation);
}

/**
 * Translate
 */
public void translate(View v) {
　　AnimationUtil.translate(findViewById(R.id.translate),0,100,0,100,1,1000,false);
}

/**
 * Shake
 */
public void shake(View v) {
　　AnimationUtil.shake(findViewById(R.id.shake), 0.0f, 10.0f, 5, 1000, true);
}
```
####more...
