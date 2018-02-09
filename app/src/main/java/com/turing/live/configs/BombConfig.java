package com.turing.live.configs;

import com.turing.live.BaseApplication;

import cn.bmob.v3.Bmob;

/**
 * 比目后端云
 * Created by YSL on 2017/6/29.
 */

public class BombConfig {

    public static void init(BaseApplication app){
        Bmob.initialize(app,"a5c089dced8f1ed0d5ec5a20976c8134");
    }
}
