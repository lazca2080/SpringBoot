package kr.co.ch08.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kr.co.ch08.service.User1Service;
import kr.co.ch08.vo.User1VO;

@Controller
public class User1Controller {
	
	@Autowired
	private User1Service service;
	
	@GetMapping("/user1/login")
	public String login() {
		return "/user1/login";
	}
	
	@PostMapping("/user1/login")
	public String login(String uid, String pass, HttpSession session) {
		
		User1VO user = service.selectUser1(uid, pass);
		
		System.out.println(user);
		
		if(user != null) {
			// 회원이 맞는경우
			session.setAttribute("sessUser", user);
			return "redirect:/user1/loginSuccess";
		}else {
			// 회원이 아닐경우
			return "redirect:/user1/login?success=101";
		}
	}
	
	@GetMapping("/user1/loginSuccess")
	public String loginSuccess() {
		return "/user1/loginSuccess";
	}
	
	@GetMapping("/user1/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("sessUser");
		session.invalidate();
		return "redirect:/user1/login?success=102";
	}
}
