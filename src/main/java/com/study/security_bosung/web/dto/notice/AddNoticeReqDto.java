package com.study.security_bosung.web.dto.notice;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AddNoticeReqDto {
	private String noticeTitle;
	private int userCode;
	private String ir1;
	private List<MultipartFile> file; // MultipartFile: 파일을 받을수 있는 객체
}
