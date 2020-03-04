---
layout: pa
title: "PA8 (open)"
doodle: "/doodle.png"
---

<h1>{{ page.title }}</h1>

This assignments has two parts, which you can complete in either order.

In the first part, you'll go back to a previous PA of your choice, measure
the performance of your implementation, and improve it.

In the second part, you'll get some practice with C, and implement and test a
min-heap in C, paying careful attention to memory allocation.

## Part I: Measuring and Improving a Past PA

You've learned a lot in this course. With the benefit of hindsight, we can
often go back and better understand programs we've written in the past. This
part of the PA gives you an opportunity to do that.

In this part of the PA, you will choose one of PA2, PA3, PA5, PA6, or PA7 (in
PA1 and PA4 there's less that's interesting to measure), perform some
measurements on its runtime performance, then make some changes and measure
those.

Your process should be as follows:

1. Choose one of the PAs above, and choose which part you are going to measure.
For example, you might choose to measure the `transform` method on
`ArrayLists`, the `solve` method for mazes, the `generateDatabase` method for
n-grams, the `isValidPartitionResult` method for partitioning, and so on. The
method you choose should have an interesting relationship (e.g. not constant)
with the size of the input. If you're not sure if a particular method/input
pair is appropriate, feel free to ask.

2. Measure that part's performance:
  - Design, choose, or create interesting input data. You should have at least
    10 _different-sized_ inputs to the program. Each should take at _least_ 50
    milliseconds to run – lower than this and the measurements will likely be
    too noisy to draw conclusions. (Note that this is a guideline to make sure
    you can get meaningful measurements, use your judgment.) This may mean you
    need to construct some large input lists/mazes/etc, depending on your
    choice of PA to measure.
  - Use the strategy from PA4 of capturing the time before and after running
    the part of the program you chose to measure all of the inputs. You choose
    how to construct the main class and where to run this. You **must** perform
    at least _five_ trials for each input, meaning you should do the same
    measurement 5 times. Record the timing from all of the trials for each
    input. You may find the CSV-generation skills from PA4 useful here.

3. Make a change designed to improve the runtime of that part of the PA, and
measure it.
  - **Be careful not to lose any work in this step.** You could save copies of
    the PA, or you could use Git to make commits and branches to track your
    progress.
  - The change could be small or large, for example swapping one data structure
    for another, re-organizing a sequence of loops, saving a repeated
    computation in a variable, swapping one sorting algorithm for another, or
    even an entirely different approach than you took the first time.
  - Write down an expectation about how much you think it will improve the
    runtime compared to the baseline from step 2.
  - Measure it as in step 2, using the _same_ inputs, 5 trials each.
  - Compare the results to the baseline and to your expectation. If there isn't
    a noticeable difference, why not? If there is, did it match your
    expectation? Did the correctness change in terms of the output produced?
    Discuss each of these questions briefly.

You will submit this in two places:

1. As a written report in PDF form to the `Programming Assignment 8 (PA8) - Part1 written` assignment.
  - Page 1 & 2 should include the choice of PA/part, description of input you
    chose or created, your measurement results as data, and a graph of the
    measurements. Use a scatter plot to show _all_ 5 trials for each
    measurement. (If you generate more data than easily fits on a page or two,
    provide a sample of 50 data points, and include a full CSV or spreadsheet
    in `Programming Assignment 8 (PA8) - Part1 measure code` below).
  - Page 3 and beyond should describe the change you made, your guess about
    what impact it would have on performance, the discussion described above,
    and the same data and charts requested above requested above for the
    measurements after making the change.
    
2. A zip of your code in the `Programming Assignment 8 (PA8) - Part1 measure code` code assignment. You should
make sure the code you used to measure, the original code for your PA, and the
updated version are included. We _may_ look at this to confirm/support the
descriptions you give above. However, the descriptions you give should be
complete enough for us to understand your original and updated implementation.

## Part II: Heaps in C

### Using C on the Lab Machines

For this PA, you should use the lab machines. If you want to set up C on your
own laptop, you're welcome to, but we probably won't be able to help, and we
recommend against it.

This document has instructions on finding your account and getting logged in,
and working remotely if you'd like:

[https://docs.google.com/document/d/1zxXlY36LOfpI4Z3zr70u3AImMirsUUBaomX9Vf7T0xg/edit?usp=sharing](https://docs.google.com/document/d/1zxXlY36LOfpI4Z3zr70u3AImMirsUUBaomX9Vf7T0xg/edit?usp=sharing)

Note that it also has some background information on running C from the command
line. We've provided the commands you need in a `Makefile` and detailed them
below, but the background in the document explains the underlying commands.

Also note that the PA is not set up with Eclipse, so directly importing the
code into Eclipse will not create a project. Instead, you can `git clone` in
Terminal to download the code, and then use any text editor to edit.

You can get the starter code here:

[https://www.dropbox.com/s/ne3kednj8hrxuim/pa8-starter-master.zip?dl=0](https://www.dropbox.com/s/ne3kednj8hrxuim/pa8-starter-master.zip?dl=0)


### Two Practice Exercises

First, you're going to do two warm-up exercises.

- `insert.c`: This file contains an implementation of inserting into an array
  that appears to work, but has some memory errors. You will use `valgrind` to
  detect and fix the memory errors. You can run this example with `make
  insert`, which will run the necessary valgrind command. You'll hand in the
  corresponding fixed C file.
- `match_struct.c`: In this file, you will write initialization code to
  construct data that matches a series of boolean checks in the `main` method.
  You'll hand in a C file that makes the `main` method print both `"You got
  ans1!"` and `"You got ans2!"` Run with `make match_struct`. You'll do
  this by editing _only_ `setupAns1` and `setupAns2`. Hint – draw a picture of
  what `main` is looking for!



### The Heap Interface In C

Unlike Java, C doesn't have language support for a feature called an
“interface.” That said, the concept of a collection of functions that implement
a particular feature independent of the underlying representation is still a
reasonable one to consider. For this heap implementation, we'll take a
collection of function headers as our interface:

```
Heap* makeHeap(int capacity);
void add(Heap* heap, int priority, char* value);
char* removeMin(Heap* heap);
char* peek(Heap* heap);
int size(Heap* heap);
void cleanupHeap(Heap* heap);
```

In addition, we've defined a few structs for you:

```
struct Entry {
  int key;
  char* value;
};
typedef struct Entry Entry;

struct Heap {
  int capacity;
  int size;
  Entry** elements;
};
typedef struct Heap Heap;
```

The `heap.h` file holds all these definitions, and is called a _header file_. C
programs are often organized with definitions in one file and declarations in
another. This is done (at a high level) to help C's compiler, which doesn't
have the same features Java's compiler has for traversing the filesystem to
find all the relevant source files.

In `heap.c`, you will write implementations of these functions. Your
implementation will be a **min heap** (the mapping with the lowest key will be
on top). Some notes on the required algorithms are in lectures from week 8:

https://github.com/ucsd-cse12-w19/ucsd-cse12-w19.github.io/tree/master/lectures/lecture20-Heap

Descriptions of each function are:

- `makeHeap`: Should return a pointer to a newly allocated `Heap` with the
  given `capacity`, a `size` of `0`, and an `elements` array allocated with the
  given capacity. The `elements` array should contain pointers (references) to
  `Entry` objects.
- `add`: Should add a new pair mapping the given `priority` as a key to the
  given value `value`. This should work by inserting `add` at the end of the
  storage array (`elements`) and bubbling up. Should run in `O(lg(size))` time,
  except when resizing. If the size is equal to the capacity, this should take
  `O(size)` time, and expand the capacity by allocating a new array of twice
  the current capacity and copying old elements over before performing the
  insertion.
- `removeMin`: Should return the value stored at the top of the heap (the one
  with the lowest priority). That element should then be removed from the heap,
  and the heap should be restored to the correct shape by moving the last
  element to the top and bubbling down. When performing bubble down, prefer
  swapping with the **left** child if both children have the same priority.
  If the heap is empty, return `NULL`. Should run in `O(lg(size))` time.
- `peek`: Should return the value stored at the top of the heap (the one with
  the lowest priority) and make no changes to the heap. If the heap is empty,
  should return `NULL`. Should run in constant time.
- `size`: Should return the number of elements in the heap (not the capacity),
  and run in constant time.
- `cleanupHeap`: Should use `free` to reclaim the memory used by the given Heap
  pointer, including its `elements` array.

Note that the reference implementation, in bubbleUp/Down, _does not_ perform
swaps between equal elements. If you write tests with many duplicates, you
might run into this behavior. We don't test in detail for either direction
here, so your implementation can go either way. However, if you write tests
that explicitly check for not bubbling past equal elements, they won't succeed
on our implementation.

### Testing

You can write tests in `test_heap.c`, where there are some examples given of
using arithmetic and checking numeric equality, and checking equality between
strings. You should write tests that create heaps, add and remove items, and
check that the operations work as expected. The two relevant functions for
writing assertions are `CuAssertIntEquals` and `CuAssertStrEquals`. In this
testing framework we need to use the equality method that matches the type we
are testing.

You can run the tests with

```
make test
```

### Checking Memory

You'll see output like this when the tests run:

```
[cs12w@ieng6-203]:pa8-implementation:642$ make test
gcc -g -Wall -o test_heap.run heap.c test_heap.c cutest/CuTest.c
valgrind --leak-check=full --undef-value-errors=no ./test_heap.run
==32451== Memcheck, a memory error detector
==32451== Copyright (C) 2002-2015, and GNU GPL'd, by Julian Seward et al.
==32451== Using Valgrind-3.12.0 and LibVEX; rerun with -h for copyright info
==32451== Command: ./test_heap.run
==32451==
.

OK (1 test)

==32451==
==32451== HEAP SUMMARY:
==32451==     in use at exit: 176 bytes in 2 blocks
==32451==   total heap usage: 8 allocs, 6 frees, 16,913 bytes allocated
==32451==
==32451== 176 (16 direct, 160 indirect) bytes in 1 blocks are definitely lost in loss record 2 of 2
==32451==    at 0x4C29BE3: malloc (vg_replace_malloc.c:299)
==32451==    by 0x400FCD: makeHeap (heap.c:101)
==32451==    by 0x401017: TestHeap (test_heap.c:7)
==32451==    by 0x40167E: CuTestRun (CuTest.c:144)
==32451==    by 0x401CE4: CuSuiteRun (CuTest.c:292)
==32451==    by 0x4010F1: RunAllTests (test_heap.c:29)
==32451==    by 0x401156: main (test_heap.c:40)
==32451==
==32451== LEAK SUMMARY:
==32451==    definitely lost: 16 bytes in 1 blocks
==32451==    indirectly lost: 160 bytes in 1 blocks
==32451==      possibly lost: 0 bytes in 0 blocks
==32451==    still reachable: 0 bytes in 0 blocks
==32451==         suppressed: 0 bytes in 0 blocks
==32451==
==32451== For counts of detected and suppressed errors, rerun with: -v
==32451== ERROR SUMMARY: 1 errors from 1 contexts (suppressed: 0 from 0)
```

The part that says "OK (1 test)" will reflect the number of tests you've
written, and is like the JUnit output for your tests.

The rest is the output of a tool called `valgrind`, which checks the memory
usage of programs. In this case, it's saying that there are 176 bytes of memory
left in use at the end of the program. You'll probably see something like this
early on, until you implement `cleanupHeap`. It means that memory that was
earlier `malloc`ed was never freed. You should make sure to free all memory
that is allocated in each test by using `cleanupHeap` on any heaps you create.
When there are no memory errors and no memory leaks, you'll see a message like this:

```
[cs12w@ieng6-203]:pa8-implementation:649$ make test
gcc -g -Wall -o test_heap.run heap.c test_heap.c cutest/CuTest.c
valgrind --leak-check=full --undef-value-errors=no ./test_heap.run
==5758== Memcheck, a memory error detector
==5758== Copyright (C) 2002-2015, and GNU GPL'd, by Julian Seward et al.
==5758== Using Valgrind-3.12.0 and LibVEX; rerun with -h for copyright info
==5758== Command: ./test_heap.run
==5758==
.

OK (1 test)

==5758==
==5758== HEAP SUMMARY:
==5758==     in use at exit: 0 bytes in 0 blocks
==5758==   total heap usage: 8 allocs, 8 frees, 16,913 bytes allocated
==5758==
==5758== All heap blocks were freed -- no leaks are possible
==5758==
==5758== For counts of detected and suppressed errors, rerun with: -v
==5758== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)
```

This is the kind of output you should see when things are in a happy state – no
memory errors, all heap blocks freed, and all tests passing!

It's important to point out that `valgrind` can catch even more than unfreed
memory. Consider this broken test:

```
void SillyTest(CuTest *tc) {
  Heap* h = NULL;
  CuAssertIntEquals(tc, 0, h->capacity);
}
```

If we run `make test` on this, we'll see this output:


```
[cs12w@ieng6-203]:pa8-implementation:664$ make test
gcc -g -Wall -o test_heap.run heap.c test_heap.c cutest/CuTest.c
valgrind --leak-check=full --undef-value-errors=no ./test_heap.run
==22849== Memcheck, a memory error detector
==22849== Copyright (C) 2002-2015, and GNU GPL'd, by Julian Seward et al.
==22849== Using Valgrind-3.12.0 and LibVEX; rerun with -h for copyright info
==22849== Command: ./test_heap.run
==22849==
==22849== Invalid read of size 4
==22849==    at 0x40101A: SillyTest (test_heap.c:8)
==22849==    by 0x4016EB: CuTestRun (CuTest.c:144)
==22849==    by 0x401D51: CuSuiteRun (CuTest.c:292)
==22849==    by 0x40115E: RunAllTests (test_heap.c:34)
==22849==    by 0x4011C3: main (test_heap.c:45)
==22849==  Address 0x0 is not stack'd, malloc'd or (recently) free'd
==22849==
==22849==
==22849== Process terminating with default action of signal 11 (SIGSEGV)
==22849==  Access not within mapped region at address 0x0
==22849==    at 0x40101A: SillyTest (test_heap.c:8)
==22849==    by 0x4016EB: CuTestRun (CuTest.c:144)
==22849==    by 0x401D51: CuSuiteRun (CuTest.c:292)
==22849==    by 0x40115E: RunAllTests (test_heap.c:34)
==22849==    by 0x4011C3: main (test_heap.c:45)
==22849==  If you believe this happened as a result of a stack
==22849==  overflow in your program's main thread (unlikely but
==22849==  possible), you can try to increase the size of the
==22849==  main thread stack using the --main-stacksize= flag.
==22849==  The main thread stack size used in this run was 8388608.
... more output down here ...
```

This message will show up in some valgrind output. This output is giving the
same kind of meaning as `NullPointerException` in Java. the `Invalid read of
size 4` pinpoints the line on which it happens, and means that we tried reading
an invalid pointer (at address `0x0`, which is address `0`, which is the same
as `NULL`).

You will submit this part to the `Programming Assignment 8 (PA8) - Part2 code` assignment on Gradescope.

## Grading

- Part 1:
  - 6 points – Baseline measurement [manually graded]
  - 6 points – Measuring a change [manually graded]
- Part 2:
  - 4 points – practice problems (`insert` and `match_struct`) [automatic]
  - 8 points – quality of testing of heap.c (graded by running against our implementations) [automatic]
  - 16 points – Implementation correctness of heap.c [automatic]

