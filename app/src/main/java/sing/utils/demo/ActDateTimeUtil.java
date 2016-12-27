package sing.utils.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import sing.util.DateTimeUtil;
import sing.utils.BaseActivity;
import sing.utils.R;

/**
 * @className   ActDateTimeUtil
 * @time        2016/9/26 10:31
 * @author      LiangYx
 * @description 日期时间选择工具类demo
 */
public class ActDateTimeUtil extends BaseActivity {

    private TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_date_time);
        textView = (TextView) findViewById(R.id.time_tv);
    }

    /**
     * 设置时间
     * @param v
     */
    public void chooseTime(View v){
        DateTimeUtil.showTime(this, new DateTimeUtil.DataCallBack() {
            @Override
            public void getData(String date) {
                textView.setText(date);
            }
        },-1, -1);// 默认小时、分钟，-1为当前时间
    }

    /**
     * 设置设置日期
     * @param v
     */
    public void chooseDate(View v){
        String str = textView.getText().toString().trim();
        int year = -1;
        int month = -1;
        int day = -1;
        if (str.length() == 10){
            year = Integer.parseInt(str.substring(0,4));
            month = Integer.parseInt(str.substring(5,7));
            day = Integer.parseInt(str.substring(8,10));
        }
        DateTimeUtil.showDate(this, new DateTimeUtil.DataCallBack() {
            @Override
            public void getData(String date) {
                textView.setText(date);
            }
        },year,month,day);// 默认年、月、日,-1为当前日期
    }

    /**
     * 设置日期+时间
     * @param v
     */
    public void chooseTimeDate(View v){
        DateTimeUtil.showDialogPicker(this, new DateTimeUtil.DataCallBack() {
            @Override
            public void getData(String date) {
                textView.setText(date);
            }
        },-1, -1,-1,-1,-1);// 默认小时、分钟、年、月、日
    }
}