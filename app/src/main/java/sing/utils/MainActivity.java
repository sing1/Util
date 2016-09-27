package sing.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import sing.utils.demo.ActAnimationUtil;
import sing.utils.demo.ActDateTimeUtil;
import sing.utils.demo.ActLogUtil;
import sing.utils.demo.ActSharedPreferencesUtil;
import sing.utils.demo.ActToastUtil;

public class MainActivity extends AppCompatActivity {

    private Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_main);
    }

    /**
     * 日期时间选择
     * @param v
     */
    public void dateTimeUtil(View v){
        startActivity(new Intent(context,ActDateTimeUtil.class));
    }

    /**
     * Toast
     * @param v
     */
    public void toastUtil(View v){
        startActivity(new Intent(context,ActToastUtil.class));
    }
    /**
     * logUtil
     * @param v
     */
    public void logUtil(View v){
        startActivity(new Intent(context, ActLogUtil.class));
    }
    /**
     * sharedPreferencesUtil
     * @param v
     */
    public void sharedPreferencesUtil(View v){
        startActivity(new Intent(context, ActSharedPreferencesUtil.class));
    }

    /**
     * AnimationUtil
     * @param v
     */
    public void animationUtil(View v){
        startActivity(new Intent(context, ActAnimationUtil.class));
    }


}