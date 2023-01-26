package kr.co.farmstory.controller;

import kr.co.farmstory.entity.UserEntity;
import kr.co.farmstory.security.MyUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping(value = {"/", "index"})
    public String index(@AuthenticationPrincipal MyUserDetails myuser, Model model){
        UserEntity user = null;
        if(myuser != null){ user = myuser.getUser(); }
        model.addAttribute("user", user);

        return "index";
    }

}
