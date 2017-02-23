package com.xc.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonUtil {

	/**
	 * 过滤对象中不需要的字段转换成json(含嵌套对象)
	 *
	 * @param obj
	 * @param filters
	 * @return
	 */
	public static String filterPropToJson(Object obj, String filters[]) {
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		Set<String> set = filter.getExcludes();
		for (String str : filters) {
			set.add(str);
		}
		return JSON
				.toJSONString(obj, SerializeConfig.getGlobalInstance(), filter, SerializerFeature.WriteNullBooleanAsFalse,
						SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero,
						SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteMapNullValue,
						SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNullListAsEmpty);
	}

	/**
	 * 包含对象中需要的字段转换成json(含嵌套对象)
	 *
	 * @param obj
	 * @param includes
	 * @return
	 */
	public static String includePropToJson(Object obj, String includes[]) {
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		Set<String> set = filter.getIncludes();
		for (String str : includes) {
			set.add(str);
		}
		return JSON
				.toJSONString(obj, SerializeConfig.getGlobalInstance(), filter, SerializerFeature.WriteNullBooleanAsFalse,
						SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero,
						SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteMapNullValue,
						SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNullListAsEmpty);
	}

	/**
	 * 包含对象中需要的字段转换成json(需指定嵌套对象)
	 *
	 * @param obj
	 * @param include
	 * @return
	 */
	public static String includePropToJson(Object obj, SimplePropertyPreFilter... include) {
		if (obj == null) {
			return "{}";
		} else if ((obj instanceof List<?> && ((List<?>) obj).size() == 0) || (obj instanceof Object[]
				&& ((Object[]) obj).length == 0)) {
			return "[]";
		}
		return JSON
				.toJSONString(obj, SerializeConfig.getGlobalInstance(), include, SerializerFeature.WriteNullBooleanAsFalse,
						SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteNullNumberAsZero,
						SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteMapNullValue,
						SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNullListAsEmpty);
	}

	/**
	 * 将指定格式json字符串转换成map
	 *
	 * @param jsonStr 输入的json字符串,格式：
	 *                {"param1":"value1","param2":"value2"}
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseJson2Map(String jsonStr) {
		if (!StringUtils.isEmpty(jsonStr)) {
			return null;
		}
		try {
			Map<String, String> map = JSON.parseObject(jsonStr, Map.class);
			return map;
		} catch (Exception e) {
			return null;
		}
	}
}
