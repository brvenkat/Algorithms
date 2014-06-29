package NonDataStructure;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to calculate the first 10 fibonacci numbers.
 * Prints the output on the screen/terminal using StringBuffer
 * @author vbalachandran
 *
 */
public class Fibonacci {
	private static final int N = 10;
	public static void main(String args[])
	{
		System.out.println("========Code to print the first 10 fibonacci numbers===============");
		try
		{
		    Fibonacci fb = new Fibonacci();
		    System.out.println("The first 10 fibonacci numbers are ");
		    List<Integer> fibList = fb.returnFibonacci(N);
		    for(Integer num: fibList)
		    	System.out.println(num);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/**
	 * Function that returns a List of the first n fibonacci numbers.
	 * It calls a helper function to accomplish this
	 * @param n
	 * @return
	 */
	public List<Integer> returnFibonacci(int n)
	{
        List<Integer> fibList = new ArrayList<Integer>();
    	boolean[] isComputedArray = new boolean[n+1];
		isComputedArray[1] = isComputedArray[2] =true;
        fibList.add(0);
        fibList.add(1);
        computeFibonacci(n, fibList,isComputedArray);
        return fibList;
	}
	
	/**
	 * Helper function that recursively computes the fibonacci 
	 * It uses a boolean array to see if we have already computed the fibonacci for a number
	 * @param n
	 * @return
	 */
	private int computeFibonacci(int n,List<Integer> fibList,boolean[] isComputedArray)
	{
       if(n == 1 || n==2)
          return n-1;
       
       int number = computeFibonacci(n-1,fibList,isComputedArray)+computeFibonacci(n-2,fibList,isComputedArray);
       
	   if(!isComputedArray[n])
	   {
           fibList.add(number);
           isComputedArray[n] = true;
	   }
       return number;
	}

}
