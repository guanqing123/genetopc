package com.stylefeng.guns.core.domain;

import lombok.Data;

/**
* create by guanqing
* 2018年7月12日 下午4:45:35
*/
@Data
public class Result<T> {
	
	/** 错误码. */
	private Integer code;
	
	/** 提示信息. */
	private String message;
	
	/** 具体的内容. */
	private T data;
}
