package com.turing.live.view.activity;

import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.turing.framework.base.BaseActivity;
import com.turing.framework.utils.PhoneUtils;
import com.turing.framework.utils.SharedPreferencesUtils;
import com.turing.framework.utils.annotate.InjectView;
import com.turing.live.Constants;
import com.turing.live.MainActivity;
import com.turing.live.R;
import com.turing.live.scene.UserScene;

import org.w3c.dom.Text;

/**
 * 开屏启动页
 */
public class LauncherActivity extends BaseActivity implements View.OnClickListener{

	@InjectView(id = R.id.iv_wx_login,onClick = true)
	private ImageView ivWxLogin;
	@InjectView(id = R.id.iv_qq_login,onClick = true)
	private ImageView ivQqLogin;
	@InjectView(id = R.id.iv_phone_login,onClick = true)
	private ImageView ivPhoneLogin;
	@InjectView(id = R.id.layout_login_style)
	private LinearLayout layoutStyle;

	private Handler mhHandler = new Handler();
	@Override
	protected int getContentView() {
		return R.layout.activity_launcher;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		System.exit(0);
	}

	@Override
	protected void initView() {

	}

	@Override
	protected void initData() {
		mhHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				int agoVersion = SharedPreferencesUtils.getInt(LauncherActivity.this, Constants.VERSION_CODE_SP,0); //历史
				int nowVersion = PhoneUtils.getApkVersionCode(LauncherActivity.this); //当前版本
				Intent intent;
				if (nowVersion > agoVersion) {
					intent = new Intent(LauncherActivity.this,GuideActivity.class);
					SharedPreferencesUtils.saveInt(LauncherActivity.this,Constants.VERSION_CODE_SP,nowVersion);
					startActivity(intent);
					LauncherActivity.this.finish();
				}else {
					if (TextUtils.isEmpty(UserScene.getUserName())){
						layoutStyle.setVisibility(View.VISIBLE);
//						intent = new Intent(LauncherActivity.this,LoginMainActivity.class);
					}else{
						intent = new Intent(LauncherActivity.this,MainActivity.class);
						startActivity(intent);
						LauncherActivity.this.finish();
					}
				}
			}
		}, 2000);

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
				intent = new Intent(LauncherActivity.this,LoginActivity.class);
				startActivity(intent);
				break;

		}
	}
}
