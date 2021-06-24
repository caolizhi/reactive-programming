package top.caolizhi.example.reative.context;

import reactor.core.publisher.Mono;

/**
 *@Description 线程间共享数据
 *@Author 宝子哥
 *@Date 2021/6/24 10:41
 *@Version 1.0
 **/
public class ContextSample {


	public static void main(String[] args) {
		String key = "message";
		Mono<String> stringMono = Mono.just("Hello")
			.flatMap(s -> Mono.deferContextual(ctx -> Mono.just(s + " " + ctx.get(key))))
			.contextWrite(ctx -> ctx.put(key, "World"));

		stringMono.subscribe(System.out::print);
	}

}
