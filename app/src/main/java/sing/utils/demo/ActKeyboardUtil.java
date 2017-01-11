package sing.utils.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import sing.util.KeyboardUtil;
import sing.utils.BaseActivity;
import sing.utils.R;


public class ActKeyboardUtil extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_keyboard_util);
    }

    public void showSoftInput(View v) {
        KeyboardUtil.hideInputMethod(ActKeyboardUtil.this,false);
    }

    public void closeSoftInput(View v) {
        KeyboardUtil.hideInputMethod(ActKeyboardUtil.this,true);
    }
}