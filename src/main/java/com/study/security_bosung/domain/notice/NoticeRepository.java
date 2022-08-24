package com.study.security_bosung.domain.notice;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoticeRepository {
	public int saveNotice(Notice notice) throws Exception;
	public int saveNoticeFiles(List<NoticeFile> list) throws Exception;
}
