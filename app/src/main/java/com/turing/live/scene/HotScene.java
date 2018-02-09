package com.turing.live.scene;

import com.turing.framework.model.PageList;
import com.turing.framework.network.RetrofitUtils;
import com.turing.framework.rx.BaseObserver;
import com.turing.framework.rx.ServerResult;
import com.turing.live.api.HotApi;
import com.turing.live.model.Banner;
import com.turing.live.model.BaseRoomInfo;
import com.turing.live.model.HotAnchor;
import com.turing.live.model.HotGirl;
import com.turing.live.model.HotListModel;
import com.turing.live.model.RoomUser;
import com.turing.live.model.SearchRoom;

import java.util.List;

/**
 * Created by YSL on 2017/6/26.
 */

public class HotScene extends RetrofitUtils{

    private static final HotApi hotApi = getRetrofit().create(HotApi.class);

    /**
     * 获取热门房间列表
     * @param pageNo
     * @param pageNum
     * @param observer
     */
    public static void getHotRoomList(int pageNo,int pageNum,BaseObserver<PageList<HotGirl>> observer){
        setSubscribe(hotApi.getHotRoomList(pageNo,pageNum),observer);
    }

    /**
     * 获取附近列表
     * @param observer
     */
    public static void getNearListTest(BaseObserver<HotAnchor> observer){
        setSubscribe(hotApi.getNearListTest(),observer);
    }

    /**
     * 获取热门Banner
     * @param observer
     */
    public static void getHotBanner(long showroom,BaseObserver<Banner> observer){
        setSubscribe(hotApi.getHotBanner(showroom),observer);
    }

    /**
     * 根据roomid或者xid获取直播间地址
     * @param xid
     * @param observer
     */
    public static void getBaseRoomInfo(String xid, BaseObserver<BaseRoomInfo> observer){
        setSubscribe(hotApi.getBaseRoomInfo(xid),observer);
    }

    /**
     * 获取直播间观看用户
     * @param limit 查询人数 eg：50
     * @param xid 直播房间id
     * @param observer
     */
    public static void getRoomUserList(String limit, String xid, BaseObserver<List<RoomUser>> observer){
        setSubscribe(hotApi.getRoomUser(limit,xid),observer);
    }

    /**
     * 搜索房间
     * @param name 房间名or主播名
     * @param page 页码 eg 1
     * @param pageSize  页容量 eg：20
     * @param observer
     */
    public static void searchRoom(String name, long page, long pageSize, BaseObserver<PageList<SearchRoom>> observer){
        setSubscribe(hotApi.searchRoom(name,name,page,pageSize),observer);
    }

    /**
     * 获取热门列表
     * @param observer
     */
    public static void getHotList(BaseObserver<List<HotListModel>> observer){
        setSubscribe(hotApi.getHotList(),observer);
    }
}
