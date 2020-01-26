---
layout: pa
title: "PA3: Worklists are A MAZE ING (Open)"
doodle: "../doodle.png"
---

**This assignment is <a href="../#programming">open to collaboration</a>.**

This assignment will teach you how to use stacks and queues as worklists, how
to implement an important search algorithm, and how the worklist choice affects
the algorithm.

_This assignment draws ideas from an assignment by Prof Langlois and Alvarado,
which in turn drew from a CSCI 151 lab assignment from Oberlin college._

The assignment is due Tuesday, January 28 at 11pm.

You can get the starter code here:

<https://www.dropbox.com/s/9ufpd60xrln3ak0/pa3-starter-master.zip?dl=0?>

## The Structure of a Maze Solver

There are a few components to the maze solver:

- The data used to represent the maze
- A choice of worklist to use for keeping track of the spaces that still need
  visiting
- An algorithm that uses the worklist to traverse the maze and find a solution

You will implement _two_ versions of the worklist, and _one_ algorithm that
will be parameterized to work with either type. Then you can put them together
to see the different versions work, and compare them.

## The Worklists

You will implement the `SearchWorklist` interface _twice_. Once you will
implement it with stack semantics, so `add` will “push” and `remove` will
“pop”, and then you will implement it with queue semantics, so `add` will
“enqueue” and `remove` will “dequeue.” In both cases, the `isEmpty` method
should return `true` when the worklist has no items in it. These are the _only_
three methods that should be implemented on these classes, and you shouldn't
change any interfaces.

You are free to use any built-in Java collections to implement these using the
adapter pattern (`LinkedList`, `Stack`, etc), as long as they have the
appropriate behavior. This may mean that your implementation is no more than a
dozen lines of code! There is one constraint we'd
like you to respect – make sure the constructors take no arguments, and
initialize the worklist to be empty.

We have provided some tests in `TestSolvers.java` that make it clear what the
behavior of the two worklist implementations should be.

## The `Maze` and `Square` Classes

There are several classes provided for you that both represent the maze and
help create it.

### `Square`

A `Square` represents a single square in the maze. It has the
following fields:

- `row` and `col`, which represent its coordinates
- `isWall`, which is true if the square represents a wall, false if it is an
  empty space
- `previous`, which you will use in the search algorithm to keep track of the
  path from the finish back to the start
- `visited`, which is initially false, and you will use in the search algorithm
  to keep track of squares that have been searched already and shouldn't be
  re-searched

You should read the methods on the `Square` class, as you will use them to
manipulate and access these fields during the search algorithm.

### `Maze`

The `Maze` class represents a rectangular maze with obstacles, a start, and a
finish. Since it just represents data, and the fields don't change via any
methods on the class, we make them all `final` and
`public`, which makes access easier. So to access the `cols` field of a `Maze`
instance with a reference stored in a variable `m`, just write `m.cols`.

The fields are:

- `rows` and `cols`, which represent the number of rows and columns in the maze
- `contents`, which contains a reference to an array of arrays of `Square`s, or
  `Square[][]`. This represents the entire maze, and the inner arrays represent
  the _rows_. This means:
  - The upper left corner of the maze is at `contents[0][0]`
  - The bottom left corner is at `contents[this.rows - 1][0]`
  - The bottom right corner is at `contents[this.rows - 1][this.cols - 1]`
  - The top right corner is at `contents[0][this.cols - 1]`
- `start` and `finish`, which represent the start square and end square for
  searching. They contain references to the corresponding `Square`s that are in
  the `contents` array.

The `Maze` class has a useful constructor just for testing, which we describe
in the testing section below.

## The Search Algorithm

The search algorithm we will use was presented in class, and is rewritten here:

**NOTE: This changed from the initial version, and the `mark the neighbor as
visited` step was moved into the inner loop**

```
initialize wl to be a new empty worklist (stack _or_ queue)
add the start square to wl
mark the start as visited
while wl is not empty:
  let current = remove the first element from wl (pop or dequeue)
  if current is the finish square
    return current
  else
    for each neighbor of current that isn't a wall and isn't visited
      mark the neighbor as visited
      set the previous of the neighbor to current
      add the neighbor to the worklist (push or enqueue)

if the loop ended, return null (no path found)
```

You will implement this algorithm, in Java, in the `solve` method of
`MazeSolver`. The parameters of `MazeSolver` are a `Maze` instance and a
(empty) worklist to use. To test the maze, you can pass in different
implementations of the worklist, and sample mazes.

Note that, for testing, returning `null` is how your implementation indicates
that there is no possible path from the source to the target.

There is one constraint on your implementation: When checking neighbors, you
_must_ add them to the worklist in the order East, South, West, North. So you
should first add (if it is not a wall or out of bounds) the `Square` one column
to the right, then the `Square` one row below (one _higher_ index, because the
top row is row 0), then one column to the left, then one row above (one _lower_
index). Our reference implementation uses this order and you should as well.
Note that this is the order in which `add` should be called, independent of the
worklist implementation.

One place where our implementation got surprisingly complicated, and where we
introduced a helper method, is in checking for available neighbors. It might be
useful to introduce a method that checks if an offset from a particular
coordinate is an empty square; this method might have a signature like

```
// Return true if the location of s, offset by rowOffset and colOffset, is in
// bounds and not a wall, false otherwise
boolean availableNeighbor(Square[][] contents, Square s, int rowOffset, int colOffset)
```

You're free to not write this method, but doing it first could give you some
useful practice, and come in handy later. You also might find variations on it
helpful, that return a `Square` if it is available, or that take a
`SearchWorklist` and add the element if it's available, etc.

## Testing

You should test your solver and the worklist implementations. Here's some
advice and help on doing it.

First, there is a constructor for `Maze` that accepts a `String[]` as an
argument. There is an example—the one we saw in class—provided for you. The
input uses a plain text format where:

- `#` indicates a wall
- `_` indicates an empty space
- `F` indicates the finish square
- `S` indicates the start square

For example, the maze from class would be written:

```
#_#_
____
_##S
F___
```

See the example provided test for how to express this as a use of the `Maze`
constructor.

On a successful run of a solvable maze, your `solve` method will have set
previous pointers from `finish` back to `start`. We wrote a method called
`showSolution` that will produce a similar array as a result, but with a `*`
for each square that was part of the path from start to finish. You can
construct these arrays (again, see the example for how), and use a helper we
provided to test them. For this example, the solution with a stack, and the add
order specified above, is:

```
#_#_
****
*##S
F___
```

With a `QueueWorklist`, the answer should be

```
#_#_
____
_##S
F***
```

Note that these tests fail in the starter code, because `solve` is
unimplemented! It will be your job to make them pass, and to thoroughly test
the rest of your implementation.


You can use the `assertArrayEquals()` method to check if your Maze solution
matches the expected solution. If you want a more detailed solution that tells
you exactly which parts of your maze are incorrect, simply run the
`formatMaze()` helper function to your actual and expected Mazes, and
`assertEquals()` them.

Here is what the JUnit output looks like on a failed solution:

<img src="./junit%20output.png">

Note that this is assuming a StackWorklist was used. The JUnit output will show you what segments of your mazes were different (in this case, rows 1-3).



## README

You must write a `README` file that contains answers to the following
questions:

- In your implementation, could the `setPrevious()` method ever be called twice
  on the same square during a single run of `solve()`? Give an example of when
  it would happen, or argue why it can't.

- Argue for or against this statement: “Solving a solvable maze with a queue
  worklist will always produce a path with length less than or equal to solving
  the maze with the stack worklist.” Either provide a counterexample, or write
  a sentence or two about why this must be true.

- Argue for or against this statement: “Solving a solvable maze with a queue
  worklist will always visit equal or fewer squares than solving the maze with
  a stack worklist.” Either provide a counterexample, or write a sentence or
  two about why this must be true.

## Style

On this PA, we will give deductions for violating the following style
guidelines:

- Lines longer than 100 characters
- Inconsistent indentation
- Test method names that don't have meaning related to the test
- Helper method names that aren't meaningful

We are also introducing some new guidelines. These new guidelines won't be
graded for credit on PA3, but may be on future PAs, and you may get feedback on
them:

- If you write a helper method with a body longer than 2 statements, we
  recommend adding a header comment (a comment above the method) that
  summarizes what it does in English.
- Avoid redundant in-line commenting

  Some examples of redundant comments are:

      // Check if n is null and throw an exception if it is:
      if(n == null) { throw new NullPointerException(); }


      // Iterate from 1 to twice the array's length
      for(int i = 0; i < array.length * 2; i += 1) {
        ...

  Write comments only when they describe an assumption, summarize, or bring up
  an interesting point that isn't directly described by the code. Focus on
  making the code understandable on its own.

## Handin Checklist

- Implementations of StackWorklist and QueueWorklist (3 methods each)
- Implementation of solve(), including any needed helpers
- Tests for StackWorklist, QueueWorklist, and solve()
- Good style for all of the above
- Answers to README questions

## Grading

A grader will be made available by Friday, Jan 24. The rough grade breakdown
is:

- 6 points README
- 10 points testing the solver
- 4 points style
- 20 points implementation correctness
  - 3 points each for stack and queue worklist
  - 14 points for working solve() method

(40 total points)

## Extension

This is not for credit, but you may enjoy trying it! Feel free to discuss on
Piazza or with each other.

Provide an implementation of `SearchWorklist` that, in the `remove()` method,
picks the Square to remove in the following way:

- Let the _path distance so far_ of each square be the number of nodes on the
path from the square back to the start.

- Let the _best possible ending_ for each square be the _Manhattan Distance_
between that square and the exit square.

- Let the _Manhattan Distance_ between two squares be:

      |row1 - row2| + |col1 - col2|

- Choose the square in the worklist with the _smallest sum_ of its best possible
ending and path distance so far to remove in each call to remove().

Test out your implementation. In what ways is it better and/or worse than the
stack and queue worklists above? Do you need to change the worklist algorithm
at all in order to use it? What information did you need to provide in the
constructor in order to implement remove in this way?

If you implement this, please _don't_ include tests in `TestSolvers.java` that
use this new worklist, as it may not work with the autograder. Feel free to
include them in a separate test file.


