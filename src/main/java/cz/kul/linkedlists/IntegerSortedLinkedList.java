package cz.kul.linkedlists;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class IntegerSortedLinkedList extends BaseSortedLinkedList<Integer> {

	public IntegerSortedLinkedList() {
		this(Collections.emptyList());
	}

	public IntegerSortedLinkedList(Collection<Integer> values) {
		this(values, new NullItemsComparatorWrapper<>(Integer::compareTo));
	}

	public IntegerSortedLinkedList(Collection<Integer> values, Comparator<Integer> comparator) {
		super(comparator);
		for (Integer value : values) {
			this.add(value);
		}
	}

}
