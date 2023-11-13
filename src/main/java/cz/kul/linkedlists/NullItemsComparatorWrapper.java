package cz.kul.linkedlists;

import java.util.Comparator;

/**
 * <p>
 * Comparator wrapper, which can compare null elements.
 * </p>
 *
 * <p>
 * It considers null as lower than any not null element. If there
 * are two not null elements, they are compared by wrapped
 * comparator.
 * </p>
 *
 * @param <T> Type of the contained element
 */
class NullItemsComparatorWrapper<T> implements Comparator <T> {

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
