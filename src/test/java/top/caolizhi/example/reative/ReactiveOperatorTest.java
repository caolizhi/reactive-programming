package top.caolizhi.example.reative;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 *@Description We can also perform operations on the data in our stream, responding to events as we see fit.
 *@Author 小志哥
 *@Date 2021/6/17 11:29
 *@Version 1.0
 **/
@Slf4j
public class ReactiveOperatorTest {

	/**
	 * onNext() 调用的时候，map 才能应用
	 **/
	@Test
	public void mapTest() {
		ArrayList<Integer> list = new ArrayList<>();
		Flux.just(1, 2, 3, 4)
			.log()
			.map(i -> i + 2)
			.subscribe(list::add);
		System.out.println(list.toString());
	}

	/**
	 * 合并两个 source，并做操作处理，然后输出另外一种 source
	 **/
	@Test
	public void zipTest() {
		ArrayList<String> list = new ArrayList<>();
		Flux.just(1, 2, 3, 4)
			.log()
			.map(i -> i*2)
			.zipWith( // The operator will continue doing so until any of the sources completes
				Flux.range(0, Integer.MAX_VALUE),
				(t1, t2) -> String.format("First Flux: %d, Second Flux: %d", t1, t2))
			.subscribe(list::add);
		assertThat(list).containsExactly(
			"First Flux: 2, Second Flux: 0",
			"First Flux: 4, Second Flux: 1",
			"First Flux: 6, Second Flux: 2",
			"First Flux: 8, Second Flux: 3");
	}



}
