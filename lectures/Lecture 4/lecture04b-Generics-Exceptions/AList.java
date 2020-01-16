public class AList<E> implements List<E> {

  E[] elements;
  int size;

  @SuppressWarnings("unchecked")
  public AList() {
	//this.elements = new String[2]; //convert to generic
	//this.elements = new E[2];      //disallowed by Java
	  
    this.elements = (E[])(new Object[2]);
    this.size = 0;
  }

  public void add(E s) {	//Change String to E 
    expandCapacity();
    this.elements[this.size] = s;
    this.size += 1;
  }

  public E get(int index) {	//change String to E for return type
	// TODO: Check for out-of-bound
    // throw IndexOutOfBoundsException	
    return this.elements[index];
  }

  public int size() {
    return this.size;
  }

  @SuppressWarnings("unchecked")
  private void expandCapacity() {
    int currentCapacity = this.elements.length;
    if(this.size < currentCapacity) { return; }

    // How to construct new array here?

    //String[] expanded = new String[this.size * 2];
    
    //a - E[] expanded = new E[this.size * 2];
    //b - E[] expanded = (E[]) (new Object[this.size * 2]);
    //c - it's too early and i'm still asleep
    
    E[] expanded = (E[]) (new Object[this.size * 2]);

    for(int i = 0; i < this.size; i += 1) {
      expanded[i] = this.elements[i];
    }
    this.elements = expanded;
  }
}
