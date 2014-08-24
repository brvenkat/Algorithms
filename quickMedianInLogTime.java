package Algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

import javax.print.attribute.standard.MediaName;

public class Problem6P2 {
	
	private static final int MAX_VERTEX_NUMBER =10001;
	
	public static void main(String args[]) {
		
		Problem6P2 p6 = new Problem6P2();
		minHeapTemplate<Integer> highHeap = new minHeapTemplate<Integer>();
		maxHeapTemplate<Integer> lowHeap = new maxHeapTemplate<Integer>();
		Integer[] medianArray = new Integer[MAX_VERTEX_NUMBER];
		BufferedReader br = null;
		int arrayCounter = 0;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader("C:\\JavaCode\\CodeExample\\src\\Algorithms\\Median.txt"));
			while ((sCurrentLine = br.readLine()) != null) 
			{
                Integer newEntry = Integer.parseInt(sCurrentLine);
                //System.out.println("Inserting "+newEntry);
                if(lowHeap.getSize() == 0 && highHeap.getSize() == 0)
                {
                	 lowHeap.insert(newEntry);
                }
                else
                {
                   if(newEntry <= lowHeap.peekMin())
                	   lowHeap.insert(newEntry);
                   else
                	   highHeap.insert(newEntry);
                }
                p6.balanceHeap(lowHeap, highHeap);
                Integer computedMedian = p6.computeMedian(lowHeap, highHeap);
                //System.out.println("Computed Median "+computedMedian);
                medianArray[arrayCounter] = computedMedian;
                ++arrayCounter;
			}
			arrayCounter = 0;
			long medianSum = 0;
			//System.out.println("I reached here ");
			while(medianArray[arrayCounter] != null )
			{
				medianSum += medianArray[arrayCounter];
				++arrayCounter;
			}
			
			System.out.println("The final Median Modulo is "+(medianSum%10000));
				
			//p5.printIt();
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("Exception "+e.getMessage());;
		}
		finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}		
	}
	
	public void balanceHeap(maxHeapTemplate<Integer> lowHeap,minHeapTemplate<Integer> highHeap)
	{
		while(Math.abs(lowHeap.getSize()-highHeap.getSize()) > 1)
		{
			if(lowHeap.getSize() > highHeap.getSize())
			{
				Integer moveInteger = lowHeap.extractMin();
				highHeap.insert(moveInteger);
			}
			else
			{
				Integer moveInteger = highHeap.extractMin();
				lowHeap.insert(moveInteger);
			}
		}
	}
	
	public Integer computeMedian(maxHeapTemplate<Integer> lowHeap,minHeapTemplate<Integer> highHeap)
	{
		Integer medianValue =0;
		if((lowHeap.getSize() + highHeap.getSize())%2 == 0)
			medianValue = lowHeap.peekMin();
		else
		{
			if(lowHeap.getSize() > highHeap.getSize())
				medianValue = lowHeap.peekMin();
			else
				medianValue = highHeap.peekMin();
		}
	
	    return medianValue;
	}
	
	public void printIt(maxHeapTemplate<Integer> lowHeap,minHeapTemplate<Integer> highHeap)
	{
		
	}
}
