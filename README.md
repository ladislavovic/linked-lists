# Sorted Linked Lists

This library provides implementation of sorted linked list. There are two implementations:
* `IntegerSortedLinkedList` - for `Integer` items
* `StringSortedLinkedList` - for `String` items

## Basic Features
Call constructor to create an instance of the list:
```java

// Create an empty list
var list = new IntegerSortedLinkedList();

// Create a list containing the given items
var list = new IntegerSortedLinkedList(List.of(10, 5, 12));
```

Collections implements (almost all) methods from `java.util.List`
interface, so you can use it like "standard" Java lists:

```java
var list = new StringSortedLinkedList();

// add a few items
list.add("foo");
list.add("bar");
list.add("baz");

// remove an item by index
list.remove(0);

// remove an item by value
list.remove("foo");

// create an Array
String[] arr = list.toArray(new String[0]);
```

The collections can contain `null` items:
```java
var list = new StringSortedLinkedList();
list.add(null);
list.add(null);
assertEquals(2, list.size());
```

## Sorting
The lists are sorted - every time you add an item to the list, it
is put in the right place according to sorting.

The default sorting sorts the items in natural way and `null` items are
sorted to the beginning.

If you need a different sorting, provide a `Comparator` instance during
the list creation, then the collection is sorted according to you 
comparator:
```java
Comparator<String> customComparator = ...;
var list = new StringSortedLinkedList(customComparator);
```

## Pointers

## Concurrency
The implementation is not thread safe. If you need to access the list
from more threads, you must synchronize them. Otherwise the collection
can be in inconsisted state.

## Missing features
* method `List.subList(int fromIndex, int toIndex)` is not implemented
* method `List.retainAll(Collection<?> c)` is not implemented
