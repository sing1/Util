package sing.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import sing.widget.SwipeBackLayout;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new SwipeBackLayout(this).bindActivity(this);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
        overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }
}