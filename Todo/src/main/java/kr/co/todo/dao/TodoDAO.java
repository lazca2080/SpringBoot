package kr.co.todo.dao;

import kr.co.todo.vo.TodoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface TodoDAO {

    public List<TodoVO> selectTodo();

    public int insertTodo(@Param("no") int no, @Param("content") String content);

    public int updateTodo(@Param("no") int no, @Param("status") int status);

    public int deleteTodo(int no);
}
