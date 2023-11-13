package cz.kul.linkedlists;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringSortedLinkedListTest {

	@Test
	void basicComparatorTest() {
		var list = new StringSortedLinkedList();
		assertTrue(list.isEmpty());
		list.add("foo");
		assertEquals(List.of("foo"), list);
		list.add("bar");
		assertEquals(List.of("bar", "foo"), list);
		list.add(null);
		assertEquals(Arrays.asList(null, "bar", "foo"), list);
		list.remove(0);
		assertEquals(List.of("bar", "foo"), list);
		assertEquals(2, list.size());
	}

	@Test
	void customComparatorTest() {
		StringSortedLinkedList list = new StringSortedLinkedList((e1, e2) -> {
			int length1 = e1 != null ? e1.length() : -1;
			int length2 = e2 != null ? e2.length() : -1;
			return Integer.compare(length1, length2);
		});
		list.add("hello");
		list.add("good morning");
		list.add("hi");
		list.add(null);
		assertEquals(Arrays.asList(null, "hi", "hello", "good morning"), list);
	}

}
