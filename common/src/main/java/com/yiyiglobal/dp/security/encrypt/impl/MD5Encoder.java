package com.yiyiglobal.dp.security.encrypt.impl;

import com.yiyiglobal.dp.security.encrypt.Encoder;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class MD5Encoder implements Encoder {

	public String decrypt(String password, String key) {
		// 不支持该方法抛出UnsupportedOperationException异常
		throw new UnsupportedOperationException("Not supported the mehtod");
	}

	public String encrypt(String password) {
		// 使用SPRING SECURITY3里的MD5实现类
		return new Md5PasswordEncoder().encodePassword(password, null);
	}

	public String encrypt(String password, String salt) {
		// 使用SPRING SECURITY3里的MD5实现类
		return new Md5PasswordEncoder().encodePassword(password, salt);
	}

	public static void main(String[] args) {
		System.out.println(new MD5Encoder().encrypt("root", "root"));
	}
}
