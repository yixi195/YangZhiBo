package com.turing.live.utils;

import android.os.Handler;

import com.turing.framework.utils.Logger;
import com.turing.live.BaseApplication;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 极光推送工具类
 * Created by YSL on 2017/4/14.
 */
public class JPushUtils {
    private static final String TAG = "JPushUtils";
    
    private static Handler handler = new Handler();
    /**
     * 设置别名
     * @param alias
     */
    public static void setAlias(final String alias){
        JPushInterface.setAliasAndTags(BaseApplication.getInstance().getApplicationContext(), alias, null, new TagAliasCallback() {
            @Override
            public void gotResult(int code, String s, Set<String> set) {
                switch (code){
                    case 0:
                       // 建议这里往 SharePreference 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
                        Logger.i(TAG,"设置别名成功！");
                        break;
                    case 6002: // 延迟 60 秒来重新设置别名
                        Logger.i(TAG,"设置别名失败！");
                        handler.postAtTime(new Runnable() {
                            @Override
                            public void run() {
                                setAlias(alias);
                            }
                        },60 * 1000);
                        break;
                }
            }
        });
    }
}
