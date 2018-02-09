package com.turing.framework.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.turing.framework.R;

/**
 * 加载Dialog
 * @author YSL
 */
public class LoadingDialog extends Dialog{
	private TextView tvContent;
	Context context;

	public LoadingDialog(Context context) {
		super(context);
		this.context = context;
	}

	public LoadingDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
//		if (context instanceof Activity) {
//			if(!MainActivity.class.getName().equals(context.getClass().getName())){
//				((Activity) context).finish();
//			}else{
//				if(!((Activity) context).isFinishing()){
//					this.dismiss();
//				}
//			}
//		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_view);
		setCancelable(true);
		setCanceledOnTouchOutside(false);
	}

	public LoadingDialog setTitle(String txt){
		if(tvContent == null){
			tvContent = (TextView)findViewById(R.id.tv_loading_msg);
		}
		if(tvContent != null){
			tvContent.setText(txt);
		}
		return this;
	}

}
