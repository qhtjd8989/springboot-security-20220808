package com.study.security_bosung.web.controller.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.security_bosung.handler.aop.annotation.Log;
import com.study.security_bosung.handler.aop.annotation.Timer;
import com.study.security_bosung.handler.excption.CustomValidationApiExcption;
import com.study.security_bosung.service.auth.AuthService;
import com.study.security_bosung.service.auth.PrincipalDetailsService;
import com.study.security_bosung.web.dto.CMRespDto;
import com.study.security_bosung.web.dto.auth.SignupReqDto;
import com.study.security_bosung.web.dto.auth.UsernameCheckReqDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthRestController {
	
	private final PrincipalDetailsService principalDetailsService;
	private final AuthService authService;
	
	@Log
	@Timer
	@GetMapping("/signup/validation/username")
	public ResponseEntity<?> checkUsername(@Valid UsernameCheckReqDto usernameCheckReqDto, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMessage = new HashMap<String, String>();
			
			bindingResult.getFieldErrors().forEach(error ->{
				errorMessage.put(error.getField(), error.getDefaultMessage());
			});
			throw new CustomValidationApiExcption("유효성 검사 실패", errorMessage);
		}
		
		boolean status = false;
		
		try {
			status = authService.checkUsername(usernameCheckReqDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "서버 오류", status));
		}
		
		return ResponseEntity.ok(new CMRespDto<>(1, "회원가입 가능여부", status));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody @Valid SignupReqDto signupReqDto, BindingResult bindingResult) {
		boolean status = false;
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMessage = new HashMap<String, String>();
			
			bindingResult.getFieldErrors().forEach(error ->{
				errorMessage.put(error.getField(), error.getDefaultMessage());
			});
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "유효성 검사 실패", errorMessage));
		}
		
		try {
			status = principalDetailsService.addUser(signupReqDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "회원가입 실패", status));
		}
		
		return ResponseEntity.ok(new CMRespDto<>(1, "회원가입 성공", status));
	}
}
