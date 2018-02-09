package com.turing.live.api;

import com.turing.framework.model.PageList;
import com.turing.framework.rx.ServerResult;
import com.turing.live.model.Banner;
import com.turing.live.model.BaseRoomInfo;
import com.turing.live.model.HotAnchor;
import com.turing.live.model.HotGirl;
import com.turing.live.model.HotListModel;
import com.turing.live.model.RoomUser;
import com.turing.live.model.SearchRoom;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 热门主播
 * Created by YSL on 2017/6/26.
 */

public interface HotApi {

    @GET("room/list?status=0&banner=1")
    Observable<ServerResult<PageList<HotGirl>>> getHotRoomList(@Query("pageno") int pageno, @Query("pagenum") int pagenum);

    @GET("room/near")
    Observable<ServerResult<HotAnchor>> getNearListTest();

    @GET("room/hotbanner")
    Observable<ServerResult<Banner>> getHotBanner(@Query("showroom") long showroom);

    @GET("room/baseinfo")
    Observable<ServerResult<BaseRoomInfo>> getBaseRoomInfo(@Query("xid") String xid);

    @GET("http://bullet.xingyan.panda.tv/room/user/v3")
    Observable<ServerResult<List<RoomUser>>> getRoomUser(@Query("limit") String limit,@Query("xid") String xid);

    @GET("http://search.xingyan.panda.tv/search/profiles?caller=xingyan_app&token=7241c78a98bec01a17b4c61de46c6d78")
    Observable<ServerResult<PageList<SearchRoom>>> searchRoom(@Query("name") String name, @Query("nickName") String nickName, @Query("page") long page, @Query("pagesize") long pagesize);

    @GET("room/hotlist")
    Observable<ServerResult<List<HotListModel>>> getHotList();

}
