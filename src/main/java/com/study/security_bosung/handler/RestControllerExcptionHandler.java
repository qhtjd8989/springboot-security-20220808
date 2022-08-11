package com.study.security_bosung.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.study.security_bosung.handler.excption.CustomValidationApiExcption;
import com.study.security_bosung.web.dto.CMRespDto;

@RestController
@ControllerAdvice
public class RestControllerExcptionHandler {
	
	@ExceptionHandler(CustomValidationApiExcption.class)
	private ResponseEntity<?> error(CustomValidationApiExcption e) {
		return ResponseEntity.badRequest().body(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()));
	}
}
