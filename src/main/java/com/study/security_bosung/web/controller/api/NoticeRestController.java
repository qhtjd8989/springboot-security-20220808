package com.study.security_bosung.web.controller.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.security_bosung.service.notice.NoticeService;
import com.study.security_bosung.web.dto.CMRespDto;
import com.study.security_bosung.web.dto.notice.AddNoticeReqDto;
import com.study.security_bosung.web.dto.notice.GetNoticeResponseDto;

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
		
		int noticeCode = 0;

		try {
			noticeCode = noticeService.addNotice(addNoticeReqDto);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "Failed to write", noticeCode));
		}
		return ResponseEntity.ok(new CMRespDto<>(1, "completing creation", noticeCode));
	}
	
	@GetMapping("/{noticeCode}")
	public ResponseEntity<?> getNotice(@PathVariable int noticeCode) {
		GetNoticeResponseDto getNoticeResponseDto = null;
		try {
			getNoticeResponseDto = noticeService.getNotice(null, noticeCode);
			if(getNoticeResponseDto == null) {
				return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "request failes", null));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "database failes", null));
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, "lookup successful", getNoticeResponseDto));
	}
	
	@GetMapping("/{flag}/{noticeCode}")
	public ResponseEntity<?> geNotice(@PathVariable String flag, @PathVariable int noticeCode) {
		GetNoticeResponseDto getNoticeResponseDto = null;
		if (flag.equals("pre") || flag.equals("next")) {
			try {
				getNoticeResponseDto = noticeService.getNotice(flag, noticeCode);
			} catch (Exception e) {
				e.printStackTrace();
				return ResponseEntity.internalServerError().body(new CMRespDto<>(-1, "database failes", null));
			}
		}else {
			return ResponseEntity.badRequest().body(new CMRespDto<>(-1, "request failes", null));
		}
		
		return ResponseEntity.ok().body(new CMRespDto<>(1, "lookup successful", getNoticeResponseDto));
	}
	
}
