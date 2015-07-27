import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	  private int N;          // size of the Queue
	  private Node first;     // top of stack
	  private Node last;
	    // helper linked list class
	  private class Node {
	        private Item item;
	        private Node next;
	        private Node prev;
	  }
	  public Deque() {
		  first=last=null;
		  N=0;
	  }
	  public boolean isEmpty()   {
		  return (first == null || last == null || N ==0);
	  }
	  public int size()   {
		  return N;
	  }
	  public void addFirst(Item item) {
		  if(item == null)
			  throw new java.lang.NullPointerException();
		  if(isEmpty())
		  {
			  first = new Node();
			  first.item=item;
			  first.next = null;
			  first.prev = null;
			  last =first;
		  }
		  else 
		  {
			  Node oldFirst = first;
			  first = new Node();
			  first.item = item;
			  first.next = oldFirst;
			  oldFirst.prev = first;
			  first.prev = null;
		  }
		  ++N;
	  }
	  public void addLast(Item item) {
		  if(item == null)
			  throw new java.lang.NullPointerException();
		  if(isEmpty())
		  {
			  last = new Node();
			  last.item = item;
			  last.next = null;
			  last.prev = null;
			  first =last;
		  }
		  else 
		  {
			  Node oldLast = last;
			  last = new Node();
			  last.item = item;
			  last.next = null;
			  last.prev = oldLast;
			  oldLast.next = last;
		  }
		  ++N;
	  }
	  public Item removeFirst() {
		  if(isEmpty())
			  throw new java.util.NoSuchElementException();
		  Node n = first;
		  first = first.next;
		  if(!(first==null))
			  first.prev = null;
		  else
			  last = null;
		  --N;
		  return n.item;
	  }
	  public Item removeLast() {
		  if(isEmpty())
			  throw new java.util.NoSuchElementException();
		  Item lastItem = last.item;
		  last = last.prev;
		  if(!(last==null))
			  last.next = null;
		  else
			  first = null;
		  --N;
		  return lastItem;  
	  }
	  public Iterator<Item> iterator()  {
		return new DequeIterator();  
	  }
	  
	  private class DequeIterator implements Iterator<Item> {
	        private Node current = first;
	        public boolean hasNext()  { return current != null;                     }
	        public void remove()      { throw new UnsupportedOperationException();  }

	        public Item next() {
	            if (!hasNext()) throw new NoSuchElementException();
	            Item item = current.item;
	            current = current.next; 
	            return item;
	        }
	  }
	  
	  public static void main(String args[])
	  {
		  Deque<Integer> deque = new Deque<Integer>();
          deque.addFirst(1);
          deque.addLast(2);
          deque.addLast(3);
          deque.removeLast();   
          deque.removeLast();   
          deque.removeLast();    
	         System.out.println(deque.size());
	  }
	  
	}
