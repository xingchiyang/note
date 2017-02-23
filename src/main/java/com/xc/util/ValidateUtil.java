package com.xc.util;

import com.xc.exception.NoteException;
import com.xc.exception.NoteExpCode;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 参数验证工具类
 */
public class ValidateUtil {

	/**
	 * 验证字符串为null或者""
	 *
	 * @param validateParam
	 * @param msg
	 */
	public static void validateStrBlank(String validateParam, String msg) {
		if (StringUtils.isEmpty(validateParam)) {
			throw new NoteException(NoteExpCode.EXP_CODE_PARAM, msg);
		}
	}

	/**
	 * 验证对象为null
	 *
	 * @param obj
	 */
	public static void validateObjectNull(Object obj, String msg) {
		if (obj == null) {
			throw new NoteException(NoteExpCode.EXP_CODE_PARAM, msg);
		}
	}

	/**
	 * 验证对象为null
	 *
	 * @param obj
	 */
	public static void validateObjectArrNull(Object obj[], String msg) {
		if (obj == null || obj.length == 0) {
			throw new NoteException(NoteExpCode.EXP_CODE_PARAM, msg);
		}
	}

	/**
	 * 验证整形对象为null
	 *
	 * @param validateParam
	 * @param msg
	 */
	public static void validateIntNull(Integer validateParam, String msg) {
		if (validateParam == null) {
			throw new NoteException(NoteExpCode.EXP_CODE_PARAM, msg);
		}
	}

	public static void validateTrue(boolean flag, String msg) {
		if (!flag) {
			throw new NoteException(NoteExpCode.EXP_CODE_PARAM, msg);
		}
	}

	public static void validateTrue(boolean flag, String expCode, String msg) {
		if (!flag) {
			throw new NoteException(expCode, msg);
		}
	}

	/**
	 * 验证UUID不为null，并且长度等于32
	 *
	 * @param validateParam
	 * @param msg
	 */
	public static void validateUUID(String validateParam, String msg) {
		if (validateParam == null || validateParam.length() != 32) {
			throw new NoteException(NoteExpCode.EXP_CODE_PARAM, msg);
		}
	}

	public static void validateListIsEmpty(List<?> validatelist, String msg) {
		if (validatelist == null || validatelist.size() <= 0) {
			throw new NoteException(NoteExpCode.EXP_CODE_PARAM, msg);
		}
	}

}
