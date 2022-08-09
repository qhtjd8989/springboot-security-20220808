package com.study.security_bosung.web.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.security_bosung.service.auth.principalDetailsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final principalDetailsService principalDetailsService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup() {
		return ResponseEntity.ok(principalDetailsService.addUser());
	}
}
