package com.stylefeng.guns.core.util;

import com.stylefeng.guns.core.domain.Result;
import com.stylefeng.guns.core.enums.GunsExceptionEnum;
import com.stylefeng.guns.core.enums.ServiceExceptionEnum;

/**
* create by guanqing
* 2018年7月12日 下午4:51:04
*/

public class ResultUtil {
	
	public static Result<Object> success(Object object){
		Result<Object> result = new Result<>();
		result.setCode(GunsExceptionEnum.REQUEST_SUCCESS.getCode());
		result.setMessage(GunsExceptionEnum.REQUEST_SUCCESS.getMessage());
		result.setData(object);
		return result;
	}
	
	public static Result<Object> success(){
		return success(null);
	}

	public static Result<Object> failure(Integer code, String message){
		Result<Object> result = new Result<>();
		result.setCode(code);
		result.setMessage(message);
		return result;
	}
	
	public static Result<Object> failure(ServiceExceptionEnum resultEnum){
		return failure(resultEnum.getCode(),resultEnum.getMessage());
	}
}
