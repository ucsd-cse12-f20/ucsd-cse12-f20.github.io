
---

# PA2 (Closed):Lists
---

**This assignment is <a href="../#programming">closed to collaboration</a>.**

This assignment will exercise your understanding of array and linked lists.

This PA is due on ** **Wednesday, October 21 at 11:59pm** **

## Getting the Code

Link to the starter code: https://github.com/CSE12-F20-Assignments/cse12-fa20-pa2-Lists-starter

Note that it is in a Github repository. There are two easy ways to download the starter files.

1. Download as a ZIP folder 

    After going to the Github repository, you should see a green button that says *Code*. Click on that button. Then click on *Download ZIP*. This should download all the files as a ZIP folder. You can then unzip/extract the zip bundle and move it to wherever you would like to work.

2. Using git clone (requires terminal/command line)

    After going to the Github repository, you should see a green button that says *Code*. Click on that button. You should see something that says *Clone with HTTPS*. Copy the link that is in that section. In terminal/command line, navigate to whatever folder/directory you would like to work. Type the command `git clone _` where the `_` is replaced with the link you copied. This should clone the repository on your computer and you can then edit the files on whatever IDE you see fit.
    
If you are unsure or have questions about how to get the starter code, feel free to make a Piazza post or ask a tutor for help.

## Code Layout

- `StringList.java` – you *cannot* edit this file
- `StringTransformer.java` – you *cannot* edit this file
- `StringChooser.java` – you *cannot* edit this file
- `ArraySL.java` – you will edit this file
- `LinkedSL.java` – you will edit this file
- `TestLists.java` – you will edit this file
- `Choosers.java` – you will edit this file
- `Transformers.java` – you will edit this file

## Part 1: Implementation (42 points)

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

- `LinkedSL(String[] contents)`
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

### `LinkedSL(String[] contents)`

*Constructor* that creates a new `LinkedSL` with its elements from
`contents` in the same order. For example, the following constructor call:

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

### Part 2: Implementations of `StringChooser` and `StringTransformer` (4 points)

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
that your tests match our expected behavior. 

###### What is is wheat and chaff?  
* Wheat and chaff are terms to describe good and bad code. **Be sure to understand these two terms as you will find that they will be used in later programming assignments as well!** Wheat refers to  functional code while chaff refers to buggy code. Throughout this course we will do the following in order to score your written tests:
    * running your written tests against a wheat implementation to see if your tests are correct. 
    * running your tests against a series of chaff implementations to make sure your tests are thorough and able to catch potential bugs.  

The following table shows the test case breakdown along with some descriptions to help you as you write your own tests. Note, **this is not comprehensive** as you will have to think of some of your own test cases but this will help guide you. 


| Test Cases   | Description | Points |
|--------------|-------------|--------|
| chaff implementations |The following are examples of bad implementations where your tests will be expected to catch the bugs, look at the names to help get an idea of what the bug could be. For tricker bugs, further explanations are given. <ul><li>chaffAlwaysChoosesFirstArraySL<ul><li>in ArraySL, chooseAll() always chooses the first element</li></ul></li><li>chaffIsEmptyReturnsTrueIfSizeGreaterThan0ArraySL</li><li>chaffReturnNewArrayArraySL<ul><li>in ArraySL, toArray() does not create a new array</li></ul></li><li>chaffIsEmptyReturnsFalseSizeGreaterThan3</li><li>chaffMustChooseLastLinkedSL</li><li>chaffDoWhileToArrayLinkedSL<ul><li>in LinkedSL, a do-while loop is used in toArray()</li></ul></li><li>chaffDoWhileTransformArraySL</li><li>chaffFixedSizeConstructorLinkedSL</li><li>chaffIncorrectTransformBoundsLinkedSL</li><li>chaffIncludeNullToArrayArraySL<ul><li>in ArraySL, extra nulls are being copied to the end of the new array</li></ul></li></ul> | 10 |
| wheat implementation | `TestLists.java` will be used against a correct implementation. This will check if the tests written are correct and do not flag any errors for the wheat implementation. | 5 |
| Constructor | Correctly populates the instance variables for the object. <ul><li>pass an empty array</li><li>pass an array with multiple values</li> </ul>| 3 |
| isEmpty |  Correctly checks to see if the ArraySL/LinkedSL is empty. <ul><li>the list is empty, returns True</li><li>the list is not empty with multiple values, returns False</li><li>the list is not empty with only one value, returns False</li> </ul>| 6 (3 for ArraySL, 3 for LinkedSL)|
| toArray |  Correctly returns an array of the values that were in the ArraySL/LinkedSL. <ul><li>the list is empty</li><li>the list has a large size</li><li>creates a new array to return</li> </ul> | 6 (3 for ArraySL, 3 for LinkedSL)|
| choose | Correctly chooses the desired elements. <ul><li>the list is empty</li><li>choose all of the elements in the list</li><li>list with only two elements, choose second element</li> <li>choose first and last in list</li></ul> | 6 (3 for ArraySL, 3 for LinkedSL)|
| transform | Correctly transforms the elements in the list. <ul><li>the list is empty</li><li>the list has two items</li><li>the list has a large size</li> </ul>| 6 (3 for ArraySL, 3 for LinkedSL)|

##### Sanity Check
When you submit, you will see a `Sanity Check`. This is a subset of the tests we will be running on your code to help see if you are on the right track. **Passing all of these does not necessarily mean you will get full credit!!!** You need to write your own tests to make sure you have the correct functionality for all of the required methods.


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
find it especially helpful to go over the worksheets and code from the lecture
on Array Lists and Linked Lists. The staff will politely decline requests
to help with the PA, however.

If you have any policy questions, please ask!

## Style

There are no graded style requirements for this PA, which has the same
suggestions as PA1. We may give you feedback on style, which you should pay
attention to, because future assignments will assign points to style.

## Part 3: Gradescope Assignment (4 points)

You will also answer question on Gradescope regarding the assignment.
The following are the questions you will need to answer. **Make sure to submit directly to the Gradescope assignment: "Programming Assignment 2 - questions"** 

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
- 2 assignment questions


## Submitting

#### Part 1
On the Gradescope assignment **Programming Assignment 2 - code** please submit the following file structure:

* *cse12pa2student*
    * ArraySL.java
    * Choosers.java
    * LinkedSL.java
    * StringChooser.java
    * StringList.java
    * StringTransformer.java
    * TestLists.java
    * Transformers.java

The easiest way to submit your files is to zip the `cse12pa2student` folder and upload that to Gradescope. You may also use the bash script provided, `prepare-submission.sh`.
You may encounter errors if you submit extra files or directories. You may submit as many times as you like till the deadline. 
#### Part 2
Please submit your answers to the questions from part 2 on the Gradescope assignment **Programming Assignment 2 - questions**. You may submit as many times as you like till the deadline.

## Scoring (50 points total)

* **Coding Style** (0 points)
* **Correctness** (42 points)
    * Does your code compile? If not, you will get 0 points.
    * Does it pass all of the provided unit tests?
* **Implementations of StringChooser/StringTransformer** (4 points)
* **Gradescope Assignment** (4 points)
    



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


