package kr.co.bookstore.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookVO {

	private int bookid;
	private String bookname;
	private String publisher;
	private int price;
}
