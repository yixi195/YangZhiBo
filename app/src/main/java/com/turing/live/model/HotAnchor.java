package com.turing.live.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 首页主播
 * Created by YSL on 2017/6/26.
 */

public class HotAnchor {

    @SerializedName("items")
    private List<HotGirl> listHotGirl;

    @SerializedName("total")
    private int total; //总数

    public List<HotGirl> getListHotGirl() {
        return listHotGirl;
    }

    public int getTotal() {
        return total;
    }
}
