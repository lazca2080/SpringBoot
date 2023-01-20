package kr.co.sboard.service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.sboard.dao.ArticleDAO;
import kr.co.sboard.repository.ArticleRepo;
import kr.co.sboard.vo.ArticleVO;
import kr.co.sboard.vo.FileVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArticleService {
	
	@Autowired
	private ArticleDAO dao;
	
	@Autowired
	private ArticleRepo repo;
	
	// 글 삽입
	@Transactional
	public int insertArticle(ArticleVO vo) {
		
		int result = 0;
		MultipartFile file = vo.getFname();
		
		// 파일 등록
		if(vo.getFname().isEmpty()) {
			// 글 등록
			vo.setFile(0);
			result = dao.insertArticle(vo);
		}else {
			// 글 등록
			vo.setFile(1);
			result = dao.insertArticle(vo);
			
			// 파일 업로드
			FileVO fvo = fileUpload(file);
			fvo.setParent(vo.getNo());
			
			// 파일 등록
			dao.insertFile(fvo);
		}
		
		return result;
	};
	
	// 글 선택
	@Transactional
	public ArticleVO selectArticle(int no) {
		ArticleVO vo = dao.selectArticle(no);
		dao.updateArticleHit(no);
		return vo;
	};
	
	// 글 전체 불러오기
	public List<ArticleVO> selectArticles(int start) {
		return dao.selectArticles(start);
	};
	
	// 글 수정
	public int updateArticle(ArticleVO vo) {
		return dao.updateArticle(vo);
	};
	
	// 글 삭제	
	public int deleteArticle(int no) {
		return dao.deleteArticle(no);
	};
	
	// 글 조회수
	public int updateArticleHit(int no) {
		return dao.updateArticleHit(no);
	}
	
	// 서비스
	@Value("${spring.servlet.multipart.location}")
	private String uploadPath;
	
	// 파일 업로드
	public FileVO fileUpload(MultipartFile file) {
		
		FileVO fvo = new FileVO();
		
		// 시스템 경로
		String path = new File(uploadPath).getAbsolutePath();
		
		// 새 파일명 생성
		String oName = file.getOriginalFilename();
		String ext   = oName.substring(oName.lastIndexOf("."));
		String nName = UUID.randomUUID().toString()+ext;
		
		File file1 = new File(path);
		if(!file1.exists()) {
			file1.mkdir();
		}
		
		// 파일 저장
		try {
			file.transferTo(new File(path, nName));
		} catch (IllegalStateException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		
		fvo.setOriName(oName);
		fvo.setNewName(nName);
		
		return fvo;
	}
	
	// 파일 다운로드
	public ResponseEntity<Resource> fileDownload(FileVO vo) throws IOException {
		
		Path path = Paths.get(uploadPath + vo.getNewName());
		String contentType = Files.probeContentType(path);
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(ContentDisposition
										.builder("attachment")
										.filename(vo.getOriName(), StandardCharsets.UTF_8)
										.build());
		
		headers.add(HttpHeaders.CONTENT_TYPE, contentType);
		
		Resource resource = new InputStreamResource(Files.newInputStream(path));
		
		return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	}
	
	// 파일 선택
	@Transactional
	public FileVO selectfile(int fno) {
		FileVO vo = dao.selectFile(fno);
		dao.updateFileDownload(fno);
		return vo;
	}
	
	// 파일 download 업데이트
	public int updateFileDownload(int no) {
		return dao.updateFileDownload(no);
	}
	
	// 페이지 시작값
	public int getLimitStart(int currentPage) {
		return (currentPage - 1) * 10;
	}
	
	// 현재 페이지
	public int getCurrentPage(String pg) {
		int currentPage = 1;
		
		if(pg != null) {
			currentPage = Integer.parseInt(pg);
		}
		return currentPage;
	}
	
	// 전체 게시물 갯수
	public long getTotalCount() {
		return dao.selectCountTotal();
	}
	
	// 마지막 페이지 번호
	public int getLastPageNum(long total) {
		int lastPage = 0;
		
		if(total % 10 == 0) {
			lastPage = (int) (total / 10);
		}else {
			lastPage = (int) (total / 10) + 1;
		}
		return lastPage;
	}
	
	// 페이지 시작번호
	public int getPageStartNum(long total, int start) {
		return (int) (total - start);
	}
	
	// 페이지 그룹
	public int[] getPageGroup(int currentPage, int lastPage) {
		int groupCurrent = (int) Math.ceil(currentPage / 10.0);
		int groupStart   = (groupCurrent-1)*10 + 1;
		int groupEnd = groupCurrent*10;
		
		if(groupEnd > lastPage) {
			groupEnd = lastPage;
		}
		
		int[] groups = {groupStart, groupEnd};
		
		return groups;
	}
}
