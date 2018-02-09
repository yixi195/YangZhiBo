package com.turing.framework.utils.secururity;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/** 
 * AES 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化；
 * 北京华付
 */  
public class AESUtil {  
     
	
    private static AESUtil instance = null;  
	public static String encode = "utf-8";
    private AESUtil() {  
  
    }  
  
    public static AESUtil getInstance() {  
        if (instance == null)  
            instance = new AESUtil();  
        return instance;  
    }

    /**
     * 加密
     * @param sSrc
     * @param sKey
     * @param ivParameter
     * @return
     */
	public String encrypt(String sSrc,String sKey,String ivParameter){  
        String result = "";  
        try {  
        
            Cipher cipher;  
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
            byte[] raw = sKey.getBytes();  
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度  
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec,iv);  
            byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));  
           
           
            result= new String(Base64.encode(encrypted,Base64.DEFAULT));
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
        // 此处使用BASE64做转码。  
        return result;  
                  
    }

    /**
     * 解密
      * @param sSrc
     * @param sKey
     * @param ivParameter
     * @return
     */
    public String decrypt(String sSrc,String sKey,String ivParameter){  
        try {  
            byte[] raw = sKey.getBytes(encode);  
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
            IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());  
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);  
            
            byte[] original = cipher.doFinal(Base64.decode(sSrc,Base64.DEFAULT));
            return new String(original, "utf-8");  
        } catch (Exception ex) {  
            ex.printStackTrace();  
            return null;  
        }  
    }  
 
}  