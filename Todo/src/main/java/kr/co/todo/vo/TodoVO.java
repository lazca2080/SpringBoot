package kr.co.todo.vo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TodoVO {
    private int no;
    private int status;
    private String content;
    private String rdate;
}
