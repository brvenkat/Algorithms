package Algorithms2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.regex.Pattern;

import Algorithms.Edge;

public class primAlgorithm {
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
	private LinkedList<Edge>[] vertexLists;
	private static int MAX_VERTEX_NUMBER =501;
	List<Integer> vArray = new ArrayList<Integer>();
	private long edgeCosts = 0;
	
	public static void main(String args[]) {
		primAlgorithm pa = new primAlgorithm();
        pa.vertexLists = new LinkedList[MAX_VERTEX_NUMBER];
        for(int i=0;i<MAX_VERTEX_NUMBER;i++)
        {
        	pa.vertexLists[i] = new LinkedList<Edge>();
        }
		BufferedReader br = null;
		try 
		{
			String sCurrentLine;
			br = new BufferedReader(new FileReader("C:\\JavaCode\\CodeExample\\src\\Algorithms2\\edges.txt"));
			int i =0;
			while ((sCurrentLine = br.readLine()) != null) 
			{
				if(i==0)
				{
					++i;
					continue;
				}
				++i;
				String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
				pa.createList(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]), Long.parseLong(elements[2]));
			}
            pa.runPrimAlgorithm(1);
            System.out.println("The overall cost is "+pa.getEdgeCosts());
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
	
	public void runPrimAlgorithm(int source)
	{
		PriorityQueue<vertexKey> pq = new PriorityQueue<vertexKey>(MAX_VERTEX_NUMBER,vkComparator);
		LinkedList<Edge> ll = vertexLists[source];
		for(Edge e: ll)
		{
			vertexKey vk = new vertexKey(e.destination, e.weight);
			pq.add(vk);
		}
		vArray.add(source);
		while(vArray.size() < MAX_VERTEX_NUMBER - 1)
		{
			vertexKey vk = pq.poll();
			if(vArray.contains(vk.vertex))
				continue;
			edgeCosts+=vk.weight;
			vArray.add(vk.vertex);
			ll = vertexLists[vk.vertex];
			for(Edge e: ll)
			{
				System.out.println("Vertex is "+vk.vertex+" destination "+e.destination+" weight "+e.weight);
				vk = new vertexKey(e.destination, e.weight);
				pq.add(vk);
			}
			
		}
	}
	
	public void createList(int source,int destination,long weight) throws Exception
	{
		Edge e = new Edge(destination,weight);
		LinkedList<Edge> ll = vertexLists[source];
		if(ll == null)
			throw new Exception ("Error with Graph construction");
		ll.add(e);
		
		e = new Edge(source,weight);
		ll = vertexLists[destination];
		if(ll == null)
			throw new Exception ("Error with Graph construction");
		ll.add(e);
	}
	
	public long getEdgeCosts()
	{
		return this.edgeCosts;
	}
	
	public static Comparator<vertexKey> vkComparator = new Comparator<vertexKey>() {
		
		public int compare(vertexKey vk1,vertexKey vk2)
		{
			return (int)(vk1.weight- vk2.weight);
		}
		
	};

}
