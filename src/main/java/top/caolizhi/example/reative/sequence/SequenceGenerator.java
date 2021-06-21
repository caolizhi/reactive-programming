package top.caolizhi.example.reative.sequence;

import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;
import reactor.util.function.Tuples;
import top.caolizhi.example.reative.dto.FibonacciState;

/**
 *@Description 同步动态生成序列
 *@Author 宝子哥
 *@Date 2021/6/18 10:25
 *@Version 1.0
 **/
public class SequenceGenerator {

	/**
	* 最简单的用法，无状态
	**/
	public Flux<Integer> generateNumbers() {
		return Flux.generate((SynchronousSink<Integer> sink) -> {
			sink.next(1);  // 只能被调用一次
			sink.complete(); // 显示调用，否则序列无法停止
		});
	}

	/**
	* 生成斐波那契数列，有状态
	**/
	public Flux<Integer> generateFibonacciWithTuples() {
		return Flux.generate(
			() -> Tuples.of(0, 1), // Callable 定义初始状态
			(state, sink) -> {    // BiFunction 生产者，消费一个 SynchronousSink，并发送下一个
				sink.next(state.getT1());
				return Tuples.of(state.getT2(), state.getT1() + state.getT2()); // 返回一个新的状态值，构建了很多个 Tuples 对象
			}
		);
	}

	/**
	* 自定义类， 有状态
	**/
	public Flux<Integer> generateFibonacciWithCustomClass(int limit) {
		return Flux.generate(
			() -> new FibonacciState(0,1),
			(fibonacciState, sink) -> {
				sink.next(fibonacciState.getFormer());
				if (fibonacciState.getLatter() > limit) {
					sink.complete();
				}
				int temp = fibonacciState.getFormer();
				fibonacciState.setFormer(fibonacciState.getLatter());
			    fibonacciState.setLatter(temp + fibonacciState.getLatter());
			    return fibonacciState;
			});
	}


}
