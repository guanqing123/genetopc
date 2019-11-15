package com.stylefeng.guns.http.core.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.stylefeng.guns.core.aop.BaseControllerExceptionHandler;
import com.stylefeng.guns.core.base.tips.ErrorTip;
import com.stylefeng.guns.core.exception.FileUploadException;
import com.stylefeng.guns.core.util.OssUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局的的异常拦截器（拦截所有的控制器）（带有@RequestMapping注解的方法上都会拦截）
 *
 * @author fengshuonan
 * @date 2016年11月12日 下午3:19:56
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends BaseControllerExceptionHandler {

	@Autowired
	private OssUtil ossUtil;
	/**
	 * 拦截 fileupload相关异常
	 * @param e
	 * @return
	 */
	@ExceptionHandler(FileUploadException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorTip fileException(FileUploadException e) {
		log.error("FileUpload异常:", e);
		ossUtil.deleteObjects(e.getPaths());
		return new ErrorTip(500, e.getMessage());
	}
}
