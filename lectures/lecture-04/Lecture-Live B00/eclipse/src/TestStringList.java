import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestStringList {

  @Test
  public void testAdd() {
    StringList slist = new ArrayStringList();
    slist.add("banana");
    slist.add("apple");

    assertEquals("banana", slist.get(0));
    assertEquals("apple", slist.get(1));
  }
  
  @Test
  public void testInsert() {
    StringList slist = new ArrayStringList();
    slist.insert(0, "a");
    slist.insert(0, "b");

    assertEquals("b", slist.get(0));
    assertEquals("a", slist.get(1));
  }
  
  @Test
  public void testRemove() {
	  StringList slist = new ArrayStringList();
	  slist.add("banana");
	  slist.add("apple");
  
	  slist.remove(0);
	  
	  assertEquals("apple", slist.get(0));
	  assertEquals(null, slist.get(1));
	  assertEquals(1, slist.size());
  }
  

  @Test
  public void testAddThenSize() {
    StringList slist = new ArrayStringList();
    slist.add("banana");
    slist.add("apple");

    assertEquals(2, slist.size());
  }



  @Test
  public void testAddMany() {
    StringList slist = new ArrayStringList();
    slist.add("a");
    slist.add("b");
    slist.add("c");
    slist.add("d");
    slist.add("e");

    assertEquals("e", slist.get(4));
    assertEquals("d", slist.get(3));
    assertEquals("c", slist.get(2));
    assertEquals("b", slist.get(1));
    assertEquals("a", slist.get(0));

  }


}

