package com.turing.live.view.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.turing.framework.rx.BaseObserver;
import com.turing.framework.utils.ToastUtil;
import com.turing.live.R;
import com.turing.live.model.BaseRoomInfo;
import com.turing.live.model.RoomUser;
import com.turing.live.scene.HotScene;
import com.turing.live.scene.UserScene;
import com.turing.live.view.activity.RegisterActivity;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 可以看得出来这是个全屏dailogFragment，他的内部有一个pager
 * 分别控制着EmptyFragment与LayerFragment
 * EmptyFragment：什么都没有
 * LayerFragment：交互界面
 * 这样就达到了滑动隐藏交互的需求，这样做也是为了避免我们自定义动画时，显示卡顿的问题
 */
public class LiveMainDialogFragment extends DialogFragment implements View.OnClickListener{

    private ViewPager viewPager;
    private LayerFragment layerFragment;
    private String limit = "50"; //查询观看用户数量
    private String xid; //直播用户的ID
    private BaseRoomInfo mBaseRoomInfo;  //房间基础信息

    public LiveMainDialogFragment(String xid){
        this.xid = xid;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_live_main, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if(position == 0){
                    return new EmptyFragment();/*返回空界面的fragment*/
                }else if (position == 1){
                    return layerFragment=new LayerFragment(LiveMainDialogFragment.this);/*返回交互界面的fragment*/
                }
                return null;
            }
            @Override
            public int getCount() {
                return 2;
            }
        });
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        viewPager.setCurrentItem(1);/*设置默认显示交互界面*/
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);/*同时将界面改为resize已达到软键盘弹出时LiveFragment不会跟随移动*/

        initData();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.MainDialog){/*设置MainDialogFragment的样式，这里的代码最好还是用我的，大家不要改动*/
            @Override
            public void onBackPressed() {
                super.onBackPressed();
                getActivity().finish();
            }
        };
        return dialog;
    }

    @Override
    public void onClick(View v) {  //关注点击事件
        if (mBaseRoomInfo.isFollow()){ //取关
            BmobQuery<BaseRoomInfo> query = new BmobQuery<>();
            query.addWhereEqualTo("userName",UserScene.getUserName());
            query.addWhereEqualTo("xid",mBaseRoomInfo.getRoominfo().getXid());
            query.findObjects(new FindListener<BaseRoomInfo>() {
                @Override
                public void done(List<BaseRoomInfo> list, BmobException e) {
                    if (e == null && list != null && list.size()>0){
                        BaseRoomInfo deleter = new BaseRoomInfo();
                        deleter.setObjectId(list.get(0).getObjectId());
                        deleter.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    ToastUtil.show("取消关注成功");
                                }else{
                                    ToastUtil.show("取消关注失败");
                                }
                            }
                        });
                    }
                }
            });
        }else{  //关注
            mBaseRoomInfo.setUserName(UserScene.getUserName());
            mBaseRoomInfo.setXid(mBaseRoomInfo.getRoominfo().getXid());
            mBaseRoomInfo.save(new SaveListener<String>() {
                @Override
                public void done(String objectId, BmobException e) {
                    if (e == null){
                        ToastUtil.show("关注成功");
                    }else{
                        ToastUtil.show("关注失败");
                    }
                }
            });
        }
        mBaseRoomInfo.setFollow(!mBaseRoomInfo.isFollow());
        layerFragment.refreshHeadInfo(mBaseRoomInfo);
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset,int positionOffsetPixels) {}
        @Override
        public void onPageSelected(int position) {
            if(position==0){
                layerFragment.hideKeyboard();
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {}
    }


    private void initData(){
        //获取房间信息
        HotScene.getBaseRoomInfo(xid, new BaseObserver<BaseRoomInfo>() {
            @Override
            public void OnFail(int code, String err) {

            }
            @Override
            public void OnSuccess(BaseRoomInfo baseRoomInfo) {
                if (baseRoomInfo == null) return;
                mBaseRoomInfo = baseRoomInfo;
                BmobQuery<BaseRoomInfo> query = new BmobQuery<>();
                query.addWhereEqualTo("userName",UserScene.getUserName());
                query.addWhereEqualTo("xid",mBaseRoomInfo.getRoominfo().getXid());
                query.findObjects(new FindListener<BaseRoomInfo>() {
                    @Override
                    public void done(List<BaseRoomInfo> list, BmobException e) {
                        if (e == null && list != null && list.size()>0){
                            mBaseRoomInfo.setFollow(true);
                        }else{
                            mBaseRoomInfo.setFollow(false);
                        }
                        //设置刷新头部信息
                        layerFragment.refreshHeadInfo(mBaseRoomInfo);
                    }
                });
            }
        });

        //获取房间观看用户信息
        HotScene.getRoomUserList(limit, xid, new BaseObserver<List<RoomUser>>() {
            @Override
            public void OnFail(int code, String err) {

            }

            @Override
            public void OnSuccess(List<RoomUser> roomUsers) {
                if (roomUsers == null) return;
                layerFragment.setRoomUserListData(roomUsers);
            }
        });

    }
}