package kr.co.todo.service;

import kr.co.todo.dao.TodoDAO;
import kr.co.todo.vo.TodoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TodoService {

    @Autowired
    private TodoDAO dao;

    public Map<Integer, List<TodoVO>> selectTodo() {
        List<TodoVO> vo = dao.selectTodo();
        return vo.stream().collect(Collectors.groupingBy(TodoVO::getStatus));
    };

    public int insertTodo(int no, String content) {
        return dao.insertTodo(no, content);
    };

    public int updateTodo(int no, int status) {
        return dao.updateTodo(no, status);
    };

    public int deleteTodo(int no) {
        return dao.deleteTodo(no);
    };
}
