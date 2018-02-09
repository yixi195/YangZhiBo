package com.turing.framework.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.turing.framework.rx.BaseObserver;
import com.turing.framework.rx.MapObserver;
import com.turing.framework.rx.ServerDataMap;
import com.turing.framework.rx.ServerResult;
import com.turing.framework.utils.Constants;

import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * retrofit工具类
 */
public abstract class RetrofitUtils {
    private static Retrofit mRetrofit;
    private static OkHttpClient mOkHttpClient;
    protected static Subscription subscription;
    protected static Gson gson;

    /**
     * 获取Retrofit对象
     */
    protected static Retrofit getRetrofit() {
        if (null == mRetrofit) {
            if (null == mOkHttpClient) {
                mOkHttpClient = OkHttp3Utils.getOkHttpClient();
            }
            //Retrofit2后使用build设计模式
            mRetrofit = new Retrofit.Builder()
                    //设置服务器路径
                    .baseUrl(Constants.HTTP_URL)
                    //添加转化库，默认是Gson 添加忽略null值
                    .addConverterFactory(GsonConverterFactory.create(buildGson()))
                    //添加回调库，采用RxJava
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    //设置使用okhttp网络请求
                    .client(mOkHttpClient)
                    .build();
        }
        return mRetrofit;
    }

    private static Gson buildGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .registerTypeAdapter(Integer.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(int.class, new IntegerDefault0Adapter())
                    .registerTypeAdapter(String.class,new StringDefaultNullAdapter())
                    .registerTypeAdapter(Object.class,new StringDefaultNullAdapter())
                    .create();
        }
        return gson;
    }

    protected static Gson buildGson2() {
        if (gson == null) {
            gson = new GsonBuilder().serializeNulls().create();
        }
        return gson;
    }

    /**
     * 插入观察者-泛型
     * @param observable 被观察者
     * @param observer 观察者
     * @param <T>
     */
    protected static <T> void setSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 【用这个】
     * @param observable
     * @param observer
     * @param <T>
     */
    protected static <T> void setSubscribe(Observable<ServerResult<T>> observable, BaseObserver<T> observer) {
        observable.map(new ServerDataMap<T>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 插入观察者-Map
     * @param observable 被观察者
     * @param observer 观察者
     */
    protected static void setMapSubscribe(Observable<Object> observable, MapObserver observer){
        observable.map(new Func1<Object, Map<Object,Object>>() {
            @Override
            public Map<Object, Object> call(Object o) {
                return (Map<Object, Object>) o;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }



}
