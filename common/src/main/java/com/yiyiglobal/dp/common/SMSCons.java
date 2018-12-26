package com.yiyiglobal.dp.common;

//短信验证码
public class SMSCons {
	private static String suffix = "约能人";
	
	public static String getVerifyCodeSMS(String verifyCode){
		return "验证码："+verifyCode +"，此验证码有效期为30分钟。"+"【"+suffix+"】";	
	}
	
	
}
