package top.caolizhi.example.reative.publisher;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

class ConvertMonoToFluxTest {

	@Test
	void monoToFluxUsingFlatMapManyTest() {
		ConvertMonoToFlux convertMonoToFlux = new ConvertMonoToFlux();
		StepVerifier.create(convertMonoToFlux.monoToFluxUsingFlatMapMany(convertMonoToFlux.monoOfList()))
			.expectNext("one")
			.expectNext("two")
			.expectNext("three")
			.expectNext("four")
			.expectComplete()
			.verify();
	}

	@Test
	void monoToFluxUsingFlatMapMapIterableTest() {
		ConvertMonoToFlux convertMonoToFlux = new ConvertMonoToFlux();
		StepVerifier.create(convertMonoToFlux.monoToFluxUsingFlatMapIterable(convertMonoToFlux.monoOfList()))
			.expectNext("one")
			.expectNext("two")
			.expectNext("three")
			.expectNext("four")
			.expectComplete()
			.verify();
	}
}
