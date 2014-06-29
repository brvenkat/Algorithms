package Algorithms;

import java.util.HashMap;

public class minHeap {
	private static final int MAX_VERTEX_NUMBER =400;
	private static final int ACTUAL_MAX_VALUE = 1000000;
	private vertexKey[] listVertices;
	private HashMap<Integer,Integer> mapToIndex;
	
	public minHeap() {
		this.listVertices = new vertexKey[MAX_VERTEX_NUMBER];
		for(int i=0;i<MAX_VERTEX_NUMBER;i++)
		{
			vertexKey vk = new vertexKey(-1, ACTUAL_MAX_VALUE);
			listVertices[i] = vk;
		}
		mapToIndex = new HashMap<Integer,Integer>();
	}
	
	public void insert(vertexKey vk)
	{
		int temp=0;
		for(int i=1;i<MAX_VERTEX_NUMBER;i++)
		{
			if(listVertices[i].vertex == -1)
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
            if(listVertices[finalPosition].weight > vk.weight)
            {
            	  int updatedVertex = listVertices[finalPosition].vertex;
            	  long updatedWeight = listVertices[finalPosition].weight;
            	  vertexKey updatedVk = new vertexKey(updatedVertex, updatedWeight);
             	  listVertices[temp] = updatedVk;
             	  mapToIndex.put(updatedVertex,temp);
            	  listVertices[finalPosition] = vk;
            	  temp=finalPosition;
            }
            mapToIndex.put(vk.vertex,temp);
		}
		//percolate down till the node goes to its rightful place
		if(finalPosition == 1)
		{
           int leftPosition = 2*finalPosition;
           int rightPosition = 2*finalPosition + 1;
           int minIndexValue = ((listVertices[leftPosition].weight<listVertices[rightPosition].weight)?leftPosition:rightPosition);
           while(listVertices[minIndexValue].weight < listVertices[finalPosition].weight)
           {
        	   vertexKey vkTemp = listVertices[finalPosition];
        	   int updatedVertex = listVertices[minIndexValue].vertex;
        	   long updatedWeight = listVertices[minIndexValue].weight;
        	   vertexKey updatedVk = new vertexKey(updatedVertex,updatedWeight);
        	   listVertices[finalPosition] = updatedVk;
        	   mapToIndex.put(updatedVertex, finalPosition);
        	   updatedVertex = vkTemp.vertex;
        	   updatedWeight = vkTemp.weight;
        	   updatedVk = new vertexKey(updatedVertex,updatedWeight);
        	   listVertices[minIndexValue] = updatedVk;
        	   mapToIndex.put(updatedVertex, minIndexValue);
        	   finalPosition = minIndexValue;
        	   leftPosition = 2*finalPosition;
        	   rightPosition = 2*finalPosition + 1;
        	   minIndexValue = ((listVertices[leftPosition].weight<listVertices[rightPosition].weight)?leftPosition:rightPosition);
           }
		}
	}
	
	public vertexKey extractMin()
	{
		
		vertexKey vk = listVertices[1];
		int vertexValue = vk.vertex;
		long weightValue = vk.weight;
		vertexKey vkNew = new vertexKey(vertexValue, weightValue);
		mapToIndex.remove(vertexValue);
		int index=0;
		for(int i=1;i<MAX_VERTEX_NUMBER;i++)
		{
			if(listVertices[i].weight == ACTUAL_MAX_VALUE)
			{
			    index = i-1;
			    break;
			}
		}

		if(index ==1 )
		{
			listVertices[1].vertex = -1;
			listVertices[1].weight = ACTUAL_MAX_VALUE;
			return vkNew;
		}

		int updatedVertex = listVertices[index].vertex;
		long updatedWeight = listVertices[index].weight;
		vertexKey updatedVk = new vertexKey(updatedVertex, updatedWeight);
		listVertices[1] = updatedVk;
	    listVertices[index].weight = ACTUAL_MAX_VALUE;
	    listVertices[index].vertex = -1;

	    int finalPosition = 1;
        int leftPosition = 2*finalPosition;
        int rightPosition = 2*finalPosition + 1;
        int minIndexValue = ((listVertices[leftPosition].weight<listVertices[rightPosition].weight)?leftPosition:rightPosition);
        boolean enteredWhile = false;

        while(listVertices[minIndexValue].weight < listVertices[finalPosition].weight)
        {
           enteredWhile = true;
     	   vertexKey vkTemp = listVertices[finalPosition];
     	   updatedWeight = listVertices[minIndexValue].weight;
     	   updatedVertex = listVertices[minIndexValue].vertex;
     	   updatedVk = new vertexKey(updatedVertex, updatedWeight);
     	   listVertices[finalPosition] = updatedVk;

     	   mapToIndex.put(updatedVertex, finalPosition);
     	   updatedVertex = vkTemp.vertex;
     	   updatedWeight = vkTemp.weight;
     	   updatedVk = new vertexKey(updatedVertex, updatedWeight);
     	   listVertices[minIndexValue] = updatedVk;

     	   mapToIndex.put(updatedVertex, minIndexValue);
     	   finalPosition = minIndexValue;
     	   leftPosition = 2*finalPosition;
     	   rightPosition = 2*finalPosition + 1;
     	   minIndexValue = ((listVertices[leftPosition].weight<listVertices[rightPosition].weight)?leftPosition:rightPosition);

        }
        if(!enteredWhile)
        	mapToIndex.put(listVertices[1].vertex, 1);
		return vkNew;
	}
	
	public boolean contains(vertexKey vk)
	{
		return mapToIndex.containsKey(vk.vertex);
	}
	
	public vertexKey find(int source)
	{
		vertexKey vk = null;
		int index = mapToIndex.get(source);
		vk = listVertices[index];
		return vk;
	}
	
	public void remove(int source)
	{
		int index = mapToIndex.get(source);
		listVertices[index].vertex = -1;
		listVertices[index].weight = ACTUAL_MAX_VALUE;
		mapToIndex.remove(source);
		
        int leftPosition = 2*index;
        int rightPosition = 2*index + 1;
        int minIndexValue = ((listVertices[leftPosition].weight<listVertices[rightPosition].weight)?leftPosition:rightPosition);
        while(listVertices[minIndexValue].weight < listVertices[index].weight)
        {
     	   vertexKey vkTemp = listVertices[index];
     	   long updatedWeight = listVertices[minIndexValue].weight;
     	   int updatedVertex = listVertices[minIndexValue].vertex;
     	   vertexKey updatedVk = new vertexKey(updatedVertex, updatedWeight);
     	   listVertices[index] = updatedVk;
     	   mapToIndex.put(updatedVertex, index);
     	   updatedVertex = vkTemp.vertex;
     	   updatedWeight = vkTemp.weight;
     	   updatedVk = new vertexKey(updatedVertex, updatedWeight);
     	   listVertices[minIndexValue] = updatedVk;
     	   mapToIndex.put(updatedVertex, minIndexValue);
     	   index = minIndexValue;
     	   leftPosition = 2*index;
     	   rightPosition = 2*index + 1;
     	   minIndexValue = ((listVertices[leftPosition].weight<listVertices[rightPosition].weight)?leftPosition:rightPosition);
        }
	}
	
	public void printIt()
	{
		for(int i=0;i<MAX_VERTEX_NUMBER;i++)
		{
			if(listVertices[i].vertex == -1)
				break;
			System.out.println("Vertex is "+listVertices[i].vertex);
		}
	}

}
