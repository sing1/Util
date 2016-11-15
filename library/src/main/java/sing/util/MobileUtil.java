package sing.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.UUID;

import static android.R.attr.name;

/**
 * 手机信息
 */
public class MobileUtil {

    // 获取IP（ipv4）地址
    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()) {    //!inetAddress.isLinkLocalAddress() Android4.0以上排除IPv6地址
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            LogUtil.e("WifiPreference IpAddress:" + ex.toString());
        }
        return null;
    }

    // 获取MAC地址  <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    public static String getLocalMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    // 获取手机移动运营商  <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
    public static String getProvidersName(Context context) {
        String ProvidersName = null;
        String IMSI = ((TelephonyManager) context.getSystemService("phone")).getSubscriberId();
        // IMSI号前面3位460是国家，紧接着后面2位00 02 07是中国移动，01是中国联通，03是中国电信。
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
            ProvidersName = "中国移动";
        } else if (IMSI.startsWith("46001")) {
            ProvidersName = "中国联通";
        } else if (IMSI.startsWith("46003")) {
            ProvidersName = "中国电信";
        }
        return ProvidersName;
    }

    // 获取手机接入点名称
    public static String getApnType(Context context) {
        Uri PREFERRED_APN_URI = Uri.parse("content://telephony/carriers/preferapn");
        String apntype = "nomatch";
        try {
            Cursor c = context.getContentResolver().query(PREFERRED_APN_URI, null, null, null, null);
            c.moveToFirst();
            return c.getString(c.getColumnIndex("apn"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return apntype;
    }

    // 获得Android手机UA信息
    public static String getUserAgentString(Context context) {
        WebView webview;
        webview = new WebView(context);
        webview.layout(0, 0, 0, 0);
        WebSettings settings = webview.getSettings();
        String ua = settings.getUserAgentString();
        LogUtil.e("UA", ua);
        return ua;
    }

    // Wifi状态切换    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    public static void toggleWiFi(Context context) {
        WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wm.setWifiEnabled(!wm.isWifiEnabled()); //设置为false时，点击关闭
    }

    // 通过反射切换移动数据 注意相关权限添加
    public static void switchMobileData(Context context) {
        Object[] arg = null;
        try {
            boolean isMobileDataEnable = invokeMethod(context, "getMobileDataEnabled", arg);
            if (!isMobileDataEnable) {
                invokeBooleanArgMethod(context, true); //开启移动数据
            } else {
                invokeBooleanArgMethod(context, false); //关闭移动数据
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取移动数据状态
    public static boolean invokeMethod(Context context, String methodName, Object[] arg) throws Exception {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class ownerClass = mConnectivityManager.getClass();
        Class[] argsClass = null;
        if (arg != null) {
            argsClass = new Class[1];
            argsClass[0] = arg.getClass();
        }
        Method method = ownerClass.getMethod(methodName, argsClass);
        Boolean isOpen = (Boolean) method.invoke(mConnectivityManager, arg);
        return isOpen;
    }

    // 开启、关闭移动数据
    public static Object invokeBooleanArgMethod(Context context, boolean value) throws Exception {
        String methodName = "setMobileDataEnabled";
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        Class ownerClass = mConnectivityManager.getClass();
        Class[] argsClass = new Class[1];
        argsClass[0] = boolean.class;
        Method method = ownerClass.getMethod(methodName, argsClass);
        return method.invoke(mConnectivityManager, value);
    }

    // 判断手机是否飞行模式
    public static boolean getAirplaneMode(Context context) {
        int isAirplaneMode = Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0);
        return (isAirplaneMode == 1) ? true : false;
    }

    // 设置手机飞行模式,rue:设置为飞行模式 false:取消飞行模式
    public static void setAirplaneModeOn(Context context, boolean enabling) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, enabling ? 1 : 0);
        Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        intent.putExtra("state", enabling);
        context.sendBroadcast(intent);
    }

    // 设置亮度
    public static void setBrightness(Activity activity, int brightness) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.screenBrightness = Float.valueOf(brightness) * (1f / 255f);
        LogUtil.e("set  lp.screenBrightness == " + lp.screenBrightness);
        activity.getWindow().setAttributes(lp);
        //save
        ContentResolver resolver = activity.getContentResolver();

        Uri uri = android.provider.Settings.System.getUriFor("screen_brightness");
        android.provider.Settings.System.putInt(resolver, "screen_brightness", brightness);
        resolver.notifyChange(uri, null);
    }

    // 获取当前亮度
    public static int getScreenBrightness(Activity activity) {
        int nowBrightnessValue = 0;
        ContentResolver resolver = activity.getContentResolver();
        try {
            nowBrightnessValue = android.provider.Settings.System.getInt(resolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowBrightnessValue;
    }

    // 停止自动亮度调节
    public static void stopAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
    }

    // 开启亮度自动调节
    public static void startAutoBrightness(Activity activity) {
        Settings.System.putInt(activity.getContentResolver(),
                Settings.System.SCREEN_BRIGHTNESS_MODE,
                Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
    }

    // 判断是否开启了自动亮度调节
    public static boolean isAutoBrightness(ContentResolver aContentResolver) {
        boolean automicBrightness = false;
        try {
            automicBrightness = Settings.System.getInt(aContentResolver, Settings.System.SCREEN_BRIGHTNESS_MODE) == Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return automicBrightness;
    }

    // 获取手机唯一值，不受重装、卸载等影响
    public static String getMyUUID(Activity context) {
        final TelephonyManager tm = (TelephonyManager) context.getBaseContext().getSystemService(context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        LogUtil.e("uuid = " + uniqueId);
        return uniqueId;
    }

    // Gps是否打开 <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);//位置管理
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}