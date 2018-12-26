package com.yiyiglobal.dp.util;

import com.yiyiglobal.dp.common.Cons;
import org.apache.commons.lang.math.NumberUtils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtil
{	
	//验证手机号
	public static final String REGEX_MOBILE = "^1[3,4,5,6,7,8,9][\\d]{9}$";
	
	//验证密码
	public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}+$";
	
	//验证邮箱
	public static final String REGEX_EMAIL = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
	
	//中英文、数字-_
	public static final String REGEX_NICKNAME = "^[a-zA-Z0-9\u3400-\u4DB5\u4E00-\u9FA5\u9FA6-\u9FBB\uF900-\uFA2D\uFA30-\uFA6A\uFA70-\uFAD9_-]*$";
	
	//链接
	public static final String REGEX_LINK = "(http://|ftp://|https://|www){0,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)[^\u4e00-\u9fa5\\s]*";
	
	//手机号和固定电话
	public static final String REGEX_CONTACT="(\\d{6,15})|((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)";


	// 数字和点
	public static final String REGEX_VERSION ="^[0-9][0-9.]{0,10}[0-9]$";
	
	//验证手机号格式是否正确,nullable为true时，mobile为空返回值为true
	public static boolean isMobile(String mobile,boolean nullable)
	{
		if(nullable){
			if(mobile == null || mobile.equals("")) return true;
		}
		
		Pattern pattern = Pattern.compile(REGEX_MOBILE);
		Matcher matcher = pattern.matcher(mobile);
		return matcher.matches();
	}
	
	//验证值是否为空
	public static boolean isEmpty(String value)
	{
		return (value == null || value.trim().equals("") || value.trim().equals("null"));
	}
	
	//验证是否为一个纯数字,并且校验它的大小范围
	public static boolean isDigitRange(String value, int minValue, int maxValue)
	{
		if (value == null || value.equals("")) {
			return false;
		}
		
		if (NumberUtils.isDigits(value)) {
			int valueI = Integer.parseInt(value);
			return (valueI <= maxValue && valueI >= minValue);
		}
		
		return false;
	}
	
	public static boolean validateLength(String value,int minLength,int maxLength){
		
		if (value == null || value.equals("")) {
			return false;
		}
		
		if( value.length() >=minLength && value.length() <=maxLength){
			return true;
		}
		
		return false;
	}
	
	//验证密码格式是否正确
	public static boolean isPassword(String value)
	{
		Pattern pattern = Pattern.compile(REGEX_PASSWORD);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
	
	//验证是否为数字
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}

		return true;
	}
	
	//验证值是否为Double
	public static boolean isDouble(String value) {
		try {
			Double.parseDouble(value);
			if (value.contains(".")) return true;
			return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	//验证是否为中文
	public boolean isChinese(char c) {  
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);  
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS  
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS  
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A  
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION  
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION  
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {  
			return true;  
		}  
		
	    return false;  
	}  
	
	//判断是否为乱码
	public boolean isMessyCode(String strName) {  
		Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");  
		Matcher m = p.matcher(strName);  
	    String after = m.replaceAll("");  
	    String temp = after.replaceAll("\\p{P}", "");  
	    char[] ch = temp.trim().toCharArray();  
	    float chLength = ch.length;  
	    float count = 0;  
	    for (int i = 0; i < ch.length; i++) {  
	    	char c = ch[i];  
	    	if (!Character.isLetterOrDigit(c)) {  
	    		if (!isChinese(c)) {  
	    			count = count + 1;  
	    			System.out.print(c);  
	    		}  
	    	}  
	    }  
	    float result = count / chLength;  
	    if (result > 0.4) {  
	    	return true;  
	    } else {  
	    	return false;  
	    }  
	}
	
	//中英文、数字,且长度为4至30位
	public static boolean isNickname(String value)
	{
		Pattern pattern = Pattern.compile(REGEX_NICKNAME);
		Matcher matcher = pattern.matcher(value);
		int valueLength = 0;
        String chinese = "[\u3400-\u4DB5\u4E00-\u9FA5\u9FA6-\u9FBB\uF900-\uFA2D\uFA30-\uFA6A\uFA70-\uFAD9]";
        /* 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1 */
        for (int i = 0; i < value.length(); i++) {
            /* 获取一个字符 */
            String temp = value.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
                /* 中文字符长度为2 */
                valueLength += 2;
            } else {
                /* 其他字符长度为1 */
                valueLength += 1;
            }
        }
		return (matcher.matches() && valueLength>=4 && valueLength<=30);
	}
	
	//判断是否是个日期类型 例如 yyyy-MM-dd 2014-05-09
	public static boolean isDate(String value)
	{
		if (isEmpty(value))
			return false;
		Pattern pattern = Pattern
				.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\-\\s]?((((0?"
						+ "[13578])|(1[02]))[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))"
						+ "|(((0?[469])|(11))[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|"
						+ "(0?2[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12"
						+ "35679])|([13579][01345789]))[\\-\\-\\s]?((((0?[13578])|(1[02]))"
						+ "[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))"
						+ "[\\-\\-\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\-\\s]?((0?["
						+ "1-9])|(1[0-9])|(2[0-8]))))))");
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
	
	//判断是否是link url地址
	public static boolean isLink(String value){
		Pattern pattern = Pattern.compile(REGEX_LINK);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}

	//判断是否是视频文件
	public static boolean isVideo(String value){
		String extension = StringUtil.getExtensionName(value);
		if(Arrays.asList(Cons.QINIU_VIDEO_TYPE).contains(extension.toUpperCase())) return true;
		else return false;
	}

	//判断是否是音频文件
	public static boolean isAudio(String value){
		String extension = StringUtil.getExtensionName(value);
		if(Arrays.asList(Cons.QINIU_AUDIO_TYPE).contains(extension.toUpperCase())) return true;
		else return false;
	}

	//判断是否是图片文件
	public static boolean isImage(String value){
		String extension = StringUtil.getExtensionName(value);
		if(Arrays.asList(Cons.QINIU_IMAGE_TYPE).contains(extension.toUpperCase())) return true;
		else return false;
	}

	
	//验证手机号码或固定电话
	public static boolean isContact(String contact)
	{
		Pattern pattern = Pattern.compile(REGEX_CONTACT);
		Matcher matcher = pattern.matcher(contact);
		return matcher.matches();
	}

	/**
	 * 版本号
	 *
	 * @param value
	 * @return
	 */
	public static boolean isVersion(String value)
	{
		Pattern pattern = Pattern.compile(REGEX_VERSION);
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}



	public static boolean isAccessTokenValide(String accessToken) {
		String regex = "^([A-Z]|[a-z]|[0-9]|-|_){1,}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(accessToken);
		return matcher.matches();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(isDigitRange("1",0,1));
		System.out.println(isDigitRange("3",0,1));
		
	}
	
	
	
}
