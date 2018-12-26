package com.yiyiglobal.dp.security.encrypt;

public interface Encoder {
	
	//默认密码
	public final static String DEFAULT_PASSWORD = "123456";
	
	//加密字符串
	public String encrypt(String password);
	
	//加密字符串
	public String encrypt(String password, String salt);
	
	//解密字符串
	public String decrypt(String password, String key);
}
