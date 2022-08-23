package com.study.security_bosung.service.notice;

import com.study.security_bosung.web.dto.notice.AddNoticeReqDto;

public interface NoticeService {
	
	public int addNotice(AddNoticeReqDto addNoticeReqDto) throws Exception;
}
