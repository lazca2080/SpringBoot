package kr.co.bookstore.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerVO {
	
	private int custId;
	private String name;
	private String address;
	private String phone;
}
