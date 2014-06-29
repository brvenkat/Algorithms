package Algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class GraphClass {
	List<Integer> vertexList;
	List<Integer> edgeList;
	HashMap<Integer,List<Integer>> edgeMap;
	HashMap<Integer,List<Integer>> vertexMap;
	private Integer edgeId;
	
	public GraphClass(int numNodes)
	{
		edgeId=1;
		vertexList =  new ArrayList<Integer>();
		edgeList = new ArrayList<Integer>();

		edgeMap = new HashMap<Integer,List<Integer>>();
	    vertexMap = new HashMap<Integer,List<Integer>>();
	}
	
	public void addEdge(Integer source,Integer destination)
	{
		boolean hasEdge = false;
		
		if(vertexList.contains(source) && vertexList.contains(destination))
		{
			for(Map.Entry<Integer, List<Integer>> entry : edgeMap.entrySet())
			{
				List<Integer> vLists = entry.getValue();
				if(vLists.contains(source) && vLists.contains(destination))
				{
					hasEdge=true;
					break;
				}
			}
		}
		if(!hasEdge)
		{
			if(!(vertexList.contains(source)))
				vertexList.add(source);
			if(!(vertexList.contains(destination)))
				vertexList.add(destination);
			
			edgeList.add(edgeId);
			
			List<Integer> vList = new ArrayList<Integer>();
			vList.add(source);
			vList.add(destination);
			edgeMap.put(edgeId, vList);
			
			if(vertexMap.containsKey(source))
			{
				List<Integer> eList = vertexMap.get(source);
				eList.add(edgeId);
			}
			else
			{
				List<Integer> eList = new ArrayList<Integer>();
				eList.add(edgeId);
				vertexMap.put(source, eList);
				
			}
			
			if(vertexMap.containsKey(destination))
			{
				List<Integer> eList = vertexMap.get(destination);
				eList.add(edgeId);
			}
			else
			{
				List<Integer> eList = new ArrayList<Integer>();
				eList.add(edgeId);
				vertexMap.put(destination, eList);
				
			}
			++edgeId;
		}
			
	}
	
	public boolean contractEdge(Integer randomNumber) throws Exception
	{

		if(!(edgeList.contains(randomNumber)))
			return false;
		else
		{
			List<Integer> vertices = edgeMap.get(randomNumber);
			if(vertices == null || vertices.size() < 2)
				throw new Exception("Something wrong in edge creation");
			Integer source = vertices.get(0);
			Integer destination = vertices.get(1);
			
			int indexToBeRemoved = edgeList.indexOf(randomNumber);
			edgeList.remove(indexToBeRemoved);
			
			if(edgeMap.containsKey(randomNumber))
				edgeMap.remove(randomNumber);
			else
			{
				//Can this happen???
				throw new Exception("Edge Construction Incorrect ");
			}
			
			HashMap<Integer,List<Integer>> newMap = new HashMap<Integer,List<Integer>>();
			Iterator<Map.Entry<Integer, List<Integer>>> iter = edgeMap.entrySet().iterator();
			while(iter.hasNext())
			{
				List<Integer> aList = new ArrayList<Integer>();
				Map.Entry<Integer,List<Integer>> entry = iter.next();
				List<Integer> tempList = entry.getValue();
				if(tempList.contains(source) && tempList.contains(destination))
				{
					if(edgeList.contains(entry.getKey()))
					{
						int indexEdgeRemoval = edgeList.indexOf(entry.getKey());
						edgeList.remove(indexEdgeRemoval);
					}
                   continue;
				}
				else
				{
					if(tempList.contains(destination) && !(tempList.contains(source)))
					{
						if((tempList.get(0)).equals(destination))
						{
							aList.add(source);
							aList.add(tempList.get(1));
						}
						else
						{
							aList.add(tempList.get(0));
							aList.add(source);
						}
					}
					else
						aList = tempList;
				}
				newMap.put(entry.getKey(), aList);
			}
			edgeMap = newMap;
			
			List<Integer> destinationEdges = vertexMap.get(destination);
			List<Integer> sourceEdges = vertexMap.get(source);
			Iterator<Integer> it = sourceEdges.iterator();
			while(it.hasNext())
			{
				Integer edgeToRemove = it.next();
				if(edgeToRemove.equals(randomNumber))
				{
					it.remove();
					break;
				}
			}
			
			for(int i=0;i<destinationEdges.size();i++)
			{
				Integer edge = destinationEdges.get(i);
				if(sourceEdges.contains(edge))
				{
					int index = sourceEdges.indexOf(edge);
					sourceEdges.remove(index);
				}
				else
				{
					if(!(edge.equals(randomNumber)))
					    sourceEdges.add(edge);
				}
			}
			indexToBeRemoved = vertexList.indexOf(destination);
			vertexList.remove(indexToBeRemoved);
			
			vertexMap.remove(destination);
			
		}
		return true;
	}
	
	public int getNumVertices()
	{
		return this.vertexList.size();
	}
	
	public int getNumEdges() {
		return this.edgeList.size();
	}
	
	public int getMinimumCut() {
		for(Map.Entry<Integer, List<Integer>> entry : vertexMap.entrySet())
		{
			List<Integer> tList = entry.getValue();
			return tList.size();
		}
		return 0;
	}
	
	public void clear()
	{
		this.vertexList.clear();
		this.vertexMap.clear();
		this.edgeList.clear();
		this.edgeMap.clear();
	}
	
	public void printIt() {
		for(Integer i: vertexList)
			System.out.println("Vertex "+i);
		for(Integer i : edgeList)
			System.out.println("Edge "+i);
		for(Map.Entry<Integer, List<Integer>> entry : vertexMap.entrySet())
		{
			System.out.println("vertex List "+entry.getKey());
			List<Integer> edges = entry.getValue();
			for(Integer j : edges)
				System.out.println("Edge "+j);
			System.out.println("--------------");
		}
		for(Map.Entry<Integer, List<Integer>> entry : edgeMap.entrySet())
		{
			System.out.println("Edge List "+entry.getKey());
			List<Integer> edges = entry.getValue();
			for(Integer j : edges)
				System.out.println("Vertex "+j);
			System.out.println("--------------");
		}
	}
	
	public int getEdgeId()
	{
		return this.edgeId;
	}

}
