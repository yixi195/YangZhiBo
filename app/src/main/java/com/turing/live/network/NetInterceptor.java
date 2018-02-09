package com.turing.live.network;

import com.turing.framework.utils.Logger;
import com.turing.framework.utils.PhoneUtils;
import com.turing.live.BaseApplication;
import com.turing.live.Constants;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 网络请求拦截器
 * Created by Yang on 2017/3/27.
 */
public class NetInterceptor implements Interceptor {
    private static final String TAG = "NetInterceptor";

    private int maxAge = 60 * 60; // 有网络时 设置缓存超时时间1个小时
    private int maxStale = 60 * 60 * 24 * 28; // 无网络时，设置超时为4周

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request oldRequest = chain.request();
        //ysl-----start----------------以下代码为添加一些公共参数使用--------------------------
        // 添加公共参数
        String time = System.currentTimeMillis()/1000 + "";
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter("lat","33.00125")
                .addQueryParameter("lng","113.56358166666665")
                .addQueryParameter("__channel","yingyongbao")
                .addQueryParameter("__version","3.1.8.3970")
                .addQueryParameter("__plat","android");
        // 构建新的请求
        Request newRequest;
        if (!PhoneUtils.isNetworkReachable(BaseApplication.getInstance().getApplicationContext())) {
            Logger.d("ysl","没网络Request设置为仅仅使用缓存");
            newRequest = oldRequest.newBuilder()
                    .method(oldRequest.method(), oldRequest.body())
                    .url(authorizedUrlBuilder.build())
//                    .addHeader("buildId", Constants.BUILD_ID)
//                    .addHeader("userToken", MineScene.getUserToken())
//                    .addHeader("userId",MineScene.getUserId())
                    .cacheControl(CacheControl.FORCE_CACHE) //无网络时只从缓存中读取
                    .build();

        }else{
            Logger.d("ysl","有网络Request不设置仅仅使用缓存");
            newRequest = oldRequest.newBuilder()
                    .method(oldRequest.method(), oldRequest.body())
                    .url(authorizedUrlBuilder.build())
//                    .addHeader("buildId", Constants.BUILD_ID)
//                    .addHeader("userToken",MineScene.getUserToken())
//                    .addHeader("userId",MineScene.getUserId())
                    .build();
        }
        //ysl-----end

        //POST
//        if ("POST".equals(newRequest.method())){
//            StringBuilder sb = new StringBuilder();
//            if (newRequest.body() instanceof FormBody) {
//                FormBody body = (FormBody) newRequest.body();
//                TreeMap<String, String> param_map = new TreeMap<>();
//                for (int i = 0; i < body.size(); i++) {
//                    param_map.put(body.name(i),body.value(i));
//                }
//                Iterator it = param_map.keySet().iterator();
//                //拼接参数
//                while (it.hasNext()) {
//                    String key = it.next().toString();
//                    String value = param_map.get(key).toString();
//                    sb.append(key + value);
//                }
//            }
//
//            if (!TextUtils.isEmpty(MineScene.getTokenSecret())){  //如果处于登陆状态,则需要加tokenSecret sign
//                Logger.i("ysl", "POST待加sign>>>:" + sb);
//                String data = MineScene.getTokenSecret() + sb.toString();
//                Logger.i("ysl", "POST待加sign>>>:" + data);
//                String sign = HashUtils.shaEncrypt(data);
//                Logger.i("ysl", "POST加sign成功>>>:" + sign);
//                newRequest = newRequest.newBuilder()
//                        .method(newRequest.method(), newRequest.body())
//                        .url(authorizedUrlBuilder.build())
//                        .addHeader("tokenSecret",MineScene.getTokenSecret())
//                        .addHeader("sign",sign)
//                        .build();
//            }
//        }else if ("GET".equals(newRequest.method())){ //GET
//            //拼接参数
//            StringBuilder sb = new StringBuilder();
//            String url = newRequest.url().toString();
//            //获取参数列表
//            String[] parts = url.split("\\?");
//            //TreeMap里面的数据会按照key值自动升序排列
//            TreeMap<String, String> param_map = new TreeMap<>();
//            //获取参数对
//            if (parts.length > 1){
//                String[] param_pairs = parts[1].split("&");
//                for (String pair : param_pairs) {
//                    String[] param = pair.split("=");
//                    if (param.length != 2) {
//                        //没有value的参数
//                        param_map.put(param[0],"");
//                        continue;
//                    }
//                    param_map.put(param[0],param[1]);
//                }
//                Iterator it = param_map.keySet().iterator();
//                while (it.hasNext()) {
//                    String key = it.next().toString();
//                    String value = param_map.get(key).toString();
//                    sb.append(key + value);
//                }
//            }
//
//            if (!TextUtils.isEmpty(MineScene.getTokenSecret())){ //如果处于登陆状态tokenSecret sign
//                String data = MineScene.getTokenSecret() + sb.toString();
//                Logger.i("ysl", "GET待加sign>>>:" + data);
//                String sign = HashUtils.shaEncrypt(data);
//                Logger.i("ysl", "GET加sign后>>>:" + sign);
//                newRequest = newRequest.newBuilder()
//                        .method(newRequest.method(), newRequest.body())
//                        .url(authorizedUrlBuilder.build())
//                        .addHeader("tokenSecret",MineScene.getTokenSecret())
//                        .addHeader("sign",sign)
//                        .build();
//            }
//
//        }
        //重新构建Response
        Response response = chain.proceed(newRequest);

        if (PhoneUtils.isNetworkReachable(BaseApplication.getInstance().getApplicationContext())) {
            Logger.d("ysl","有网络 设置缓存超时时间1个小时");
            response.newBuilder()
                    .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .header("Cache-Control", "public, max-age=" + maxAge)
                    .build();
        } else {
            Logger.d("ysl","没有网络 设置超时时间为4周");
            response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
        //**************************打印Log**********************
        return response;
    }
}
