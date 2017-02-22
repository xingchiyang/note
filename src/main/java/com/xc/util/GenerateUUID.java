package com.xc.util;

import java.util.UUID;

/**
 * UUID生成器
 */
public class GenerateUUID {

	/**
	 * 生成UUID36位，带-
	 *
	 * @return
	 */
	public static String getUUID36() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * 生成UUID32位，不带-
	 *
	 * @return
	 */
	public static String getUUID32() {
		return getUUID36().replaceAll("-", "");
	}
}
