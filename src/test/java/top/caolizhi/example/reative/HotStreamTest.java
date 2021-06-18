package top.caolizhi.example.reative;

import java.time.Duration;
import java.util.Date;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

/**
 *@Description TODO
 *@Author 小志哥
 *@Date 2021/6/17 13:30
 *@Version 1.0
 **/
@Slf4j
public class HotStreamTest {

	/**
	 * By calling publish() we are given a ConnectableFlux.
	 * This means that calling subscribe() won't cause it to start emitting, allowing us to add multiple subscriptions:
	 **/
	@Test
	public void connectableFluxTest() {
		reactor.core.publisher.ConnectableFlux<Object> publish = Flux.create(fluxSink -> {
			while (true) {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				fluxSink.next(new Date(System.currentTimeMillis()));
			}
		}).publish();

		publish.subscribe(System.out::println);  // nothing output

		publish.connect();  // Flux will not start emitting, until we call connect()
	}


	/**
	 * 节流
	 * values will only be pushed to our subscriber every two seconds,
	 * meaning the console will be a lot less hectic.
	 **/
	@Test
	public void throttlingTest() {
		ConnectableFlux<Object> publish = Flux.create(fluxSink -> {
			while (true) {
				fluxSink.next(new Date(System.currentTimeMillis()));
			}
		})
		.sample(Duration.ofSeconds(2))
		.publish();

		publish.subscribe(System.out::println);

		publish.connect();

	}

}
