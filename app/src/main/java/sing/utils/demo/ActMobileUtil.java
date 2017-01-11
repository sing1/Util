package sing.utils.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import sing.util.MobileUtil;
import sing.utils.BaseActivity;
import sing.utils.R;

import static sing.util.MobileUtil.getAirplaneMode;
import static sing.util.MobileUtil.getApnType;
import static sing.util.MobileUtil.getLocalIpAddress;
import static sing.util.MobileUtil.getLocalMacAddress;
import static sing.util.MobileUtil.getMyUUID;
import static sing.util.MobileUtil.getProvidersName;
import static sing.util.MobileUtil.getScreenBrightness;
import static sing.util.MobileUtil.getUserAgentString;
import static sing.util.MobileUtil.isGpsEnabled;
import static sing.util.MobileUtil.startAutoBrightness;
import static sing.util.MobileUtil.stopAutoBrightness;

public class ActMobileUtil extends BaseActivity {

    private TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_mobile_util);

        init();
    }

    private void init() {
        text = (TextView) findViewById(R.id.text);
        text.append("IP（ipv4）地址:" + getLocalIpAddress(this)+"\n");
        text.append("MAC地址:" + getLocalMacAddress(this)+"\n");
        text.append("手机移动运营商:" + getProvidersName(this)+"\n");
        text.append("手机接入点名称:" + getApnType(this)+"\n");
        text.append("手机UA信息:" + getUserAgentString(this) + "\n");
        text.append("获取手机唯一值:" + getMyUUID(this) + "\n");
        text.append("Gps是否打开:" + isGpsEnabled(this) + "\n");
        text.append("是否飞行模式:" + getAirplaneMode(this) + "\n");
        text.append("获取当前亮度:" + getScreenBrightness(this) + "\n");

        ((SeekBar)findViewById(R.id.seek_bar)).setProgress(getScreenBrightness(this));
        ((SeekBar)findViewById(R.id.seek_bar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MobileUtil.setBrightness(ActMobileUtil.this,progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    // Wifi状态切换
    public void changeWifi(View v){
        MobileUtil.toggleWiFi(this);
    }

    // 切换移动数据
    public void changeData(View v){
        MobileUtil.switchMobileData(this);
    }

    public void openLight(View v){
        startAutoBrightness(this);
    }

    public void closeLight(View v){
        stopAutoBrightness(this);
    }
}