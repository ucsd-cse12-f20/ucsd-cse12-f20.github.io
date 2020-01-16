class Node {
  String value;
  Node next;
  public Node(String value, Node next) {
    this.value = value;
    this.next = next;
  }
}

public class LinkedStringList implements StringList {

  Node front;

  // How will we construct it?
  public LinkedStringList() {
    this.front = new Node(null, null);
  }

  public void prepend(String s) {
    Node n = new Node(s, this.front.next);
    this.front.next = n;
  }

  public void add(String s) {
    Node n = this.front;
    while(n.next != null) {
      n = n.next;
    }
    n.next = new Node(s, null);
  }

  public String get(int index) {
    Node n = this.front.next;
    for(int i = 0; i < index; i += 1) {
      n = n.next;
    }
    return n.value;
  }

  public int size() {
    Node n = this.front.next;
    int count = 0;
    while(n != null) {
      n = n.next;
      count += 1;
    }
    return count;
  }




  // How will we implement the methods?














}

