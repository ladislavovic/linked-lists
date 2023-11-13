package cz.kul.linkedlists;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * <p>
 * Sorted linked list implementation for Integer elements.
 * </p>
 *
 * <p>
 * Elements are sorted from the lowest to greatest. Nulls are at the beginning. If you need
 * different order, use you own {@link Comparator}.
 * </p>
 *
 */
public class IntegerSortedLinkedList extends BaseSortedLinkedList<Integer> {

	public IntegerSortedLinkedList() {
		this(Collections.emptyList());
	}

	public IntegerSortedLinkedList(Collection<Integer> elements) {
		this(elements, new NullItemsComparatorWrapper<>(Integer::compareTo));
	}

	public IntegerSortedLinkedList(Collection<Integer> elements, Comparator<Integer> comparator) {
		super(comparator);
		this.addAll(elements);
	}

}
