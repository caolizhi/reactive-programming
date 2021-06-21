package top.caolizhi.example.reative.publisher;

import reactor.core.publisher.Mono;

/**
 *@Description 阻塞和非阻塞的方式去提取 Mono 里面的数据
 *@Author 宝子哥
 *@Date 2021/6/21 10:26
 *@Version 1.0
 **/
public class ExtractMono {

	Mono<String> blockingHelloWorld() {
		return Mono.just("Hello World!");
	}


}
