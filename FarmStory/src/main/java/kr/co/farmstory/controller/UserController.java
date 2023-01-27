package kr.co.farmstory.controller;

import kr.co.farmstory.service.EmailService;
import kr.co.farmstory.service.UserService;
import kr.co.farmstory.vo.TermsVO;
import kr.co.farmstory.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private EmailService emailService;
    
    // 로그인
    @GetMapping("user/login")
    public String login(){ return "user/login"; }

    // 회원가입
    @GetMapping("user/register")
    public String register(){
        return "user/register";
    }

    // 회원가입
    @PostMapping("user/register")
    public String register(UserVO vo, HttpServletRequest req){
        vo.setRegip(req.getRemoteAddr());
        service.insertUser(vo);
        return "redirect:/";
    }

    // 약관
    @GetMapping("user/terms")
    public String terms(Model model){
        TermsVO terms = service.selectTerms();
        model.addAttribute("terms", terms);
        return "user/terms";
    }

    // 아이디 중복확인
    @ResponseBody
    @GetMapping("user/checkUid")
    public Map<String, Integer> checkUid(@RequestParam String uid){
        int result = service.checkUid(uid);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    // 별명 중복확인
    @ResponseBody
    @GetMapping("user/checkNick")
    public Map<String, Integer> checkNick(@RequestParam String nick){
        int result = service.checkNick(nick);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    // 휴대폰 중복확인
    @ResponseBody
    @GetMapping("user/checkHp")
    public Map<String, Integer> checkHp(@RequestParam String hp){
        int result = service.checkHp(hp);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    // 이메일 중복확인
    @ResponseBody
    @GetMapping("user/checkEmail")
    public Map<String, Integer> checkEmail(@RequestParam String email){
        int result = service.checkEmail(email);

        Map<String, Integer> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }
    
    // 휴대폰 인증확인
    @ResponseBody
    @PostMapping("user/emailConfirm")
    public Map<String, String> emailConfirm(@RequestParam String email) throws Exception {

        String confirm = emailService.sendSimpleMessage(email);

        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", confirm);

        return resultMap;
    }
}
