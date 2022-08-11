package com.study.security_bosung.web.dto.auth;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.study.security_bosung.domain.user.User;

import lombok.Data;

@Data
public class SignupReqDto {
	@NotBlank
	@Pattern(regexp = "^[가-힇]*$", message = "한글만 입력 가능합니다.")
	private String name;
	
	@NotBlank
	@Email
	private String email;
	
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,12}$") // {1} : 첫글자는 무조건 영문, {4,12} : 4부터 12글자까지
	private String username;
	
	@NotBlank
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[-~!@#$%^&*_+=])[a-zA-Z\\d-~!@#$%^&*_+=]{8,16}$") 
	// ^: 시작표시 []: 문자들의 집합 $:끝표시 ?: 임의의 값표시 \\d: 숫자를 포함할 수 있다 .*: 무수히 많은 문자
	private String password;
	
	@AssertTrue(message = "아이디 중복확인이 되지 않았습니다.")
	private boolean checkUsernameFlag;
	
	public User toEntity() {
		return User.builder()
				.user_name(name)
				.user_email(email)
				.user_id(username)
				.user_password(new BCryptPasswordEncoder().encode(password))
				.user_roles("ROLE_USER")
				.build();
	}
}
