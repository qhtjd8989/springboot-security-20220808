package com.study.security_bosung.handler.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TimerAop {
	// Aop 관점분리
	private final Logger LOGGER = LoggerFactory.getLogger(getClass()); // TimerAop.class, this.getclass랑 같음
	
	@Pointcut("execution(* com.study.security_bosung.web.controller..*.*(..))")// *: 접근지정자(생략), return타입
	private void pointCut() {}
	
	@Pointcut("@annotation(com.study.security_bosung.handler.aop.annotation.Timer)")
	private void enableTimer() {}
	
	@Around("pointCut() && enableTimer()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		Object result = joinPoint.proceed(); // 호출 -> 실행 -> 결과를 result에 담음
		
		stopWatch.stop();
		
		LOGGER.info(">>>>> {}({}) 메소드 실행 시간: {}ms",
				joinPoint.getSignature().getDeclaringTypeName(),
				joinPoint.getSignature().getName(),
				stopWatch.getTotalTimeSeconds());
		return result;
	}
}
