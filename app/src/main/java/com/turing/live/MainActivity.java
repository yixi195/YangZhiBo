package com.turing.live;

import android.content.Intent;
import android.graphics.Color;
import android.icu.text.IDNA;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jaeger.library.StatusBarUtil;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.turing.framework.base.BaseActivity;
import com.turing.framework.utils.annotate.InjectView;
import com.turing.live.view.activity.LiveActivity;
import com.turing.live.view.fragment.HomeFragment;
import com.turing.live.view.fragment.MineFragment;
import com.umeng.analytics.MobclickAgent;

import rx.functions.Action1;

/**
 * 主界面
 */
public class MainActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(id = android.R.id.tabhost)
    private FragmentTabHost mTabHost;
    @InjectView(id = R.id.tab_live,onClick = true)
    private RelativeLayout rlLive;
    /**
     *  首页-我
     */
    private Class<Fragment>[] mFragments = new Class[] {HomeFragment.class, MineFragment.class};
    private final int TAB_HOME = 0,TAB_MINE = 1;
    /**
     * TabView
     */
    private View[] mTabViews;
    /**
     * 当前选择的Index
     */
    private int mSelectedIndex = 0;
    /**
     * 用于恢复
     */
    private static final String EXTRA_POSITION = "extra_position";
    public static boolean isForeground = false;

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mTabHost.setup(this,getSupportFragmentManager(),android.R.id.tabcontent);
        mTabHost.getTabWidget().setVisibility(View.GONE);
        for (Class<Fragment> item : mFragments)
        {
            mTabHost.addTab(mTabHost.newTabSpec(item.getName()).setIndicator(item.getName()), item, null);
        }

        mTabViews = new View[]{findViewById(R.id.tab_home), findViewById(R.id.tab_mine)};
        for (View tab : mTabViews) {
            tab.setOnClickListener(this);
        }
    }

    @Override
    protected void initData() {
        initTab();
        applyPermission();

    }

    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_POSITION, mSelectedIndex);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mSelectedIndex = savedInstanceState.getInt(EXTRA_POSITION, 0);
    }

    /**
     * 初始化Tab
     */
    private void initTab(){
        mTabViews[mSelectedIndex].setSelected(true);
        selectTab(mSelectedIndex);
    }
    /**
     * 6.0+申请写入读取权限
     */
    private void applyPermission(){
        if (Build.VERSION.SDK_INT >= 23) {
            new RxPermissions(this).request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean aBoolean) {}
                    });
        }
    }

    /**
     * 切换Tab
     * @param index 0首页 1我的
     */
    private void selectTab(int index) {
        if (index >= 0 && index < mFragments.length && mTabHost.getCurrentTab() != index) {
            mSelectedIndex = index;
            mTabHost.setCurrentTab(index);
            for (int i = 0;i<mTabViews.length;i++){
                mTabViews[i].setSelected(index == i);
            }

            if (index == 0){
                StatusBarUtil.setColor(this, Color.parseColor("#20b4e9"),0);
            }else{
                StatusBarUtil.setColor(this, Color.parseColor("#93dff9"),0);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tab_home: //首页
                selectTab(TAB_HOME);
                break;
            case R.id.tab_live: //直播
                startActivity(new Intent(MainActivity.this, LiveActivity.class));
                break;
            case R.id.tab_mine: //我的
                selectTab(TAB_MINE);
                break;
        }
    }

    private long mLastBackTime;
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - mLastBackTime) > 2000) {
            showToast("再按一次将退出应用");
            mLastBackTime = currentTime;
        }else{
            MobclickAgent.onKillProcess(this);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }
    }
}
