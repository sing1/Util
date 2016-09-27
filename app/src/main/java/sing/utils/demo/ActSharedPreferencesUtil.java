package sing.utils.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import sing.util.DateTimeUtil;
import sing.util.SharedPreferencesUtil;
import sing.util.ToastUtil;
import sing.utils.R;

public class ActSharedPreferencesUtil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_shared_preferences);
    }

    public void put(View v){
        SharedPreferencesUtil.put("key","valse");
        ToastUtil.showShort("保存成功");
    }
    public void get(View v){
        ToastUtil.showShort((String)SharedPreferencesUtil.get("key","default"));
    }
    public void clear(View v){
        if (SharedPreferencesUtil.clear()){
            ToastUtil.showShort("清除成功");
        }else{
            ToastUtil.showShort("清除失败");
        }
    }
    public void remove(View v){
        if (SharedPreferencesUtil.remove("key")){
            ToastUtil.showShort("移除成功");
        }else{
            ToastUtil.showShort("移除失败");
        }
    }
}