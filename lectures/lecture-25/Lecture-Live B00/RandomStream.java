import java.util.*;

public class RandomStream implements Iterable<Integer> {

	//inner class
	class RandomIterator implements Iterator<Integer> {
		int current = 0;
		
		public Integer next() {
			int value = random.nextInt(bound - start);
			this.current += 1;
			return value + start;
		}
		
		public boolean hasNext() {
			return current < size;
		}
	}

	//outer class
	Random random;
	int size = 0;
	int bound = 0;
	int start = 0;
	
	public RandomStream(int size, int start, int bound) { 
		this.random = new Random();
		this.size = size;
		this.bound = bound;
		this.start = start;
	}

	public Iterator<Integer> iterator() {
		return new RandomIterator();
	}
	
	public static void main(String[] args) {
		RandomStream r = new RandomStream(12, 10, 100);
		
		for (Integer i : r) {
			System.out.print(i + " ");
		}
		System.out.println();
		
		for (Integer i : r) {
			System.out.print(i + " ");
		}
		System.out.println();
				
		//Random random = new Random();
		//for (int i = 0; i < 10; i++) {
		//	System.out.print(random.nextInt(100) + " ");
		//}
		//System.out.println();
	}
}
