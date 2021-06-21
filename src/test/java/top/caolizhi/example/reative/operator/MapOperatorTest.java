package top.caolizhi.example.reative.operator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import reactor.test.StepVerifier;

class MapOperatorTest {

	@Test
	void mapOperatorTest() {
		MapOperator mapOperator = new MapOperator();
		StepVerifier.create(mapOperator.mapOperator())
			.expectNext("CAOLIZHI")
			.expectNext("TOP")
			.expectComplete()
			.verify();
	}
}
