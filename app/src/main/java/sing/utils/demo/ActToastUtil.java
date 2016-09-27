package sing.utils.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import sing.util.ToastUtil;
import sing.utils.R;

/**
 * @className   ActToastUtil
 * @time        2016/9/26 10:37
 * @author      LiangYx
 * @description ToastUtil demo
 */
public class ActToastUtil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_toast);
    }

    public void toastShortInt(View v){
        ToastUtil.showShort(R.string.test_toast_short);
    }
    public void toastShortString(View v){
        ToastUtil.showShort("test toast short");
    }
    public void toastLongInt(View v){
        ToastUtil.showLong(R.string.test_toast_long);
    }
    public void toastLongString(View v){
        ToastUtil.showLong("test toast long");
    }
}