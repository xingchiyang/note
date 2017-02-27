package com.xc.util.page;

/**
 * 排序转换
 * @author yejh
 * 2016年5月10日
 */
public class SortConvert {

	/**
	 * 排序转换
	 * @param sortKey
	 * @param sortType
	 * @param defaultKey
	 * @param defaultType
	 * @return
	 */
	public static String convert(String sortKey, Integer sortType, String defaultKey, Integer defaultType) {
		if (sortKey == null || "".equals(sortKey.trim())) {
			sortKey = defaultKey;
		}
		if (sortType == null) {
			sortType = defaultType;
		}
		String sortTypeValue = "asc";
		if (-1 == sortType) {
			sortTypeValue = "desc";
		}
		return sortKey + " " + sortTypeValue;
	}
}
