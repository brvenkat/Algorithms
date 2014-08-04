package Algorithms;

import graphAlgorithms.graphNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Problem4 {
	
	private LinkedList<Integer>[] vertexLists;
	private LinkedList<Integer>[] vertexLists2;
	private LinkedList<Integer>[] newVertexLists;
	HashMap<Integer,Integer> tempMap;
	
	private final int MAX_VERTEX_NUMBER = 875715;
	
	private Boolean[] visited;
	private Integer[] finishTime;
	
	List<Integer> finalArrayList;
	
	Integer ARRAYMIN = 0;
	
	int numnodes = 0;
	
	int timeFinished = 1;
	
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
	public static void main(String args[])
	{
		Problem4 p4 = new Problem4();
		long s1 = System.currentTimeMillis();
		p4.init();
		long e1 = System.currentTimeMillis();
		System.out.println("Time to Init "+(e1-s1));
		BufferedReader br = null;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader("C:\\JavaCode\\CodeExample\\src\\Algorithms\\SCC.txt"));
			long reads = System.currentTimeMillis();
			while ((sCurrentLine = br.readLine()) != null) 
			{
				String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
				Integer source = Integer.parseInt(elements[0]);
				Integer destination = Integer.parseInt(elements[1]);
                p4.addEdge(source, destination);
			}
			long reade = System.currentTimeMillis();
			System.out.println("Time to Add Edge "+(reade-reads));
			p4.computeSCC();
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
	
	public void init() {
		vertexLists = new LinkedList[MAX_VERTEX_NUMBER];
		for(int i=0;i<MAX_VERTEX_NUMBER;i++)
		{
			vertexLists[i]=new LinkedList<Integer>();
		}
		
		vertexLists2 = new LinkedList[MAX_VERTEX_NUMBER];
		for(int i=0;i<MAX_VERTEX_NUMBER;i++)
		{
			vertexLists2[i]=new LinkedList<Integer>();
		}
		
		newVertexLists = new LinkedList[MAX_VERTEX_NUMBER];
		for(int i=0;i<MAX_VERTEX_NUMBER;i++)
		{
			newVertexLists[i]=new LinkedList<Integer>();
		}
		
		visited = new Boolean[MAX_VERTEX_NUMBER];
		for(int i=0;i<MAX_VERTEX_NUMBER;i++)
			visited[i] = false;
		
		finishTime = new Integer[MAX_VERTEX_NUMBER];
		
	    tempMap = new HashMap<Integer,Integer>();
		
		finalArrayList = new ArrayList<Integer>();
	}
	
	public void addEdge(Integer source,Integer destination) throws Exception
	{
		LinkedList<Integer> dList = vertexLists[destination];
		if(dList == null)
		   throw new Exception("Please check Destination Graph Construction "+source+":"+destination);
		dList.add(source);
		
		LinkedList<Integer> sList = vertexLists2[source];
		if(dList == null)
			   throw new Exception("Please check Source Graph Construction "+source+":"+destination);
		sList.add(destination); 
	}
	
	public void computeSCC() throws Exception
	{
		long s2 = System.currentTimeMillis();
		DFSLoopOne();
		long e2 = System.currentTimeMillis();
		System.out.println("Time for Loop One "+(e2-s2));
		
		//printFinish();
		long intStart = System.currentTimeMillis();
		for(int i=1;i<MAX_VERTEX_NUMBER;i++)
		{
			LinkedList<Integer> aList = vertexLists2[i];
			LinkedList<Integer> nList = new LinkedList<Integer>();
			for(int j=0;j<aList.size();j++)
			{
				nList.add(tempMap.get(aList.get(j)));
			}
			newVertexLists[tempMap.get(i)] = nList;
		}
		long intEnd = System.currentTimeMillis();
		System.out.println("Time for just hash "+(intEnd-intStart));

		for(int i=0;i<MAX_VERTEX_NUMBER;i++)
			visited[i] = false;
		
		long s3 = System.currentTimeMillis();
		DFSLoopTwo();
		long e3 = System.currentTimeMillis();
		System.out.println("Time for Loop two "+(e3-s3));

		Collections.sort(finalArrayList);;
		for(int i=finalArrayList.size()-1;i>=0;i--)
			System.out.println("The max 5 SCC sizes are "+finalArrayList.get(i));
	}
	
	public void DFSLoopOne() throws Exception
	{
		for(int i=MAX_VERTEX_NUMBER-1;i>=1;i--)
		{
			if(!visited[i])
				DFSPassOne(i);
		}
	}
	
	public void DFSLoopTwo()
	{
		for(int i=MAX_VERTEX_NUMBER-1;i>=1;i--)
		{
			numnodes=0;
			if(!visited[i])
				DFSPassTwo(i);
			updateSCCList(numnodes);
		}
	}
	
	public void DFSPassOne(Integer source) throws Exception
	{
		visited[source] = true;
		LinkedList<Integer> ll = vertexLists[source];
		for(int i=0;i<ll.size();i++)
		{
			if(!visited[ll.get(i)])
				DFSPassOne(ll.get(i));
		}
		if(timeFinished > MAX_VERTEX_NUMBER)
			throw new Exception ("Time Finished Exceeds MAX VERTEX count");
		tempMap.put(source, timeFinished);
		++timeFinished;
	}
	
	public void DFSPassTwo(Integer source)
	{
		++numnodes;
		visited[source] = true;
		LinkedList<Integer> ll = newVertexLists[source];
		for(int i=0;i<ll.size();i++)
		{
			if(!visited[ll.get(i)])
				DFSPassTwo(ll.get(i));
		}
	}
	
	public void updateSCCList(Integer newValue)
	{
		//Check if new Value can be fitted 
		if(finalArrayList.size() < 5)
		{
			finalArrayList.add(newValue);
			Collections.sort(finalArrayList);
		}
		else
		{
			if(newValue > finalArrayList.get(0))
			{
				finalArrayList.remove(finalArrayList.get(0));
				finalArrayList.add(newValue);
				Collections.sort(finalArrayList);
			}
		}
	}
	
	public void printIt()
	{
		for(int i=1;i<vertexLists.length;i++)
		{
			System.out.println("Vertex is "+i);
			LinkedList<Integer> ll = vertexLists[i];
			for(Integer j: ll)
				System.out.println("Vertices connected "+j);
		}
	}
	
	public void printFinish()
	{
		for(Map.Entry<Integer, Integer> entry: tempMap.entrySet())
			System.out.println("Vertex "+entry.getKey()+" Finish time "+entry.getValue());
	}

}
