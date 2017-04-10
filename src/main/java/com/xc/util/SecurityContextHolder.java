package com.xc.util;

/**
 * Created by yb on 2017/4/11 0011.
 */
public class SecurityContextHolder {
	private static final ThreadLocal<String> userIdHolder = new ThreadLocal<String>();

	public static void add(String userId) {
		userIdHolder.set(userId);
	}

	public static String getUserId() {
		return userIdHolder.get();
	}
}
