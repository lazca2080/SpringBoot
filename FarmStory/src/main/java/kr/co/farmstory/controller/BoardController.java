package kr.co.farmstory.controller;

import kr.co.farmstory.entity.UserEntity;
import kr.co.farmstory.security.MyUserDetails;
import kr.co.farmstory.service.BoardService;
import kr.co.farmstory.vo.BoardVO;
import kr.co.farmstory.vo.FileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
@Slf4j
public class BoardController {

    @Autowired
    private BoardService service;

    // 글 목록
    @GetMapping("board/list")
    public String list(String group, String cate, Model model, String pg, @AuthenticationPrincipal MyUserDetails myuser){
        UserEntity user = null;
        if(myuser != null){ user = myuser.getUser(); }
        model.addAttribute("user", user);

        int currentPage   = service.getCurrentPage(pg);
        int start         = service.getLimitStart(currentPage);
        long total        = service.getTotalCount();
        int lastPage      = service.getLastPageNum(total);
        int pageStartNum  = service.getPageStartNum(total, start);
        int[] groups      = service.getPageGroup(currentPage, lastPage);

        List<BoardVO> boards = service.selectBoards(cate, start);

        model.addAttribute("group", group);
        model.addAttribute("cate", cate);
        model.addAttribute("articles", boards);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("pageStartNum", pageStartNum);
        model.addAttribute("groups", groups);

        return "board/list";
    }

    // 글 수정
    @GetMapping("board/modify")
    public String modify(String group, String cate, Model model, int no, @AuthenticationPrincipal MyUserDetails myuser){
        UserEntity user = null;
        if(myuser != null){ user = myuser.getUser(); }
        model.addAttribute("user", user);

        model.addAttribute("group", group);
        model.addAttribute("cate", cate);

        BoardVO article = service.selectBoard(no);
        model.addAttribute("article", article);

        return "board/modify";
    }

    // 글 수정
    @PostMapping("board/modify")
    public String modify(BoardVO vo, String group, String cate, int no, HttpServletRequest req){
        vo.setRegip(req.getRemoteAddr());
        service.updateBoard(vo);
        return "redirect:/board/view?group="+group+"&cate="+cate+"&no="+no;
    }

    // 글 보기
    @GetMapping("board/view")
    public String view(String group, String cate, Model model, int no, @AuthenticationPrincipal MyUserDetails myuser){
        UserEntity user = null;
        if(myuser != null){ user = myuser.getUser(); }
        model.addAttribute("user", user);

        model.addAttribute("group", group);
        model.addAttribute("cate", cate);

        BoardVO article = service.selectBoard(no);

        model.addAttribute("article", article);
        return "board/view";
    }

    // 글 작성
    @GetMapping("board/write")
    public String write(String group, String cate, Model model, @AuthenticationPrincipal MyUserDetails myuser){
        UserEntity user = null;
        if(myuser != null){ user = myuser.getUser(); }
        model.addAttribute("user", user);

        model.addAttribute("group", group);
        model.addAttribute("cate", cate);
        return "board/write";
    }

    // 글 작성
    @PostMapping("board/write")
    public String write(String group, String cate, BoardVO vo, HttpServletRequest req, @AuthenticationPrincipal MyUserDetails myuser){
        vo.setUid(myuser.getUser().getUid());
        vo.setRegip(req.getRemoteAddr());

        service.insertBoard(vo);
        return "redirect:/board/list?group="+group+"&cate="+cate;
    }

    // 파일 다운로드
    @GetMapping("board/download")
    public ResponseEntity<Resource> download(int fno) throws IOException {
        FileVO fvo = service.selectFile(fno);

        ResponseEntity<Resource> respEntity = service.fileDownload(fvo);
        return respEntity;
    }
}
