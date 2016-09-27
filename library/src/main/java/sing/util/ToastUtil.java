package sing.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

public class ToastUtil {

    private static Context context;
    public static void init(Context context){
        ToastUtil.context = context;
    }

    private static Handler handler = new Handler(Looper.getMainLooper());
    private static Toast toast = null;
    private static Object synObj = new Object();

    /**
     * short Toast
     */
    public static void showShort(String msg) {
        showMessage(msg, Toast.LENGTH_SHORT);
    }

    /**
     * short Toast
     */
    public static void showShort(int msg) {
        showMessage(msg, Toast.LENGTH_SHORT);
    }

    /**
     * long Toast
     */
    public static void showLong(String msg) {
        showMessage(msg, Toast.LENGTH_LONG);
    }

    /**
     * long Toast
     */
    public static void showLong(int msg) {
        showMessage(msg, Toast.LENGTH_LONG);
    }

    /**
     * Send to Toast
     */
    private static void showMessage(String msg, int len) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, msg, len);
        toast.show();
    }

    /**
     * Send to Toast
     */
    private static void showMessage(int msg, int len) {
        if (toast != null) {
            toast.cancel();
        }
        toast = Toast.makeText(context, msg, len);
        toast.show();
    }
}