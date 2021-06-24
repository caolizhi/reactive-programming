package top.caolizhi.example.reative.backpressure;

import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/**
 *@Description 背压测试
 *@Author 宝子哥
 *@Date 2021/6/23 14:49
 *@Version 1.0
 **/
public class SampleBackpressure {

	public static void main(String[] args) {
		Flux.range(10, 10)
			.doOnRequest(r -> System.out.println("request of " + r)) // Add behavior (side-effect) triggering a LongConsumer when this Flux receives any request.
			.subscribe(new BaseSubscriber<Integer>() {
				@Override
				protected void hookOnSubscribe(Subscription subscription) {
					request(4);
				}

				@Override
				protected void hookOnNext(Integer value) {
					System.out.println("Cancelling after having received " + value);
					super.cancel();
				}
			});
	}
}
