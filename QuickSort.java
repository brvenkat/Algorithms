package Algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Algorithms.pivotSelector.pivotHolder;

public class Problem2 {
	private static long numComparisons = 0;
	private static long numComparisons1 = 0;
	private static long numComparisons2 = 0;
	public static void main(String[] args) {
		BufferedReader br = null;
		 
		List<Long> arrayNumbers = new ArrayList<Long>();
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("C:\\JavaCode\\CodeExample\\bin\\Algorithms\\QuickSort.txt"));
 
			while ((sCurrentLine = br.readLine()) != null) 
				arrayNumbers.add(Long.parseLong(sCurrentLine)) ;
			Problem2 p2 = new Problem2();
			p2.quickSortThird(arrayNumbers, 0, arrayNumbers.size()-1);
			System.out.println("Comparison using last pivot "+numComparisons2);
            //p2.quickSort(arrayNumbers,0,arrayNumbers.size()-1);
            //System.out.println("Comparison using First Pivot "+numComparisons);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public void quickSort(List<Long> arrayNumbers,int left,int right)
	{
		if(left>=right)
			return;
        Long pivotValue = pivotSelector.getInstance().chooseDefaultPivot(arrayNumbers,left);
        int pivotPosition = partitionArray(arrayNumbers, pivotValue,left,right);
        numComparisons = numComparisons + (right-left);
        quickSort(arrayNumbers, left, pivotPosition-1);
        quickSort(arrayNumbers, pivotPosition+1, right);
	}
	
	public void quickSortSecond(List<Long> arrayNumbers,int left,int right)
	{
		if(left>=right)
			return;
        Long pivotValue = pivotSelector.getInstance().chooseFinalElementAsPivot(arrayNumbers,right);
		Long temp = arrayNumbers.get(right);
		arrayNumbers.set(right, arrayNumbers.get(left));
		arrayNumbers.set(left, temp);
        int pivotPosition = partitionArray(arrayNumbers, pivotValue,left,right);
        numComparisons1 = numComparisons1 + (right-left);
        quickSortSecond(arrayNumbers, left, pivotPosition-1);
        quickSortSecond(arrayNumbers, pivotPosition+1, right);		
	}
	
	public void quickSortThird(List<Long> arrayNumbers,int left,int right)
	{
		if(left>=right)
			return;
		pivotHolder ph = pivotSelector.getInstance().chooseMedianPivot(arrayNumbers,left,right);
        Long pivotValue = ph.number;
        //System.out.println("Pivot Value is "+pivotValue);
		Long temp = arrayNumbers.get(ph.index);
		//System.out.println("The index of pivot is "+ph.index);
		//System.out.println("left is "+left);
		//System.out.println("Right is "+right);
		//System.out.println("====================");
		arrayNumbers.set(ph.index, arrayNumbers.get(left));
		arrayNumbers.set(left, temp);
        int pivotPosition = partitionArray(arrayNumbers, pivotValue,left,right);
        numComparisons2 = numComparisons2 + (right-left);
        quickSortThird(arrayNumbers, left, pivotPosition-1);
        quickSortThird(arrayNumbers, pivotPosition+1, right);		
	}
	
	public int partitionArray(List<Long> arrayNumbers,Long pivot,int left,int right)
	{
		//numComparisons= numComparisons + (right-left);
		int i = left+1;
		for(int j=left+1;j<=right;j++)
		{
			if(arrayNumbers.get(j) < pivot)
			{
				Long temp = arrayNumbers.get(i);
				arrayNumbers.set(i, arrayNumbers.get(j));
				arrayNumbers.set(j, temp);
				i = i+1;
			}
		}
		Long temp = arrayNumbers.get(i-1);
		arrayNumbers.set(i-1,arrayNumbers.get(left));
		arrayNumbers.set(left, temp);
		return i-1;
	}

}
