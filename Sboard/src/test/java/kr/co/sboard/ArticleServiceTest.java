package kr.co.sboard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import kr.co.sboard.service.ArticleService;

@SpringBootTest
public class ArticleServiceTest {
	
	@Autowired
	private ArticleService service;
	
	@Test
	public void fileUpload() {
	}

}
