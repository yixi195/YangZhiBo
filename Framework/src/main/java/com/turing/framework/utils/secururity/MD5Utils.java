package com.turing.framework.utils.secururity;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 */
public class MD5Utils {

	public static String signature(String data) {
		if (TextUtils.isEmpty(data)) {
			return "";
		}
		try {
			data = new String(data.getBytes(), "UTF-8");
		} catch (Exception e) {
		}
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		if (md5 == null) {
			return "";
		}
		md5.reset();
		md5.update(data.getBytes());
		byte[] sign = md5.digest();
		return byte2hex(sign);
	}


	/**
	 * 将二进制转换成16进制
	 *
	 */
	private static String byte2hex(byte[] data) {
		StringBuffer hex = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			String value = Integer.toHexString(data[i] & 0XFF);
			if (value.length() == 1) {
				hex.append("0");
			}
			hex.append(value);
		}
		return hex.toString();
	}
}
