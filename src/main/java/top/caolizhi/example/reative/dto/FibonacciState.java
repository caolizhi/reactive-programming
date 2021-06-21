package top.caolizhi.example.reative.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *@Description TODO
 *@Author 宝子哥
 *@Date 2021/6/18 13:24
 *@Version 1.0
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class FibonacciState {

	private int former;
	private int latter;
}
