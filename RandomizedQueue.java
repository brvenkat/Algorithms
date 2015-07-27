import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	   private Item[] a;         // array of items
	   private int N;  
	   
	   public RandomizedQueue()  
	   {
		   a = (Item[]) new Object[2];
		   N=0;
	   }
	   public boolean isEmpty()                
	   {
		   return N == 0;
	   }
	   public int size()        
	   {
		   return N;
	   }
	   public void enqueue(Item item)       
	   {
		  if(item == null)
			  throw new java.lang.NullPointerException();
		   if(N==a.length)
			   resize(2*N);
		   a[N++] = item;
	   }
	   
	   public Item dequeue()    
	   {
		  if(isEmpty())
			  throw new java.util.NoSuchElementException();
		   int randomInteger = StdRandom.uniform(N);
	       Item swapItem = a[randomInteger];
	       a[randomInteger] = a[N-1];
	       a[N-1] = null;                              
	       N--;
	       if (N > 0 && N == a.length/4) resize(a.length/2);
        		return swapItem;
	   }
	   
	   public Item sample()     
	   {
		  if(isEmpty())
			  throw new java.util.NoSuchElementException();
		  int randomInteger = StdRandom.uniform(N);
		  return a[randomInteger];
	   }
	   
       private void resize(int capacity) {
	        assert capacity >= N;
	        Item[] temp = (Item[]) new Object[capacity];
	        for (int i = 0; i < N; i++) {
	            temp[i] = a[i];
	        }
	        a = temp;
       }
	   
	   public Iterator<Item> iterator()    {
		   return new RandomArrayIterator();
	   }
	   
	   private class RandomArrayIterator implements Iterator<Item> {
		   
	        private int i;
	        private boolean visited[];
	        private int valuesPrinted;

	        public RandomArrayIterator() {
	        	if(N > 0)
	        	{
		        	valuesPrinted=0;
	        		i = StdRandom.uniform(N);
	        		visited = new boolean[N];
	        	}
	        	else
	        		i = -1;
	        }

	        public boolean hasNext() {
	        	if(valuesPrinted >= N)
	        		return false;
	            return i != -1 && valuesPrinted < N;
	        }

	        public void remove() {
	            throw new UnsupportedOperationException();
	        }

	        public Item next() {
	            if (!hasNext()) throw new NoSuchElementException();
	            i = StdRandom.uniform(N);
	            while(visited[i])
	            	 i = StdRandom.uniform(N);
	            visited[i] = true;
	            Item item =  a[i];
	            ++valuesPrinted;
	            return item;
	        }
		   
	   }
	   // return an independent iterator over items in random order
	   public static void main(String[] args) {
		   RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
		   for(int i=0;i<1000;i++)
			   rq.enqueue(i);
	   }
}
