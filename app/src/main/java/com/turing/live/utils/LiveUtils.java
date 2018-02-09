package com.turing.live.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.pili.pldroid.player.AVOptions;
import com.turing.live.BaseApplication;
import com.turing.live.R;
import com.turing.live.view.activity.PLVideoTextureActivity;

/**
 * 直播工具类
 * Created by YSL on 2017/6/27.
 */

public class LiveUtils {

    /**
     * 观看直播
     * @param xid 直播间id
     * @param  photoPath 封面地址
     * @param videoPath  直播地址
     */
    public static void startLookLive(Context context,String xid,String photoPath,String videoPath){
        Intent intent = new Intent(context, PLVideoTextureActivity.class);
        intent.putExtra("xid",xid);
        intent.putExtra("photoPath",photoPath);
        intent.putExtra("videoPath",videoPath); //直播地址
        intent.putExtra("mediaCodec", AVOptions.MEDIA_CODEC_SW_DECODE);  //软解
        intent.putExtra("liveStreaming",1); //直播
        context.startActivity(intent);
    }

    /**
     * 获取等级图标
     * @param level
     * @return
     */
    public static int getLevIco(String level){
        if ("1".equals(level)){
            return R.mipmap.rank_1;
        }else if ("2".equals(level)){
            return R.mipmap.rank_2;
        }else if ("3".equals(level)){
            return R.mipmap.rank_3;
        }else if ("4".equals(level)){
            return R.mipmap.rank_4;
        }else if ("5".equals(level)){
            return R.mipmap.rank_5;
        }else if ("6".equals(level)){
            return R.mipmap.rank_6;
        }else if ("7".equals(level)){
            return R.mipmap.rank_7;
        }else if ("8".equals(level)){
            return R.mipmap.rank_8;
        }else if ("9".equals(level)){
            return R.mipmap.rank_9;
        }else if ("10".equals(level)){
            return R.mipmap.rank_10;
        }else if ("11".equals(level)){
            return R.mipmap.rank_11;
        }else if ("12".equals(level)){
            return R.mipmap.rank_12;
        }else if ("13".equals(level)){
            return R.mipmap.rank_13;
        }else if ("14".equals(level)){
            return R.mipmap.rank_14;
        }else if ("15".equals(level)){
            return R.mipmap.rank_15;
        }else if ("16".equals(level)){
            return R.mipmap.rank_16;
        }else{
            return R.mipmap.rank_1;
        }
    }
}
