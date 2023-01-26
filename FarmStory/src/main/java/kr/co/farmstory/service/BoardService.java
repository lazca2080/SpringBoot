package kr.co.farmstory.service;

import kr.co.farmstory.dao.BoardDAO;
import kr.co.farmstory.vo.BoardVO;
import kr.co.farmstory.vo.FileVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardDAO dao;

    // 글 작성
    public int insertBoard(BoardVO vo) {

        int result = 0;
        MultipartFile file = vo.getFname();

        if(vo.getFname().isEmpty()){
            // 글 등록
            vo.setFile(0);
            result = dao.insertBoard(vo);
        }else{
            // 글 등록
            vo.setFile(1);
            result = dao.insertBoard(vo);

            FileVO fvo = fileUpload(file);
            fvo.setParent(vo.getNo());

            // 파일 등록
            dao.insertFile(fvo);
        }

        return result;
    };
    
    // 해당 글 선택
    public BoardVO selectBoard(int no) { return dao.selectBoard(no); };

    // 글 목록
    public List<BoardVO> selectBoards(@RequestParam("cate") String cate, @RequestParam("start") int start) {

        return dao.selectBoards(cate, start);
    };

    // 글 수정
    public int updateBoard(BoardVO vo) {
        return dao.updateBoard(vo);
    };

    // 글 삭제
    public int deleteBoard(int no) {
        return dao.deleteBoard(no);
    };
    
    // 서비스

    // 파일 경로
    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    // 파일 업로드
    public FileVO fileUpload(MultipartFile file){
        
        FileVO fvo = new FileVO();
        
        // 시스템 경로
        String path = new File(uploadPath).getAbsolutePath();

        // 새 파일명 생성
        String oriName = file.getOriginalFilename();
        String ext = oriName.substring(oriName.lastIndexOf("."));
        String newName = UUID.randomUUID().toString()+ext;
        
        // 저장 폴더가 없다면 생성
        File checkFolder = new File(path);
        if(!checkFolder.exists()){
            checkFolder.mkdir();
        }
        
        // 파일 저장
        try {
            file.transferTo(new File(path, newName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        fvo.setOriName(oriName);
        fvo.setNewName(newName);

        return fvo;
    }
    
    // 파일 선택
    public FileVO selectFile(int fno){
        return dao.selectFile(fno);
    }
    
    // 파일 다운로드
    public ResponseEntity<Resource> fileDownload(FileVO vo) throws IOException {
        Path path = Paths.get(uploadPath, vo.getNewName());
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
    
    // 페이지 시작값
    public int getLimitStart(int currentPage){
        return (currentPage - 1 ) * 10;
    }

    // 현재 페이지
    public int getCurrentPage(String pg){
        int currentPage = 1;

        if(pg != null){
            currentPage = Integer.parseInt(pg);
        }

        return currentPage;
    }
    
    // 전체 게시물 갯수
    public long getTotalCount(){
        return dao.selectCountTotal();
    }

    // 마지막 페이지 번호
    public int getLastPageNum(long total){
        int lastPage = 0;
        if(total % 10 == 0){
            lastPage = (int) (total/10);
        }else{
            lastPage = (int) (total/10)+1;
        }
        return lastPage;
    }

    // 페이지 시작 번호
    public int getPageStartNum(long total, int start){
        return (int) (total - start);
    }

    // 페이지 그룹
    public int[] getPageGroup(int currentPage, int lastPage){
        int groupCurrent = (int) Math.ceil(currentPage/10.0);
        int groupStart = (groupCurrent - 1) * 10 + 1;
        int groupEnd = groupCurrent * 10;

        if(groupEnd > lastPage){
            groupEnd = lastPage;
        }

        int[] groups = {groupStart, groupEnd};

        return groups;
    }
}
