package top.caolizhi.example.reative.subscriber;

import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/**
 *@Description 自定义一个 subscriber，默认是 request unbounded
 *@Author 宝子哥
 *@Date 2021/6/23 13:49
 *@Version 1.0
 **/
public class SampleUnboundedSubscriber<T> extends BaseSubscriber<T> {

	public void hookOnSubscribe(Subscription subscription) {
		super.hookOnSubscribe(subscription);
	}

	protected void hookOnNext(T value){
		System.out.println(value);
	}


	public static void main(String[] args) {
		SampleUnboundedSubscriber<Integer> subscriber = new SampleUnboundedSubscriber<>();
		Flux<Integer> range = Flux.range(1, 4);
		range.subscribe(subscriber);
	}
}
