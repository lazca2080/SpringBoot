package kr.co.todo.controller;

import kr.co.todo.service.TodoService;
import kr.co.todo.vo.TodoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TodoController {

    @Autowired
    private TodoService service;

    @GetMapping(value={"/", "index"})
    public String select(Model model){
        Map<Integer, List<TodoVO>> vo = service.selectTodo();
        model.addAttribute("todo", vo);
        return "index";
    }

    @ResponseBody
    @PostMapping("insert")
    public Map<String, Integer> insert(@RequestParam(value="jsonData[]") List<String> jsonData){
        int no = Integer.parseInt(jsonData.get(0));
        String content = jsonData.get(1).toString();

        int result = service.insertTodo(no, content);

        Map<String, Integer> map = new HashMap<>();
        map.put("result", result);

        return map;
    }

    @ResponseBody
    @PostMapping("update")
    public Map<String, Integer> update(@RequestParam(value="jsonData[]") List<String> jsonData){
        int no = Integer.parseInt(jsonData.get(0));
        int status = Integer.parseInt(jsonData.get(1));

        int result = service.updateTodo(no, status);

        Map<String, Integer> map = new HashMap<>();
        map.put("result", result);

        return map;
    }

    @ResponseBody
    @PostMapping("delete")
    public Map<String, Integer> delete(@RequestParam("no") int no){
        int result = service.deleteTodo(no);
        Map<String, Integer> map = new HashMap<>();
        map.put("result", result);

        return map;

    }
}
