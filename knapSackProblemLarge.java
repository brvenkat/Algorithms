package Algorithms2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.regex.Pattern;

public class knapSackProblemLarge {
	HashMap<Integer,Boolean> visitedMap;
	List<Integer> vertexList;
	HashMap<Integer,String> vertexMapping;
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

	public bigClusterClassNew()
	{
		visitedMap = new HashMap<Integer,Boolean>();
		vertexList = new ArrayList<Integer>();
		vertexMapping = new HashMap<Integer,String>();
	}

	public static void main(String args[])
	{
		knapSackProblemLarge bc = new knapSackProblemLarge();
		BufferedReader br = null;
		try
		{

			br = new BufferedReader(new FileReader("C:\\JavaCode\\CodeExample\\src\\Algorithms2\\clustering_big.txt"));
			int i =0;
			String sCurrentLine;
			long startTime = System.currentTimeMillis();
			while ((sCurrentLine = br.readLine()) != null)
			{
				if(i==0)
				{
					++i;
					continue;
				}
				String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
				StringBuffer sb = new StringBuffer();
				for(int j=0;j<elements.length;j++)
					sb.append(elements[j]);
				Integer sbInteger = Integer.parseInt(sb.toString(),2);
				bc.visitedMap.put(sbInteger, false);
				bc.vertexList.add(sbInteger);
				bc.vertexMapping.put(sbInteger, sb.toString());
			}
			long endTime = System.currentTimeMillis();
			System.out.println("Time to read graph "+(endTime-startTime));
			startTime = System.currentTimeMillis();
			int numClusters = bc.computeClusters();
			endTime = System.currentTimeMillis();
			System.out.println("The total time taken is "+(endTime-startTime));
			System.out.println("The number of clusters is "+numClusters);

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

	public int computeClusters() throws Exception
	{
		int numClusters=0;
		for(Integer node: vertexList)
		{
			if(!(visitedMap.get(node)))
			{
				doBFS(node);
				++numClusters;
			}
		}
		return numClusters;
	}

	private void doBFS(Integer node) throws Exception
	{
		visitedMap.put(node, true);
		Queue<Integer> q = new LinkedList<Integer>();
		q.add(node);
		while(!q.isEmpty())
		{
			Integer tempNode = q.poll();
			List<Integer> tempList = getPossibleNeighbours(tempNode);
			for(Integer item: tempList)
			{
				if(!(vertexList.contains(item)))
					continue;

				if(!visitedMap.get(item))
				{
					visitedMap.put(item, true);
					q.add(item);
				}
			}
		}
	}

	public List<Integer> getPossibleNeighbours(Integer nodeValue) throws Exception
	{
		String nodeValueinString = vertexMapping.get(nodeValue);
		List<Integer> neighbourList = new ArrayList<Integer>();
		for(int i=0;i<nodeValueinString.length();i++)
		{
			StringBuffer tempBuffer = new StringBuffer();
			tempBuffer.append(nodeValueinString.substring(0, i));
			if(nodeValueinString.charAt(i)=='0')
				tempBuffer.append("1");
			else
				tempBuffer.append("0");
			tempBuffer.append(nodeValueinString.subSequence(i+1, nodeValueinString.length()));
			if(visitedMap.containsKey(Integer.parseInt(tempBuffer.toString(),2)))
			{
			   Integer value = Integer.parseInt(tempBuffer.toString(),2);
			   neighbourList.add(value);
			}

		}
		for(int i=0;i<nodeValueinString.length();i++)
		{
			for(int j =i+1;j<nodeValueinString.length();j++)
			{
				StringBuffer tempBuffer = new StringBuffer();
				tempBuffer.append(nodeValueinString.substring(0, i));
				if(nodeValueinString.charAt(i)=='0')
					tempBuffer.append("1");
				else
					tempBuffer.append("0");
				tempBuffer.append(nodeValueinString.substring(i+1, j));
				if(nodeValueinString.charAt(j)=='0')
					tempBuffer.append("1");
				else
					tempBuffer.append("0");
				tempBuffer.append(nodeValueinString.subSequence(j+1, nodeValueinString.length()));
				Integer intValue = Integer.parseInt(tempBuffer.toString(),2);
				if(visitedMap.containsKey(intValue))
					   neighbourList.add(intValue);
			}
		}
		return neighbourList;
	}
}
