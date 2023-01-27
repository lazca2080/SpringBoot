package kr.co.farmstory.service;

import kr.co.farmstory.dao.UserDAO;
import kr.co.farmstory.vo.TermsVO;
import kr.co.farmstory.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDAO dao;

    @Autowired
    private PasswordEncoder encoder;
    
    // 회원가입
    public int insertUser(UserVO vo) {
        vo.setPass(encoder.encode(vo.getPass1()));
        return dao.insertUser(vo);
    };

    // 회원 찾기
    public UserVO selectUser(String uid) {
        return dao.selectUser(uid);
    };
    
    public List<UserVO> selectUsers() {
        return dao.selectUsers();
    };
    
    // 약관 
    public TermsVO selectTerms() {
        return dao.selectTerms();
    };

    // 아이디 중복확인
    public int checkUid(String uid){
        return dao.checkUid(uid);
    }

    // 별명 중복확인
    public int checkNick(String nick){
        return dao.checkNick(nick);
    }

    // 휴대폰 중복확인
    public int checkHp(String hp){
        return dao.checkHp(hp);
    }

    // 메일 중복확인
    public int checkEmail(String email){
        return dao.checkEmail(email);
    }
}
