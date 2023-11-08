package cz.kul.linkedlists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

public class StringSortedLinkedList extends BaseSortedLinkedList<String> {

	public StringSortedLinkedList() {
		this(Collections.emptyList());
	}

	public StringSortedLinkedList(Collection<String> values) {
		this(values, new NullItemsComparatorWrapper<>(String::compareTo));
	}

	public StringSortedLinkedList(Collection<String> values, Comparator<String> comparator) {
		super(comparator);
		for (String value : values) {
			this.add(value);
		}
	}

}
