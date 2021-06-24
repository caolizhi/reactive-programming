package top.caolizhi.example.reative.subscriber;

import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/**
 *@Description 自定义一个 subscriber，如果要自定义请求数量，要覆盖 hookOnSubscribe 和 hookOnNext 方法
 *@Author 宝子哥
 *@Date 2021/6/23 13:49
 *@Version 1.0
 **/
public class SampleSubscriber<T> extends BaseSubscriber<T> {

	public void hookOnSubscribe(Subscription subscription) {
		System.out.println("Subscribed");
		request(1);
	}

	public void hookOnNext(T value) {
		System.out.println(value);
		request(1);
	}

	public static void main(String[] args) {
		SampleSubscriber<Integer> subscriber = new SampleSubscriber<>();
		Flux<Integer> range = Flux.range(1, 4);
		range.subscribe(subscriber);
	}


}
