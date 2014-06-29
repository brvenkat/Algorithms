package Algorithms;

import java.util.HashMap;

public class minHeapTemplate<T> {
	private static final int MAX_VERTEX_NUMBER =30000;
	private static final Integer ACTUAL_MAX_VALUE = 1000000;
	private T[] listVertices;
	private HashMap<Integer,Integer> mapToIndex;
	public static int heapSize;
	
	public minHeapTemplate() {
		this.listVertices = (T[])new Object[MAX_VERTEX_NUMBER];
		heapSize = 0;
		for(int i=0;i<MAX_VERTEX_NUMBER;i++)
			listVertices[i] = (T)ACTUAL_MAX_VALUE;
	}
	
	private int compare(T k1,T k2) {
		Comparable<T> comparable = (Comparable<T>)k1;
		return comparable.compareTo(k2);
	}
	
	public void insert(T vk)
	{
		int temp=0;
		for(int i=1;i<MAX_VERTEX_NUMBER;i++)
		{
			if(listVertices[i] == ACTUAL_MAX_VALUE)
			{
				temp=i;
				break;
			}
		}
		//To insert Vk at index temp and bubble up/down if needed
		listVertices[temp] = vk;
		int finalPosition = temp;
		while(finalPosition > 1)
		{
			finalPosition = finalPosition/2;
            if(compare(listVertices[finalPosition],vk) > 0)
            {
            	  T tmp = listVertices[finalPosition];
             	  listVertices[temp] = tmp;
            	  listVertices[finalPosition] = vk;
            	  temp=finalPosition;
            }
		}
		//percolate down till the node goes to its rightful place
		if(finalPosition == 1)
		{
           int leftPosition = 2*finalPosition;
           int rightPosition = 2*finalPosition + 1;
           int minIndexValue = ((compare(listVertices[leftPosition],listVertices[rightPosition]) <= 0)?leftPosition:rightPosition);
           while(compare(listVertices[minIndexValue], listVertices[finalPosition]) <=0)
           {
        	   T vkTemp = listVertices[finalPosition];
        	   T updatedVk = listVertices[minIndexValue];
        	   listVertices[finalPosition] = updatedVk;
        	   listVertices[minIndexValue] = vkTemp;
        	   finalPosition = minIndexValue;
        	   leftPosition = 2*finalPosition;
        	   rightPosition = 2*finalPosition + 1;
        	   minIndexValue = ((compare(listVertices[leftPosition],listVertices[rightPosition]) <= 0)?leftPosition:rightPosition);
           }
		}
		++heapSize;
	}
	
	public T peekMin()
	{
		return listVertices[1];
	}
	
	public T extractMin()
	{
		
		T vk = listVertices[1];
		--heapSize;
		T vkNew = vk;
		int index=0;
		for(int i=1;i<MAX_VERTEX_NUMBER;i++)
		{
			if(listVertices[i] == ACTUAL_MAX_VALUE)
			{
			    index = i-1;
			    break;
			}
		}

		if(index ==1 )
		{
			listVertices[1] = (T)ACTUAL_MAX_VALUE;
			return vkNew;
		}
		T updatedVk = listVertices[index];
		listVertices[1] = updatedVk;
	    listVertices[index] = (T)ACTUAL_MAX_VALUE;

	    int finalPosition = 1;
        int leftPosition = 2*finalPosition;
        int rightPosition = 2*finalPosition + 1;
        int minIndexValue = ((compare(listVertices[leftPosition],listVertices[rightPosition]) <= 0)?leftPosition:rightPosition);
        while(compare(listVertices[minIndexValue],listVertices[finalPosition]) <=0)
        {
     	   T vkTemp = listVertices[finalPosition];
     	   T updatedVkNew = listVertices[minIndexValue];
     	   listVertices[finalPosition] = updatedVkNew;
     	   listVertices[minIndexValue] = vkTemp;

     	   finalPosition = minIndexValue;
     	   leftPosition = 2*finalPosition;
     	   rightPosition = 2*finalPosition + 1;
     	   minIndexValue = ((compare(listVertices[leftPosition],listVertices[rightPosition]) <= 0)?leftPosition:rightPosition);

        }
		return vkNew;
	}
	
	public int getSize() {
		return heapSize;
	}
	
	public void printIt()
	{
		for(int i=0;i<MAX_VERTEX_NUMBER;i++)
		{
			if(listVertices[i] == ACTUAL_MAX_VALUE)
				break;
			System.out.println("Vertex is "+listVertices[i]);
		}
	}

}
