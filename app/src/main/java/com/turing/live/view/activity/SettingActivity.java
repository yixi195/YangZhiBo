package com.turing.live.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.turing.framework.base.BaseActivity;
import com.turing.framework.utils.ActivityManager;
import com.turing.framework.utils.AppManager;
import com.turing.framework.utils.ToastUtil;
import com.turing.framework.utils.annotate.InjectView;
import com.turing.live.MainActivity;
import com.turing.live.R;
import com.turing.live.scene.UserScene;

import java.net.IDN;

/**
 * 设置
 * Created by YSL on 2017/6/30.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{
    @InjectView(id = R.id.btn_logout,onClick = true)
    private Button btnLoginOut;

    @Override
    protected int getContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_logout:
                UserScene.loginOut();
                showToast("您已退出登录");
                startActivity(new Intent(this,LoginMainActivity.class));
                ActivityManager.finishActivity(MainActivity.class.getSimpleName());
                finish();
                break;
        }
    }
}
