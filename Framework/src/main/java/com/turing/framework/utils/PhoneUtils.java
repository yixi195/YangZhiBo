package com.turing.framework.utils;

import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 手机工具类
 * Created by Yang on 2017/3/24.
 */
public class PhoneUtils {


    private static ConnectivityManager sConnectMgr;

    /**
     * 判断网络是否可用
     * @param context Context对象
     */
    public static boolean isNetworkReachable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        if (current == null) {
            return false;
        }
        return (current.isAvailable());
    }
    /**
     * 拨号 注意检查权限6.0
     * @param context
     * @param phoneNum
     */
//    public static void call(Context context,String phoneNum) {
//        if (context == null) return;
//        try {
//            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
//            context.startActivity(intent);
//        } catch (Exception ex) {
//            ToastManager.getInstance(context).showToast("无法访问移动网络");
//        }
//    }
    /**
     * 打开QQ临时会话界面
     * @param context
     * @param qqNum
     */
    public static void callQQ(Context context, String qqNum) {
        if (context == null) return;
        try {
            String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qqNum;
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } catch (Exception ex) {
            ToastManager.getInstance(context).showToast("请先安装QQ应用程序");
        }
    }

    /**
     * 获取版本号
     * @param context
     * @return
     */
    public static int getApkVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }
    /**
     * 获取版本名称
     * @param context
     * @return
     */
    public static String getApkVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(),
                    0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0";
        }
    }
    /**
     * 获取手机屏幕分辨率
     * @param context
     * @return
     */
    public static String getScreenPx(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return width + " * " + height;
    }
    /**
     * 去应用市场评分
     */
    public static void goAppMarket(Context context) {
        try {
            Uri uri = Uri.parse("market://details?id="+ context.getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            ToastManager.getInstance(context).showToast( "请先安装一款应用市场软件！");
        }
    }

    /**
     * 显示软键盘
     */
    public static void showKeyBoard(Context context,View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);
    }

    /**
     * 关闭软键盘
     * @param context
     */
    public static void hideKeyBoard(Context context, View view){
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    /**
     * 复制指定内容
     * @param context
     * @param txt  内容String文本
     */
    public static void copyText(Context context,String txt){
        ClipboardManager myClipboard;
        myClipboard = (ClipboardManager)context.getSystemService(context.CLIPBOARD_SERVICE);
        ClipData myClip;
        myClip = ClipData.newPlainText("text", txt);
        myClipboard.setPrimaryClip(myClip);
    }

    /**
     * 获取最顶层Activity全称
     * @param context
     * @return
     */
    public static String getTopActivityName(Context context){
        if (context == null) return "";
        ActivityManager am = (ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        return cn.getClassName();
    }

    //获取网络状态
    public static String getNetworkTypeName(Context context) {
           if (context != null) {
               sConnectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                  if (sConnectMgr != null) {
                         NetworkInfo info = sConnectMgr.getActiveNetworkInfo();
                           if (info != null) {
                                      switch (info.getType()) {
                                               case ConnectivityManager.TYPE_WIFI:
                                                      return "WIFI";
                                                case ConnectivityManager.TYPE_MOBILE:
                                                            return getNetworkTypeName(info.getSubtype());
                                               }
                                   }
                         }
                 }

            return getNetworkTypeName(TelephonyManager.NETWORK_TYPE_UNKNOWN);
         }
    //获取网络状态
    public static String getNetworkTypeNameLogin(Context context) {
        if (context != null) {
            sConnectMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (sConnectMgr != null) {
                NetworkInfo info = sConnectMgr.getActiveNetworkInfo();
                if (info != null) {
                    switch (info.getType()) {
                        case ConnectivityManager.TYPE_WIFI:
                            return "WIFI";
                        case ConnectivityManager.TYPE_MOBILE:
                            return getNetworkTypeNameLogin(info.getSubtype());
                    }
                }
            }
        }

        return getNetworkTypeNameLogin(TelephonyManager.NETWORK_TYPE_UNKNOWN);
    }

    public static String getNetworkTypeNameLogin(int type) {
        switch (type) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "2G";
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "3G";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "4G";
            default:
                return "UNKNOWN";
        }
    }
    public static String getNetworkTypeName(int type) {
        switch (type) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "GPRS";
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "EDGE";
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return "HSUPA";
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "HSPA";
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return "CDMA - EvDo rev. 0";
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return "CDMA - EvDo rev. A";
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return "CDMA - EvDo rev. B";
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return "CDMA - 1xRTT";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return "CDMA - eHRPD";
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "iDEN";
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "HSPA+";
            default:
                return "UNKNOWN";
        }
    }

    /**
     * 判断是否为合法手机号
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean isPhoneNumber(String str) throws PatternSyntaxException {
        String regExp = "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(str);
        return m.matches();
    }

}
