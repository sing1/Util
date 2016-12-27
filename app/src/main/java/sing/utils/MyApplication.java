package sing.utils;

import android.app.Application;

import sing.util.LogUtil;
import sing.util.SharedPreferencesUtil;
import sing.util.ToastUtil;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferencesUtil.init(this,"share_data");
        LogUtil.init(true,"LogUtils");
        ToastUtil.init(this);
    }
}