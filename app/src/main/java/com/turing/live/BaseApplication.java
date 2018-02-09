package com.turing.live;

import android.app.Application;
import android.os.Handler;
import android.text.TextUtils;

import com.dou361.baseutils.utils.LogUtils;
import com.dou361.baseutils.utils.UtilsManager;
import com.turing.framework.base.IBaseRequirement;
import com.turing.framework.network.OkHttp3Utils;
import com.turing.framework.utils.ChannelUtil;
import com.turing.framework.view.BaseViewHelper;
import com.turing.live.base.BaseFrameworkInit;
import com.turing.live.configs.BombConfig;
import com.turing.live.configs.UmengConfigs;

/**
 * App入口
 * Created by YSL on 2017/6/13.
 */
public class BaseApplication extends Application {

    private String channel; //渠道号
    private static BaseApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        /**工具类库初始化*/
        UtilsManager.init(this, "", new Handler(), Thread.currentThread());
        UtilsManager.getInstance().setDebugEnv(true);
        UtilsManager.getInstance().setLogLevel(LogUtils.LogType.LEVEL_ERROR);
        BaseViewHelper.getInstance().setApplicationContext(getApplicationContext());

        /**初始化依赖框架*/
        IBaseRequirement requirement = new BaseFrameworkInit();
        if (requirement != null) {
            BaseViewHelper.getInstance().setBaseRequirement(requirement);
            OkHttp3Utils.setInterceptors(requirement.getInterceptors());
            if (!TextUtils.isEmpty(requirement.getUrl()))
                com.turing.framework.utils.Constants.HTTP_URL = requirement.getUrl();
            if (!TextUtils.isEmpty(requirement.getImageCacheDir()))
                com.turing.framework.utils.Constants.IMAGE_CACHE_DIR = requirement.getImageCacheDir();
        }

        /**初始化比目 */
        BombConfig.init(this);
        /**初始化友盟 */
        channel = ChannelUtil.getChannel(this,"DEV");
        UmengConfigs.init(this,channel);

    }


    public static BaseApplication getInstance() {
        return instance;
    }
}
