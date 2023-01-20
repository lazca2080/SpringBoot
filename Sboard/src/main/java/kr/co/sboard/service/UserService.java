package kr.co.sboard.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.co.sboard.dao.UserDAO;
import kr.co.sboard.repository.UserRepo;
import kr.co.sboard.vo.TermsVO;
import kr.co.sboard.vo.UserVO;

@Service
public class UserService {
	
	@Autowired
	private UserDAO dao;
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	// 회원 관리
	public int insertUser(UserVO vo) {
		vo.setPass(encoder.encode(vo.getPass1()));
		return dao.insertUser(vo);
	};
	public List<UserVO> selectUser() {
		return dao.selectUser();
	};
	public UserVO selectUsers(String uid) {
		return dao.selectUsers(uid);
	};
	public int updateUser(UserVO vo) {
		return dao.updateUser(vo);
	};
	public int deleteUser(String uid) {
		return dao.deleteUser(uid);
	};
	
	// 약관
	public TermsVO selectTerms() {
		return dao.selectTerms();
	}
	
	// 아이디 중복 확인
	public int countByUid(String uid) {
		return repo.countByUid(uid);
	}
	
	// 별명 중복 확인
	public int countByNick(String nick) {
		return repo.countByNick(nick);
	}
	
	// 이메일 중복 확인
	public int countByEmail(String email) {
		return repo.countByEmail(email);
	}
	

}
