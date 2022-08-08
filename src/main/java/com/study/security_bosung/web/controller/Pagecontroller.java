package com.study.security_bosung.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Pagecontroller {
	
	@GetMapping({"/", "/index"})
	public String loadIndex() {
		return "index";
	}
	
	@GetMapping("/auth/signin")
	public String loadSignin() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String loadSignup() {
		return "auth/signup";
	}
	
	@GetMapping("/mypage")
	public String loadMyPage() {
		return "mypage";
	}
}
