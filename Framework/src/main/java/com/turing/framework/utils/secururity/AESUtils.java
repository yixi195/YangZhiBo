package com.turing.framework.utils.secururity;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密工具类
 */
public class AESUtils {

	/**
	 * 解密
	 */
	public static String decrypt(String data, String key) {
		if (TextUtils.isEmpty(data) || TextUtils.isEmpty(key)
				|| key.length() % 16 != 0) {
			return "";
		}
		Cipher cipher = null;
		try {
			byte[] raw = key.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (cipher == null) {
			return "";
		}
		byte[] encrypt = hex2byte(data);
		if (encrypt == null || encrypt.length == 0) {
			return "";
		}
		String original = null;
		try {
			byte[] decrypt = cipher.doFinal(encrypt);
			original = new String(decrypt, "UTF-8");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return original;
	}

	/**
	 * 加密
	 *
	 */
	public static String encrypt(String data, String key) {
		if (TextUtils.isEmpty(data) || TextUtils.isEmpty(key)
				|| key.length() != 16) {
			return "";
		}
		byte[] encrypt = null;
		try {
			byte[] raw = key.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			encrypt = cipher.doFinal(data.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		if (encrypt == null) {
			return "";
		}
		return byte2hex(encrypt).toLowerCase();
	}


	/**
	 * 将16进制转换为二进制
	 *
	 */
	private static byte[] hex2byte(String hex) {
		int len = hex.length();
		if (len % 2 == 1) {
			return null;
		}
		byte[] data = new byte[len / 2];
		for (int i = 0; i != len / 2; i++) {
			String value = hex.substring(i * 2, i * 2 + 2);
			try {
				data[i] = (byte) Integer.parseInt(value, 16);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return data;
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
