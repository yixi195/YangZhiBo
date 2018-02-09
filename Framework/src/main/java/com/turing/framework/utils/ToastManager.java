package com.turing.framework.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.turing.framework.rx.BaseSubscriber;

import java.lang.ref.WeakReference;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Toast管理.
 * <p/>
 * Created by Yang on 2017/3/24.
 */
public class ToastManager {
    private static Context mContext;
    private WeakReference<Toast> mShowingToastRef;

    private static class ToastHolder{
        private final static ToastManager instance = new ToastManager();
    }
    private ToastManager() {}

    public static ToastManager getInstance(Context context){
        mContext = context;
        return ToastHolder.instance;
    }

    public void showToast(final int resId) {
        Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                Toast toast = null;
                if (mShowingToastRef != null) {
                    toast = mShowingToastRef.get();
                }
                if (toast == null) {
                    toast = Toast.makeText(mContext, resId, Toast.LENGTH_LONG);
                    mShowingToastRef = new WeakReference<>(toast);
                } else {
                    toast.setText(resId);
                }
                toast.show();
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new BaseSubscriber<Void>() {
        });
    }

    public void showToast(final @NonNull String message) {
        Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                Toast toast = null;
                if (mShowingToastRef != null) {
                    toast = mShowingToastRef.get();
                }
                if (toast == null) {
                    toast = Toast.makeText(mContext, message, Toast.LENGTH_LONG);
                    mShowingToastRef = new WeakReference<>(toast);
                } else {
                    toast.setText(message);
                }
                toast.show();
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new BaseSubscriber<Void>() {
        });
    }
}
