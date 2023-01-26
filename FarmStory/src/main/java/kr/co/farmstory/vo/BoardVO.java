package kr.co.farmstory.vo;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BoardVO {

    private int no;
    private int parent;
    private String comment;
    private String cate;
    private String title;
    private String content;
    private int file;
    private MultipartFile fname;
    private int hit;
    private String uid;
    private String regip;
    private String rdate;

    // 추가
    private String nick;
    private FileVO fileVO;

}
