package com.turing.framework.utils;

import android.os.Handler;

/**
 * 单例类
 */
 public class SingleHandler extends Handler {
	private static SingleHandler handler = null;

	private SingleHandler() {
	}

	public static SingleHandler getInstance(){
		if(handler == null){
			synchronized (SingleHandler.class) {
				if (handler == null) {
					handler = new SingleHandler();
				}
			}
		}
		return handler;
	}
	
}
