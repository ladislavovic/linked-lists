package cz.kul.linkedlists;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

class BaseSortedLinkedList<T> implements List<T> {

	private Node<T> first;

	private int size = 0;

	private Comparator<T> comparator;

	BaseSortedLinkedList(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	BaseSortedLinkedList(Collection<T> items, Comparator<T> comparator) {
		this.comparator = comparator;
		for (T item : items) {
			add(item); // TODO replace with addAll
		}
	}

	public boolean add(T val) {
		Node<T> item = createNode(val);

		if (first == null) {
			first = item;
			size++;
			return true;
		}

		Node<T> current = first;
		while (true) {
			int res = comparator.compare(val, current.getValue());
			if (res > 0) {
				if (current.getNext() == null) {
					insertAfter(current, item);
					break;
				} else {
					current = current.getNext();
				}
			} else {
				insertBefore(current, item);
				break;
			}
		}

		size++;
		return true;
	}

	private void insertBefore(Node<T> node, Node<T> inserted) {
		inserted.setPrevious(node.getPrevious());
		inserted.setNext(node);

		if (node.getPrevious() != null) {
			node.getPrevious().setNext(inserted);
		}

		node.setPrevious(inserted);

		if (node == first) {
			first = inserted;
		}
	}

	private void insertAfter(Node<T> node, Node<T> inserted) {
		inserted.setPrevious(node);
		inserted.setNext(node.getNext());

		if (node.getNext() != null) {
			node.getNext().setPrevious(inserted);
		}

		node.setNext(inserted);
	}









	@Override
	public boolean remove(Object o) {
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return false;
	}






	private Node<T> getNode(int index) {
		Node<T> n = first;
		for (long i = 0; i < index; i++) {
			n = n.getNext();
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
			first = next;
		}

		size--;

		return toRemove.getValue();
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int indexOf(Object value) {
		int index = 0;
		for (Node<T> n = first; n != null; n = n.getNext()) {
			if (Objects.equals(value, n.getValue())) {
				return index;
			}
			index++;
		}
		return -1;
	}

	public int lastIndexOf(Object value) {
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

	public boolean contains(Object value) {
		return indexOf(value) >= 0;
	}

	private class SLLIterator implements ListIterator<T> {

		Node<T> current;
		int currentIndex = 0;

		SLLIterator() {
			current = first;
			currentIndex = 0;
		}

		@Override
		public boolean hasNext() {
			return current != null;
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







	@Override
	public Object[] toArray() {
		return new Object[0];
	}

	@Override
	public <T1> T1[] toArray(T1[] a) {
		return null;
	}

	public ListIterator<T> listIterator() {
		return new SLLIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		return null;
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return null;
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


	@Override
	public String toString() {
		Iterator<T> it = iterator();
		if (it.hasNext()) {
			return "[]";
		}

		final int MAX = 10;
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = 0; i < MAX && it.hasNext(); i++) {
			sb.append(it.next());
			if (it.hasNext()) {
				sb.append(", ");
			}
		}

		if (size >= MAX) {
			sb.append("...");
		}

		sb.append("]");
		return sb.toString();
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

	@Override
	public T set(int index, T element) {
		return null;
	}

	@Override
	public void add(int index, T element) {

	}

	public BaseSortedLinkedList<T> sublist(int from, int to) {
		checkIndex(from);
		checkIndex(to); // TODO to index can be equal to size

		// TODO implement
		return null;
	}

	private Node<T> createNode(T value) {
		return new Node<T>(value);
	}

	private void checkIndex(int index) {
		if (index >= size || index < 0) {
			String msg = String.format("Index out of range. index=%s, size=%s", index, size);
			throw new IndexOutOfBoundsException(msg);
		}

	}


}
