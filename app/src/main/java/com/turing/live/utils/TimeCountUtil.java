package com.turing.live.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

/**
 * 倒计时验证码
 */
public class TimeCountUtil extends CountDownTimer {
    private TextView mButton;

    public TimeCountUtil(TextView button, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.mButton = button;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        // 按钮不可用
        mButton.setEnabled(false);
        String showText = millisUntilFinished / 1000 + "秒";
        mButton.setText(showText);
    }

    @Override
    public void onFinish() {
        // 按钮设置可用
        mButton.setEnabled(true);
        mButton.setText("重新获取验证码");
    }
}
