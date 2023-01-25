package kr.co.farmstory.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping("board/list")
    public String list(String group, String cate, Model model){
        model.addAttribute("group", group);
        model.addAttribute("cate", cate);
        return "board/list";
    }

    @GetMapping("board/modify")
    public String modify(String group, String cate, Model model){
        model.addAttribute("group", group);
        model.addAttribute("cate", cate);
        return "board/modify";
    }

    @GetMapping("board/view")
    public String view(String group, String cate, Model model){
        model.addAttribute("group", group);
        model.addAttribute("cate", cate);
        return "board/view";
    }

    @GetMapping("board/write")
    public String write(String group, String cate, Model model){
        model.addAttribute("group", group);
        model.addAttribute("cate", cate);
        return "board/write";
    }
}
