package top.caolizhi.example.reative.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *@Description TODO
 *@Author 小志哥
 *@Date 2021/6/16 9:43
 *@Version 1.0
 **/
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

	private String id;
	private String name;

}
