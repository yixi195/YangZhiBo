package com.turing.framework.rx;

import com.turing.framework.BuildConfig;

import rx.Subscriber;

/**
 * 基本订阅者
 * Created by Yang on 2017/3/28.
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {
        if (BuildConfig.DEBUG) {
            e.printStackTrace();
        }
    }

    @Override
    public void onNext(T t) {

    }
}
