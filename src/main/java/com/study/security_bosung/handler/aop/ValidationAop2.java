package com.study.security_bosung.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import com.study.security_bosung.handler.excption.CustomValidationApiExcption;

@Aspect
@Component
public class ValidationAop2 {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Pointcut("@annotation(com.study.security_bosung.handler.aop.annotation.ValidCheck2)")// 경로
	private void enableValid() {}
	
	@Before("enableValid()") // before는 JoinPoint사용
	public void validBefore(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs(); // 매개변수들이 들어옴
		
		LOGGER.info(">>>>>>>유효성 검사 중...");
		
		for(Object arg : args) {
			if(arg instanceof BindingResult) { 
				BindingResult bindingResult = (BindingResult) arg;
				
				if(bindingResult.hasErrors()) {
					Map<String, String> errorMap = new HashMap<String, String>();
					
					bindingResult.getFieldErrors().forEach(error -> {
						errorMap.put(error.getField(), error.getDefaultMessage());
					});
					
					throw new CustomValidationApiExcption("유효성 검사 실패", errorMap);
				}
			}
		}
	}
	
	@AfterReturning(value = "enableValid()", returning = "returnObj")
	public void afterReturn(JoinPoint joinPoint, Object returnObj) {
		LOGGER.info("유효성 검사 완료: {}", returnObj); // return이 있을때
	}
}



















