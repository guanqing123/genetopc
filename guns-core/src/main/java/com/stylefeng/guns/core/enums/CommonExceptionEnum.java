package com.stylefeng.guns.core.enums;

import com.stylefeng.guns.core.exception.ServiceExceptionEnum;

public enum CommonExceptionEnum implements ServiceExceptionEnum {
		FILE_OSS_ERROR(400, "图片上传阿里云出错")
	;
	
	private CommonExceptionEnum(int code, String message) {
		// TODO Auto-generated constructor stub
		this.code = code;
		this.message = message;
	}

	private Integer code;

	private String message;
	
	@Override
	public Integer getCode() {
		// TODO Auto-generated method stub
		return code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}

}
