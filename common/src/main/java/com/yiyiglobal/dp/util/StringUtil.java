package com.yiyiglobal.dp.util;

import java.util.Random;
import java.util.UUID;


public class StringUtil {
	private static String randChars = "0123456789abcdefghigklmnopqrstuvtxyzABCDEFGHIGKLMNOPQRSTUVWXYZ";
	private static String randCharsNoBig = "0123456789abcdefghigklmnopqrstuvtxyz";
	private static Random random = new Random();	
	
	//获取随机字符串
	public static String getRandStr(int length, boolean isOnlyNum) {
		int size = isOnlyNum ? 10 : 62;
		StringBuffer hash = new StringBuffer(length);
		for (int i = 0; i < length; i++) {
			hash.append(randChars.charAt(random.nextInt(size)));
		}
		return hash.toString();
	}
	
	public static String getRandStrNoBig(int length) {
		int size =36;
		StringBuffer hash = new StringBuffer(length);
		for (int i = 0; i < length; i++) {
			hash.append(randCharsNoBig.charAt(random.nextInt(size)));
		}
		return hash.toString();
	}

	/**
	 * 获取文件扩展名
	 * @param filename
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}
    
	public static String getUUID(){    
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");    
		return uuid;    
	}

	public static String parseRadix36(Integer num) {
		String radixNum = Long.toString(num, 36);
		return radixNum;
	}

}
