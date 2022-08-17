package com.study.security_bosung.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.study.security_bosung.service.auth.PrincipalDetails;

@Controller
public class Pagecontroller {
	
	// server side render
	@GetMapping({"/", "/index"})
	public String loadIndex(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		model.addAttribute("principal", principalDetails);
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
