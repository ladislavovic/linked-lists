package cz.kul.linkedlists;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

class BaseSortedLinkedList<T> implements List<T> {

	/**
	 * The first node of the linked linst
	 */
	private Node<T> first;

	/**
	 * The current size of the list
	 */
	private int size = 0;

	/**
	 * The comparator for value sorting
	 */
	private final Comparator<T> comparator;

	BaseSortedLinkedList(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	BaseSortedLinkedList(Collection<T> items, Comparator<T> comparator) {
		this.comparator = comparator;
		addAll(items);
	}

	@Override
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

	@Override
	public boolean remove(Object o) {
		Node<T> node = firstNodeAndIndex(o).node;
		if (node != null) {
			removeNode(node);
			return true;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object item : c) {
			if (!contains(item)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		for (T val : c) {
			add(val);
		}
		return !c.isEmpty();
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		throw new UnsupportedOperationException("Adding to particular index is not supported in sorted list.");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean modified = false;
		for (Object val : c) {
			if (remove(val)) {
				modified = true;
			}
		}
		return modified;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public T remove(int index) {
		checkIndex(index);
		Node<T> toRemove = getNode(index);
		removeNode(toRemove);
		return toRemove.getValue();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return first == null;
	}

	@Override
	public int indexOf(Object value) {
		return firstNodeAndIndex(value).index;
	}

	@Override
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

	@Override
	public boolean contains(Object value) {
		return indexOf(value) >= 0;
	}

	@Override
	public Iterator<T> iterator() {
		return listIterator();
	}

	@Override
	public Object[] toArray() {
		return toArray(new Object[0]);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <U> U[] toArray(U[] a) {

		// TODO does it store ArrayStoreException when wrong array type?

		if (a.length < size) {
			a = (U[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
		}
		Iterator<T> it = iterator();
		int i = 0;
		for (; it.hasNext(); i++) {
			a[i] = (U) it.next();
		}

		if (a.length > size) {
			a[size] = null;
		}

		return a;
	}

	@Override
	public ListIterator<T> listIterator() {
		return new BaseSortedLinkedListIterator();
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		// TODO impelement
		return null;
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
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
		while (i1.hasNext() && i2.hasNext()) {
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

	private Node<T> createNode(T value) {
		return new Node<T>(value);
	}

	private void checkIndex(int index) {
		if (index >= size || index < 0) {
			String msg = String.format("Index out of range. index=%s, size=%s", index, size);
			throw new IndexOutOfBoundsException(msg);
		}

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

	private Node<T> getNode(int index) {
		Node<T> n = first;
		for (long i = 0; i < index; i++) {
			n = n.getNext();
		}
		return n;
	}

	private void removeNode(Node<T> toRemove) {
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
	}

	private NodeAndIndex<T> firstNodeAndIndex(Object value) {
		int index = 0;
		for (Node<T> n = first; n != null; n = n.getNext()) {
			if (Objects.equals(value, n.getValue())) {
				return new NodeAndIndex<>(n, index);
			}
			index++;
		}
		return new NodeAndIndex<>(null, -1);
	}

	private class BaseSortedLinkedListIterator implements ListIterator<T> {

		Node<T> current;
		int currentIndex = 0;

		BaseSortedLinkedListIterator() {
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

	private static class NodeAndIndex<T> {
		Node<T> node;
		int index;

		public NodeAndIndex(Node<T> node, int index) {
			this.node = node;
			this.index = index;
		}

	}


}
