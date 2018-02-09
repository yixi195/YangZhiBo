package com.turing.live.view.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.turing.framework.base.BaseActivity;
import com.turing.framework.utils.ActivityManager;
import com.turing.framework.utils.Logger;
import com.turing.framework.utils.PhoneUtils;
import com.turing.framework.utils.annotate.InjectView;
import com.turing.live.MainActivity;
import com.turing.live.R;
import com.turing.live.model.User;
import com.turing.live.utils.TimeCountUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * 注册页面
 * Created by YSL on 2017/6/29.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener{
    @InjectView(id = R.id.tv_getCode,onClick = true)
    private TextView tvCode;
    @InjectView(id = R.id.et_phoneNum)
    private EditText etPhoneNum;
    @InjectView(id = R.id.et_username)
    private EditText etUserName;
    @InjectView(id = R.id.et_pwd)
    private EditText etPwd;
    @InjectView(id = R.id.btn_register,onClick = true)
    private Button btnRegister;
    private TimeCountUtil mTimeCountUtil;

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        mTimeCountUtil = new TimeCountUtil(tvCode, 60000, 1000);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        String phone = etPhoneNum.getText().toString().trim();
        switch (v.getId()){
            case R.id.tv_getCode: //获取验证码
                if (TextUtils.isEmpty(phone)) {
                    showToast("电话不能为空");
                }else if(!PhoneUtils.isPhoneNumber(phone)) {
                    showToast("请输入正确的电话号码");
                }else {
                    mTimeCountUtil.start();
                    showToast("正在发送验证码");
                }
                break;
            case R.id.btn_register:
                final String userName = etUserName.getText().toString().trim();
                final String pwd = etPwd.getText().toString().trim();

                showLoading("请求中...");
                BmobQuery<User> bmobQuery = new BmobQuery<>();
                bmobQuery.addWhereEqualTo("userName",userName);
                bmobQuery.findObjects(new FindListener<User>() {
                    @Override
                    public void done(List<User> list, BmobException e) {
                        if (e == null && list != null && list.size()>0){
                            //已存在
                            dismissLoading();
                            showToast("用户名已存在");
                        }else{
                            User user = new User();
                            user.setUserName(userName);
                            user.setPassWord(pwd);
                            user.setHeadUrl("http://k2.jsqq.net/uploads/allimg/1705/7_170524143440_5.jpg");
                            user.save(new SaveListener<String>() {
                                @Override
                                public void done(String objectId, BmobException e) {
                                    dismissLoading();
                                    if (e == null){
                                        showToast("注册成功!");
                                        RegisterActivity.this.finish();
                                    }else{
                                        showToast("注册失败:" + e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                });
                break;

        }

    }
}
