package top.caolizhi.example.reative.operator;

import java.util.Locale;

import org.reactivestreams.Publisher;

import com.google.common.base.Function;

import reactor.core.publisher.Flux;

/**
 *@Description FlatMap 操作符
 *@Author 宝子哥
 *@Date 2021/6/21 16:36
 *@Version 1.0
 **/
public class FlatmapOperator {

	/**
	 * 把输入转化到一个 Publisher ，而不是普通对象，扁平化 2 个流 （字符串 “caolizhi” 和 字符串 “top” 的流）
	**/
	public Flux<String> flatmapOperator() {
		Function<String, Publisher<String>> mapper = s -> Flux.just(s.toUpperCase(Locale.ROOT).split(""));
		Flux<String> in = Flux.just("caolizhi", "top");
		return in.flatMap(mapper);
	}

}
