package top.caolizhi.example.reative.publisher;

import static reactor.core.publisher.Sinks.EmitFailureHandler.*;

import java.util.Arrays;

import lombok.SneakyThrows;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 *@Description TODO
 *@Author 宝子哥
 *@Date 2021/6/24 9:20
 *@Version 1.0
 **/
public class ColdHot {

	/**
	 * cold 流每次订阅都是全部的数据
	**/
	public Flux<String> coldFlux() {
		return Flux.fromIterable(Arrays.asList("blue", "green", "orange", "purple"));
	}

	/**
	 * hot 流每次订阅都是订阅之后的数据
	**/
	public Flux<String> hotFlux(Sinks.Many<String> hotSource) {
		return hotSource.asFlux().map(String::toUpperCase);
	}

	private Sinks.Many<String> getHotSource() {
		return Sinks.unsafe().many().multicast().directBestEffort();
	}

	@SneakyThrows
	public static void main(String[] args) {
		ColdHot coldHot = new ColdHot();
		System.out.println("cold 流每次订阅都是输出全部的数据");
		coldHot.coldFlux().subscribe(s -> System.out.println("Flux Subscriber 1 " + s));
		coldHot.coldFlux().subscribe(s -> System.out.println("Flux Subscriber 2 " + s));
		System.out.println("----------------------------------------------------------------------------------");

		System.out.println("hot 流每次订阅都是订阅之后产生的数据");
		Sinks.Many<String> hotSource = coldHot.getHotSource();

		System.out.println("第一次订阅");
		coldHot.hotFlux(hotSource).subscribe(s -> System.out.println("Subscriber 1 to hot source:" + s));

		System.out.println("第一次发送数据");
		Thread.sleep(5000);
		hotSource.emitNext("blue", FAIL_FAST);
		hotSource.tryEmitNext("green").orThrow();

		System.out.println("第二次订阅");
		coldHot.hotFlux(hotSource).subscribe(s -> System.out.println("Subscriber 2 to hot source:" + s));

		System.out.println("第二次发送数据");
		Thread.sleep(5000);
		hotSource.emitNext("orange", FAIL_FAST);
		hotSource.emitNext("purple", FAIL_FAST);
		hotSource.emitComplete(FAIL_FAST);


	}
}
