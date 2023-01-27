package kr.co.farmstory.dao;

import kr.co.farmstory.vo.BoardVO;
import kr.co.farmstory.vo.FileVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface BoardDAO {

    public int insertBoard(BoardVO vo);
    public BoardVO selectBoard(int no);
    public List<BoardVO> selectBoards(@Param("cate") String cate, @Param("start") int start);
    public List<BoardVO> selectIndex();
    public int updateBoard(BoardVO vo);
    public int deleteBoard(int no);

    // 파일
    public int insertFile(FileVO vo);
    public FileVO selectFile(int fno);
    
    // 페이지 번호
    public int selectCountTotal(String cate);
}
