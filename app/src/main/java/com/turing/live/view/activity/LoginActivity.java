package com.turing.live.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.turing.framework.base.BaseActivity;
import com.turing.framework.utils.ActivityManager;
import com.turing.framework.utils.AppManager;
import com.turing.framework.utils.Logger;
import com.turing.framework.utils.annotate.InjectView;
import com.turing.framework.widget.MyTitleBar;
import com.turing.live.MainActivity;
import com.turing.live.R;
import com.turing.live.model.User;
import com.turing.live.scene.UserScene;
import com.turing.live.utils.JPushUtils;

import java.util.List;
import java.util.logging.LogManager;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 手机登录
 * Created by YSL on 2017/6/29.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    @InjectView(id = R.id.title_bar)
    private MyTitleBar myTitleBar;
    @InjectView(id = R.id.et_login_name)
    private EditText etName;
    @InjectView(id = R.id.et_login_pwd)
    private EditText etPwd;
    @InjectView(id = R.id.btn_login,onClick = true)
    private Button btnLogin;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        myTitleBar.setOnRightClickListener(new View.OnClickListener() { //注册
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                final String userName = etName.getText().toString().trim();
                final String pwd = etPwd.getText().toString().trim();
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(pwd)){
                    showToast("用户名或密码不能为空");
                    return;
                }

                BmobQuery<User> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("userName",userName);
                showLoading("登录中...");
                bmobQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        dismissLoading();
                        if (e == null){
                            Logger.i("ysl","查询成功》》》" + list.toString());
                            if (list.size() > 0 && pwd.equals(list.get(0).getPassWord())){
                                showToast("登录成功!");
                                JPushUtils.setAlias(userName);
                                saveUserInfo(list.get(0));
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));

                                ActivityManager.finishActivity(LoginMainActivity.class.getSimpleName());
                                ActivityManager.finishActivity(LoginActivity.class.getSimpleName());
                            }else{
                                showToast("用户名不存在或密码错误");
                            }
                        }else{
                            showToast("登录失败:" + e.getMessage());
                        }
                    }
                });

                break;
        }
    }

    /**
     * 保存用户信息
     * @param user
     */
    private void saveUserInfo(User user){
        if (user == null) return;
        UserScene.setUserName(user.getUserName());
        UserScene.setUserHeadUrl(user.getHeadUrl());
        UserScene.setUserId(user.getObjectId());

    }
}
