package cz.kul.linkedlists;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public abstract class AbstractSortedLInkedList<T> {

	private Node<T> first;

	private int size = 0;

	private Comparator<T> comparator;

	AbstractSortedLInkedList(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public void add(T val) {
		Node<T> item = createNode(val);

		if (first == null) {
			first = item;
			return;
		}

		Node<T> current = first;
		while (true) {
			int res = comparator.compare(current.getValue(), val);
			if (res > 0) {
				if (current.getNext() == null) {
					item.setPrevious(current);
					break;
				} else {
					current = current.getNext();
				}
			} else {
				item.setPrevious(current.getPrevious());
				item.setNext(current);
				break;
			}
		}

		size++;
	}

	private Node<T> getNode(int index) {
		Node<T> n = first;
		for (long i = 0; i < index; i++) {
			n = first.getNext();
		}
		return n;
	}

	public T remove(int index) {
		checkIndex(index);

		Node<T> toRemove = getNode(index);

		Node<T> previous = toRemove.getPrevious();
		Node<T> next = toRemove.getNext();
		if (previous != null) {
			previous.setNext(next);
		}
		if (next != null) {
			next.setPrevious(previous);
		}

		// TODO is it needed? yes, document it more
		toRemove.setPrevious(null);
		toRemove.setNext(null);

		if (toRemove == first) {
			first = null;
		}

		size--;

		return toRemove.getValue();
	}

	public long size() {
		return size;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int indexOf(T value) {
		int index = 0;
		for (Node<T> n = first; n != null; n = n.getNext()) {
			if (Objects.equals(value, n.getValue())) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public int lastIndexOf(T value) {
		int index = 0;
		int lastMatch = -1;
		for (Node<T> n = first; n != null; n = n.getNext()) {
			if (Objects.equals(value, n.getValue())) {
				lastMatch = index;
			} else {
				if (lastMatch >= 0) {
					return lastMatch;
				}
			}
			index++;
		}
		return lastMatch;
	}

	public boolean contains(T value) {
		return indexOf(value) >= 0;
	}

	private class SLLIterator implements ListIterator<T> {

		SLLIterator() {
			current = first;
			currentIndex = 0;
		}

		Node<T> current;
		int currentIndex = 0;

		@Override
		public boolean hasNext() {
			return current.getNext() != null;
		}

		@Override
		public T next() {
			Node<T> ret = current;
			current = current.getNext();
			currentIndex++;
			return ret.getValue();
		}

		@Override
		public boolean hasPrevious() {
			return current.getPrevious() != null;
		}

		@Override
		public T previous() {
			Node<T> ret = current;
			current = current.getPrevious();
			currentIndex--;
			return ret.getValue();
		}

		@Override
		public int nextIndex() {
			return currentIndex;
		}

		@Override
		public int previousIndex() {
			return currentIndex - 1;
		}

		@Override
		public void remove() {
			// TODO

		}

		@Override
		public void set(T t) {
			current.setValue(t);
		}

		@Override
		public void add(T t) {
			// TODO
		}

	}

	public Iterator<T> iterator() {
		return listIterator();
	}

	public ListIterator<T> listIterator() {
		return new SLLIterator();
	}

	public void clear() {

		// TODO GC comment
		for (Node<T> n = first; n != null; ) {
			Node<T> next = n.getNext();
			n.setPrevious(null);
			n.setNext(null);
			n = next;
		}
		first = null;
	}

	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof List)) {
			return false;
		}

		Iterator<T> i1 = iterator();
		Iterator<?> i2 = ((List<?>) o).iterator();
		while(i1.hasNext() && i2.hasNext()) {
			if (!Objects.equals(i1.next(), i2.next())) {
				return false;
			}
		}
		return i1.hasNext() == i2.hasNext();
	}

	@Override
	public int hashCode() {
		T firstVal = isEmpty() ? null : first.getValue();
		return Objects.hash(firstVal, size);
	}

	public T get(int index) {
		checkIndex(index);

		Node<T> node = getNode(index);
		return node.getValue();
	}

	public AbstractSortedLInkedList<T> sublist(int from, int to) {
		checkIndex(from);
		checkIndex(to); // TODO to index can be equal to size

		// TODO implement
		return null;
	}

	private Node<T> createNode(T value) {
		return new Node<T>(value);
	}

	private void checkIndex(int index) {
		if (index >= size) {
			String msg = String.format("Index out of range. index={}, size={}", index, size);
			throw new IndexOutOfBoundsException(msg);
		}

	}


}
