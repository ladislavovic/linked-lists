package cz.kul.linkedlists;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * <p>An implementation of sorted linked list.</p>
 *
 * <p>It is a base class for lists containing particular data types elements.</p>
 *
 * <p>It implements {@link List} interface. So the behaviour of methods
 * inherited from this interface is defined there.</p>
 *
 * <p>Features</p>
 * <ul>
 *   <li>Keeps elements sorted</li>
 *   <li>Can contain null elements</li>
 *   <li>For singlethread access only. If you need to access from more threads, you must synchronize.</li>
 * </ul>
 *
 * @param <T> Type of contained element
 *
 * @see List
 */
class BaseSortedLinkedList<T> implements List<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaseSortedLinkedList.class);

	/**
	 * The first node of the linked list
	 */
	private Node<T> first;

	/**
	 * The current size of the list
	 */
	private int size = 0;

	/**
	 * The comparator for element sorting
	 */
	private final Comparator<T> comparator;

	BaseSortedLinkedList(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	BaseSortedLinkedList(Collection<T> elements, Comparator<T> comparator) {
		this.comparator = comparator;
		addAll(elements);
	}

	@Override
	public boolean add(T e) {
		Node<T> node = createNode(e);

		if (first == null) {
			first = node;
			size++;
			LOGGER.trace("An element '" + e + "' added.");
			return true;
		}

		Node<T> current = first;
		while (true) {
			int res = comparator.compare(e, current.getElement());
			if (res > 0) {
				if (current.getNext() == null) {
					insertAfter(current, node);
					break;
				} else {
					current = current.getNext();
				}
			} else {
				insertBefore(current, node);
				break;
			}
		}

		size++;
		return true;
	}

	@Override
	public boolean remove(Object o) {
		Node<T> node = findFirstOccurrence(o);
		if (node != null) {
			removeNode(node);
			return true;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object element : c) {
			if (!contains(element)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		for (T e : c) {
			add(e);
		}
		return !c.isEmpty();
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		throw notPossibleBecauseOfSortedCollection();
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean modified = false;
		for (Object e : c) {
			if (remove(e)) {
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
		return toRemove.getElement();
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
	public int indexOf(Object e) {
		return findIndexOfFirstOccurrence(e);
	}

	@Override
	public int lastIndexOf(Object e) {
		int index = 0;
		int lastMatch = -1;
		for (Node<T> n = first; n != null; n = n.getNext()) {
			if (Objects.equals(e, n.getElement())) {
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
	public boolean contains(Object e) {
		return indexOf(e) >= 0;
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
		return new BaseSortedLinkedListIterator(first, 0);
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		checkIndex(index);
		Node<T> node = getNode(index);
		return new BaseSortedLinkedListIterator(node, index);
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}

	public void clear() {

		// Cleaning links between nodes is not necessary but it
		// makes work easier for GC
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
		T firstVal = isEmpty() ? null : first.getElement();
		return Objects.hash(firstVal, size);
	}

	public T get(int index) {
		checkIndex(index);

		Node<T> node = getNode(index);
		return node.getElement();
	}

	@Override
	public T set(int index, T element) {
		throw notPossibleBecauseOfSortedCollection();
	}

	@Override
	public void add(int index, T element) {
		throw notPossibleBecauseOfSortedCollection();
	}

	private Node<T> createNode(T e) {
		return new Node<T>(e);
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

		LOGGER.trace("An element '" + inserted.getElement() + "' added.");
	}

	private void insertAfter(Node<T> node, Node<T> inserted) {
		inserted.setPrevious(node);
		inserted.setNext(node.getNext());

		if (node.getNext() != null) {
			node.getNext().setPrevious(inserted);
		}

		node.setNext(inserted);

		LOGGER.trace("An element '" + inserted.getElement() + "' added.");
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

		toRemove.setPrevious(null);
		toRemove.setNext(null);

		if (toRemove == first) {
			first = next;
		}

		size--;

		LOGGER.trace("An element '" + toRemove.getElement() + "' removed.");
	}

	private NodeAndIndex<T> findFirstOccurrenceWithIndex(Object e) {
		int index = 0;
		for (Node<T> n = first; n != null; n = n.getNext()) {
			if (Objects.equals(e, n.getElement())) {
				return new NodeAndIndex<>(n, index);
			}
			index++;
		}
		return new NodeAndIndex<>(null, -1);
	}

	private Node<T> findFirstOccurrence(Object e) {
		return findFirstOccurrenceWithIndex(e).node;
	}

	private int findIndexOfFirstOccurrence(Object e) {
		return findFirstOccurrenceWithIndex(e).index;
	}

	private UnsupportedOperationException notPossibleBecauseOfSortedCollection() {
		throw new UnsupportedOperationException("This operations does not make sense in sorted collection.");
	}

	private class BaseSortedLinkedListIterator implements ListIterator<T> {

		/** This node is returned when you call {@link #next()} */
		Node<T> next;

		/** This node is returned when you call {@link #previous()} */
		Node<T> previous;

		/** The last node returned by {@link #next()} or {@link #previous()} */
		Node<T> lastReturned;

		/** The index of current iterator position */
		int nextIndex = 0;

		BaseSortedLinkedListIterator(Node<T> node, int nodeIndex) {
			next = node;
			previous = node != null ? node.getPrevious() : null;
			nextIndex = nodeIndex;
		}

		@Override
		public boolean hasNext() {
			return next != null;
		}

		@Override
		public T next() {
			if (next == null) {
				throw new NoSuchElementException();
			}
			lastReturned = next;
			previous = next;
			next = next.getNext();
			nextIndex++;
			return lastReturned.getElement();
		}

		@Override
		public boolean hasPrevious() {
			return previous != null;
		}

		@Override
		public T previous() {
			if (previous == null) {
				throw new NoSuchElementException();
			}
			lastReturned = previous;
			next = previous;
			previous = previous.getPrevious();
			nextIndex--;
			return lastReturned.getElement();
		}

		@Override
		public int nextIndex() {
			return nextIndex;
		}

		@Override
		public int previousIndex() {
			return nextIndex - 1;
		}

		@Override
		public void remove() {
			if (lastReturned == null) {
				throw new IllegalStateException();
			}
			if (previous == lastReturned) {
				previous = lastReturned.getPrevious();
				removeNode(lastReturned);
				nextIndex--;
			} else if (next == lastReturned) {
				next = lastReturned.getNext();
				removeNode(lastReturned);
				next = previous.next;
			} else {
			  throw new IllegalStateException("An unexpected state occurred.");
			}
			lastReturned = null;
		}

		@Override
		public void set(T t) {
			throw notPossibleBecauseOfSortedCollection();
		}

		@Override
		public void add(T t) {
			throw notPossibleBecauseOfSortedCollection();
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
