package top.caolizhi.example.reative.sequence;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;

class SequenceCreatorTest {

	/**
	*
	*
	* 问题：线程执行顺序不对！！！！！！
	**/
	@Test
	void createNumbersTest() throws InterruptedException {
		// 产生两个数组
		SequenceGenerator sequenceGenerator = new SequenceGenerator();
		List<Integer> sequence1 = sequenceGenerator.generateFibonacciWithTuples().take(3).collectList().block();
		List<Integer> sequence2 = sequenceGenerator.generateFibonacciWithTuples().take(4).collectList().block();
		SequenceCreator sequenceCreator = new SequenceCreator();
		List<Integer> list = new ArrayList<>();

		Thread thread1 = new Thread(() -> {

			sequenceCreator.consumer.accept(sequence1);
		});

		Thread thread2 = new Thread(() -> sequenceCreator.consumer.accept(sequence2));
		sequenceCreator.createNumbers().log().subscribe(list::add);
		thread1.start();
		System.out.println("thead 1 name is: " + thread1.getName());
		thread2.start();
		System.out.println("thead 2 name is: " + thread2.getName());
		thread1.join();
		thread2.join();

		assertThat(list).containsExactly(0, 1, 1, 0, 1, 1, 2); // 理论上输出的顺序无法保证一致
	}

	@Test
	void createAndCancelNumbersTest() throws InterruptedException {

		SequenceGenerator sequenceGenerator = new SequenceGenerator();
		List<Integer> sequence1 = sequenceGenerator.generateFibonacciWithTuples().take(3).collectList().block();
		SequenceCreator sequenceCreator = new SequenceCreator();
		List<Integer> list = new ArrayList<>();
		Thread thread1 = new Thread(() -> sequenceCreator.consumer.accept(sequence1));

		sequenceCreator.createAndCancelNumbers().log().subscribe(new BaseSubscriber<Integer>() {
			@Override
			protected void hookOnSubscribe(Subscription subscription) {
				super.hookOnSubscribe(subscription);
			}

			@Override
			protected void hookOnNext(Integer value) {
				list.add(value);
				super.cancel();
			}
		});
		thread1.start();
		thread1.join();
		assertThat(list).containsExactly(0); // 理论上输出的顺序无法保证一致
	}

	/**
	 * 异步：单个线程
	 **/
	@Test
	void pushNumbersTest() throws InterruptedException {
		// 产生数组
		SequenceGenerator sequenceGenerator = new SequenceGenerator();
		List<Integer> sequence1 = sequenceGenerator.generateFibonacciWithTuples().take(3).collectList().block();
		SequenceCreator sequenceCreator = new SequenceCreator();
		List<Integer> list = new ArrayList<>();

		Thread thread = new Thread(() -> sequenceCreator.consumer.accept(sequence1));
		sequenceCreator.pushNumbers().log().subscribe(list::add);

		thread.start();

		assertThat(list).containsExactly(0, 1, 1); // 理论上输出的顺序无法保证一致
	}
}
