package kr.co.sboard.dao;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.co.sboard.vo.TermsVO;
import kr.co.sboard.vo.UserVO;

@Mapper
@Repository
public interface UserDAO {
	
	// 회원 관리
	public int insertUser(UserVO vo);
	public List<UserVO> selectUser();
	public UserVO selectUsers(String uid);
	public int updateUser(UserVO vo);
	public int deleteUser(String uid);
	
	// 약관
	public TermsVO selectTerms();

}
