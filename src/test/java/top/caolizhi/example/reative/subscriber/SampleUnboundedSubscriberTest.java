package top.caolizhi.example.reative.subscriber;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class SampleUnboundedSubscriberTest {

	@Test
	public void subscriberTest() {
		SampleUnboundedSubscriber<Integer> subscriber = new SampleUnboundedSubscriber<>();
		Flux<Integer> range = Flux.range(1, 4);
		StepVerifier.create(range)
			.expectNext(1)
			.expectNext(2)
			.expectNext(3)
			.expectNext(4)
			.expectComplete()
			.verify();
	}

}
