package Algorithms2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.regex.Pattern;

import Algorithms.minHeap;
import Algorithms.vertexKey;
import Algorithms2.Edge;

public class johnsonAlgorithm {
	
	int[][] shortestPathArray;
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
	List<Integer> vArray = new ArrayList<Integer>();
	private static int NUM_VERTICES;
	private LinkedList<Edge>[] vertexLists;
	private LinkedList<Edge>[] origVertexLists;
	PriorityQueue<vertexKey> pq = new PriorityQueue<vertexKey>(NUM_VERTICES+1,vkComparator);
	long[][] shortestPathLength;
	public johnsonAlgorithm()
	{
		shortestPathArray = new int[NUM_VERTICES+1][NUM_VERTICES+1];
		shortestPathArray[0][0] =0;
		for(int i=1;i<NUM_VERTICES+1;i++)
		{
            shortestPathArray[0][i] = Integer.MAX_VALUE;
		}
		this.vertexLists = new LinkedList[NUM_VERTICES+1];
		for(int i=0;i<NUM_VERTICES+1;i++)
			vertexLists[i] = new LinkedList<Edge>();
		this.origVertexLists = new LinkedList[NUM_VERTICES+1];
		for(int i=0;i<NUM_VERTICES+1;i++)
			origVertexLists[i] = new LinkedList<Edge>();
		shortestPathLength = new long[NUM_VERTICES+1][NUM_VERTICES+1];
	}
	
	public static void main(String args[])
	{
		BufferedReader br = null;
		String sCurrentLine;
		int i=0;
		johnsonAlgorithm ja =null;
        try
        {
			br = new BufferedReader(new FileReader("C:\\JavaCode\\CodeExample\\src\\Algorithms2\\g3.txt"));
			while ((sCurrentLine = br.readLine()) != null) 
			{
				if(i==0)
				{
					String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
					NUM_VERTICES = Integer.parseInt(elements[0]);
                    ja = new johnsonAlgorithm();
                    ++i;
                    continue;
				}
				String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
				int sourceVertex = Integer.parseInt(elements[0]);
				int destinationVertex = Integer.parseInt(elements[1]);
				int edgeCost = Integer.parseInt(elements[2]);
				Edge e = new Edge(sourceVertex,edgeCost);
				LinkedList<Edge> ll = ja.vertexLists[destinationVertex];
				ll.add(e);
				Edge eOrig = new Edge(destinationVertex,edgeCost);
				LinkedList<Edge> llOrig = ja.origVertexLists[sourceVertex];
				llOrig.add(eOrig);
			}
			ja.initSource();
			boolean hasCycle = ja.runBellManFord();
			if(hasCycle)
				System.out.println("Graph has negative cycle");
			else
			{
				ja.reComputeEdges();
				ja.runDijkstra();
				ja.reComputePathDistance();
				long minimum = ja.findMinimum();
				System.out.println("The minimum value is "+minimum);
				
			}
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
	
	private void initSource()
	{
		for(int i=1;i<NUM_VERTICES+1;i++)
		{
			LinkedList<Edge> e = vertexLists[i];
			Edge sEdge = new Edge(0,0);
			e.add(sEdge);
		}
	}
	
	private boolean runBellManFord()
	{
		boolean hasCycle = false;
		for(int i=1;i<NUM_VERTICES+1;i++)
		{
			for(int v=1;v<NUM_VERTICES+1;v++)
			{
				int caseOne = shortestPathArray[i-1][v];
				int caseTwo = getMinWeight(i, v);
				shortestPathArray[i][v] = (caseOne<caseTwo?caseOne:caseTwo);
			}
		}
	    hasCycle = checkCycle();
	    return hasCycle;
	}
	
	private void reComputeEdges()
	{
		for(int i=1;i<NUM_VERTICES+1;i++)
		{
			LinkedList<Edge> ll = origVertexLists[i];
			LinkedList<Edge> llNew = new LinkedList<Edge>();
			for(Edge e:ll)
			{
				e.weight += -shortestPathArray[NUM_VERTICES-1][e.source]+shortestPathArray[NUM_VERTICES-1][i];
				Edge eNew = new Edge(e.source,e.weight);
				llNew.add(eNew);
			}
			origVertexLists[i] = llNew;
		}
	}
	
	private void runDijkstra()
	{
		System.out.println("Starting Dijkstra");
		for(int i=1;i<NUM_VERTICES+1;i++)
		{
			dijkstraHelper(i);
		}
		System.out.println("Ending Dijkstra");
	}
	
	private void reComputePathDistance()
	{
		for(int i=1;i<NUM_VERTICES+1;i++)
		{
			for(int j=1;j<NUM_VERTICES+1;j++)
			{
				shortestPathLength[i][j] = shortestPathLength[i][j]-shortestPathArray[NUM_VERTICES-1][i]+shortestPathArray[NUM_VERTICES-1][j];
			}
		}
	}
	
	private long findMinimum()
	{
		long minimum =Long.MAX_VALUE;
		for(int i=1;i<NUM_VERTICES+1;i++)
		{
			for(int j=1;j<NUM_VERTICES+1;j++)
			{
				if(minimum > shortestPathLength[i][j] && i != j && shortestPathLength[i][j] != 0)
				{
					minimum = shortestPathLength[i][j];
				}
			}
		}
		//System.out.println("SHORTEST "+shortestPathLength[2][5]);
		return minimum;
	}
	public static Comparator<vertexKey> vkComparator = new Comparator<vertexKey>() {
		
		public int compare(vertexKey vk1,vertexKey vk2)
		{
			return (int)(vk1.weight- vk2.weight);
		}
		
	};
	
	private void dijkstraHelper(int source)
	{
		vertexKey v = new vertexKey(source,0);
		pq.add(v);
		HashMap<Integer,Long> heapMap = new HashMap<Integer,Long>();
		//System.out.println("SOurce is "+source);
		while(!pq.isEmpty())
		{
			//System.out.println("SOurce is "+source);
			vertexKey newV = pq.poll();
			int newVertex = newV.vertex;
			if(vArray.size() == NUM_VERTICES - 1)
				break;
			if(!vArray.contains(newVertex))
			{
				if(newV.weight != 0)
				{
					//System.out.println("Source "+source+" Destination "+newV.vertex+" weight "+newV.weight);
					shortestPathLength[source][newV.vertex]=newV.weight;
				}
			   vArray.add(newVertex);
			   LinkedList<Edge> ll = origVertexLists[newVertex];
                //System.out.println("New vertex is "+newVertex);
			    for(Edge e: ll)
			    {
			    	vertexKey vToCompare = new vertexKey(e.source, e.weight);
			    	if(!pq.contains(vToCompare))
			    	{
			    			vertexKey vToInsert = new vertexKey(e.source, (newV.weight+e.weight));
			    			//System.out.println("Vertex "+e.source+ " weight "+e.weight);
			    			pq.add(vToInsert);
			    			heapMap.put(e.source, (newV.weight+e.weight));
			    	}
			    	else
			    	{
			    		long currentValue=0;
			    		if(heapMap.containsKey(e.source))
			    			currentValue = heapMap.get(e.source);
			    		long minWeight =((newV.weight+e.weight)<currentValue?(newV.weight+e.weight):currentValue);
			    		pq.remove(e.source);
			    		//System.out.println("Vertex "+ e.destination+ " old weight "+(newV.weight+e.weight)+ " new weight "+vToCompare.weight);
			    		vertexKey vToInsert = new vertexKey(e.source, minWeight);
			    		pq.add(vToInsert);
                        heapMap.put(e.source, minWeight);
			    	}
			    }
			  }
		  }
		vArray.clear();
		heapMap.clear();
	}
	
	private boolean checkCycle()
	{
		boolean hasCycle = false;
		for(int i=1;i<NUM_VERTICES+1;i++)
		{
			if(shortestPathArray[NUM_VERTICES][i] < shortestPathArray[NUM_VERTICES-1][i])
			{
				//System.out.println("ARRAY SHORT "+i+":"+shortestPathArray[NUM_VERTICES][i]+": "+shortestPathArray[NUM_VERTICES-1][i]);
				hasCycle = true;
				break;
			}
		}
		return hasCycle;
	}
	private int getMinWeight(int i,int source)
	{
		int minWeight = Integer.MAX_VALUE;
		LinkedList<Edge> eList = vertexLists[source];
		for(Edge e: eList)
		{
			int w = e.source;
			if(shortestPathArray[i-1][w] == Integer.MAX_VALUE)
				continue;
			if(shortestPathArray[i-1][w]+e.weight < minWeight)
				minWeight = shortestPathArray[i-1][w] + e.weight;
		}
		return minWeight;
	}

}
