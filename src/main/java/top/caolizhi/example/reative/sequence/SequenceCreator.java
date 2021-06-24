package top.caolizhi.example.reative.sequence;

import java.util.List;
import java.util.function.Consumer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

/**
 *@Description 异步动态生成序列，多个线程产生序列
 *@Author 宝子哥
 *@Date 2021/6/18 10:25
 *@Version 1.0
 **/
public class SequenceCreator {

	public Consumer<List<Integer>> consumer;

	/**
	 * 异步：多个线程
	 * 无状态，onNext 可以调用很多次，多个线程
	 *
	 **/
	public Flux<Integer> createNumbers() {
		return Flux.create((FluxSink<Integer> sink) -> {
			SequenceCreator.this.consumer = items -> items.forEach(sink::next);
		});
	}

	/**
	 * cancel 发生在 dispose 之前
	**/
	public Flux<Integer> createAndCancelNumbers() {
		return Flux.create((FluxSink<Integer> sink) -> {
			SequenceCreator.this.consumer = items -> items.forEach(sink::next);
			sink.onDispose(() -> System.out.println("完成或关闭"))
				.onCancel(() -> System.out.println("取消了"));
		});
	}

	/**
	* 异步：单个线程
	**/
	public Flux<Integer> pushNumbers() {
		return Flux.push((FluxSink<Integer> sink) -> {
			SequenceCreator.this.consumer = items -> items.forEach(sink::next);
		});
	}

}
