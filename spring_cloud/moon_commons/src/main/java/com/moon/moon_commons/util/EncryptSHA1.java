package com.moon.moon_commons.util;

import java.security.MessageDigest;
import java.util.Map;


public class EncryptSHA1 {

	public static String getSHA1HexString(String str){
        // SHA1签名生成
		try{
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(str.getBytes());
        byte[] digest = md.digest();

        StringBuffer hexstr = new StringBuffer();
        String shaHex = "";
        for (int i = 0; i < digest.length; i++) {
            shaHex = Integer.toHexString(digest[i] & 0xFF);
            if (shaHex.length() < 2) {
                hexstr.append(0);
            }
            hexstr.append(shaHex);
        }
        return hexstr.toString();
        }catch(Exception ex){
        	throw new RuntimeException("SHA1算法加密失败!", ex);
        }
    }
	
	public static String buildSHA1AscRequestParams(Map<String, ? extends Object> params, String secretkey){
		String queryString = StringUtil.buildAscQueryString(params);
		return buildSHA1RequestParams(queryString, secretkey);
	}
	
	public static String buildSHA1RequestParams(String queryString, String secretkey){
		StringBuffer buf = new StringBuffer();
		if(StringUtil.isNotEmpty(queryString)){
			buf.append(queryString);
		}
		if(StringUtil.isNotEmpty(secretkey)){
			buf.append(secretkey);
		}
		return getSHA1HexString(buf.toString());
	}
}
