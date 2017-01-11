package sing.utils.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import sing.util.MatchUtil;
import sing.util.ToastUtil;
import sing.utils.BaseActivity;
import sing.utils.R;

public class ActMatchUtil extends BaseActivity{

    private Spinner spinner;
    private EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_match_util);

        init();
    }

    private void init() {
        spinner = (Spinner) findViewById(R.id.spinner);
        editText = (EditText) findViewById(R.id.editText);
    }

    public void check(View v){
        int position = spinner.getSelectedItemPosition();
        String text = editText.getText().toString().trim();
        boolean bool = false;
        if (position == 0){ // 判断字符串是否为数字
            bool = MatchUtil.isNumeric(text);
        }else if (position == 1){// 判断字符串是否为日期格式
            bool = MatchUtil.isDate(text);
        }else if (position == 2){// 判断是否为IP
            bool = MatchUtil.isIp(text);
        }else if (position == 3){// 判断是否为邮箱
            bool = MatchUtil.isEmail(text);
        }else if (position == 4){ // 判断是否为汉字
            bool = MatchUtil.isChinese(text);
        }else if (position == 5){// 判断是否为车牌
            bool = MatchUtil.isCarId(text);
        }else if (position == 6){// 判断是否为邮政编码
            bool = MatchUtil.isPostCode(text);
        }else if (position == 7){
            bool = MatchUtil.isIDCard(text);
        }
        ToastUtil.showShort(bool+"");
    }
}