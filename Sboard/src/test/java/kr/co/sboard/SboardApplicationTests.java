package kr.co.sboard;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import kr.co.sboard.dao.UserDAO;
import kr.co.sboard.repository.UserRepo;
import kr.co.sboard.service.ArticleService;
import kr.co.sboard.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class SboardApplicationTests {

	void contextLoads() {
	}
	
	@Autowired
	private UserDAO dao;
	
	@Autowired
	private UserRepo repo;
	
	@Test
	public void insertTest() {
		
		UserVO vo = UserVO.builder()
				.uid("test101")
				.pass("1234")
				.name("테스트")
				.nick("테스트")
				.email("test@test.com")
				.hp("test")
				.regip("0:0:0:0:0:0:0:1")
				.build();
		
		int result = dao.insertUser(vo);
		
		log.info("result : " + result);
		
	}
	
	public void countUid() {
		int result = repo.countByUid("pjh");
		log.info("result : " + result);
	}
}
