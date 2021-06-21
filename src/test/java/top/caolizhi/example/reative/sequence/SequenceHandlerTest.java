package top.caolizhi.example.reative.sequence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class SequenceHandlerTest {

	@Test
	void handleIntegerSequenceTest() {
		SequenceHandler sequenceHandler = new SequenceHandler();
		SequenceGenerator sequenceGenerator = new SequenceGenerator();
		Flux<Integer> sequence = sequenceGenerator.generateFibonacciWithTuples().take(10); // 0,1,1,2,3,5,8,13,21,34,55

		StepVerifier.create(sequenceHandler.handleIntegerSequence(sequence))
			.expectNext(0, 1, 4, 17)
			.expectComplete()
			.verify();
	}
}
