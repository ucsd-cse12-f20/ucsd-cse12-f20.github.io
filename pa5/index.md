---
layout: pa
title: "PA5: Testing Partition (closed)"
doodle: "/doodle.png"
---

This assignment will teach you how to write tests in a thorough, automated way,
will explore some properties of quicksort, and will give you structured
practice in re-using code you find on the Web.

*This assignment is inspired by [an assignment from Brown University's
CS019](https://cs.brown.edu/courses/cs019/2016/sortaclesortacle.html).*

## Part I: A Bad (and Good) Implementation Detector

### Testing with Properties

So far in this class, we have usually written tests by following this process:

1. Construct the input data
2. Perform an operation
3. Check that the resulting data is equal to some expected value

This works well for writing a small or medium number of tests targeted at
particularly interesting cases. Checking specific output values, however, isn't
the only or necessarily the best way to test and gain confidence in an
implementation. In fact, sometimes it won't work at all.

Consider the `partition` helper method of quick sort as an interface (here
we'll restrict it to just partitioning arrays of `String`s):

```
interface Partitioner {
  // Change strs between start (inclusive) and end (exclusive), such that
  // all values at indices lower than a pivot index are smaller than or equal
  // to the value at the pivot, and all values at indices higher than the pivot
  // are larger than or equal to the value at the pivot

  int partition(String[] strs, int start, int end);
}
```

In lecture and discussion, we noted that there are many ways to implement
`partition`, in particular the choice of the _pivot index_ is important. Not
only could we choose different pivots, but one choice is to have a _random_
choice of pivot!  Let's imagine writing a test for a `Partitioner`:

```
class PartitionerFromLecture implements Partitioner {
  public int partition(String[] strs, int low, int high) {
    int pivotStartIndex = Random.nextInt(high - low);
    ... implementation from lecture ...
  }
}


@Test
public void testPartitionerFromLecture() {
  Partitioner p = new PartitionerFromLecture();
  String[] input = {"z", "b", "a", "f"};
  int pivot = p.partition(input, 0, 4);

  assertArrayEquals(???, input); // What to expect?
  assertEquals(???, pivot);
}
```

For two items, there are some clever solutions. You can use [special
matchers](https://stackoverflow.com/a/19064484/2718315),
for instance.

Instead of writing out all the tests by hand, we should step back from the
problem. We really care that the array is _correctly partitioned_ – there
shouldn't be elements larger than the pivot value at earlier indices, or
elements smaller than the pivot value at later indices. There are other
properties, too, like all the elements that were in the input list should
appear the same number of times in the output list – if `partition` duplicates
or loses elements, it isn't doing its job!

So, instead of writing single tests, we should write methods that, given a
partition algorithm, check if it satisfies some desired _properties_ that
partitioning ought to. Properties sufficient to show a valid partitioning are:

- All the elements in the original array are present in the array _after_ we
  call partition
- No values at indices other than those from `low` (inclusive) to `high`
  (exclusive) changed their values
- The elements from `low` to `high` are correctly partitioned:
  - `partition` returns some _pivot index_ between `low` (inclusive) and `high`
    (exclusive)
  - At all indices from `low` up to the pivot index the string is smaller
    than or equal to (according to `compareTo`) the value at the pivot index
  - At all indices from the pivot index up to `high - 1`, the string is larger
    than or equal to (according to `compareTo`) the value at the pivot index

### Your Task

You will turn the properties above into code that checks if a given result from
partition is valid.  That means your program will decide, for any call to
`partition`, if it behaves as we'd expect. Further, we can extend this idea to
build a method that takes a `Partitioner` and returns `null` if we believe it
to be a good partitioner, and a `CounterExample` if we can find an input array
and low/high bounds that partition incorrectly:

```
CounterExample findCounterExample(Partitioner p);
```

`CounterExample` is defined to contain:

- The _input_ to a call to partition (an array, a low index, and a high index)
- The _result_ of a call to partition (an array and a returned pivot index)
- A `reason`, as a `String`, that you choose in order to describe why it is
  invalid. Some suggestions are below.

You will write a version of `CounterExample` and use it to check multiple
different partition implementations, some good and some bad. Note that, even
beyond the argument above about randomness, there are _multiple possible
correct implementations of partition_.

You must implement two methods to help you; you can implement other helpers as
you see fit. The two methods you must implement are:

```
/*
 * Return null if the pivot and after array reflect a correct partitioning of 
 * the before array between low and high.
 *
 * Return a non-null String (your choice) describing why it isn't a valid
 * partition if it is not a valid result. You might choose Strings like these,
 * though there may be more you want to report:
 *
 * - "after array doesn't have same elements as before"
 * - "Item before pivot too large"
 * - "Item after pivot too small"
 */
String isValidPartitionResult(String[] before, int low, int high, int pivot, String[] after)
```

```
/*
 * Generate a list of items of size n
 */
String[] generateInput(int n);
```

This method should create a list of items to use as input to purported
partition algorithms. It's up to you how it generates the items; it should
produce an array of length `n`, however.

### An Overall Strategy

Here's one way you might approach this problem:

- First, implement and test `isValidPartitionResult`. Think of several
  interesting individual cases (specific arrays and low/high bounds) you can
  imagine in a first pass, and test it on those cases.  Note that to test
  `isValidPartitionResult`, you will be creating pairs of arrays of strings for
  input and expected output (at first, by hand), and checking _both_ for
  success and for failure: you should have some tests where the `after`
  parameter and `pivot` describe an incorrect partitioning, and some correct.
- Implement `generateInput` in a simple way – make `n` Strings of random single
  characters. Test that the method returns the right number of elements without
  any errors.
- Implement a (really) incorrect version of `Partitioner`, that makes no
  changes at all to the underlying array in its `partition` method. Implement
  a _good_ version of `Partitioner` as well (you can take the one from
  class/discussion), adapted to work as a `Partitioner`.
- Try putting together a first version of `findCounterExample`. It could create
  a single list using `generateInput`, partition it with the given partitioner,
  check if it was sorted correctly using `isValidPartitionResult`, and return
  `null` if it partitioned correctly or a `CounterExampel` if it didn't. Note:
  you will need to _save_ the original array, since sorters can and will make
  changes to them! You can use `Arrays.copyOf` to make a copy of an array:
  
  ```
  String[] input1 = {"a", "b", "c", "a"};
  String[] original1 = Arrays.copyOf(input1, input1.length);
  ```
    
  With this flow, you can test that `findCounterExample` returns `null` when
  passed the good partitioner, and a `CounterExample` when given the bad
  partitioner. The testing methods `assertNull` and `assertNotNull` can be
  helpful here.

You can write these tests in `TestPartitionOracle.java` (yes, the tester has
its own tests!). This will get you through the beginning of the problem, and
familiar with all the major interfaces. With this in hand, you can proceed with
more refined tests. Here are some ideas:

- Make a copy of the good `Partitioner` you wrote, and change it in a subtle
  way, maybe change a < to a <= in comparison or vice versa. Is it still a good
  partitioner? Can your `findCounterExample` check that?
- Make a copy of the good `Partitioner` you wrote and change it in an obviously
  breaking way, maybe by setting an element to the wrong value. Does
  `findCounterExample` correctly return some `CounterExample` for this
  implementation?
- Change `findCounterExample` to call `generateInput` many times, and check that
  _all_ the generated lists sort correctly, returning the first failure as a
  `CounterExample` if it didn't.
- Feel free to add some interesting hand-written cases to `findCounterExample`
  where you use interesting input lists that you construct by hand. You can
  combine whether they sort correctly or not (e.g. partition them and then check
  `isValidPartitionResult`).
- Use the partition implementations that you find on the Web (below) and check
  if they are good or bad.
- The `java.util.Random` class has useful tools for generating random numbers
  and strings.  You can create a random number generator and use it to get
  random integers from 0 to a bound, which you can combine with ASCII codes to
  get readable random strings:

  ```
  Random r = new Random();
  int asciiForACapLetter = r.nextInt(26) + 65;  // Generates a random letter from A - Z
  String s = Character.toString((char)(asciiForACapLetter));
  ```
- You may find it useful to copy the arrays into lists so you can remove
  elements and use other list operations in your oracle. This is a useful
  one-line way to copy an array into an ArrayList:

  ```
  List<String> afterAsList = new ArrayList<>(Arrays.asList(after));
  ```

Overall, your goal is to make it so `findCounterExample` will return `null` for
any reasonable good partition implementation, and find a `CounterExample` for
any bad partition implementation with extremely high probability. We will
provide you with a bunch of them to test against while the assignment is out,
and we may test on more than we provide you in the initial autograder.

We won't test on truly crazy situations, like a partitioner that only fails
when passed lists of 322 elements, or when a one of the strings in the array is
`"Henry"`. The bad implementations will involve things logically related to
sorting and manipulating lists, like boundary cases, duplicates, ordering,
length, base cases, and comparisons, as a few examples.

**Assume** that there are no `null` items in the arrays, that sorts won't put
`null` items in the arrays, and that the variables holding lists of items won't
contain `null`. There are plenty of interesting behavior to consider without
it!

**Don't** have your implementation of `findCounterExample` take more than a few
seconds per sorting implementation. You don't need to create million element
lists to find the issues, and it will just slow down grading. You should focus
on generating (many, maybe hundreds or thousands of) small interesting lists
rather than a few big ones, which should process very quickly.

## Part II: Copying Code from the Internet

There's a lot of code out there in the world. Much of it is available, with
permissive licensing, for free on the Web. When you're learning, it's often
useful to write implementations yourself to gain experience. However, there are
also skills related to finding and re-using code, rather than writing your own
from scratch. These skills are useful to develop, and come with their own set
of best practices.

When you re-use or repurpose code, there are two main concerns:

- Are you allowed, legally and ethically? Your course, company, or institution
  may have its own rules, and there are laws about how you can re-use or modify
  code depending on its software license. There are also simple intellectual
  honesty issues around giving credit to the right sources. It may be the case
  that you shouldn't even be _looking_ at other code that solves your problem.
  This is usually the case in programming courses, for example.
- More practically, does the code actually do what you want? If it's a method,
  are the inputs and outputs the types your program will expect? Does it match
  your performance expectations in terms of its runtime? If you need to change
  it to adapt to your application, will that invalidate any assumptions of the
  original version?

For this assignment, you must go _find three `partition` implementations in
Java on the Web_. You should document the source you got it from clearly, and
adapt it to fit the `Partitioner` interface that partitions `String`s For each
implementation you find, you write in a header comment with the method:

- Where it came from as a URL, and list the author (usernames or emails count!)
  if you can identify the author
- A URL for the _license_ or other rules posted for the re-use of the code. In
  code repositories like those on Github, this will usually be in a file called
  `LICENSE` or `LICENSE.txt` in the root of the repository. Here's one for
  [openjdk](https://github.com/dmlloyd/openjdk/blob/jdk/jdk/LICENSE), a free
  and open source Java implementation, for example. Don't use code for which
  you can't find the rules of re-use!
- Describe what changes you made to adapt it to this problem
- Indicate if it was buggy or not (by using handwritten tests, or potentially
  by using your tester, if you have it ready) and why
- Describe the worst case of its runtime behavior using a tight big-O bound

Put these implementations in the provided files `WebPartitioner1-3.java`.

A search engine is your friend here. Searching “Java partition implementation”
or “Java quicksort implementation” is a fine way to start. Searching “java
partition implementation site:github.com” gives a bunch of promising options,
as well.  Have fun searching, there's lots of cool stuff out there!

**NOTE: This part of the assignment comes with a deliberate, narrow exception
to the Academic Integrity policy for the course. You shouldn't, in any other
assignment (or other parts of this assignment) go hunting for code on the Web
that solves the assignment for you.  You certainly shouldn't do it in other
classes or at your job unless you know it's acceptable to do so – you should
always know and consult the policies relevant to your current context. We (the
instructors) know how to search for code on the Web. So do intellectual
property attorneys, to extend the analogy to the professional context.**

## Asking for Help

This is a **closed** PA, and the coding task for this assignment is to
implement and test `findCounterExample` along with the implementation
adaptations from online, and you must complete that on your own. However there
are lots of ways to get help! In particular, we talked about quicksort and
partition in lecture, so feel free to ask TAs for help understanding those
algorithms. You should also feel free to ask and consult us if you need help
determining if you can use a particular implementation from the Web by
interpreting its license.

Some good questions to ask a tutor or TA if you don't quite understand:

- What's the difference between quicksort and partition?
- Why does partition take a low and a high index along with the array?
- What is a pivot?
- What are different ways to choose a pivot?

## File Summary

Starter code is here: [https://www.dropbox.com/s/1ehbahbjas0onq7/pa5-starter-master.zip?dl=0](https://www.dropbox.com/s/1ehbahbjas0onq7/pa5-starter-master.zip?dl=0)

- `PartitionOracle.java`:
  - `findCounterExample` (you implement this)
  - `generateInput` (you implement this)
  - `isValidPartitionResult` (you implement this)
- `TestPartitionOracle.java`: You will write your tests of the methods above here
- `CounterExample.java` (do not edit this)
- `Partitioner.java` (do not edit this): Defines the signature of the
  `partition` method implemented by all sorters. You will implement this
  interface several times to test `findCounterExample`.
- `WebPartitioner1-3.java`: For your implementations found on the Web that you
  will adapt to implement `Partitioner`.

There is no dedicated README for this PA – the header comments in the
WebPartitioners serve as the written part of your grade for this PA.

## Style

The style guidelines are the same as PA3, with the following additions:

- Lines **must not** be indented more than 6 times. If you have a need to
  indent more than 6 levels, build a helper method or otherwise reorganize your
  code.
- If you write a helper method with a body longer than 2 statements, **you
  must** add a header comment (a comment above the method) that summarizes what
  it does in English.

The remark about redundant inline commenting from PA3 is still a
recommendation, not something we will enforce.


## Submission

You will only hand in a zip archive of the pa5-starter directory containing all
the files above. You can use the provided `prepare-submission.sh` script for
this.

Grading breakdown (40 total points):

- 10 points: `isValidPartitionResult`, graded automatically
- 5 points: `generateInput`, graded automatically
- 11 points: `findCounterExample`, graded by how it performs on good and bad
  partitions that we provide, graded automatically
- 5 points: Test and code readability and style [manually graded]
- 9 points: (3 points each) for the sort implementations you find online and describe [manually graded]


