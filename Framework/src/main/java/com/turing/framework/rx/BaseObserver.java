package com.turing.framework.rx;


import android.text.TextUtils;

import com.turing.framework.BuildConfig;

import rx.Observer;

/**
 * 基本观察者
 * Created by YSL on 2017/4/17.
 */

public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace();
        }
        if (e instanceof ServerResult.ServerErrorException) {
            int status = ((ServerResult.ServerErrorException) e).result.getStatus();
            String err = ((ServerResult.ServerErrorException) e).result.getMessage();
            if (TextUtils.isEmpty(err)){
                err = "系统错误";
            }
            OnFail(status,err);
        }else{
            String err = e.toString();
            if (err.contains("HTTP 504")){
                err = "无网络连接";
            }else if (err.contains("HTTP 502")){
                err = "服务器错误";
            }else if (err.contains("HTTP 404")){
                err = "服务器无响应";
            }else {
                if (!BuildConfig.DEBUG){
                    err = "网络不稳定，请稍后再试";
                }
            }
            OnFail(5001,err);
        }
    }

    @Override
    public void onNext(T t) {
        OnSuccess(t);
    }

    public abstract void OnFail(int code,String err);
    public abstract void OnSuccess(T t);
}
