package top.caolizhi.example.reative.controller;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import top.caolizhi.example.reative.service.TestService;

/**
 *@Description
 *@Author 宝子哥
 *@Date 2021/6/15 15:36
 *@Version 1.0
 **/
@RestController
@Slf4j
public class SseController {

	@Autowired
	TestService testService;

	/**
	 * 普通spring mvc 请求
	**/
	@GetMapping(value = "/1")
	public String get1() throws InterruptedException {
		log.info("mvc start");
		TimeUnit.SECONDS.sleep(5);
		log.info("mvc end");
		return "haha, spring mvc";
	}

	/**
	 * webflux 请求
	**/
	@GetMapping("/2")
	public Mono<String> get2() {
		log.info("webflux start");
		Mono<String> mono = Mono.defer(() -> {
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return Mono.just("hehe, webflux");
		});
		log.info("webflux end");
		return mono;
	}

	/**
	 * web flux 服务器推送, Server Sent Event
	 * 需要指定 media type
	**/
	@GetMapping(value = "/3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> get3() {

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


	private void sleep3s() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
