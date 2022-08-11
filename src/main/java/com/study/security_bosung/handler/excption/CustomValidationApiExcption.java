package com.study.security_bosung.handler.excption;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class CustomValidationApiExcption extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private Map<String, String> errorMap;
	
	public CustomValidationApiExcption() {
		this("error", new HashMap<String, String>());
	}
	
	public CustomValidationApiExcption(String message) {
		this(message, new HashMap<String, String>());
	}
	
	public CustomValidationApiExcption(String message, Map<String, String> errorMap) {
		super(message);
		this.errorMap = errorMap;
	}

	
	
}
