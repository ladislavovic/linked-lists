# Sorted Linked Lists

This library provides implementation of sorted linked list. There are two implementations available:
* `IntegerSortedLinkedList` - for `Integer` elements
* `StringSortedLinkedList` - for `String` elements

## Requirements
This library requires Java 11. For logging it uses SLF4J. No other dependencies are required.

## Basic Features
Call constructor to create a list instance:
```java

// Create an empty list
var list = new IntegerSortedLinkedList();

// Create a list containing the given elements
var list = new IntegerSortedLinkedList(List.of(10, 5, 12));
```

Collections implements (almost all) methods from `java.util.List`
interface, so you can use it like "standard" Java lists:

```java
var list = new StringSortedLinkedList();

// add a few elements
list.add("foo");
list.add("bar");
list.add("baz");

// remove an element by index
list.remove(0);

// remove an element by value
list.remove("foo");

// create an Array
String[] arr = list.toArray(new String[0]);
```

It is allowed to have `null` elements in the list:
```java
var list = new StringSortedLinkedList();
list.add(null);
list.add(null);
assertEquals(2, list.size());
```

## Sorting
The lists are sorted - every time you add an element to the list, it
is put in the right place according to the sorting.

By default, elements are sorted in natural order and `null` items are
sorted to the beginning.

If you need a different sorting, provide your custom `Comparator`.
Then the collection is sorted according to the
given comparator:
```java
Comparator<String> customComparator = ...;
var list = new StringSortedLinkedList(customComparator);
```

## Concurrency
The implementation is not thread safe. If you need to access the list
from more threads, you must synchronize them. Otherwise the collection
can be in inconsisted state.
