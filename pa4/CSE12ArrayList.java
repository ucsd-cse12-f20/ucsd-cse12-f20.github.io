package cse12pa2student;

public class CSE12ArrayList<T> implements CSE12List<T> {
	
	private T[] contents;
	private int size;
	
	@SuppressWarnings(value = { "unchecked" })
	public CSE12ArrayList() {
		this.contents = (T[])(new Object[10]);
	}
	
	@SuppressWarnings(value = { "unchecked" })
	private void expandCapacity(int targetSize) {
		if(this.contents.length < targetSize) {
			T[] newContents = (T[])(new Object[this.contents.length * 2]);
			for(int i = 0; i < this.contents.length; i += 1) {
				newContents[i] = this.contents[i];
			}
			this.contents = newContents;
		}
	}
	
	@Override
	public void append(T e) {
		expandCapacity(this.size + 1);
		this.contents[this.size] = e;
		this.size += 1;
	}

	@Override
	public void prepend(T e) {
		expandCapacity(this.size + 1);
		for(int i = this.size - 1; i >= 0; i -= 1) {
			this.contents[i + 1] = this.contents[i];
		}
		this.contents[0] = e;
		this.size += 1;
	}

	@Override
	@SuppressWarnings(value = { "unchecked" })
	public void empty() {
		this.contents = (T[])(new Object[10]);
		this.size = 0;
	}

	@Override
	public Paginator<T> paginate(int perPage) {
		return new ALPaginator<T>(this.contents, this.size, perPage);
	}

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public T getAt(int index) {
		if(index >= this.size || index < 0) {
			throw new IndexOutOfBoundsException("Index " + index + " out of bounds.");
		}
		return this.contents[index];
	}

	@Override
	public void removeFirst(T e) {
    return;
	}

	@Override
	public int findFirst(T e) {
    return 0;
	}

}

