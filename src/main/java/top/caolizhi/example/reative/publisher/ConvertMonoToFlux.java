package top.caolizhi.example.reative.publisher;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 *@Description 把 Mono<List<T>> 转换成 Flux<T>
 *     map 操作符是同步的转换 Mono，flatMap 操作符是异步的转换 Mono
 *@Author 宝子哥
 *@Date 2021/6/21 10:40
 *@Version 1.0
 **/
@Slf4j
public class ConvertMonoToFlux {

	public Mono<List<String>> monoOfList() {
		List<String> list = Lists.newArrayList();
		list.add("one");
		list.add("two");
		list.add("three");
		list.add("four");
		return Mono.just(list);
	}

	/**
	 * 需要调用 fromIterable，使用广泛
	**/
	public Flux<String> monoToFluxUsingFlatMapMany(Mono<List<String>> monoList) {
		return monoList.flatMapMany(Flux::fromIterable).log();
	}

	/**
	 * 优化了 flatMapMany
	**/
	public Flux<String> monoToFluxUsingFlatMapIterable(Mono<List<String>> monoList) {
		return monoList.flatMapIterable(list -> list).log();
	}

}
