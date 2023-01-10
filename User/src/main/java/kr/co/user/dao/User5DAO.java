package kr.co.user.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.user.vo.User5VO;

@Repository
public class User5DAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public void insertUser5(User5VO vo) {
		mybatis.insert("user5.insertUser5", vo);
	}
	
	public User5VO selectUser5(String uid) {
		return mybatis.selectOne("user5.selectUser5", uid);
	}
	
	public List<User5VO> selectUser5s() {
		return mybatis.selectList("user5.selectUser5s");
	}
	
	public void updateUser5(User5VO vo) {
		mybatis.update("user5.updateUser5", vo);
	}
	
	public void deleteUser5(String uid) {
		mybatis.delete("user5.deleteUser5", uid);
	}
}
