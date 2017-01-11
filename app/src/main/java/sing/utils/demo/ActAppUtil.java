package sing.utils.demo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.TextView;

import java.util.List;

import sing.util.AppUtil;
import sing.util.bean.AppProcessInfo;
import sing.utils.BaseActivity;
import sing.utils.R;

public class ActAppUtil extends BaseActivity{

    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_app_util);

        init();
    }

    private void init() {
        tv = (TextView) findViewById(R.id.tv);
        tv.append("演示部分API：\n\n");
        tv.append("系统API的版本:" + AppUtil.getAPIVersion() + "\n");
        tv.append("应用程序名称:" + AppUtil.getAppName(this) + "\n");
        tv.append("应用程序版本名称:" + AppUtil.getVersionName(this) + "\n");
        tv.append("应用程序版本号:" + AppUtil.getVersionCode(this) + "\n");
        tv.append("应用程序包名:" + AppUtil.getPackageName(this) + "\n");
        tv.append("内核数:" + AppUtil.getNumCores() + "\n");
        tv.append("应用程序是否获取Root权限:" + AppUtil.getRootPermission(this) + "\n");
        tv.append("获取可用内存:" + AppUtil.getAvailMemory(this) + "\n");
        tv.append("获取总内存:" + AppUtil.getTotalMemory(this) + "\n"+ "\n");
        tv.append("获取运行的进程列表:\n");
        tv.append("      进程获取中...\n");

        new Thread(new Runnable() {
            @Override
            public void run() {
                list = AppUtil.getRunningAppProcesses(ActAppUtil.this);
                handler.sendEmptyMessage(1);
            }
        }){}.start();
    }

    List<AppProcessInfo> list;
    Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            if (list == null){return;}
            tv.append("      进程获取完毕\n");
            for (int i = 0;i<list.size();i++){
                tv.append((i+1)+"、"
                        + "appName:"+list.get(i).appName+";" + "\n"
                        + "      pid:" + list.get(i).pid+";" + "\n"
                        + "      uid:" + list.get(i).uid + ";" + "\n"
                        + "      占用内存:" + list.get(i).memory + ";" + "\n"
                        + "      占用的cpu:" + list.get(i).cpu + ";" + "\n"
                        + "      当前使用的线程数:" + list.get(i).threadsCount + ";" + "\n"
                        + "      是否是系统进程:" + list.get(i).isSystem + ";" + "\n" );
            }
        }
    };
}