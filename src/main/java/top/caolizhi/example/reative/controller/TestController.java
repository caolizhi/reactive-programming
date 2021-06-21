package top.caolizhi.example.reative.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.caolizhi.example.reative.service.TestService;

/**
 *@Description TODO
 *@Author 宝子哥
 *@Date 2021/6/15 15:36
 *@Version 1.0
 **/
@RestController
public class TestController {

	@Autowired
	TestService testService;

	@GetMapping(value = "/1", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> helloWorld() {

		return Flux.create(sink -> {
			sink.next("a");
			sleep3s();
			sink.next("b");
			sleep3s();
			sink.next("c");
			sleep3s();
			sink.next("d");
			sleep3s();
			sink.next("e");
			sleep3s();
			sink.next("f");
			sleep3s();
			sink.complete();
		});
	}

	@GetMapping("/2")
	public Mono<String> test() {
		return Mono.just("abc");
	}

	private void sleep3s() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
