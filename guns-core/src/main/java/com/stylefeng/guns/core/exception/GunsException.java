package com.stylefeng.guns.core.exception;

/**
 * 封装guns的异常
 *
 * @author fengshuonan
 * @Date 2017/12/28 下午10:32
 */
public class GunsException extends RuntimeException {

	private static final long serialVersionUID = 3569127086593812453L;

	private Integer code;

    private String message;

    public GunsException(ServiceExceptionEnum serviceExceptionEnum) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
    }
    
    public GunsException(Integer code, String message) {
    	this.code = code;
    	this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
