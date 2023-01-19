package kr.co.sboard.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.sboard.service.UserService;
import kr.co.sboard.vo.TermsVO;
import kr.co.sboard.vo.UserVO;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private PasswordEncoder encoder;
	
	// 회원 관리
	@GetMapping("user/login")
	public String login(Principal principal) {
		
		if(principal != null) {
			return "redirect:/list";
		}else{
			return "user/login";
		}
	}
	@GetMapping("user/register")
	public String register() {
		return "user/register";
	}
	@PostMapping("user/register")
	public String register(UserVO vo, HttpServletRequest req) {
		
		vo.setRegip(req.getRemoteAddr());
		
		log.info("vo : " + vo);
		
		service.insertUser(vo);
		return "redirect:/user/login?success=101";
	}
	@GetMapping("user/modify")
	public String modify(String uid, Model model) {
		UserVO user = service.selectUsers(uid);
		model.addAttribute("user", user);
		return "user/modify"; 
	}
	@GetMapping("user/delete")
	public String delete() {
		return "user/delete";
	}
	
	// 약관
	@GetMapping("user/terms")
	public String terms(Model model) {
		
		TermsVO terms = service.selectTerms();
		model.addAttribute("terms", terms);
		
		return "user/terms";
	}
	
	// 아이디 중복 확인
	@ResponseBody
	@GetMapping("user/checkUid")
	public Map<String, Integer> checkUid(@RequestParam("uid") String uid) {
		
		int result = service.countByUid(uid);
		
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("result", result);
		
		return resultMap;
	}
	
	// 별명 중복 확인
	@ResponseBody
	@GetMapping("user/{nick}")
	public Map<String, Integer> checkNick(@PathVariable("nick") String nick) {
		
		int result = service.countByNick(nick);
		
		Map<String, Integer> resultMap = new HashMap<>();
		resultMap.put("result", result);
		
		log.info("nick : " + nick);
		
		return resultMap;
	}
}
