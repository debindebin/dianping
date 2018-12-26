package com.yiyiglobal.dp.common;

//redis key值
public class RedisCons {
	
	public static final String VERIFYCODE_ = "VERIFYCODE_"; // 记录获取短信验证码信息， + mobile
	
	public static final String USER_ = "USER_"; // 记录用户Session信息，+userId

	public static final String WX_ACCESS_TOKEN = "WX_ACCESS_TOKEN";	// 微信授权
	public static final String WX_JS_API_TICKET = "WX_JS_API_TICKET";	// 微信JsApiTicket

	public static final String CONFIG ="CONFIG";  	//配置文件
	public static class Config {
		public final static String VERIFYCODE = "VERIFYCODE";	//万能验证码
	}

	public static final String AUTO_INCREMENT = "AUTO_INCREMENT";		//自增id值
	public static class AutoIncrement {
		public final static String USER_ID = "USER_ID";					//用户ID
	}

	public static final String HOT_REVIEW_RES = "HOT_REVIEW_RES";	// 24小时热评资源

	public static final String USER_SIGN_IN_INFO = "USER_SIGN_IN_INFO_";
	public static final String LAST_SIGN_IN = "LAST_SIGN_IN";
	public static final String SIGN_IN_SUM_COUNT = "SIGN_IN_SUM_COUNT";
	public static final String SIGN_IN_CONTINUITY_COUNT = "SIGN_IN_CONTINUITY_COUNT";

}
