package NonDataStructure;

/**
 * Function to calculate the first 10 fibonacci numbers.
 * Prints the output on the screen/terminal using StringBuffer
 * @author vbalachandran
 *
 */
public class Fibonacci {
	private static final int N = 10;
	public static boolean[] printedArray = new boolean[N+1];
	private static StringBuffer sb = new StringBuffer("0,1");
	public static void main(String args[])
	{
		System.out.println("========Code to print the first 10 fibonacci numbers===============");
		try
		{
			printedArray[0] = printedArray[1] =true;
		    Fibonacci fb = new Fibonacci();
		    System.out.println("The first 10 fibonacci numbers are ");
		    fb.computeFibonacci(N);
		    System.out.println(sb.toString());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Helper function that recursively computes the fibonacci 
	 * It uses a boolean array to see if we have already computed the fibonacci for a number
	 * @param n
	 * @return
	 */
	public int computeFibonacci(int n)
	{
       if(n == 1 || n==2)
          return n-1;
       
       int number = computeFibonacci(n-1)+computeFibonacci(n-2);
       
	   if(!printedArray[n])
	   {
           sb.append(","+number);
           printedArray[n] = true;
	   }
	   
       return number;
	}

}
