package Algorithms2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.regex.Pattern;

public class clusterManager {
	
	private static final int NUM_VERTICES = 11;	
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
	HashSet<Integer> verticesList;
	int[][] vArray;
	PriorityQueue<Edges> pq;
	
	public clusterManager()
	{
		verticesList = new HashSet<Integer>();
		vArray = new int[NUM_VERTICES][NUM_VERTICES];
		pq = new PriorityQueue<Edges>();
	}

	public static void main(String args[]) {
		clusterManager c = new clusterManager();
		BufferedReader br = null;
		try 
		{
			String sCurrentLine;
			br = new BufferedReader(new FileReader("C:\\JavaCode\\CodeExample\\src\\Algorithms2\\hw2_1_tc_10.txt"));
			int i =0;
			while ((sCurrentLine = br.readLine()) != null) 
			{
				if(i==0)
				{
					++i;
					continue;
				}
				String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
				Edges e = new Edges(Integer.parseInt(elements[0]),Integer.parseInt(elements[1]),Long.parseLong(elements[2]));
				c.pq.add(e);
				c.vArray[Integer.parseInt(elements[0])][Integer.parseInt(elements[1])] = Integer.parseInt(elements[2]);
				c.vArray[Integer.parseInt(elements[1])][Integer.parseInt(elements[0])] = Integer.parseInt(elements[2]);
				c.verticesList.add(Integer.parseInt(elements[0]));
				c.verticesList.add(Integer.parseInt(elements[1]));
			}
			c.computeClusters();
			//c.printIt();
			long minimum = c.findSpacing();
			System.out.println("The Maximum Spacing is "+minimum);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("Exception "+e.getMessage());;
		}
		finally 
		{
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}		
	}
	
	public void computeClusters()
	{
		int numClusters = NUM_VERTICES-1;
		while(numClusters > 4)
		{
			Edges e = pq.poll();
			int source = e.source;
			int destination = e.destination;
			long weight = e.weight;
			//System.out.println("SOURCE "+source+" DESTINATION "+destination +" WEIGHT "+weight);
			Edges newEdge = new Edges(destination,source,weight);
			pq.remove(newEdge);
			fusePoints(e);
			--numClusters;
		}
		
	}
	
	private void fusePoints(Edges e)
	{
		int destination = e.destination;
	    updateArray(e);
	    //System.out.println("DESTINATION REMOVED "+destination);
	    verticesList.remove(destination);
	}
	
	private void updateArray(Edges e)
	{
		int source = e.source;
		int destination = e.destination;
		long weight = e.weight;
		for(int i=1;i<NUM_VERTICES;i++)
		{
			if(i==source)
				continue;
			if(destination != i && verticesList.contains(i))
			{
				if(vArray[source][i] > vArray[destination][i])
				{
					Edges sEdge = new Edges(i,source,vArray[source][i]);
					pq.remove(sEdge);
					sEdge = new Edges(source,i,vArray[i][source]);
					pq.remove(sEdge);
					vArray[source][i] = vArray[destination][i];
					vArray[i][source] = vArray[source][i];
					sEdge = new Edges(i,source,vArray[i][source]);
					pq.add(sEdge);
					sEdge = new Edges(source,i,vArray[source][i]);
					pq.add(sEdge);
				}
			}
			/**
			 * Remove all Edges that contain the destination since it is fused with the source
			 */
			
			Edges e2 = new Edges(destination,i,vArray[destination][i]);
			pq.remove(e2);
			e2 = new Edges(i,destination,vArray[i][destination]);
			pq.remove(e2);
			vArray[destination][i] = -99;
			vArray[i][destination] = -99;
			
		}
		vArray[source][destination] = -99;
	}
	
	private long findSpacing() throws Exception
	{
		long minimum = Long.MAX_VALUE;
		
        int[] finalList = new int[4];
        Iterator<Integer> setIterator = verticesList.iterator();
        int i = 0;
        
        while(setIterator.hasNext())
        {
        	finalList[i] = setIterator.next();
        	++i;
        	if(i > 4)
        		throw new Exception("Something wrong with final output");
        }
        
        for(int j=0;j<4;j++)
        {
        	for(int k=0;k<4;k++)
        	{
        		  if(j==k)
        			  continue;
        	      int distance = vArray[finalList[j]][finalList[k]];
        	      if(distance < minimum)
        	    	  minimum = distance;
        	}
        }
        
		return minimum;
	}
	
	public void printIt()
	{
		System.out.println("+++++++++++");
		System.out.println(verticesList);
	}

}
