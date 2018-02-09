package com.turing.live.view.holder;

import android.content.Context;
import android.view.View;

import com.turing.framework.utils.Logger;

/**
 * 基本Holder
 * Created by YSL on 2017/3/31.
 */

public abstract class BaseHolder {

    private View contentView;
    protected Context context;

    public BaseHolder(Context context) {
        Logger.d("ysl","BaseHolder()-------------");
        this.context = context;
        contentView = setContentView(context);
        init();
    }

    //把当前的view返回给父类
    public View getContentView() {
        Logger.d("ysl","BaseHolder()--getContentView()--------");
        return contentView;
    }

    //设置根视图
    public abstract View setContentView(Context context);

    //执行一些初始化的操作，非必须,需要的话重写即可
    public void init(){
        Logger.d("ysl","BaseHolder()--init()--------");
    }
}
