package sing.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import sing.util.bean.AppProcessInfo;
import sing.util.bean.ProcessInfo;

/**
 * APK相关
 */
public class AppUtil {

    /**
     * 判断Android系统API的版本
     */
    public static int getAPIVersion() {
        int APIVersion;
        try {
            APIVersion = Integer.valueOf(android.os.Build.VERSION.SDK);
        } catch (NumberFormatException e) {
            APIVersion = 0;
        }
        return APIVersion;
    }

    /**
     * 获取包信息
     */
    public static PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            String packageName = context.getPackageName();
            info = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 获取应用程序名称
     */
    public static String getAppName(Context context) {
        int labelRes = getPackageInfo(context).applicationInfo.labelRes;
        return context.getResources().getString(labelRes);
    }

    /**
     * 获取应用程序版本名称
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }
    /**
     * 获取应用程序版本号
     */
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    /**
     * 得到包名
     */
    public static String getPackageName(Context context) {
        return getPackageInfo(context).packageName;
    }

    /**
     * 描述：获取运行的进程列表.
     */
    public static List<String[]> mProcessList = null;//数组列表
    public static List<AppProcessInfo> getRunningAppProcesses(Context context) {
        ActivityManager activityManager = null;
        List<AppProcessInfo> list = null;
        PackageManager packageManager = null;
        try {
            activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            packageManager = context.getApplicationContext().getPackageManager();
            list = new ArrayList<>();
            // 所有运行的进程
            List<ActivityManager.RunningAppProcessInfo> appProcessList = activityManager.getRunningAppProcesses();
            ApplicationInfo appInfo = null;
            AppProcessInfo abAppProcessInfo = null;
            PackageInfo packageInfo = getPackageInfo(context);

            if (mProcessList != null) {
                mProcessList.clear();
            }
            mProcessList = getProcessRunningInfo();

            for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) {
                abAppProcessInfo = new AppProcessInfo(appProcessInfo.processName, appProcessInfo.pid, appProcessInfo.uid);
                appInfo = getApplicationInfo(context, appProcessInfo.processName);
                if (appInfo != null) {
                    if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                        abAppProcessInfo.isSystem = true;
                    } else {
                        abAppProcessInfo.isSystem = false;
                    }
                    Drawable icon = appInfo.loadIcon(packageManager);
                    String appName = appInfo.loadLabel(packageManager).toString();
                    abAppProcessInfo.icon = icon;
                    abAppProcessInfo.appName = appName;
                } else {
                    // :服务的命名
                    if (appProcessInfo.processName.indexOf(":") != -1) {
                        appInfo = getApplicationInfo(context, appProcessInfo.processName.split(":")[0]);
                        Drawable icon = appInfo.loadIcon(packageManager);
                        abAppProcessInfo.icon = icon;
                    }
                    abAppProcessInfo.isSystem = true;
                    abAppProcessInfo.appName = appProcessInfo.processName;
                }

                ProcessInfo processInfo = getMemInfo(appProcessInfo.processName);
                abAppProcessInfo.memory = processInfo.memory;
                abAppProcessInfo.cpu = processInfo.cpu;
                abAppProcessInfo.status = processInfo.status;
                abAppProcessInfo.threadsCount = processInfo.threadsCount;
                list.add(abAppProcessInfo);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 描述：根据进程名返回应用程序.
     *
     * @param context
     * @param processName
     * @return
     */
    public static ApplicationInfo getApplicationInfo(Context context, String processName) {
        if (processName == null) {
            return null;
        }

        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        List<ApplicationInfo> appList = packageManager.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (ApplicationInfo appInfo : appList) {
            if (processName.equals(appInfo.processName)) {
                return appInfo;
            }
        }
        return null;
    }

    /**
     * 描述：kill进程.
     */
    public static void killProcesses(Context context, int pid, String processName) {
        String cmd = "kill -9 " + pid;
        String Command = "am force-stop " + processName + "\n";
        Process sh = null;
        DataOutputStream os = null;
        try {
            sh = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(sh.getOutputStream());
            os.writeBytes(Command + "\n");
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            sh.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtil.e(processName);
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = null;
        try {
            if (processName.indexOf(":") == -1) {
                packageName = processName;
            } else {
                packageName = processName.split(":")[0];
            }

            activityManager.killBackgroundProcesses(packageName);
            Method forceStopPackage = activityManager.getClass().getDeclaredMethod("forceStopPackage", String.class);
            forceStopPackage.setAccessible(true);
            forceStopPackage.invoke(activityManager, packageName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 描述：根据进程名获取CPU和内存信息.
     */
    public static ProcessInfo getMemInfo(String processName) {
        ProcessInfo process = new ProcessInfo();
        if (mProcessList == null) {
            mProcessList = getProcessRunningInfo();
        }
        String processNameTemp;

        for (Iterator<String[]> iterator = mProcessList.iterator(); iterator.hasNext(); ) {
            String[] item = iterator.next();
            processNameTemp = item[9];
            if (processNameTemp != null && processNameTemp.equals(processName)) {
                process.pid = Integer.parseInt(item[0]);// Process ID
                process.cpu = item[2];// CPU
                process.status = item[3];// S
                process.threadsCount = item[4];// thread
                long mem = 0;// Mem
                if (item[6].indexOf("M") != -1) {
                    mem = Long.parseLong(item[6].replace("M", "")) * 1000 * 1024;
                } else if (item[6].indexOf("K") != -1) {
                    mem = Long.parseLong(item[6].replace("K", "")) * 1000;
                } else if (item[6].indexOf("G") != -1) {
                    mem = Long.parseLong(item[6].replace("G", "")) * 1000 * 1024 * 1024;
                }
                process.memory = mem;
                process.uid = item[8];// UID
                process.processName = item[9];// Process Name
                break;
            }
        }
        if (process.memory == 0) {
            LogUtil.e("##" + processName + ",top -n 1未找到");
        }
        return process;
    }

    /**
     * 描述：根据进程ID获取CPU和内存信息.
     */
    public static ProcessInfo getMemInfo(int pid) {
        ProcessInfo process = new ProcessInfo();
        if (mProcessList == null) {
            mProcessList = getProcessRunningInfo();
        }
        String tempPidString = "";
        int tempPid = 0;
        int count = mProcessList.size();
        for (int i = 0; i < count; i++) {
            String[] item = mProcessList.get(i);
            tempPidString = item[0];
            if (tempPidString == null) {
                continue;
            }
            LogUtil.e("##"+item[9]+",PID:"+tempPid);
            tempPid = Integer.parseInt(tempPidString);
            if (tempPid == pid) {
                LogUtil.d("##"+item[9]+","+process.memory);
                process.pid = Integer.parseInt(item[0]);//Process ID
                process.cpu = item[2];// CPU
                process.status = item[3]; // S
                process.threadsCount = item[4];// thread
                long mem = 0;
                if (item[6].indexOf("M") != -1) {
                    mem = Long.parseLong(item[6].replace("M", "")) * 1000 * 1024;
                } else if (item[6].indexOf("K") != -1) {
                    mem = Long.parseLong(item[6].replace("K", "")) * 1000;
                } else if (item[6].indexOf("G") != -1) {
                    mem = Long.parseLong(item[6].replace("G", "")) * 1000 * 1024 * 1024;
                }
                process.memory = mem;// Mem
                process.uid = item[8];// UID
                process.processName = item[9];// Process Name
                break;
            }
        }
        return process;
    }

    /**
     * 描述：执行命令.
     */
    public static String runCommand(String[] command, String workdirectory) {
        String result = "";
        LogUtil.e("#" + command);
        try {
            ProcessBuilder builder = new ProcessBuilder(command);
            // set working directory
            if (workdirectory != null) {
                builder.directory(new File(workdirectory));
            }
            builder.redirectErrorStream(true);
            Process process = builder.start();
            InputStream in = process.getInputStream();
            byte[] buffer = new byte[1024];
            while (in.read(buffer) != -1) {
                String str = new String(buffer);
                result = result + str;
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 描述：运行脚本.
     */
    public static String runScript(String script) {
        String sRet;
        try {
            final Process m_process = Runtime.getRuntime().exec(script);
            final StringBuilder sbread = new StringBuilder();
            Thread tout = new Thread(new Runnable() {
                public void run() {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(m_process.getInputStream()), 8192);
                    String ls_1;
                    try {
                        while ((ls_1 = bufferedReader.readLine()) != null) {
                            sbread.append(ls_1).append("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            tout.start();

            final StringBuilder sberr = new StringBuilder();
            Thread terr = new Thread(new Runnable() {
                public void run() {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(m_process.getErrorStream()), 8192);
                    String ls_1;
                    try {
                        while ((ls_1 = bufferedReader.readLine()) != null) {
                            sberr.append(ls_1).append("\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            terr.start();

            int retvalue = m_process.waitFor();
            while (tout.isAlive()) {
                Thread.sleep(50);
            }
            if (terr.isAlive())
                terr.interrupt();
            String stdout = sbread.toString();
            String stderr = sberr.toString();
            sRet = stdout + stderr;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return sRet;
    }

    /**
     * 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)
     * @return 应用程序是/否获取Root权限
     */
    public static boolean getRootPermission(Context context) {
        String packageCodePath = context.getPackageCodePath();
        Process process = null;
        DataOutputStream os = null;
        try {
            String cmd = "chmod 777 " + packageCodePath;
            // 切换到root帐号
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(cmd + "\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 描述：获取进程运行的信息.
     */
    public static List<String[]> getProcessRunningInfo() {
        List<String[]> processList = null;
        try {
            String result = runCommandTopN1();
            processList = parseProcessRunningInfo(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processList;
    }

    /**
     * 描述：top -n 1.
     *
     * @return
     */
    public static String runCommandTopN1() {
        String result = null;
        try {
            String[] args = {"/system/bin/top", "-n", "1"};
            result = runCommand(args, "/system/bin/");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 描述：解析数据.
     *
     * @param info User 39%, System 17%, IOW 3%, IRQ 0% PID PR CPU% S #THR VSS
     *             RSS PCY UID Name 31587 0 39% S 14 542288K 42272K fg u0_a162
     *             cn.amsoft.process 313 1 17% S 12 68620K 11328K fg system
     *             /system/bin/surfaceflinger 32076 1 2% R 1 1304K 604K bg
     *             u0_a162 /system/bin/top
     * @return
     */
    public static List<String[]> parseProcessRunningInfo(String info) {
        List<String[]> processList = new ArrayList<String[]>();
        int Length_ProcStat = 10;
        String tempString = "";
        boolean bIsProcInfo = false;
        String[] rows = null;
        String[] columns = null;
        rows = info.split("[\n]+");
        // 使用正则表达式分割字符串
        for (int i = 0; i < rows.length; i++) {
            tempString = rows[i];
             LogUtil.e(tempString);
            if (tempString.indexOf("PID") == -1) {
                if (bIsProcInfo == true) {
                    tempString = tempString.trim();
                    columns = tempString.split("[ ]+");
                    if (columns.length == Length_ProcStat) {
                        // 把/system/bin/的去掉
                        if (columns[9].startsWith("/system/bin/")) {
                            continue;
                        }
                         LogUtil.e("#"+columns[9]+",PID:"+columns[0]);
                        processList.add(columns);
                    }
                }
            } else {
                bIsProcInfo = true;
            }
        }
        return processList;
    }

    /**
     * 描述：获取可用内存.
     */
    public static long getAvailMemory(Context context) {
        // 获取android当前可用内存大小
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        // 当前系统可用内存 ,将获得的内存大小规格化
        LogUtil.e("可用内存 = " + memoryInfo.availMem);
        return memoryInfo.availMem;
    }

    /**
     * 描述：总内存.
     */
    public static long getTotalMemory(Context context) {
        // 系统内存信息文件
        String file = "/proc/meminfo";
        String memInfo;
        String[] strs;
        long memory = 0;

        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader, 8192);
            // 读取meminfo第一行，系统内存大小
            memInfo = bufferedReader.readLine();
            strs = memInfo.split("\\s+");
            for (String str : strs) {
                LogUtil.e(str + "\t");
            }
            // 获得系统总内存，单位KB
            memory = Integer.valueOf(strs[1]).intValue();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Byte转位KB或MB
        LogUtil.e("总内存 = " + memory * 1024);
        return memory * 1024;
    }

    /**
     * 描述，打开并安装文件
     * @param file    apk文件
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        context.startActivity(intent);
    }

    /**
     * 描述：卸载程序
     * @param packageName 包名
     */
    public static void uninstallApk(Context context, String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        Uri packageURI = Uri.parse("package:" + packageName);
        intent.setData(packageURI);
        context.startActivity(intent);
    }

    /**
     * 描述：用来判断服务是否运行
     * @param className 判断的服务名字 "com.xxx.xx..XXXService"
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;//用来判断是否运行
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);//Activity管理
        List<ActivityManager.RunningServiceInfo> servicesList = activityManager.getRunningServices(Integer.MAX_VALUE);//运行状态的服务
        Iterator<ActivityManager.RunningServiceInfo> l = servicesList.iterator();
        while (l.hasNext()) {
            ActivityManager.RunningServiceInfo si = l.next();
            if (className.equals(si.service.getClassName())) {//比较，判断
                isRunning = true;
            }
        }
        return isRunning;
    }

    /**
     * 停止服务.
     *
     * @param className the class name
     * @return true, if successful
     */
    public static boolean stopRunningService(Context context, String className) {
        Intent intent_service = null;
        boolean ret = false;
        try {
            intent_service = new Intent(context, Class.forName(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (intent_service != null) {
            ret = context.stopService(intent_service);
        }
        return ret;
    }

    /**
     * Gets the number of cores available in this device, across all processors.
     * Requires: Ability to peruse the filesystem at "/sys/devices/system/cpu"
     *
     * @return The number of cores, or 1 if failed to get result
     */
    public static int getNumCores() {
        try {
            // Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            // Filter to only list the devices we care about
            File[] files = dir.listFiles(new FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    // Check if filename is "cpu", followed by a single digit number
                    if (Pattern.matches("cpu[0-9]", pathname.getName())) {
                        return true;
                    }
                    return false;
                }
            });
            // Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }
}