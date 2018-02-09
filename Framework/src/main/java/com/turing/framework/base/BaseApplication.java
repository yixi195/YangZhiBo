package com.turing.framework.base;

import android.app.Application;
import android.text.TextUtils;

import com.turing.framework.network.OkHttp3Utils;
import com.turing.framework.network.RetrofitUtils;
import com.turing.framework.utils.Constants;
import com.turing.framework.view.BaseViewHelper;

/**
 *
 */
public abstract class BaseApplication extends Application {

    protected String mChannel; //渠道名称

    @Override
    public void onCreate() {
        super.onCreate();

        BaseViewHelper.getInstance().setApplicationContext(getApplicationContext());
        // 初始化框架
        IBaseRequirement requirement = getBaseRequirement();
        if (requirement != null) {
            BaseViewHelper.getInstance().setBaseRequirement(requirement);
            OkHttp3Utils.setInterceptors(requirement.getInterceptors());
            if (!TextUtils.isEmpty(requirement.getUrl()))
                Constants.HTTP_URL = requirement.getUrl();
            if (!TextUtils.isEmpty(requirement.getImageCacheDir()))
                Constants.IMAGE_CACHE_DIR = requirement.getImageCacheDir();
        }
    }

    /**
     * 获取实现 IBaseRequirement 接口的对象，来初始化框架
     * **/
    public abstract IBaseRequirement getBaseRequirement();

}
