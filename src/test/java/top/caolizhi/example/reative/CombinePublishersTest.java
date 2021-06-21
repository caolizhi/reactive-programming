package top.caolizhi.example.reative;

import java.time.Duration;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

/**
 *@Description 合并 publisher
 *@Author 宝子哥
 *@Date 2021/6/17 16:05
 *@Version 1.0
 **/

@Slf4j
public class CombinePublishersTest {

	private static Flux<Integer> evenNumbers;
	private static Flux<Integer> oddNumbers;

	@BeforeAll
	static void init() {
		int min = 1, max = 5;
		evenNumbers = Flux.range(min, max).filter(i -> i % 2 == 0);
		oddNumbers = Flux.range(min, max).filter(i -> i % 2 == 1);
	}

	/**
	 * 等待第一个 source 完成，再去订阅第二个 source，有顺序，长度不变，前后拼接，不交错
	 **/
	@Test
	public void concatTest() {
		Flux<Integer> concat = Flux.concat(evenNumbers, oddNumbers);

		StepVerifier.create(concat)
			.expectNext(2)
			.expectNext(4)
			.expectNext(1)
			.expectNext(3)
			.expectNext(5)
			.expectComplete()
			.verify();
	}


	/**
	 * 跟上面的用法差不多
	 **/
	@Test
	public void concatWithTest() {
		Flux<Integer> concatWith = evenNumbers.concatWith(oddNumbers);

		StepVerifier.create(concatWith)
			.expectNext(2)
			.expectNext(4)
			.expectNext(1)
			.expectNext(3)
			.expectNext(5)
			.expectComplete()
			.verify();
	}


	/**
	 * 从两个 source 中拉取最近的一个元素进行 BiFunction 的操作， 本例中：
	 *  evenNumbers：  ———— 2 ———— 4 — |
	 *  oddNumbers：            ———— 1 ———— 3 ———— 5 — |
	 *  result：                ———— (4 + 1) ———— (4 + 3) ———— (4 + 5)
	 **/
	@Test
	public void combineLatestTest() {
		Flux<Integer> combineLatest = Flux.combineLatest(evenNumbers, oddNumbers, (t1, t2) -> t1 + t2);

		StepVerifier.create(combineLatest)
			.expectNext(5) // 4 + 1
			.expectNext(7) // 4 + 3
			.expectNext(9) // 4 + 5
			.expectComplete()
			.verify();

		Flux<Integer> combineLatest2 = Flux.combineLatest(oddNumbers, evenNumbers, (t1, t2) -> t1 + t2);

		StepVerifier.create(combineLatest2)
			.expectNext(7) // 5 + 2
			.expectNext(9) // 5 + 4
			.expectComplete()
			.verify();
	}


	/**
	* 长度不变，交错拼接
	**/
	@Test
	public void mergeTest() {
		Flux<Integer> merge = Flux.merge(evenNumbers, oddNumbers);

		StepVerifier.create(merge)
			.expectNext(2)
			.expectNext(4)
			.expectNext(1)
			.expectNext(3)
			.expectNext(5)
			.expectComplete()
			.verify();
	}

	/**
	 * 长度不变，交错拼接
	 **/
	@Test
	public void mergeWithTest() {
		Flux<Integer> merge = evenNumbers.mergeWith(oddNumbers);

		StepVerifier.create(merge)
			.expectNext(2)
			.expectNext(4)
			.expectNext(1)
			.expectNext(3)
			.expectNext(5)
			.expectComplete()
			.verify();
	}

	/**
	*     time(ms): 300  500  600  900  1000
	 * even number:      ---2---------------4--|
	 *  odd number: ---1---------3----5--|
	 * **/
	@Test
	public void mergeDelayTest() {
		Flux<Integer> mergeDelay = Flux.merge(
			evenNumbers.delayElements(Duration.ofMillis(500L)).log(),
			oddNumbers.delayElements(Duration.ofMillis(300L))).log();

		StepVerifier.create(mergeDelay)
			.expectNext(1)
			.expectNext(2)
			.expectNext(3)
			.expectNext(5)
			.expectNext(4)
			.expectComplete()
			.verify();
	}

	/**
	*  类似于 group by，然后拼接在一起，不用等前面的 source 完成
	**/
	@Test
	public void mergeSequentialTest() {
		Flux<Integer> mergeDelay = Flux.mergeSequential(evenNumbers, oddNumbers, evenNumbers);
		StepVerifier.create(mergeDelay)
			.expectNext(2)
			.expectNext(4)
			.expectNext(1)
			.expectNext(3)
			.expectNext(5)
			.expectNext(2)
			.expectNext(4)
			.expectComplete()
			.verify();
	}

	@Test
	public void mergeDelayErrorTest() {
		Flux<Integer> mergeDelayError = Flux.mergeDelayError(
			1,
			evenNumbers.delayElements(Duration.ofMillis(500)),
			oddNumbers.delayElements(Duration.ofMillis(300)));

		StepVerifier.create(mergeDelayError)
			.expectNext(1)
			.expectNext(2)
			.expectNext(3)
			.expectNext(5)
			.expectNext(4)
			.expectComplete()
			.verify();
	}

	/**
	* 对 source 1 和 source 2 进行操作，返回新流， 谁的流先触发 onComplete ，就结束
	**/
	@Test
	public void zipTest() {
		Flux<Integer> zip = Flux.zip(evenNumbers, oddNumbers, (a, b) -> a + b);
		StepVerifier.create(zip)
			.expectNext(3) // 2 + 1
			.expectNext(7) // 4 + 3
			//.expectNext(5)  // 报错，因为 evenNumbers 已经触发了 onComplete 事件
			.expectComplete()
			.verify();
	}

	@Test
	public void zipWithTest() {
		Flux<Integer> zipWith = evenNumbers.zipWith(oddNumbers, (a, b) -> a + b);
		StepVerifier.create(zipWith)
			.expectNext(3) // 2 + 1
			.expectNext(7) // 4 + 3
			//.expectNext(5)  // 报错，因为 evenNumbers 已经触发了 onComplete 事件
			.expectComplete()
			.verify();
	}




}
