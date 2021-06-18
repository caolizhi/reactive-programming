package top.caolizhi.example.reative.service;

import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;

/**
 *@Description TODO
 *@Author 小志哥
 *@Date 2021/6/15 15:36
 *@Version 1.0
 **/
@Service
public class TestService {

	public Flux<String> helloWorld() {
		return Flux.fromStream(IntStream.range(0, 100).mapToObj(s -> "我是：" + s));
	}
}
