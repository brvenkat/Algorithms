import java.util.Iterator;

public class Subset {
		
	public static void main(String args[])
	{
		int k=0;
		String enteredInput = args[0];
		try 
		{
			k = Integer.parseInt(enteredInput);
		} 
		catch(Exception e)
		{
			System.out.println("Please enter a valid Integer");
			return;
		}
		RandomizedQueue<String> randomQueue = new RandomizedQueue<String>();
		while(!(StdIn.isEmpty()))
		{
			String enteredValue  = StdIn.readString();
			randomQueue.enqueue(enteredValue);
		}
		Iterator<String> randomIterator = randomQueue.iterator();
		for(int i=0;i<k;i++)
		{
			StdOut.println(randomIterator.next());
		}
	}

}
