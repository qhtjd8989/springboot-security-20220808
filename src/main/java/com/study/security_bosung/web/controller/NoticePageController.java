package com.study.security_bosung.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice")
public class NoticePageController {
	
	@GetMapping("/list")
	public String loadNoticeList() {
		return "notice/notice";
	}
	
	@GetMapping("/addition")
	@CrossOrigin
	public String loadNoticeInsert() {
		return "notice/notice_insert";
	}
	
	@GetMapping("/{noticeCode}")
	public String loadNoticeDetail() {
		return "notice/notice_detail";
	}
	
	@GetMapping("/modification/{noticeCode}")
	public String loadNoticeModify() {
		return "notice/notice_modify";
	}
}
