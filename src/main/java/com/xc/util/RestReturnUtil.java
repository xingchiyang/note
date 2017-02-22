package com.xc.util;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator on 2017/02/22 0022.
 */
public class RestReturnUtil {
	/**
	 * 返回json对象
	 *
	 * @param count
	 * @return {
	 * "data":  [{
	 * "id": "id"
	 * }]
	 * "total": count
	 * }
	 */
	public static String toObject(Object object, Integer count) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("data", object);
		jsonObj.put("total", count);
		return jsonObj.toJSONString();
	}

	/**
	 * 返回一个名称为name的json对象
	 *
	 * @param name
	 * @return 例如name为id，返回：
	 * {
	 * "id": object
	 * }
	 */
	public static String toObject(String name, Object object) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put(name, object);
		return jsonObj.toJSONString();
	}
}
