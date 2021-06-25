package top.caolizhi.example.reative.publisher;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import reactor.core.publisher.Mono;

/**
 *@Description just 和 defer 的区别
 *@Author 宝子哥
 *@Date 2021/6/25 10:23
 *@Version 1.0
 **/
public class JustAndDefer {

	/**
	 * Mono.just 5s 后订阅和一开始就订阅没有区别，都会直接创建，eagerly
	 * Mono.defer 是订阅的时候才会去触发 lambda 函数，lazily
	 *
	**/
	public static void main(String[] args) throws InterruptedException {

		Mono<Date> mono = Mono.just(new Date());
		Mono<Date> monoDefer = Mono.defer(() -> Mono.just(new Date()));
		mono.subscribe(x -> System.out.println("mono: " + x));
		monoDefer.subscribe(x -> System.out.println("mono defer: " + x));

		TimeUnit.SECONDS.sleep(5);
		mono.subscribe(x -> System.out.println("mono: " + x));
		monoDefer.subscribe(x -> System.out.println("mono defer: " + x));

	}
}
