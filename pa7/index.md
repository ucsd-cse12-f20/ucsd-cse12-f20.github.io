---
layout: pa
title: "PA7: BSTs and Range Queries (closed)"
doodle: "/doodle.png"
---

In this assignment you’ll finish an implementation of a a `Map` interface that
makes use of ordering, and use its special properties to perform new kinds of
queries.

This PA is **closed** to collaboration.

You can get the starter code here:

https://github.com/ucsd-cse12-w19/pa7-starter

## Part I: Ordered Queries in `OrderedDefaultMap`

We've provided an **incomplete** implementations of `OrderedDefaultMap` for
you, implemented with a binary search tree, in `BSTMap.java`. It already
supports several methods, which we have or will discuss in lecture. You will
fill in the methods listed below.

- `keys`, `entries`, and `values`, which are specified to return elements
  according to the order of the keys. You **must** implement these in _O(n)_
  time, where _n_ is the number of elements in the tree.
- `floor` and `ceiling`, which query keys based on ordering. You **must**
  implement these in _O(h)_ time, where _h_ is the height of the tree.
- `range`, which selects a subset of the keys in the map in an ordered range.
  You **must** implement this in _O(m + h)_ time, where _h_ is the height of
  the tree, and _m_ is the number of elements in the tree between `low`
  (inclusive) and `high` (exclusive). The important thing to note here is that
  you should _not_ visit all the elements in the tree in order to implement
  this method.

These are all based on methods provided by Java's `TreeMap` class. More
information and references are provided in the Javadoc for `OrderedDefaultMap`.
You should implement and test these thoroughly.

You can also refer to the slides from
[discussion](https://docs.google.com/presentation/d/1YGHfoM81irUi1xgImlZZk0NPaXG4eRLR65_BRcqADQc/edit#slide=id.g4fd51eaa8b_0_237)
for some examples of range queries.

## Part II: A New Kind of Query

In PA6, you implemented comparison queries where you could provide two n-grams
and visualize a graph comparing them. In this PA, you will build a query that
takes two strings, and finds all the n-grams _between_ the strings. This is
particularly useful because it lets us query for all the n-grams that _start
with_ some string. For example, we could search for all the strings from `"has
a "` to `"has a!"` to get all the 3-grams that start with `has a`. This is
broken out in detail below.

### As a Console Interface

You will use this method to implement a new kind of query at the console. This
query, instead of producing a graph, simply prints out a list of the **top 10**
n-grams that are in range of the query:

```
Enter query: is a --is a!
is a good: 41
is a very: 40
is a lot: 16
is a prosperous: 15
is a great: 14
is a big: 11
is a little: 11
is a new: 10
is a major: 9
is a matter: 8
```

- The `--` is a separator that distinguishes the `low` and `high` parts of the
  query.
- The space after `is a ` is intentional and meaningful. It makes sure the
  string `is a` (with no space) is not included in the range.
- `!` is the first character _after_ the space character in ASCII, so this
  range includes all strings that start with `is a ` (with a space after the
  `a`).

The console interface should print _only_ the top 10 results, as ordered by the
total sum of times the n-gram appears across all years in the dataset. This
should be implemented in the `main` method of `Loader.java`.

You are free to re-use code and ideas from your PA6 submission for this. It's
interesting (though not required) to keep the other query interface with graph
drawing working at the same time as this one. For PA7 you're only required to
make this interface work.

### As Methods

You will implement several methods to help build out this interface.


#### rangeSearch

```
OrderedDefaultMap<String, Integer> rangeSearch(  
    OrderedDefaultMap<Integer, OrderedDefaultMap<String, Integer>> db,
    String low,
    String high)
```

This will return a new map that has keys from across _all_ the years of db,
where the key is between `low` (inclusive) and `high` (exclusive). The value of
the key in the returned map should be the **sum** of the number of times that key
appeared across all the years.

Some details and hints about the implementation:

- The `range` method you implement in the `BSTMap` class will be particularly
  effective here.
- You can refer to the slides from
  [discussion](https://docs.google.com/presentation/d/1YGHfoM81irUi1xgImlZZk0NPaXG4eRLR65_BRcqADQc/edit#slide=id.g4fd51eaa8b_0_237)
  for some examples of range queries and this method's behavior.
- Realistically, this method will be best at searching for relatively small
  ranges proportional to the overall dataset. Queries with `low` = `"a"` and
  `high` = `"z"` could reasonably hit limits like the stack overflowing or
  taking a long time to complete. You can check out the extensions section
  below for ideas about how to make these cases better.

#### topN

```
List<Entry<String, Integer>> topN(
    OrderedDefaultMap<Integer, OrderedDefaultMap<String, Integer>> db,
    String low, String high, int n)
```

This will return a list of (up to) the top `n` entries in _descending order by
value_, where the key is an n-gram and the value is the number of times it
appears across the whole dataset. You will probably want to implement this
method by making use of `rangeSearch` first, then extracting entries with
`.entries`, and finally performing the sort and choosing the top `n` elements.

Some details and hints about the implementation:

- If there are fewer than `n` words in the specified range, the returned list
  should only include those words.
- If there are more than `n` words in the specified range, the returned list
  should include only the `n` most frequently appearing words.
- You may find these methods particularly useful:
  - [Collections.sort](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Collections.html#sort(java.util.List,java.util.Comparator))
  - [List.subList](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/List.html#subList(int,int))
  - [Integer.compareTo](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Integer.html#compareTo(java.lang.Integer)) and [String.compareTo](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/String.html#compareTo(java.lang.String))

#### generateDatabase

You can test the methods above by constructing inputs by hand. You must also
read the sample data into a database as in PA6, but this time with an
`OrderedDefaultMap` instead of a `DefaultMap`. We are **not** providing an
implementation, you should make sure you understand this part of PA6 and adapt
it to work with the `OrderedDefaultMap` you complete in PA7.

```
OrderedDefaultMap<Integer, OrderedDefaultMap<String, Integer>> generateDatabase(Path path)
```

Other than the change in type, the behavior of the method should be the same as
in PA6. For PA7, you should be using a `BSTMap` rather than the
`DefaultMapImpl` you built in PA6 for the actual storage.

## `Comparator` for Instantiating `BSTMap` (and more!)

One important requirement of a binary search tree is to **compare** the keys.
Since the tree type itself is generic in the key type `K`, we can't guarantee
that the keys are integers or doubles (which are comparable with `<`), and we
can't even guarantee that they have a `compareTo` method (like `String`).
Instead, the _user_ of the tree needs to provide an implementation of
comparison when creating the map.

To support this, Java has a [built-in interface called
`Comparator`](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Comparator.html)
that expresses this idea of user-defined comparisons. An implementatation of
`Comparator` provides a single method, called `compare`, that takes two
elements of a particular type and returns a negative number if the first is
“smaller” than the second, `0` if they are equal, and a positive number if the
first is “larger” than the second.

For `Integer`s and `String`s, you can use _method references_ to create
instances of `Comparable` using built-in methods. The tests file provides an example:

```
/**
 * This is an example test that shows using a method reference as a Comparator.
 * The use of String::compareTo accomplishes the same thing as writing out a
 * whole interface implementation like below and using `new StringComparator()`.
 * 
 * It is highly recommended that you use method references like these to make
 * testing easier. Note that Integer::compareTo is a method that can be
 * referenced this way as well!
 * 
 * class StringComparator implements Comparator<String> {
 * 
 *   public int compare(String s1, String s2) { return s1.compareTo(s2); }
 * 
 * }
*/
@Test
public void testSetAndGet() {
  BSTMap<String, Integer> bst = new BSTMap<>(String::compareTo);
  bst.set("a", 1);
  assertEquals(1, (int) bst.get("a"));
}
```

Using these method references is sufficient for the `Integer` and `String` keys
used to build the map for the database.

Comparators can be particularly useful when we may want to compare items by
different criteria. For example, we may have a class defining a person:

```
class Person { String name; int age; }
```

And sometimes we want to order `People` by `name`, and other times by `age`. We
could define a `Comparator` for each of those cases:

```
class AgeComparator implements Comparator<Person> {
  public int compare(Person p1, Person p2) { return p1.age - p2.age; }
}
class NameComparator implements Comparator<Person> {
  public int compare(Person p1, Person p2) { return p1.name.compareTo(p2.name); }
}
```

You may find this idea useful when sorting `Entry` lists by value in `topN`.

## Testing

There are two files provided for you for testing, `BSTTest.java` and
`LoaderTest.java`. Both have some helpful hints about getting started testing
including some tests you may want to try yourself once you get far enough.

In addition, we've provided a directory called `./test-data` that contains some
files useful for testing against as sample text that's smaller than the full
dataset but still interesting. `LoaderTest.java` shows some examples of using
this data to test the database generation method and some others.

## Limitations

- Range queries on the large dataset may fail if they span very large ranges,
  and will perform best on short ranges. For example, if you query from `a` to
  `z`, you should expect to wait a very long time or get a stack overflow. The
  best ranges focus on prefix-style queries, and you should focus on handling
  queries like those at the end of the writeup
- In lecture, we discuss that adding keys in sorted order is the worst case for
  this BST data structure. Keep that in mind as you use this structure; are
  there ways you may find yourself running into this worst case? Can you work
  around it?

Note that these problems are addressed explicitly by data structures you will
implement in CSE 100!

## README

Respond to the following prompts in your README:

1. Copy the output of 2 range queries on the large dataset that you found
interesting and explain why you find them interesting
2. Measure the time to load the database with your `BSTMap`. Is loading the
database faster in terms of real time in milliseconds with BSTMap or with the
DefaultMapImpl from PA6? Why?
3. Speculate – would doing a range query with the DefaultMapImpl be faster or
slower in terms of milliseconds than with the BSTMap. Why?
4. Justify the runtime bounds for the methods you wrote in `BSTMap`

## Asking for Help

This is a **closed** PA. That said, there are many ways to ask for related help:

- Ask anything as a private Piazza question (though you are not guaranteed a
  detailed answer)
- PA6 is still open, and you can learn a lot by completing PA6 fully. You can
  do all of the `BSTMap` methods without thinking about PA6, but it's a great
  idea to get detailed help on PA6 before tackling the `Loader` methods in PA7
  if you don't have a complete PA6
- You can always ask for clarification about built-in Java libraries
- You can always ask for clarification and help with the BST implementations we
  work through in class

## Rubric

Total 50 points

- `BSTMap` methods: 20 points [automatic]
- `Loader` methods other than `main`: 14 points [automatic]
- `Loader` main and query interface: 4 points [manual]
- `README`: 8 points [manual]
- Style: 4 points [manual]

## Extensions

These are not for credit, but you may find them interesting to explore.

- Extend the interface (and the query input) to give responses in a year range
  AND a range of keys
- Extend the interface (and the query input) to allow filtering on the
  _category_ of the source of the string (fiction, news, etc), in addition to
  the year and a word range
- The trees we implemented have bad performance if we insert all the keys in
  sorted order. Come up with a way to improve the performance of the tree in
  this case.
- We used range queries to construct a kind of _prefix query_, where we found
  all strings that started with a certain substring. Can you come up with a
  specialized kind of tree that might be particularly good at prefix queries on
  strings (it doesn't need to support all possible key types).

## Other Query Outputs

Here are some other query outputs from our implementation. Since we have a
different filtering process than you might, your answers may differ slightly,
but they should be similar.


```
Enter query: has a --has a!
has a lot: 15
has a new: 9
has a chance: 7
has a different: 5
has a good: 4
has a great: 4
has a number: 4
has a plan: 4
has a very: 4
has a big: 3
```

```
Enter query: computer--computer!
computer: 139
computer screen: 7
computer and: 5
computer animation: 5
computer industry: 5
computer science: 5
computer model: 4
computer to: 4
computer for: 3
computer literacy: 3
```

```
Enter query: undergraduate --undergraduate!
undergraduate students: 9
undergraduate education: 5
undergraduate students (: 5
undergraduate courses: 2
undergraduate degree: 2
undergraduate English: 1
undergraduate English major: 1
undergraduate I: 1
undergraduate I spent: 1
undergraduate a: 1
```
