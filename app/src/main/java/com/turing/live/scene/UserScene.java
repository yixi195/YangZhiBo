package com.turing.live.scene;

import com.turing.framework.utils.SharedPreferencesUtils;
import com.turing.live.BaseApplication;
import com.turing.live.model.SpFinal;
import com.turing.live.utils.JPushUtils;

/**
 * 用户
 * Created by YSL on 2017/6/29.
 */

public class UserScene {

    public static String getUserName() {
        return SharedPreferencesUtils.getString(BaseApplication.getInstance().getApplicationContext(),SpFinal.USER_NAME,"");
    }

    public static void setUserName(String userName) {
        SharedPreferencesUtils.saveString(BaseApplication.getInstance().getApplicationContext(), SpFinal.USER_NAME,userName);
    }

    public static String getUserHeadUrl() {
        return SharedPreferencesUtils.getString(BaseApplication.getInstance().getApplicationContext(),SpFinal.USER_HEAD_URL,"");
    }

    public static void setUserHeadUrl(String userHeadUrl) {
        SharedPreferencesUtils.saveString(BaseApplication.getInstance().getApplicationContext(), SpFinal.USER_HEAD_URL,userHeadUrl);
    }

    public static String getUserId() {
        return SharedPreferencesUtils.getString(BaseApplication.getInstance().getApplicationContext(),SpFinal.USER_ID,"");
    }

    public static void setUserId(String userId) {
        SharedPreferencesUtils.saveString(BaseApplication.getInstance().getApplicationContext(), SpFinal.USER_ID,userId);
    }

    /**
     * 注销用户
     */
    public static void loginOut(){
        SharedPreferencesUtils.remove(BaseApplication.getInstance(),SpFinal.USER_NAME);
        SharedPreferencesUtils.remove(BaseApplication.getInstance(),SpFinal.USER_HEAD_URL);
        SharedPreferencesUtils.remove(BaseApplication.getInstance(),SpFinal.USER_ID);
        JPushUtils.setAlias("");
    }
}
