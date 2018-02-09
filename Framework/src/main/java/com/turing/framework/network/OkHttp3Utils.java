package com.turing.framework.network;

import android.support.annotation.NonNull;

import com.turing.framework.utils.Logger;
import com.turing.framework.view.BaseViewHelper;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * 类描述：封装一个OkHttp3的获取类
 *  Created by YSL on 2016/8/3 0003.
 */
public class OkHttp3Utils {

    private static OkHttpClient mOkHttpClient;
    //设置缓存目录
    private static File cacheDirectory = new File(BaseViewHelper.getInstance().getApplicationContext().getCacheDir().getAbsolutePath(), "CacheFile");
    //设置缓存目录大小 10M
    private static Cache cache = new Cache(cacheDirectory,10 * 1024 * 1024);
    private static List<Interceptor> interceptors;

    public static void setInterceptors(List<Interceptor> items){
        interceptors = items;
    }

    /**
     * 获取OkHttpClient对象
     */
    public static OkHttpClient getOkHttpClient() {

        if (null == mOkHttpClient) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (interceptors != null)
                builder.networkInterceptors().addAll(interceptors);

            //同样okhttp3后也使用build设计模式
            mOkHttpClient = builder
                    //设置一个自动管理cookies的管理器
//                    .cookieJar(new CookiesManager())
                    //添加拦截器
                    .addInterceptor(new LogInterceptor())
                    //添加网络连接器
//                    .addNetworkInterceptor(new CookiesInterceptor(MyApplication.getInstance().getApplicationContext()))
                    //设置请求读写的超时时间
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .cache(cache)
                    .build();
        }
        return mOkHttpClient;
    }

    /**
     * 自动管理Cookies
     */
    private static class CookiesManager implements CookieJar {
        private final PersistentCookieStore cookieStore = new PersistentCookieStore(BaseViewHelper.getInstance().getApplicationContext());
        //在接收时，读取response header中的cookie
        @Override
        public void saveFromResponse(@NonNull HttpUrl url, @NonNull List<Cookie> cookies) {
            if (cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }else{
                Logger.d("ysl","cookie为null");
            }
        }
        //分别是在发送时向request header中加入cookie
        @Override
        public List<Cookie> loadForRequest(@NonNull HttpUrl url) {
            Logger.d("ysl","url为---" + url);
            List<Cookie> cookies = cookieStore.get(url);
            if (cookies.size() < 1) {
                Logger.d("ysl","cookies为null");
            }
            return cookies;
        }
    }

}
