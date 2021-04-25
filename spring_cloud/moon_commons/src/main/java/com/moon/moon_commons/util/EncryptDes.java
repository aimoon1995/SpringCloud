package com.moon.moon_commons.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

/**
 * 
 * 使用DES加密与解密,可对byte[],String类型进行加密与解密 密文可使用String,byte[]存储.
 * 
 * 方法: void getKey(String strKey)从strKey的字条生成一个Key
 * 
 * String getEncString(String strMing)对strMing进行加密,返回String密文 String
 * getDesString(String strMi)对strMin进行解密,返回String明文
 * 
 * byte[] getEncCode(byte[] byteS)byte[]型的加密 byte[] getDesCode(byte[]
 * byteD)byte[]型的解密
 */
public class EncryptDes {
	
	public static final String DEFAULT_KEY = "Fi$8#f)va(,345";
	
	public static String encode(String key, String encodeStr){
		EncryptDes des = new EncryptDes(key);
		return des.encode(encodeStr);
	}
	
	public static String decode(String key, String encodeStr){
		EncryptDes des = new EncryptDes(key);
		return des.decode(encodeStr);
	}
	
	private Key key;

	public EncryptDes(){
	}
	
	public EncryptDes(String key){
		setKey(key);
	}
	
	/**
	 * 根据参数生成KEY
	 * 
	 * @param strKey
	 */
	public void setKey(String strKey) {
		try {
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG"); 
			KeyGenerator _generator = KeyGenerator.getInstance("DES");
			secureRandom.setSeed(strKey.getBytes("UTF-8")); 
			_generator.init(secureRandom);
			this.key = _generator.generateKey();
			_generator = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加密String明文输入,String密文输出
	 * 
	 * @param strMing
	 * @return
	 */
	public String encode(String strMing) {
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		Encoder base64en = Base64.getEncoder();
		try {
			byteMing = strMing.getBytes("UTF-8");
			byteMi = this.getEncCode(byteMing);
			strMi = base64en.encodeToString(byteMi);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			base64en = null;
			byteMing = null;
			byteMi = null;
		}
		return strMi;
	}

	/**
	 * 解密 以String密文输入,String明文输出
	 * 
	 * @param strMi
	 * @return
	 */
	public String decode(String strMi) {
		Decoder base64De = Base64.getDecoder();
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
			byteMi = base64De.decode(strMi);
			byteMing = this.getDesCode(byteMi);
			strMing = new String(byteMing, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			base64De = null;
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}

	/**
	 * 加密以byte[]明文输入,byte[]密文输出
	 * 
	 * @param byteS
	 * @return
	 */
	private byte[] getEncCode(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 解密以byte[]密文输入,以byte[]明文输出
	 * 
	 * @param byteD
	 * @return
	 */
	private byte[] getDesCode(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cipher = null;
		}
		return byteFina;
	}
}