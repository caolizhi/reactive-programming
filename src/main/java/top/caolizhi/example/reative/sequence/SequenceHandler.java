package top.caolizhi.example.reative.sequence;

import reactor.core.publisher.Flux;

/**
 *@Description 处理 publisher 产生的序列，相当于 map
 *@Author 宝子哥
 *@Date 2021/6/21 9:56
 *@Version 1.0
 **/
public class SequenceHandler {

	/**
	 * 找偶数，并且调用 next， 只能调用一次
	**/
	public Flux<Integer> handleIntegerSequence(Flux<Integer> sequence) {
		return sequence.handle((number, sink) -> {
			if (number % 2 == 0) {
				sink.next(number / 2 );
			}
		});
	}

}
