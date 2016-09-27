package sing.utils.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import sing.util.LogUtil;
import sing.util.ToastUtil;
import sing.utils.R;

/**
 * @className   ActLogUtil
 * @time        2016/9/26 13:27
 * @author      LiangYx
 * @description LogUtils demo
 */
public class ActLogUtil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_log);
    }

    public void logi(View v){
        LogUtil.i("log.i()");
    }
    public void logd(View v){
        LogUtil.d("log.d()");
    }
    public void logv(View v){
        LogUtil.v("log.v()");
    }
    public void logw(View v){
        LogUtil.w("log.w()");
    }
    public void loge(View v){
        LogUtil.e("log.e()");
    }
    public void tagLogi(View v){
        LogUtil.i("自定义TAG","log.i()");
    }
    public void tagLogd(View v){
        LogUtil.d("自定义TAG","log.d()");
    }
    public void tagLogv(View v){
        LogUtil.v("自定义TAG","log.v()");
    }
    public void tagLogw(View v){
        LogUtil.w("自定义TAG","log.w()");
    }
    public void tagLoge(View v){
        LogUtil.e("自定义TAG","log.e()");
    }
}