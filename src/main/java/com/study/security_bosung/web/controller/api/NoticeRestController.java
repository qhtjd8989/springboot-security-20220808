package com.study.security_bosung.web.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.security_bosung.service.notice.NoticeService;
import com.study.security_bosung.web.dto.notice.AddNoticeReqDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/notice")
@Slf4j
@RequiredArgsConstructor
public class NoticeRestController {
	
	private final NoticeService noticeService; 
	
	// 파일의 경로를 변수처럼 사용할 수 있음
	
	
	@PostMapping("")
	public ResponseEntity<?> addNotice(AddNoticeReqDto addNoticeReqDto) {
		log.info(">>>>>>{}", addNoticeReqDto);
		log.info(">>>>>> fileName: {}", addNoticeReqDto.getFile().get(0).getOriginalFilename());
		
		
		
		try {
			noticeService.addNotice(addNoticeReqDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
