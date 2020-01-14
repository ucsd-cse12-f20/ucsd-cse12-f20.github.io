---
layout: pa
title: "PA2 (Closed): Manipulating Lists"
doodle: "../doodle.png"
---

**This assignment is <a href="../#programming">closed to collaboration</a>.**

This assignment will exercise your understanding of array and linked lists.

It is due on Wednesday, January 23 at 11pm. A submission link will be provided
on Gradescope by Monday, January 19.

## Setup and Goal

You can get the starter code at
[https://github.com/ucsd-cse12-w19/pa2-starter](https://github.com/ucsd-cse12-w19/pa2-starter),
which contains the following files:

- `StringList.java` – you *cannot* edit this file
- `StringTransformer.java` – you *cannot* edit this file
- `StringChooser.java` – you *cannot* edit this file
- `ArraySL.java` – you will edit this file
- `LinkedSL.java` – you will edit this file
- `TestLists.java` – you will edit this file
- `Choosers.java` – you will edit this file
- `Transformers.java` – you will edit this file


You are going to implement the `StringList` interface below twice, once for
array lists and once for linked lists:

```
public interface StringList {
  String[] toArray();
  void transformAll(StringTransformer st);
  void chooseAll(StringChooser sc);
  boolean isEmpty();
}
```

You will also implement a constructor for linked lists that takes a string
array (`String[]`) as a parameter; we've provided the constructor of this form
for array lists.

In total, you will implement and thoroughly test:

- `LinkedSL(String[] initialElements)`
- `isEmpty` for both types of list
- `toArray` for both types of list
- `transformAll` for both types of list
- `chooseAll` for both types of list
- several implementations of the `StringTransformer` and `StringChooser`
  interfaces

The related interfaces `StringTransformer` and `StringChooser` are defined as:

```
public interface StringTransformer {
  String transformElement(String s);
}

public interface StringChooser {
  boolean chooseElement(String s);
}
```

## Method and Class Descriptions

### `LinkedSL(String[] initialElements)`

*Constructor* that creates a new `LinkedSL` with its elements from
`initialElements` in the same order. For example, the following constructor call:

```
String[] input = {"a", "b", "c"};
LinkedSL = new LinkedSL(input);
```

should create a new Linked string list with contents `"a", "b", "c"`.

### `toArray`

Returns the contents of the list as a new array, with elements in the same
order they appear in the list. The length of the array produced must be the
same as the size of the list.

### `isEmpty`

Returns `true` if the list has no elements, `false` otherwise

### `transformAll`

Changes the contents of the list according to the provided `StringTransformer`.
Each element in the list should be given as an argument to the
`transformElement` method of the given transformer, with the result of the call
to `transformElement` stored in the list at the same index. For example,
consider the provided `UpperCaseTransformer` that implements
`StringTransformer`. If we construct a list like:

```
String[] contents = {"a", "b", "c"};
ArraySL asl = new ArraySL(contents);
asl.transformAll(new UpperCaseTransformer());
```

then we should expect the contents of the list after to be `"A", "B", "C"`.

### `chooseAll`

Changes the list to contain only elements selected by the `StringChooser`.
After calling `chooseAll`, the list should contain only the elements for which
calling `chooseElement` on the element returns `true`. The elements should remain in
the same order after `chooseAll` is called. For example, consider the provided
`LongWordChooser` that implements `StringChooser`. If we construct a list like:

```
String[] contents = {"longword", "longerword", "short"};
ArraySL asl = new ArraySL(contents);
asl.chooseAll(new LongWordChooser());
```

then we should expect the contents of the list after to be `"longword",
"longerword"`.

### Implementations of `StringChooser` and `StringTransformer`

You must add (at least) 2 implementations of **each** of these interfaces. Your
implementations of `StringChooser` should go into the file `Choosers.java`, and
your implementations of `StringTransformer` should go into the file
`Transformers.java`. You have free choice in what you implement for these, and
they will be graded manually.

## Getting Started

After you get the code, you will notice that the class bodies for both
`ArraySL` and `LinkedSL` are quite empty. Indeed, trying to compile the program
will result in errors like:

```
src/cse12pa2student/ArraySL.java:3: error: ArraySL is not abstract and does not override abstract method isEmpty() in StringList
public class ArraySL implements StringList {
       ^
src/cse12pa2student/LinkedSL.java:13: error: LinkedSL is not abstract and does not override abstract method isEmpty() in StringList
public class LinkedSL implements StringList {
       ^
2 errors
```

The first thing you should do is get a basic implementation of all the required
methods in place. For example, you might fill in `isEmpty` with a method that
always returns `false`:

```
public boolean isEmpty() {
  return false;
}
```

This clearly returns the wrong value, but by adding methods with the right
types, you can make the two implementations compile and start running the
tester. Do this first!

After doing this, you can start testing out the individual methods. You may
want to start with `ArraySL`, since the constructor is already provided,
implementing those methods first. You can run the tests and see the results for
both implementations, even if one is incorrect, as long as both compile.

Test as you go, and as you get more comfortable with the code move back and
forth between the implementations as you see fit to make progress.

## Testing

The thoroughness and correctness of your tests will be graded automatically. To
test correctness, we will run your tests against our reference implementation,
and they should all succeed. The thoroughness will be assessed by running your
tests against each buggy implementation and checking if the results are
different than on the reference implementation.

You will be able to see the correctness information in Gradescope to confirm
that your tests match our expected behavior. The thoroughness information will
be available only after you submit, so make sure to test a number of
interesting cases.

## Constraints

Your implementation is subject to the following constraints; violating them
will result in substantial deductions or a 0:

- You cannot use `ArraySL` to implement `LinkedSL` or vice versa
- You cannot add, remove, or change fields in `ArraySL` or `LinkedSL`
- Your implementations of `ArraySL` and `LinkedSL` cannot use the built-in Java
  collections classes (including `ArrayList` and `LinkedList`)
- In your tests in `TestLists.java`, you can only use the `makeList` method to
  construct lists, and you can only call methods declared on the `StringList`
  interface to manipulate the lists you create. This makes sure we can
  automatically grade your submission.

You *are* allowed and encouraged:

- to write any helper methods or classes you need or want
- to use methods you've already written within a class to help implement others

You are free to use all of the following resources:

- Code from this PA writeup
- Code from lecture
- Code from discussion
- Code posted on the course web site and linked resources
- Code from your past PAs
- Code that was public on Piazza before the PA was released
- Code or ideas from the official Java documentation

We encourage you to make heavy use of these resources! Much of these are linked
from the schedule on the course web page.

## Asking for Help

This is a *closed* PA, so you cannot get any help from other students or ask
implementation questions of the staff. If you have any questions about the PA,
you must ask them as _private_ questions on Piazza. Do not post publicly about
this PA, even for clarification questions. Doing so is a violation of academic
integrity. The full rules for closed PAs are <a href="../#programming">on the
course web site</a>.

You can always ask the staff about anything from lecture. For this PA, you may
find it especially helpful to go over the worksheets and code from January 14
(Monday) and January 16 (Wednesday). The staff will politely decline requests
to help with the PA, however.

If you have any policy questions, please ask!

## Style

There are no graded style requirements for this PA, which has the same
suggestions as PA1. We may give you feedback on style, which you should pay
attention to, because future assignments will assign points to style.

## README

You will answer the following question in your README:

1. Describe a mistake you made in your implementation, and how you fixed it.
(Don't worry if you don't think your implementation is fully complete when
answering this, just talk about some mistake you made to get to the point
you're at).

2. Was it easier to implement `toArray`, `transformAll`, and `chooseAll` on one of
`ArraySL` or `LinkedSL`? Why? (150 words or less)

## Rubric and Checklist

Checklist:

- `chooseAll` for `ArraySL` and for `LinkedSL`
- `transformAll` for `ArraySL` and for `LinkedSL`
- `isEmpty` for `ArraySL` and for `LinkedSL`
- `toArray` for `ArraySL` and for `LinkedSL`
- `LinkedSL` constructor with `String[]` parameter
- A correct and thorough set of tests
- 2 implementations of `StringChooser` (in addition to `LongWordChooser`)
- 2 implementations of `StringTransformer` (in addition to `UpperCaseTransformer`)
- 2 README questions

Rubric:

- 31 points – implementation correctness
  - 3 points for each of 9 methods [autograded]
  - 4 points for implementations of `StringChooser` and `StringTransformer` [manually graded]
- 5 points – test correctness [autograded]
- 10 points – test thoroughness [autograded]
- 4 points – written questions [manually graded]

(50 total points)

The submission link will be available on Gradescope as `pa2`.

## Modern Java: Lambda Expressions

**This section is not required for completing the PA, but is really cool and
can help you write more tests more quickly.**

Read this blog post, up to the part about Block Lambda Expressions:

[https://medium.freecodecamp.org/learn-these-4-things-and-working-with-lambda-expressions-b0ab36e0fffc](https://medium.freecodecamp.org/learn-these-4-things-and-working-with-lambda-expressions-b0ab36e0fffc)

Note that `StringChooser` and `StringTransformer` are _functional interfaces_.
This means that another way to write this example:

```
String[] contents = {"a", "b", "c"};
ArraySL asl = new ArraySL(contents);
asl.transformAll(new UpperCaseTransformer());
```

is to instead write:

```
String[] contents = {"a", "b", "c"};
ArraySL asl = new ArraySL(contents);
asl.transformAll(s -> s.toUpperCase());
```

This doesn't require writing the `UpperCaseTransformer` class at all!

Lambda expressions were added in Java version 8, and allow for us to write
methods and classes in the concise style shown above if we design with
functional interfaces in mind.

