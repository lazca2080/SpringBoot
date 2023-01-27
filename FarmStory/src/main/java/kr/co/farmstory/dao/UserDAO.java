package kr.co.farmstory.dao;

import kr.co.farmstory.vo.TermsVO;
import kr.co.farmstory.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDAO {

    public int insertUser(UserVO vo);
    public UserVO selectUser(String uid);
    public List<UserVO> selectUsers();
    public int modifyUser(String uid);
    public int deleteUser(String uid);
    
    // 약관
    public TermsVO selectTerms();

    // 회원가입
    public int checkUid(String uid);
    public int checkNick(String nick);
    public int checkHp(String hp);
    public int checkEmail(String email);

}
