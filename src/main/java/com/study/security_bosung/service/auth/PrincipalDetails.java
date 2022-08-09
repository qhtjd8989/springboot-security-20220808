package com.study.security_bosung.service.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.study.security_bosung.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails{

	private static final long serialVersionUID = 1L;
	
	private User user;
	
	public PrincipalDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
//		List<String> roleList = user.getUserRoles();
		
		user.getUserRoles().forEach(role -> {
			grantedAuthorities.add(() -> role);
		});
		
//		for(String role : roleList) {
//			GrantedAuthority authority = new GrantedAuthority() {
//				
//				@Override
//				public String getAuthority() {
//					return role;
//				}
//			};
//			grantedAuthorities.add(authority);
//		}
		return null;
	}

	@Override
	public String getPassword() {
		return user.getUser_password();
	}

	@Override
	public String getUsername() {
		return user.getUser_id();
	}
	
	/*
	 * 계정 만료 여부
	 * true: 계정 만료 안됨
	 * false: 계정 만료
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	
	/*
	 * 계정 잠김 여부(ex: 비밀번호 실패, 일정기간 미접속등...)
	 * true: 계정 잠기지 않음
	 * false: 계정 잠김
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	
	/*
	 * 비밀번호 만료 여부
	 * true: 만료 안됨
	 * false: 만료됨
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	/*
	 * 사용자 활성화 여부
	 * true: 활성화
	 * false: 비활성화
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

}
