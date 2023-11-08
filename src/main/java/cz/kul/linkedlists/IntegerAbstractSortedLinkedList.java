package cz.kul.linkedlists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;

public class IntegerAbstractSortedLinkedList extends AbstractSortedLInkedList<Integer> {

	public IntegerAbstractSortedLinkedList() {
		this(new ArrayList<Integer>()); // TODO not nice
	}

	public IntegerAbstractSortedLinkedList(Collection<Integer> values) {
		this(values, new DefaultComparator());
	}

	public IntegerAbstractSortedLinkedList(Collection<Integer> values, Comparator<Integer> comparator) {
		super(comparator);
		for (Integer value : values) {
			this.add(value);
		}
	}

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


}
