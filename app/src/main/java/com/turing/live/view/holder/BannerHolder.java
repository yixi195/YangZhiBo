package com.turing.live.view.holder;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.pili.pldroid.player.AVOptions;
import com.turing.framework.rx.BaseObserver;
import com.turing.framework.utils.Logger;
import com.turing.framework.utils.ToastUtil;
import com.turing.live.model.Banner;
import com.turing.live.model.BaseRoomInfo;
import com.turing.live.scene.HotScene;
import com.turing.live.utils.LiveUtils;
import com.turing.live.view.activity.PLVideoTextureActivity;
import com.turing.live.widgets.BannerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Banner轮播图
 * Created by YSL on 2017/3/31.
 */
public class BannerHolder extends BaseHolder {

    private List<String> imgUrlList = new ArrayList<>();
    private List<String> imgUrlListCopy = new ArrayList<>();
    private List<Banner.BannersBean> bannersBeanList = new ArrayList<>();
    private BannerView bannerView;

    public BannerHolder(Context context) {
        super(context);
    }


    @Override
    public View setContentView(Context context) {
        Logger.d("ysl", "BannerHolder---setContentView");
        bannerView = new BannerView(context);
        return bannerView;
    }


    @Override
    public void init() {
        bannerView.setOnHeaderViewClickListener(new BannerView.HeaderViewClickListener() {
            @Override
            public void HeaderViewClick(int position) {
                String xid = bannersBeanList.get(position).getRoomid();
                HotScene.getBaseRoomInfo(xid, new BaseObserver<BaseRoomInfo>() {
                    @Override
                    public void OnFail(int code, String err) {
                        ToastUtil.show(err);
                    }
                    @Override
                    public void OnSuccess(BaseRoomInfo baseRoomInfo) {
                        if (baseRoomInfo == null || baseRoomInfo.getVideoinfo() == null) return;
                        Logger.i("ysl","直播地址是>>" + baseRoomInfo.getVideoinfo().getStreamurl());
                        LiveUtils.startLookLive(context,baseRoomInfo.getRoominfo().getXid(),baseRoomInfo.getRoominfo().getPhoto(),baseRoomInfo.getVideoinfo().getStreamurl());
                    }
                });
            }
        });
        super.init();
    }

    /**
     * 显示banner
     */
    public void showBanner() {
        HotScene.getHotBanner(1,new BaseObserver<Banner>() {
            @Override
            public void OnFail(int code, String err) {

            }

            @Override
            public void OnSuccess(Banner banner) {
                Logger.i("ysl","Banner OnSuccess>>>" + banner.toString());
                if (banner == null || banner.getBanners() == null || banner.getBanners().size() == 0){
                    imgUrlListCopy.clear();
                    bannerView.setVisibility(View.GONE);
                    bannerView.setImgUrlData(null);
                    return;
                }

                bannerView.setVisibility(View.VISIBLE);
                bannersBeanList.clear();
                imgUrlList.clear();
                bannersBeanList.addAll(banner.getBanners());

                for (int i = 0;i<banner.getBanners().size();i++){
                    imgUrlList.add(banner.getBanners().get(i).getImg());
                }
                bannerView.setImgUrlData(imgUrlList);
//                if (imgUrlListCopy.size() == 0 && imgUrlList.size() > 0){
//                    BroadcastUtils.sendLocalBroadcast(new Intent(Constants.HOME_TOP));
//                }
                imgUrlListCopy.addAll(imgUrlList);
            }
        });
    }

    /**
     * 开始Banner
     */
    public void startBanner() {
        if (bannerView != null) {
            bannerView.startRoll();
        }
    }

}
