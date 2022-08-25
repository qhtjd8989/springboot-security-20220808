package com.study.security_bosung.service.notice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.study.security_bosung.domain.notice.Notice;
import com.study.security_bosung.domain.notice.NoticeFile;
import com.study.security_bosung.domain.notice.NoticeRepository;
import com.study.security_bosung.web.dto.notice.AddNoticeReqDto;
import com.study.security_bosung.web.dto.notice.GetNoticeResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{
	
	@Value("${file.path}")
	private String filePath;
	
	private final NoticeRepository noticeRepository;

	@Override
	public int addNotice(AddNoticeReqDto addNoticeReqDto) throws Exception {
		Predicate<String> predicate = (filename) -> !filename.isBlank();
		
		Notice notice = null;
		
		
		notice = Notice.builder()
				.notice_title(addNoticeReqDto.getNoticeTitle())
				.user_code(addNoticeReqDto.getUserCode())
				.notice_content(addNoticeReqDto.getIr1())
				.build();
		
		noticeRepository.saveNotice(notice); // code를 가지고있음
		
		if(predicate.test(addNoticeReqDto.getFile().get(0).getOriginalFilename())) { // 파일명이 비어있지 않은 경우
			List<NoticeFile> noticeFiles = new ArrayList<NoticeFile>();
			
			for(MultipartFile file : addNoticeReqDto.getFile()){
				String originalFilename = file.getOriginalFilename();
				String tempFilename = UUID.randomUUID().toString().replaceAll("-", "")+ "_" + originalFilename;; // 랜덤한 문자열 생성(고유한 key값 생성)
				log.info(tempFilename);
				Path uploadPath = Paths.get(filePath, "notice/" + tempFilename);
				
				File f = new File(filePath + "notice");
				if(!f.exists()) {
					f.mkdirs();
				}
				
				try {
					Files.write(uploadPath, file.getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
				// 리스트 생성
				noticeFiles.add(NoticeFile.builder().notice_code(notice.getNotice_code()).file_name(tempFilename).build());
			}
			noticeRepository.saveNoticeFiles(noticeFiles);
		}
		
		return notice.getNotice_code(); // insert된 notice의 code
	}

	@Override
	public GetNoticeResponseDto getNotice(String flag, int noticeCode) throws Exception {
		GetNoticeResponseDto getNoticeResponseDto = null;
		
		Map<String, Object> reqMap = new HashMap<String, Object>();
		reqMap.put("flag", flag);
		reqMap.put("notice_code", noticeCode);
		
		List<Notice> notices = noticeRepository.getNotice(reqMap); // list를 받는다
		
		if(!notices.isEmpty()) { // 비어있지 않으면 동작
			List<Map<String, Object>> downloadfiles = new ArrayList<Map<String, Object>>();
			notices.forEach(notice -> {
				Map<String, Object> fileMap = new HashMap<String, Object>();
				fileMap.put("fileCode", notice.getFile_code());
				
				String fileName = notice.getFile_name();
				fileMap.put("fileName", fileName.substring(fileName.indexOf("_") + 1)); // _ 다음부터 잘라서 가져감
				downloadfiles.add(fileMap);
			});
			
			Notice firstNotice = notices.get(0);
			
			getNoticeResponseDto = GetNoticeResponseDto.builder()
					.noticeCode(firstNotice.getNotice_code())
					.noticeTitle(firstNotice.getNotice_title())
					.userCode(firstNotice.getUser_code())
					.userId(firstNotice.getUser_id())
					.createDate(firstNotice.getCreate_date().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
					.noticeCount(firstNotice.getNotice_count())
					.noticeContent(firstNotice.getNotice_content())
					.downloadFiles(downloadfiles)
					.build();
		}
		
		return getNoticeResponseDto;
	}

}
