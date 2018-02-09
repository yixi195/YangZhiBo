package com.turing.framework.utils;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.turing.framework.view.BaseViewHelper;

/**
 * 广播
 */
public class BroadcastUtils {

    /**
     * 发送一个广播，此广播只在我们的app内有效
     * */
    public static void sendLocalBroadcast(Intent in) {
        Context context = BaseViewHelper.getInstance().getApplicationContext();
        LocalBroadcastManager.getInstance(context).sendBroadcast(in);
    }

    /**
     * 发送一个全局的广播，此广播整个系统都可以收到
     * */
    public static void sendBroadcast(Intent in) {
        Context context = BaseViewHelper.getInstance().getApplicationContext();
        context.sendBroadcast(in);
    }

}
