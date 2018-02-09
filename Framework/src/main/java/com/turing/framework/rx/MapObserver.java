package com.turing.framework.rx;


import android.text.TextUtils;

import com.turing.framework.BuildConfig;
import com.turing.framework.utils.Logger;
import com.turing.framework.utils.StringUtils;

import java.util.Map;

import rx.Observer;

/**
 * Map观察者
 * Created by YSL on 2017/4/19.
 */
public abstract class MapObserver implements Observer<Map<Object,Object>> {

    @Override
    public void onCompleted() {
        Logger.i("ysl","onCompleted>>>>>");

    }
    @Override
    public void onError(Throwable e) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace();
        }
        String err = e.toString();
        if (TextUtils.isEmpty(err)){
            err = "系统错误";
        } else if (err.contains("HTTP 504")){
            err = "无网络连接";
        }else if (err.contains("HTTP 502")){
            err = "服务器错误";
        }else if (err.contains("HTTP 404")){
            err = "服务器无响应";
        }
        OnError(5001,err);
    }
    @Override
    public void onNext(Map<Object, Object> result) {
        Logger.i("ysl","onNext>>>>>" + result.toString());
        if (result != null && result.containsKey("status") && result.containsKey("msg")) {
            String code = StringUtils.strRemoveDot(result.get("status"));
            if ("0".equals(code)) {
                OnSuccess(result);
            }else{
                String err = String.valueOf(result.get("msg"));
                OnError(Integer.valueOf(code),err);
            }
        }else {
            OnError(5001,"服务器错误");
        }
    }

    public abstract void OnSuccess(Map<Object,Object> result);
    public abstract void OnError(int code,String err);

}
