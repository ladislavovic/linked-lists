package cz.kul.linkedlists;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringSortedLinkedListTest {

	@Test
	void basicTest() {
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

}
