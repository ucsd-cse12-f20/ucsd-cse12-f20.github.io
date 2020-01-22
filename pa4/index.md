---
layout: pa
title: "PA4: Runtime, Measured and Modeled (open)"
doodle: "../doodle.png"
---

This assignment will give you experience working with big-Ο/θ/Ω
representations, practice matching them to implementations, and perform
measurements of the runtime of different methods.

_This assignment is inspired by a combination of a lab in Swarthmore College's
CS35, and by a similar assignment by Marina Langlois in CSE12 at UCSD_

Read the whole writeup before starting – there are several different pieces of
the assignment you will need to hand in. In particular, you will submit:

- Some code for measuring to the `pa4-code` assignment (starter code: [https://github.com/ucsd-cse12-w19/pa4-starter](https://github.com/ucsd-cse12-w19/pa4-starter))
- A PDF to the `pa4-written` assignment

Make sure you know how to generate a PDF (Google Docs is a good option) and ask
for help if you don't, well in advance of the deadline!

## Big-O Justification

Indicate whether the following assertions are true or false, and give a
justification:

- _n + 5n<sup>2</sup> + 8n<sup>4</sup>_ is _O(n)_
- _n! + n<sup>2</sup>_ is _O(n * log n)_
- _log n + n * log n + log(log n)_ is _Ω(n)_
- _n<sup>2</sup> + n/4 + 6_ is _Θ(n<sup>3</sup>)_
- _1/(n<sup>50</sup>) + log32_ is _Θ(1)_
- _1/(n<sup>50</sup>) + log32_ is _O(1)_

If you are justifying the positive direction, give choices of `n0` and `C`. For
big-Θ, make sure to justify both big-O and big-Ω, or big-O in both directions.

[These slides](https://github.com/ucsd-cse12-w19/ucsd-cse12-w19.github.io/tree/master/lectures/lecture10-O-Theta-Omega) give a definition of big-Θ and big-Ω, which were not
covered in detail in class. The strategies we showed in class for big-O can
also be used for big-Ω, and big-Θ simply combines the two.

If you are justifying the negative direction, indicate which term(s) can't work
because one is guaranteed to grow faster or slower than the other.

As a quick guide, here is an ordering of functions from slowest-growing
(indeed, the first two _shrink_ as n increases) to fastest-growing that you
might find helpful:

- f(n) = 1/(n<sup>2</sup>)
- f(n) = 1/n
- f(n) = 1
- f(n) = log(n)
- f(n) = sqrt(n)
- f(n) = n
- f(n) = n<sup>2</sup>
- f(n) = n<sup>3</sup>
- f(n) = n<sup>4</sup>
- ... and so on for constant polynomials ...
- f(n) = 2<sup>n</sup>
- f(n) = n!
- f(n) = n<sup>n</sup>

Provide this written up on the _first_ page of `pa4.pdf`.

## List Analysis

Consider the two files [ArrayStringList.java](./ArrayStringList.java) and
[LinkedStringList.java](./LinkedStringList.java), which are the starter files
from PA2.
Answer the following questions, and justify them with one or two sentences
each:

- Give a tight big-O bound for the _best case_ running time of `prepend` in
  ArrayStringList
- Give a tight big-O bound for the _best case_ running time of `prepend` in
  LinkedStringList
- Give a tight big-O bound for the _worst case_ running time of `prepend` in
  ArrayStringList
- Give a tight big-O bound for the _worst case_ running time of `prepend` in
  LinkedStringList
- Give a tight big-O bound for the _best case_ running time of `add` in
  ArrayStringList
- Give a tight big-O bound for the _best case_ running time of `add` in
  LinkedStringList
- Give a tight big-O bound for the _worst case_ running time of `add` in
  ArrayStringList
- Give a tight big-O bound for the _worst case_ running time of `add` in
  LinkedStringList

In all cases, give answers in terms of the _current size of the list_, and
assume that the list has some non-empty size _n_. That is, you shouldn't
consider the empty list as a best case; instead think about the best case based
on other factors like size, capacity, and nodes.

Notable points to consider:

- Creating an array takes time proportional to the length of the array
- When considering the running time of a method, make sure to take into
  account any helpers methods it uses!

Example for `get` in the `LinkedStringList` class:

    The get method is O(1) in the best case, when the index is 0. In this case
    it will do constant work checking the index and immediately return the
    first element, never entering the while loop.

    The get method is O(n) in the worst case, because the index could be at
    the end of the list (for example, index n - 1). In this case, the while
    loop will run n times, spending constant time on each iteration, resulting
    in overall O(n) number of steps taken.

Provide this written up on the _second and third_ pages of `pa4.pdf`.

## Mystery Functions

We have provided you with a `.jar` file that contains implementations of the
following methods:

```
	public static void f1(int n) {
		int a = 0;
		for (int i = 0; i < n; i += 1) {
			a = i;
		}
	}
	public static void f2(int n) {
		int a = 0;
		for(int i = 0; i < n; i += 2) {
			a = i;
		}
	}
	public static void f3(int n) {
		int a = 0;
		for(int i = 0; i < n * n; i += 1) {
			a = i;
		}
	}
	public static void f4(int n) {
		int a = 0;
		for(int i = 0; i < n; i += 1) {
			for(int j = i; j < n; j += 1) {
				a = i + j;
			}
		}
	}
	public static void f5(int n) {
		int a = 0;
		for(int i = 0; i < n * n; i += 1) {
			for(int j = 0; j <= i / 2; j += 1) {
				a = i + j;
			}
		}
	}
	public static void f6(int n) {
		int k = 1, a = 0;
		for(int i = 0; i < n; i += 1) {
			for(int j = 0; j <= k * 2; j += 1) {
				a = i + j;
			}
			k = k * 2;
		}
	}
```

However, in that file, they are called `mysteryA-F`, and they are in a
different order, and we don't provide the source of that file. You have two
tasks: determining a big-Θ bound for each method labeled 1-6 analyzing
the source above, and determining which mystery method A-F corresponds to the
implementations above by measuring against provided (but hidden)
implementation.

### Identifying Bounds from Code

Determine a big-Θ bound for each function, and justify it with a few
sentences. Give only the most relevant term, so use, for example _Θ(n)_, not
_Θ(4n + 2)_ Provide this written up on the _fourth_ page of `pa4.pdf`.

### Measuring Implementations

You will write a program to:

- Measure the mystery methods
- Use your measurements to match the mystery methods to the sources above
- Generate several graphs to justify your work

You have a lot of freedom in how you do this; the deliverables you need to
produce are specified at the end of this section. There are a few methods that
we _require_ that you write in order to do this, and they will help guide you
through the measurement process.

#### The `measure` Method

You _must_ write the following two methods in the `Measure` class:

```
public static List<Measurement> measure(String[] toRun, int startN, int stopN)`
public static String measurementsToCSV(List<Measurement> measurements)
```

where `Measurement` is defined in `Measurement.java`.

- `measure` should work as follows:

  1. It assumes each string in `toRun` is one of the letters A-F.
  
  2. For each of the implementations to run, it runs the corresponding
  `mysteryX` method `stopN - startN` times, providing a value of `n` starting
  at `startN` and ending at `stopN` each time.

  3. For each of these runs, it _measures_ the time it takes to run. You can do
  this by using the method `System.nanoTime()` (see an example in [discussion
  code](https://github.com/ucsd-cse12-w19/ucsd-cse12-w19.github.io/blob/master/discussion/week4-discussion-friday-runtime-measurement/MeasurementDemo.java))
  
  4. For each of the measured runs, it creates a `Measurement` whose `valueOfN`
  field is the value that was used for the given run, whose `name` field is the
  single-letter string of the implementation that ran, and whose
  `nanosecondsToRun` field is a measurement, and adds it to a running list of
  measurements.

  5. The final result is the list of measurements.

**Example**:

This call:

```
		measure(new String[]{"A", "B"}, 40, 100);
```

Should produce a list that has 122 measurements, 61 of which will have `name`
equal to `"A"` and 61 of which will have `name` equal to `"B"`. Each of the 61
for each name will have a different `valueOfN` from 40 to 100, and each will
have a different number of nanoseconds (as was measured).

### The `measurementsToCSV` method

The `measurementsToCSV` method takes a list of measurements (for example, as
returned from `measure`) and generates a comma-separated-values `String` of the
measurements. It should have the following format, where the first row is a
literal header row and the other rows are example data. Note that this data is
completely made up, and may not match your measurements.

You might choose to put all of the measurements for a single letter together:

```
name,n,nanoseconds
A,40,1034
A,41,1039
A,42,2033
... many rows for A ...
A,100,432
B,40,1034
B,41,4038
... many rows for B ...
```

You might also choose to put all of the measurements for a single round of `n`
together:

```
name,n,nanoseconds
A,40,1034
B,40,1034
A,41,1039
B,41,4038
A,42,2033
B,42,4038
... many alternating rows of A, B ...
A,100,432
B,100,8038
```

Either layout is fine, do what makes sense to you, or what matches your
`measure` function best, etc.

### Strategies for Measuring

You can use the `measure` and `measurementsToCSV` methods to produce data about
how the functions behaved in terms of their runtime. You should fill in the
`main` method with whatever you find useful for using your measuring methods to
compare the mystery implementations. You have total choice in how you implement
this – you can add new helpers, print the CSV format out to a file, copy/paste
it into a spreadsheet, use a tool you like for plotting, etc. The goal is to
use measurements to identify the different implementations. Feel free to look
up documentation for writing Strings out to files and use it, or use
`System.out.println` and copy/paste the output, etc. It's probably pretty
expedient to copy the data into Excel or a Google Sheet.

There are a few high-level strategies to consider:

- If an implementation is very slow, it could take a really long time to
  measure it for large n. If you notice something is taking a long time, stop
  the program and try the same mystery methods on a smaller input range. Does
  the smaller range tell you anything useful?
- Some of the methods might have similar big-O bounds, but have different
  constants that can be measured in terms of absolute time.
- Some of the methods might take vastly different times to run on certain
  inputs, so plotting them next to one another will show one with a flat line
  at 0 and the other with some interesting curve. Make sure to check what the
  relative numbers are when inspecting the output.

You will use these measurements to figure out which mystery method matches the
implementations above, and generate three graphs to justify your answers.
 
### Avoiding Obscuring Optimizations

On many platforms and Java versions, simple methods like the above get
_optimized_ to run much faster than their theoretical number of steps might
suggest. Java is pretty smart – it can, while running, figure out how to make
them run quickly enough that empirical measurements become hard to make. If
you're seeing that even on values of n in the hundreds of thousands, you get
effectively constant behavior, you should try _disabling_ these optimizations
to get more useful measurements for distinguishing the implementations.

Instructions for doing this are in the _Turning Off Java Optimizations_ section
of this Google Doc (scroll to the end):

[https://docs.google.com/document/d/1vwckO76TrBT8B5E4xQ2-v2OXncLa6SQWuaQkNZaCPB0/edit](https://docs.google.com/document/d/1vwckO76TrBT8B5E4xQ2-v2OXncLa6SQWuaQkNZaCPB0/edit)

Note that this will make all the mystery methods run _a lot_ slower, so you may
want to _decrease_ the values of n you use after making this change to avoid
waiting a long time.


## Submission Instructions

There are four artifacts to submit for this PA:

By Thursday, 10:59pm
- The `pa4-code` assignment in Gradescope, where you will submit your
  final code for performing measurements, along with a `README` describing how
  you measured things. This `README` should just contain a few sentences
  describing how you ran your program to generate data and what decisions you
  made
- The `pa4-written` assignment in Gradescope, where you will submit a _single_
  PDF file called `pa4.pdf`.
  - The _first_ page should have your big-O justifications, which should take
    up one page (you don't have to write a page of text! But don't put any
    other answers on the first page)
  - The _second_ and _third_ pages should have your List analysis, which should
    take up pages 2 and 3 (you don't have to write two pages of text! But don't
    put any other answers on the second or third pages)
  - The _rest_ of the pages should have your matchings for the mystery
    functions, along with your graphs and justifications
    - The BigO bounds for each implementation f1-6.
    - A listing that matches each of mysteryA-F to an implementation f1-6 above 
    - Three graphs that justify a few choices above. These don't need to
      exhaustively describe all of your matchings, but they must be generated
      from real data that you measured using `measure`, and they must show an
      interesting relationship that helps justify the matching.

If you want a guide on how to get from the CSV data to a graph, look here:

[https://docs.google.com/document/d/1vwckO76TrBT8B5E4xQ2-v2OXncLa6SQWuaQkNZaCPB0/edit](https://docs.google.com/document/d/1vwckO76TrBT8B5E4xQ2-v2OXncLa6SQWuaQkNZaCPB0/edit)

## Grade Breakdown

Note that this assignment is **mostly manually graded**, so there's little
value in submitting after the deadline.

(70 total points)
- 16 points `measure` and `measurementsToCSV` [autograded]
- 2 points README describing how you measured [manually graded]
- 12 points initial big-O justifications [manually graded]
- 16 points list method analysis [manually graded]
- 24 points matching activity [manually graded]
  - 12 points for complexity bounds on f1-6
  - 6 points for a correct matching
  - 6 points for 3 relevant graphs

