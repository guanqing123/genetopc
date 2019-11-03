package com.stylefeng.guns.core.base.tips;

import com.stylefeng.guns.core.exception.ServiceExceptionEnum;

/**
 * 返回给前台的错误提示
 *
 * @author guanqing
 * @date 2018年7月19日 上午11:04:22
 */
public class ErrorTip extends Tip {

    public ErrorTip(int code, String message) {
        super();
        this.code = code;
        this.message = message;
    }
    
    public ErrorTip(ServiceExceptionEnum exceptionEnum) {
    	super();
    	this.code = exceptionEnum.getCode();
    	this.message = exceptionEnum.getMessage();
    }
}
