public class ArrayStringList implements StringList {

  String[] elements;
  int size;

  // How will we construct it?
  public ArrayStringList() {
	  this.elements = new String[2];
	  this.size = 0;
	  //initial value
	  //a: 2
	  //b: 0 - correct
	  //c: something else
  }

  // How will it implement the methods?

  /* Add an element at the end of the list */
  public void add(String s)
  {
	  expandCapacity();
	  this.elements[this.size] = s;
	  this.size += 1;
  }

  /* Get the element at the given index */
  public String get(int index)
  {
	  //check for out-of-bounds
	  //throw IndexOutOfBoundsException
	  return this.elements[index];
  }

  /* Get the number of elements in the list */
  public int size()
  {
	  return this.size;
  }
  
  void expandCapacity()
  {
	// NOTE(): Changed currentSize to currentCapacity below
    // because it's a better name for the variable
    int currentCapacity = this.elements.length;
    if(this.size < currentCapacity) { return; }

    String[] expanded = new String[currentCapacity * 2];

    for(int i = 0; i < this.size; i += 1) {
      expanded[i] = this.elements[i];
    }
    this.elements = expanded;
  }
}
