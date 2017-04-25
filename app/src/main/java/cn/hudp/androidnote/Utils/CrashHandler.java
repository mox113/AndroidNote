package cn.hudp.androidnote.Utils;


import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 * <p>
 * 需要在Application中注册，为了要在程序启动器就监控整个程序。
 * Created by HuDP on 2017/1/17.
 */
public class CrashHandler implements UncaughtExceptionHandler {
    public static final String TAG = "CrashHandler";

    //系统默认的UncaughtException处理类
    private UncaughtExceptionHandler mDefaultHandler;
    //CrashHandler实例
    private static CrashHandler instance;
    //程序的Context对象
    private Context mContext;
    private static long startTime;
    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Crash/";
    private CrashInfo crashInfo;

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
            startTime = System.currentTimeMillis();
        }
        return instance;
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        mContext = context;
        //获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //收集设备参数信息
        crashInfo = getDeviceInfo();
        crashInfo.setCrashTrigger(ex.getMessage());
        crashInfo.setAppError(getThreadStackInfo(ex));
        crashInfo.setUserID(404);
        sendCrashLog(crashInfo);
        //保存日志文件
        saveCatchInfo2File(crashInfo);
        return true;
    }

    public void sendCrashLog(CrashInfo infos) {
        Log.d(TAG, "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~CRASH_LOG_START~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        Log.e(TAG, infos.toString());
        Log.d(TAG, "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~CRASH_LOG_END~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

//        Intent intent = new Intent(mContext, UploadCrashService.class);
//        intent.putExtra("INFO", infos);
//        PendingIntent restartIntent = PendingIntent.getService(mContext, 0, intent, 0);
//        //退出程序
//        AlarmManager mgr = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
//        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000,
//                restartIntent); // 1秒钟后重启应用
    }

    private String getThreadStackInfo(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String info = writer.toString();
        return info;
    }

    /**
     * 保存错误信息到文件中
     *
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveCatchInfo2File(CrashInfo infos) {
        String crashInfo = infos.toString();
        try {
            String time = infos.getOccurDate();
            String fileName = "crash-" + time + ".txt";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File dir = new File(filePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(filePath + fileName);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
                writer.write(crashInfo);
                writer.close();
            }
            return fileName;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        }
        return null;
    }


    /**
     * 获取手机信息
     */
    public CrashInfo getDeviceInfo() {
        CrashInfo info = new CrashInfo();
        info.setUserID(0);
        info.setOccurDate(formatter.format(new Date()));
        info.setAppPackage(mContext.getPackageName());
        try {
            info.setAppLevel(mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        long time = (startTime - System.currentTimeMillis()) / 1000; //启动时间
        info.setUsingTime((int) (time / 60) + "分" + time % 60 + "秒");
        info.setAppState(1);
        info.setPhoneType(Build.BRAND + " " + Build.MODEL);
        info.setPhoneVersion("Android " + Build.VERSION.RELEASE + "，API" + Build.VERSION.SDK_INT);
        info.setPhoneROM(Build.MANUFACTURER);

        String[] abis = new String[]{};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            abis = Build.SUPPORTED_ABIS;
        } else {
            abis = new String[]{Build.CPU_ABI, Build.CPU_ABI2};
        }
        StringBuilder abiStr = new StringBuilder();
        for (String abi : abis) {
            abiStr.append(abi);
            abiStr.append(',');
        }
        info.setPhoneCPU(abiStr.toString());
        info.setAvailMemory(getAvailMemory());
        return info;


    }

    /**
     * 获取当前可用内存大小
     *
     * @return
     */
    private String getAvailMemory() {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return Formatter.formatFileSize(mContext, mi.availMem);
    }
}
