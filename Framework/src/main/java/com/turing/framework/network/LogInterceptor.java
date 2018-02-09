package com.turing.framework.network;

import com.turing.framework.utils.Logger;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;

/**
 * 日志打印
 * Created by YSL on 2017/5/4.
 */
class LogInterceptor implements Interceptor {
    public static String TAG = "LogInterceptor";

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        okhttp3.Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration=endTime-startTime;
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Logger.i(TAG,"\n");
        Logger.i(TAG,"----------Start----------------");
        Logger.i(TAG, "| "+request.toString());
        String method=request.method();
        if("POST".equals(method)){
            StringBuilder sb = new StringBuilder();
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                for (int i = 0; i < body.size(); i++) {
                    sb.append(body.encodedName(i))
                            .append("=")
                            .append(body.encodedValue(i))
                            .append(",");
                }
                sb.delete(sb.length() - 1, sb.length());
                Logger.i(TAG, "| RequestParams:{"+sb.toString()+"}");
            }
        }
        Logger.i(TAG, "| Response:" + content);
        Logger.i(TAG,"----------End:"+duration+"毫秒----------");
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
