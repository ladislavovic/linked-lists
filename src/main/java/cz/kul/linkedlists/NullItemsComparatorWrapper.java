package cz.kul.linkedlists;

import java.util.Comparator;

public class NullItemsComparatorWrapper<T> implements Comparator <T> {

	private Comparator<T> wrapped;

	public NullItemsComparatorWrapper(Comparator<T> wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public int compare(T a, T b) {
		if (a != null) {
			if (b != null) {
				return wrapped.compare(a, b);
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
