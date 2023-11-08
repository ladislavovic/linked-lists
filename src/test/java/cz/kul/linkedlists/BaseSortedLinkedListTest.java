package cz.kul.linkedlists;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BaseSortedLinkedListTest {


	// TODO duplicity
	private static class DefaultComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer a, Integer b) {
			if (a != null) {
				if (b != null) {
					return a.compareTo(b);
				} else {
					return 1;
				}
			} else {
				if (b != null) {
					return -1;
				} else {
					return 0;
				}
			}
		}

	}



	@Test
	void add() {
		var list = new BaseSortedLinkedList<>(new DefaultComparator());
		list.add(3);
		assertEquals(List.of(3), list);
		list.add(5);
		assertEquals(List.of(3, 5), list);
		list.add(4);
		assertEquals(List.of(3, 4, 5), list);
		list.add(2);
		assertEquals(List.of(2, 3, 4, 5), list);
		list.add(null);
		assertEquals(Arrays.asList(null, 2, 3, 4, 5), list);
	}

	@Test
	void removeByIndex() {
		var list = new BaseSortedLinkedList<>(Arrays.asList(null, 2, 3, 4, 5), new DefaultComparator());
		list.remove(1);
		assertEquals(Arrays.asList(null, 3, 4, 5), list);
		list.remove(0);
		assertEquals(Arrays.asList(3, 4, 5), list);
		list.remove(2);
		assertEquals(Arrays.asList(3, 4), list);
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(2));
		assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
	}

	@Test
	void removeByItem() {
		var list = new BaseSortedLinkedList<>(Arrays.asList(null, 2, 3, 4, 5), new DefaultComparator());
		assertTrue(list.remove((Object) 3));
		assertEquals(Arrays.asList(null, 2, 4, 5), list);

		assertTrue(list.remove(null));
		assertEquals(Arrays.asList(2, 4, 5), list);

		assertTrue(list.remove((Object) 5));
		assertEquals(Arrays.asList(2, 4), list);

		Assertions.assertFalse(list.remove((Object) 10));
		assertEquals(Arrays.asList(2, 4), list);
	}

	@Test
	void size() {
		{
			var list = new BaseSortedLinkedList<>(Arrays.asList(null, 2, 3, 4, 5), new DefaultComparator());
			assertEquals(5, list.size());
		}

		{
			var list = new BaseSortedLinkedList<>(new DefaultComparator());
			assertEquals(0, list.size());
			list.add(10);
			assertEquals(1, list.size());
			list.add(10);
			assertEquals(2, list.size());
			list.remove(0);
			assertEquals(1, list.size());
			list.remove(0);
			assertEquals(0, list.size());
		}
	}

	@Test
	void isEmpty() {
	}

	@Test
	void indexOf() {
	}

	@Test
	void lastIndexOf() {
	}

	@Test
	void contains() {
	}

	@Test
	void iterator() {
	}

	@Test
	void listIterator() {
	}

	@Test
	void clear() {
	}

	@Test
	void testEquals() {
	}

	@Test
	void testHashCode() {
	}

	@Test
	void get() {
	}

	@Test
	void sublist() {
	}
}