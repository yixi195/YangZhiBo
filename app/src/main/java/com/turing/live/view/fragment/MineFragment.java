package com.turing.live.view.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.turing.framework.base.BaseFragment;
import com.turing.framework.utils.GlideCircleTransform;
import com.turing.framework.utils.annotate.InjectView;
import com.turing.live.R;
import com.turing.live.model.SystemMessage;
import com.turing.live.model.User;
import com.turing.live.scene.UserScene;
import com.turing.live.view.activity.SettingActivity;
import com.turing.live.view.activity.SystemMessageActivity;
import com.turing.live.widgets.CustomRoundView;

/**
 * 我的
 * Created by YSL on 2017/6/13.
 */

public class MineFragment extends BaseFragment implements View.OnClickListener{
    @InjectView(id = R.id.iv_head)
    private CustomRoundView ivHead;
    @InjectView(id = R.id.tv_name)
    private TextView tvUserName;
    @InjectView(id = R.id.tv_id)
    private TextView tvUserId;

    @InjectView(id = R.id.layout_setting,onClick = true)
    private LinearLayout layoutSetting;
    @InjectView(id = R.id.tv_pay,onClick = true)
    private TextView tvPay;
    @InjectView(id = R.id.layout_message,onClick = true)
    private LinearLayout layoutMessage;
    @InjectView(id = R.id.layout_class,onClick = true)
    private LinearLayout layoutClass;
    @InjectView(id = R.id.layout_rank,onClick = true)
    private LinearLayout layoutRank;
    @InjectView(id = R.id.layout_author)
    private LinearLayout layoutAuthor;

    @Override
    protected int getContentView() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected void initData() {
        Glide.with(getActivity()).load(UserScene.getUserHeadUrl()).into(ivHead);
        tvUserName.setText(UserScene.getUserName());
        tvUserId.setText(UserScene.getUserId());

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.layout_message: //消息
                intent = new Intent(getActivity(), SystemMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.layout_setting: //设置
                intent = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
                break;

        }

    }
}
