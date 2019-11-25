package com.stylefeng.guns.http.core.weixin.tool;

import java.util.Objects;

import com.stylefeng.guns.http.core.weixin.wxobj.result.BaseResult;

/**
* create by guanqing
* 2019年11月25日 下午9:02:38
*/
public class ResultCheck {

	public static final String SUCCESS_CODE = "0";
	public static final String SUCCESS_MESSAGE = "ok";
	
	/**
	 * 检查返回结果是否正确
	 * @param result
	 * @return
	 */
	public static boolean isSuccess(BaseResult result) {
		return Objects.nonNull(result) && (SUCCESS_CODE.equals(result.getErrCode()) || result.getErrCode() == null || "".equals(result.getErrCode()));
	}
}
