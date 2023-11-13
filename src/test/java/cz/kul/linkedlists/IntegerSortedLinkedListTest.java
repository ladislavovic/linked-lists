package cz.kul.linkedlists;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IntegerSortedLinkedListTest {

	@Test
	void basicComparatorTest() {
		var list = new IntegerSortedLinkedList();
		assertTrue(list.isEmpty());
		list.add(10);
		assertEquals(List.of(10), list);
		list.add(9);
		assertEquals(List.of(9, 10), list);
		list.add(null);
		assertEquals(Arrays.asList(null, 9, 10), list);
		list.remove(0);
		assertEquals(List.of(9, 10), list);
		assertEquals(2, list.size());
	}

	@Test
	void customComparatorTest() {
		Comparator<Integer> evenOdd = (e1, e2) -> {
			int mod1 = e1 % 2;
			int mod2 = e2 % 2;
			return Integer.compare(mod1, mod2);
		};

		IntegerSortedLinkedList list = new IntegerSortedLinkedList(evenOdd);
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		assertEquals(Set.of(2, 4), Set.of(list.get(0), list.get(1)));
		assertEquals(Set.of(1, 3), Set.of(list.get(2), list.get(3)));
	}

}
