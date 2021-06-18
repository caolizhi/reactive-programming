package top.caolizhi.example.reative;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 *@Description TODO
 *@Author 小志哥
 *@Date 2021/6/17 9:56
 *@Version 1.0
 **/

@Slf4j
public class ReactiveBasicTest {

	/**
	 * 10:06:46.302 [main] INFO reactor.Flux.Array.1 - | onSubscribe([Synchronous Fuseable] FluxArray.ArraySubscription)
	 * 10:06:46.304 [main] INFO reactor.Flux.Array.1 - | request(unbounded)
	 * 10:06:46.305 [main] INFO reactor.Flux.Array.1 - | onNext(1)
	 * 10:06:46.305 [main] INFO reactor.Flux.Array.1 - | onNext(2)
	 * 10:06:46.305 [main] INFO reactor.Flux.Array.1 - | onNext(3)
	 * 10:06:46.305 [main] INFO reactor.Flux.Array.1 - | onNext(4)
	 * 10:06:46.305 [main] INFO reactor.Flux.Array.1 - | onComplete()
	 **/
	@Test
	public void basicFluxTest() {
		List<Integer> arrayList = new ArrayList<>();
		Flux.just(1,2,3,4)
			.log()
			.subscribe(arrayList::add);
		assertThat(arrayList).containsExactly(1,2,3,4);
	}

	/**
	 * @Description 自定义实现 {@link Subscriber} 接口
	 **/
	@Test
	public void customImplementSubscriberTest() {
		List<Integer> arrayList = new ArrayList<>();
		Flux.just(1,2,3,4)
			.log()
			.subscribe(new Subscriber<Integer>() {

				@Override
				public void onSubscribe(Subscription s) {
					s.request(Long.MAX_VALUE); // 如果值是 1，就是只订阅一次，只取一次值；
					System.out.println("我订阅了");
				}

				@Override
				public void onNext(Integer integer) {
					arrayList.add(integer);
					System.out.println("有数据来了:" + integer);
				}

				@Override
				public void onError(Throwable t) {
					System.out.println("错误发生了");
				}

				@Override
				public void onComplete() {
					System.out.println("数据完成了");
					assertThat(arrayList).containsExactly(1,2,3,4);
					System.out.println("检验成功！");
				}
			});
	}

}
