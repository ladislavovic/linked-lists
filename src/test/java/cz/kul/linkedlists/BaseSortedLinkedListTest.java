package cz.kul.linkedlists;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class BaseSortedLinkedListTest {

	final Comparator<Integer> DEFAULT_COMPARATOR = new NullItemsComparatorWrapper<>(Integer::compareTo);

	@Test
	void add() {
		var list = new BaseSortedLinkedList<>(DEFAULT_COMPARATOR);
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
		var list = new BaseSortedLinkedList<>(Arrays.asList(null, 2, 3, 4, 5), DEFAULT_COMPARATOR);
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
		var list = new BaseSortedLinkedList<>(Arrays.asList(null, 2, 3, 4, 5), DEFAULT_COMPARATOR);
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
			var list = new BaseSortedLinkedList<>(Arrays.asList(null, 2, 3, 4, 5), DEFAULT_COMPARATOR);
			assertEquals(5, list.size());
		}

		{
			var list = new BaseSortedLinkedList<>(DEFAULT_COMPARATOR);
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
	void linkIterator() {
		BaseSortedLinkedList<Integer> list = new BaseSortedLinkedList<>(List.of(0, 1, 2, 3, 4, 5), DEFAULT_COMPARATOR);
		ListIterator<Integer> it = list.listIterator();

		assertThrows(NoSuchElementException.class, it::previous);
		checkIteratorState(it, true, false, 0, -1);
		assertEquals(0, it.next());
		assertEquals(1, it.next());
		assertEquals(2, it.next());
		checkIteratorState(it, true, true, 3, 2);
		assertEquals(3, it.next());
		assertEquals(4, it.next());
		assertEquals(5, it.next());
		checkIteratorState(it, false, true, 6, 5);
		assertThrows(NoSuchElementException.class, it::next);
		assertEquals(5, it.previous());
		assertEquals(4, it.previous());
		assertEquals(3, it.previous());
		checkIteratorState(it, true, true, 3, 2);
		assertEquals(2, it.previous());
		assertEquals(1, it.previous());
		assertEquals(0, it.previous());
		checkIteratorState(it, true, false, 0, -1);
		assertThrows(NoSuchElementException.class, it::previous);
	}

	private void checkIteratorState(
			ListIterator<Integer> it,
			boolean hasNext,
			boolean hasPrevious,
			int nextIndex,
			int previousIndex) {
		assertEquals(hasNext, it.hasNext());
		assertEquals(hasPrevious, it.hasPrevious());
		assertEquals(nextIndex, it.nextIndex());
		assertEquals(previousIndex, it.previousIndex());
	}

	@Test
	void testRemovingByIterator() {
		BaseSortedLinkedList<Integer> list = new BaseSortedLinkedList<>(List.of(0, 1, 2, 3, 4, 5), DEFAULT_COMPARATOR);
		ListIterator<Integer> it = list.listIterator();
		assertThrows(IllegalStateException.class, it::remove);
		it.next();
		it.remove();
		assertEquals(List.of(1, 2, 3, 4, 5), list);
		it.next();
		it.next();
		it.next();
		it.remove();
		assertEquals(List.of(1, 2, 4, 5), list);
		it.next();
		it.next();
		it.remove();
		assertEquals(List.of(1, 2, 4), list);
	}

	@Test
	void testStream() {
		BaseSortedLinkedList<Integer> list = new BaseSortedLinkedList<>(List.of(0, 1, 2, 3, 4, 5), DEFAULT_COMPARATOR);
		List<Integer> list2 = list.stream().collect(Collectors.toList());
		assertEquals(list, list2);
	}

	@Test
	void lastIndexOf() {
		BaseSortedLinkedList<Integer> list = new BaseSortedLinkedList<>(
				Arrays.asList(null, null, null, 1, 0, 5, 3, 3, 3),
				DEFAULT_COMPARATOR);
		assertEquals(2, list.lastIndexOf(null));
		assertEquals(3, list.lastIndexOf(0));
		assertEquals(4, list.lastIndexOf(1));
		assertEquals(7, list.lastIndexOf(3));
		assertEquals(8, list.lastIndexOf(5));
	}

	@Test
	void clear() {
		BaseSortedLinkedList<Integer> list = new BaseSortedLinkedList<>(Arrays.asList(1, 2, 3), DEFAULT_COMPARATOR);
		assertFalse(list.isEmpty());
		list.clear();
		assertTrue(list.isEmpty());
	}

}
