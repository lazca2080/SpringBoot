package kr.co.farmstory.controller;

import kr.co.farmstory.entity.UserEntity;
import kr.co.farmstory.security.MyUserDetails;
import kr.co.farmstory.service.BoardService;
import kr.co.farmstory.vo.BoardVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Controller
public class MainController {

    @Autowired
    private BoardService service;

    @GetMapping(value = {"/", "index"})
    public String index(@AuthenticationPrincipal MyUserDetails myuser, Model model){
        UserEntity user = null;
        if(myuser != null){ user = myuser.getUser(); }
        model.addAttribute("user", user);

        Map<String, List<BoardVO>> map = service.selectIndex();
        model.addAttribute("index", map);

        return "index";
    }

}
