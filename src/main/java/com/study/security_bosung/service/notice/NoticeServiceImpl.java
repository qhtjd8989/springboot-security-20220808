package com.study.security_bosung.service.notice;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.study.security_bosung.web.dto.notice.AddNoticeReqDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class NoticeServiceImpl implements NoticeService{

	@Value("${file.path}")
	private String filePath;

	@Override
	public int addNotice(AddNoticeReqDto addNoticeReqDto) throws Exception {
		String originalFilename = addNoticeReqDto.getFile().get(0).getOriginalFilename();
		
		if(originalFilename.isBlank()) {
			
		}else {
			String tempFilename = UUID.randomUUID().toString().replaceAll("-", "")+ "_" + originalFilename;; // 랜덤한 문자열 생성(고유한 key값 생성)
			log.info(tempFilename);
			
		}
		
		
		return 0;
	}

}
