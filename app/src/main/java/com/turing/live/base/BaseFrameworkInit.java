package com.turing.live.base;

import com.turing.framework.base.IActivityHelper;
import com.turing.framework.base.IBaseRequirement;
import com.turing.framework.base.IFragmentHelper;
import com.turing.live.Constants;
import com.turing.live.network.NetInterceptor;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;

/**
 * 对整理的框架进行数据的初始化-详情查看IBaseRequirement
 * Created by huang on 2017/5/15.
 */

public class BaseFrameworkInit implements IBaseRequirement {

    @Override
    public String getUrl() {
        return Constants.API_BASE_URL;
    }

    @Override
    public List<Interceptor> getInterceptors() {
        List<Interceptor> interceptors = new ArrayList<>();
        interceptors.add(new NetInterceptor());
        return interceptors;
    }

    @Override
    public IActivityHelper getActivityHelper() {
        return null;
    }

    @Override
    public IFragmentHelper getFragmentHelper() {
        return null;
    }

    @Override
    public String getImageCacheDir() {
        return "TuringLive/image_cache";
    }
}
