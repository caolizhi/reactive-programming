package top.caolizhi.example.reative.operator;

import com.google.common.base.Function;

import reactor.core.publisher.Flux;

/**
 *@Description Map 操作符
 *@Author 宝子哥
 *@Date 2021/6/21 16:36
 *@Version 1.0
 **/
public class MapOperator {

	/**
	 * 转化为新的普通对象
	**/
	public Flux<String> mapOperator() {
		Function<String, String> mapper = String::toUpperCase;
		Flux<String> in = Flux.just("caolizhi", "top");
		return in.map(mapper);
	}

}
