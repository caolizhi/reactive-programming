package top.caolizhi.example.reative.publisher;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;

/**
 *@Description 阻塞和非阻塞的方式去提取 Mono 里面的数据
 *@Author 宝子哥
 *@Date 2021/6/21 10:23
 *@Version 1.0
 **/
public class ExtractMonoTest {

	private static ExtractMono extractMono;
	private static String expected;

	@BeforeAll
	static void init() {
		extractMono = new ExtractMono();
		expected = "Hello World!";
	}

	/**
	 * block 是阻塞的方式
	 * 阻塞获取数据，但是这样和响应式编程的原则是违背的
	**/
	@Test
	public void blockingHelloWorldTest() {
		String content = extractMono.blockingHelloWorld().block(Duration.of(1000, ChronoUnit.MILLIS));
		assertEquals(expected, content);
	}

	@Test
	public void blockingOptionalTest() {
		Optional content = Mono.<String>empty().blockOptional();
		assertEquals(Optional.empty(), content);
	}

	/**
	 * subscribe() 是非阻塞的方式
	**/
	@Test
	public void nonBlockingTest() {
		extractMono.blockingHelloWorld().subscribe(content -> assertEquals(expected, content));
	}

}
