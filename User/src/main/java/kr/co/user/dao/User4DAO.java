package kr.co.user.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.user.vo.User4VO;

@Repository
public class User4DAO {
	
	@Autowired
	private SqlSessionTemplate mybatis;
	
	public void insertUser4(User4VO vo) {
		mybatis.insert("user4.insertUser4", vo);
	}
	
	public User4VO selectUser4(int seq) {
		return mybatis.selectOne("user4.selectUser4", seq);
	}

	public List<User4VO> selectUser4s() {
		return mybatis.selectList("user4.selectUser4s");
	}
	
	public void updateUser4(User4VO vo) {
		mybatis.update("user4.updateUser4", vo);
	}
	
	public void deleteUser4(int seq) {
		mybatis.delete("user4.deleteUser4", seq);
	}
}
