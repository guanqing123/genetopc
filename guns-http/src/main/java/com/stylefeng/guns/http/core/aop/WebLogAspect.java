package com.stylefeng.guns.http.core.aop;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.stylefeng.guns.core.support.HttpKit;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class WebLogAspect {

	@Pointcut("execution(public * com.stylefeng.guns.http.web.*.*(..))")
	public void logPointCut() {}
	
	@Before("logPointCut()")
	public void doBefore(JoinPoint joinPoint) {
		// 接收到请求,记录请求内容
		HttpServletRequest request = HttpKit.getRequest();
		
		// 记录下请求内容
		log.info("ip : " + request.getRemoteAddr());
		log.info("请求地址 : " + request.getRequestURI().toString());
		log.info("http method : " + request.getMethod());
		log.info("class method : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		log.info("参数 : " + Arrays.toString(joinPoint.getArgs()));
	}
	
	@AfterReturning(returning = "ret", pointcut = "logPointCut()") // returning的值和doAfterReturning的参数名一致
	public void doAfterReturning(Object ret) {
		// 处理完请求，返回内容
        log.info("返回值 : " + ret);
	}
	
	@Around("logPointCut()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		long startTime = System.currentTimeMillis();
        Object ob = pjp.proceed();// ob 为方法的返回值
        log.info("耗时 : " + (System.currentTimeMillis() - startTime) + " ms");
        return ob;
	}
}
