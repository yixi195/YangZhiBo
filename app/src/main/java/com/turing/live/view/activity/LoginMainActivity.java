package com.turing.live.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.turing.framework.base.BaseActivity;
import com.turing.framework.utils.annotate.InjectView;
import com.turing.live.MainActivity;
import com.turing.live.R;

/**
 * 登录主页
 * Created by YSL on 2017/6/28.
 */
public class LoginMainActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(id = R.id.iv_wx_login,onClick = true)
    private ImageView ivWxLogin;
    @InjectView(id = R.id.iv_qq_login,onClick = true)
    private ImageView ivQqLogin;
    @InjectView(id = R.id.iv_phone_login,onClick = true)
    private ImageView ivPhoneLogin;

    @Override
    protected int getContentView() {
        return R.layout.activity_login_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.iv_wx_login: //微信登录
                showToast("暂未开通...");
//                intent = new Intent(LoginMainActivity.this, MainActivity.class);
//                startActivity(intent);
                break;
            case R.id.iv_qq_login: //QQ登录
                showToast("暂未开通...");
                break;
            case R.id.iv_phone_login: //手机登录
                intent = new Intent(LoginMainActivity.this,LoginActivity.class);
                startActivity(intent);
                break;

        }
    }
}
