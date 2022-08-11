package com.study.security_bosung.handler.aop.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME) // 언제 적용시킬지
@Target({ TYPE, METHOD }) // type: type앞에 쓸 수 있다, method: method위에 쓸 수 있다
public @interface Timer {

}
