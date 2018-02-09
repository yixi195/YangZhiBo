package com.turing.live.view.fragment;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.turing.framework.base.BaseFragment;
import com.turing.framework.base.BaseListSwipFragment;
import com.turing.framework.utils.annotate.InjectView;
import com.turing.live.R;
import com.turing.live.adapter.HomeAdapter;
import com.turing.live.view.activity.SearchActivity;
import com.turing.live.view.activity.SystemMessageActivity;

/**
 * 首页
 * Created by YSL on 2017/6/13.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    @InjectView(id = R.id.iv_search, onClick = true)
    private ImageView iv_search;
    @InjectView(id = R.id.iv_msg, onClick = true)
    private ImageView iv_msg;
    @InjectView(id = R.id.viewpager)
    private ViewPager viewPager;
    @InjectView(id = R.id.tabs)
    private TabLayout tabLayout;
    public static final long INTERNAL_TIME = 500;
    private long mLastTabSelectedTime;
    private HomeAdapter adapter;

    private Fragment[] mFragments = new Fragment[]{new FollowLiveFragment(), new HotLiveFragment(), new NearbyLiveFragment()};
    private String[] titles = new String[]{"关注", "热门", "附近"};

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        setupViewPager();
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 如果用户连续两次点击Tab间隔不超过100ms,则返回顶部.
                long now = System.currentTimeMillis();
                if (now - mLastTabSelectedTime <= INTERNAL_TIME) {
                    int position = tab.getPosition();
                    Fragment fragment = adapter.getItem(position);
                    if (fragment instanceof HotLiveFragment) {
                        ((HotLiveFragment) fragment).backTop();
                    }
                } else {
                    mLastTabSelectedTime = now;
                }
            }
        });
    }

    private void setupViewPager() {
         adapter = new HomeAdapter(getActivity().getSupportFragmentManager());
        for (int i = 0; i < mFragments.length; i++) {
            adapter.addFragment(mFragments[i], titles[i]);
        }
        viewPager.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        viewPager.setCurrentItem(1); //默认热门

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_search:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_msg:
                intent = new Intent(getActivity(), SystemMessageActivity.class);
                startActivity(intent);
                break;
        }
    }
}
