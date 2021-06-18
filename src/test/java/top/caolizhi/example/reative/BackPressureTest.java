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
 *@Description 背压
 *@Author 小志哥
 *@Date 2021/6/17 9:56
 *@Version 1.0
 **/

@Slf4j
public class BackPressureTest {

	/**
	 * @Description backpressure 背压
	 **/
	@Test
	public void backpressureTest() {
		List<Integer> arrayList = new ArrayList<>();
		Flux.just(1,2,3,4)
			.log()
			.subscribe(new Subscriber<Integer>() {

				private Subscription s;
				private int onNextAccount;

				@Override
				public void onSubscribe(Subscription s) {
					this.s = s;
					s.request(2); // 2 只订阅2次
					System.out.println("我订阅了");
				}

				@Override
				public void onNext(Integer integer) {
					System.out.println("数据来了：" + integer);
					arrayList.add(integer);
					onNextAccount ++;
					if (onNextAccount % 2 == 0) {
						s.request(2);
						System.out.println("新一波数据来了");
					}

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
