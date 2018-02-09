package com.turing.live.view.activity;

import com.turing.framework.base.BaseActivity;
import com.turing.live.R;
import com.turing.live.view.fragment.LiveMainDialogFragment;

/**
 *  直播
 * Created by YSL on 2017/6/13.
 */

public class LiveActivity extends BaseActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_live;
    }

    @Override
    protected void initView() {
        new LiveMainDialogFragment("1").show(getSupportFragmentManager(),"LiveMainDialogFragment");

    }

    @Override
    protected void initData() {

    }
}
