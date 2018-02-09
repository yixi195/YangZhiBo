package com.turing.live.configs;

import android.app.Application;

import com.turing.framework.utils.Logger;
import com.turing.framework.utils.PhoneUtils;
import com.turing.live.BuildConfig;
import com.turing.live.utils.Utils;
import com.umeng.analytics.MobclickAgent;

/***
 * 友盟配置
 */
public class UmengConfigs {
    /**
     * 初始化
     * @param application
     * @param channel 渠道名称
     */
    public static void init(Application application, String channel) {
        Logger.i("ysl","友盟key>>>" + Utils.getAppcationMetaData(application.getApplicationContext(),"UMENG_APPKEY"));
        MobclickAgent.UMAnalyticsConfig config = new MobclickAgent.UMAnalyticsConfig(application,"595a0de7c62dca096d001a07",channel);
        MobclickAgent.startWithConfigure(config);
        MobclickAgent.setCatchUncaughtExceptions(false);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
        //只配置了微信、QQ/Qzone
//        PlatformConfig.setWeixin("wxfeb884530fe957df", "58bc64de301614a7ea21e6753c37266f");
//        PlatformConfig.setQQZone("1106110320", "Cu72SPkkPHqjTSCD");
//        UMShareAPI.get(application); //友盟分享初始化sdk
        MobclickAgent.enableEncrypt(true);
    }
}
