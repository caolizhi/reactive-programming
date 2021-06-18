package top.caolizhi.example.reative;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 *@Description TODO
 *@Author 小志哥
 *@Date 2021/6/17 15:09
 *@Version 1.0
 **/
@Slf4j
public class ConcurrencyTest {

	/**
	* 08:58:54.189 [main] DEBUG reactor.util.Loggers - Using Slf4j logging framework
	 * 08:58:54.227 [parallel-1] INFO reactor.Flux.Array.1 - | onSubscribe([Synchronous Fuseable] FluxArray.ArraySubscription)
	 * 08:58:54.238 [parallel-1] INFO reactor.Flux.Array.1 - | request(unbounded)
	 * 08:58:54.238 [parallel-1] INFO reactor.Flux.Array.1 - | onNext(1)
	 * 08:58:54.238 [parallel-1] INFO reactor.Flux.Array.1 - | onNext(2)
	 * 08:58:54.239 [parallel-1] INFO reactor.Flux.Array.1 - | onNext(3)
	 * 08:58:54.239 [parallel-1] INFO reactor.Flux.Array.1 - | onNext(4)
	 * 08:58:54.239 [parallel-1] INFO reactor.Flux.Array.1 - | onComplete()
	**/
	@Test
	public void schedulerTest() {
		List<Integer> list = new ArrayList<>();
		Flux.just(1, 2, 3, 4)
			.log()
			.map(i -> i * 2)
			.subscribeOn(Schedulers.parallel())
			.subscribe(list::add);
		assertThat(list).containsExactly(2, 4, 6, 8);
	}
}
