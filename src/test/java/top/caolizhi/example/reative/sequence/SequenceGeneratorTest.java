package top.caolizhi.example.reative.sequence;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

class SequenceGeneratorTest {

	@Test
	void generateNumbersTest() {
		Flux<Integer> integerFlux = new SequenceGenerator().generateNumbers();
		StepVerifier.create(integerFlux)
			.expectNext(1)
			.expectComplete()
			.verify();
	}

	@Test
	void generateFibonacciWithTuplesTest() {
		SequenceGenerator sequenceGenerator = new SequenceGenerator();
		Flux<Integer> integerFlux = sequenceGenerator.generateFibonacciWithTuples().log().take(5);

		StepVerifier.create(integerFlux)
			.expectNext(0, 1, 1, 2, 3)
			.expectComplete()
			.verify();
	}

	@Test
	void generateFibonacciWithCustomClassTest() {
		SequenceGenerator sequenceGenerator = new SequenceGenerator();
		Flux<Integer> integerFlux = sequenceGenerator.generateFibonacciWithCustomClass(10).log();

		StepVerifier.create(integerFlux)
			.expectNext(0, 1, 1, 2, 3, 5, 8)
			.expectComplete()
			.verify();
	}
}
