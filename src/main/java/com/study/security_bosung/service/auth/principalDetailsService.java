package com.study.security_bosung.service.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.study.security_bosung.domain.user.User;
import com.study.security_bosung.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class principalDetailsService implements UserDetailsService{

	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userEntity = null;
		
		try {
			userEntity = userRepository.findUserByUsername(username);
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException(username);
		}
		
		if(userEntity == null) {
			throw new UsernameNotFoundException(username + "사용자이름은 사용 할 수 없습니다.");
		}
				
		return new PrincipalDetails(userEntity);
	}
	
	public boolean addUser() {
		User user = User.builder()
				.user_name("최보성")
				.user_email("qhtjd8899@naver.com")
				.user_id("abcd")
				.user_password(new BCryptPasswordEncoder().encode("1234"))
				.user_roles("ROLE_USER, ROLE_MANAGER")
				.build();
		
		try {
			userRepository.save(user);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	 

}
