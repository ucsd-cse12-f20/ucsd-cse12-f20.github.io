import java.util.ArrayList;

public interface Queue<E> {
  void enqueue(E element);
  E dequeue();
  int size();
}

//Adapter pattern
class ALQueue<E> implements Queue<E> {

	//int size = 0;
	
	//Idea 1:
	//ArrayList<E> contents = new ArrayList<E>();
	
	//Idea 2:
	ArrayList<E> contents;
	public ALQueue() {		//no <E> at the end of constructor
		this.contents = new ArrayList<E>();
		//this.size = 0;
	}

	//A - Both work - correct
	//B - One works
	//C - none work
	
	public int size() {
		//return this.size;
		return this.contents.size();
	}

	public void enqueue(E element) 	{
		this.contents.add(this.contents.size(), element);
		//this.contents.add(element);
		//this.contents.add(0, element);
	}

	public E dequeue() {
		E element = this.contents.remove(0);
		return element;
	}

	public String toString() {
		return "front -> " + this.contents.toString() + " <- back";
	}
	
	//Queue<Integer> q = new .....
	//q.enqueue(4);
	//q.enqueue(10);
	//System.out.println(q);
	//	front -> 4, 10 <- back
	//int i = q.dequeue();
	//System.out.println(q);
	//	front -> 10 <- back
}
