package cz.kul.linkedlists;

public class IntItem { // TODO must be not public class

	private int value;

	private IntItem next;

	private IntItem previous;

	public IntItem(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public IntItem getNext() {
		return next;
	}

	public void setNext(IntItem next) {
		this.next = next;
	}

	public IntItem getPrevious() {
		return previous;
	}

	public void setPrevious(IntItem previous) {
		this.previous = previous;
	}

}
