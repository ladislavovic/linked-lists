package cz.kul.linkedlists;

/**
 * Node of the bi-directional linked list.
 *
 * @param <T> Type of the element
 */
class Node<T> {

	/**
	 * Element holded in this node
	 */
	T element;

	/**
	 * Previous Node. Can be null.
	 */
	Node<T> previous;

	/**
	 * Next Node. Can be null.
	 */
	Node<T> next;

	public Node(T element) {
		this.element = element;
	}

	public T getElement() {
		return element;
	}

	public void setElement(T element) {
		this.element = element;
	}

	public Node<T> getPrevious() {
		return previous;
	}

	public void setPrevious(Node<T> previous) {
		this.previous = previous;
	}

	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}

}
