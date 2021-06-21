package top.caolizhi.example.reative.operator;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

class FlatmapOperatorTest {

	@Test
	void flatmapOperatorTest() {
		List<String> list = Lists.newArrayList();
		FlatmapOperator flatmapOperator = new FlatmapOperator();
		flatmapOperator.flatmapOperator().subscribe(list::add);
		assertThat(list).containsExactly("C","A","O","L","I","Z","H","I", "T","O","P");
	}
}
