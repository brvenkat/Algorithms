package Algorithms;

import graphAlgorithms.distance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.PriorityQueue;

public class Problem5New {
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
	private static final Pattern COMMA_PATTERN = Pattern.compile("\\,");
	private LinkedList<Edge>[] vertexLists;
	private static final int MAX_VERTEX_NUMBER =201;
	minHeap pq = new minHeap();
	List<Integer> vArray = new ArrayList<Integer>();
	private static final List<Integer> sList = Arrays.asList(7,37,59,82,99,115,133,165,188,197);
	//private static final int MAX_VERTEX_NUMBER =14;
	//private static final List<Integer> sList = Arrays.asList(6,9,10,11,13);
	
	public static void main(String args[]) {
		long startTime = System.currentTimeMillis();
		Problem5New p5 = new Problem5New();
        p5.vertexLists = new LinkedList[MAX_VERTEX_NUMBER];
        for(int i=0;i<MAX_VERTEX_NUMBER;i++)
        {
        	p5.vertexLists[i] = new LinkedList<Edge>();
        }
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader("C:\\JavaCode\\CodeExample\\src\\Algorithms\\dijkstraData.txt"));
			while ((sCurrentLine = br.readLine()) != null) 
			{
				String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
				for(int i=1;i<elements.length;i++) {
					String[] twoValues = COMMA_PATTERN.split(elements[i]);
					p5.createList(Integer.parseInt(elements[0]), Integer.parseInt(twoValues[0]), Long.parseLong(twoValues[1]));
				}
			}
			//p5.printIt();
            p5.computeShortestPath(1);
            System.out.println("EXECUTION TIME "+(System.currentTimeMillis()-startTime));
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
	
	public static Comparator<vertexKey> vkComparator = new Comparator<vertexKey>() {
		
		public int compare(vertexKey vk1,vertexKey vk2)
		{
			return (int)(vk1.weight- vk2.weight);
		}
		
	};
	
	public void createList(int source,int destination,long weight) throws Exception
	{
		Edge e = new Edge(destination,weight);
		LinkedList<Edge> ll = vertexLists[source];
		if(ll == null)
			throw new Exception ("Error with Graph construction");
		ll.add(e);
	}
	
	public void computeShortestPath(int source) 
	{
		vertexKey v = new vertexKey(source,0);
		pq.insert(v);
		
		while(vArray.size() < MAX_VERTEX_NUMBER - 1)
		{
			vertexKey newV = pq.extractMin();

			int newVertex = newV.vertex;
			if(!vArray.contains(newVertex) && newVertex != -1)
			{
				vArray.add(newVertex);
				if(sList.contains(newVertex))
					System.out.println("Vertex "+newVertex+ " shortestPath "+newV.weight);
			    LinkedList<Edge> ll = vertexLists[newVertex];

			    for(Edge e: ll)
			    {
			    	vertexKey vToCompare = new vertexKey(e.destination, e.weight);
			    	if(!pq.contains(vToCompare))
			    	{
			    			vertexKey vToInsert = new vertexKey(e.destination, (newV.weight+e.weight));
			    			//System.out.println("Vertex "+e.destination+ " weight "+e.weight);
			    			pq.insert(vToInsert);
			    	}
			    	else
			    	{
			    		long currentValue=(pq.find(e.destination)).weight;
			    		long minWeight =((newV.weight+e.weight)<currentValue?(newV.weight+e.weight):currentValue);
			    		pq.remove(e.destination);
			    		vertexKey vToInsert = new vertexKey(e.destination, minWeight);
			    		pq.insert(vToInsert);

			    	}
			    }	
			  }
		  }
		}
	
	public void printIt()
	{
		for(int i=1;i<MAX_VERTEX_NUMBER;i++)
		{
			System.out.println("Vertex "+i);
			LinkedList<Edge> ll = vertexLists[i];
			for(Edge e : ll)
				System.out.println("Vertex "+e.destination+" Weight "+e.weight);
			System.out.println("------------");
		}
	}
}
