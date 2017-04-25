package cn.hudp.androidnote.Utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by HuDP on 2016/11/22.
 */

public class ScreenUtil {
    /**
     * 得到设备屏幕的宽度
     */
//    public static int getScreenWidth(Context context) {
//        return context.getResources().getDisplayMetrics().widthPixels;
//    }
    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 得到设备屏幕的高度
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 得到设备的密度
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    /**
     * 把密度转换为像素
     */
    public static int dip2px(Context context, float dp) {
        final float scale = getScreenDensity(context);
        return (int) (dp * scale + 0.5);
    }
}
