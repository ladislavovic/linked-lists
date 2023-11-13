package cz.kul.linkedlists;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

/**
 * <p>
 * Sorted linked list implementation for String elements.
 * </p>
 *
 * <p>
 * Elements are sorted from the lowest to greatest. Nulls are at the beginning. If you need
 * different order, use you own {@link Comparator}.
 * </p>
 *
 */
public class StringSortedLinkedList extends BaseSortedLinkedList<String> {

	public StringSortedLinkedList() {
		this(Collections.emptyList());
	}

	public StringSortedLinkedList(Collection<String> elements) {
		this(elements, new NullItemsComparatorWrapper<>(String::compareTo));
	}

	public StringSortedLinkedList(Comparator<String> comparator) {
		this(Collections.emptyList(), comparator);
	}

	public StringSortedLinkedList(Collection<String> elements, Comparator<String> comparator) {
		super(comparator);
		this.addAll(elements);
	}

}
