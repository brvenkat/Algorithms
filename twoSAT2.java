package Algorithms2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Pattern;

public class twoSAT2 {
	
	public static int NUM_VARIABLES;
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
    private HashMap<Integer,LinkedList<Integer>> vertexLists;
    private HashMap<Integer,LinkedList<Integer>> vertexLists2;
    private HashMap<Integer,LinkedList<Integer>> newVertexLists;
    HashMap<Integer,Integer> tempMap;
    TreeMap<Integer,Integer> tempMapTime;
    private HashMap<Integer,Boolean> visited;
    private HashMap<Integer,Boolean> visitedFinished;
    HashMap<Integer,List<Integer>> finalMap;
    TreeSet<Integer> vList;
    int numnodes =0;
    int timeFinished=1;
	
	public twoSAT2()
	{
		vertexLists = new HashMap<Integer,LinkedList<Integer>>();
		vertexLists2 = new HashMap<Integer,LinkedList<Integer>>();
		newVertexLists = new HashMap<Integer,LinkedList<Integer>>();

		visited = new HashMap<Integer,Boolean>();
		visitedFinished = new HashMap<Integer,Boolean>();

		tempMap = new HashMap<Integer,Integer>();
		tempMapTime = new TreeMap<Integer,Integer>();
		finalMap = new HashMap<Integer,List<Integer>>();
		vList = new TreeSet<Integer>();
		
	}
	
	public static void main(String args[])
	{
		BufferedReader br = null;
		int i=0;
		String sCurrentLine;
		twoSAT2 ts = null;
		try 
		{
			br = new BufferedReader(new FileReader("C:\\JavaCode\\CodeExample\\src\\Algorithms2\\2sat6.txt"));
			while ((sCurrentLine = br.readLine()) != null) 
			{
				if(i==0)
				{
					String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
					NUM_VARIABLES = Integer.parseInt(elements[0]);
					ts = new twoSAT2();
					++i;
					continue;
				}
				String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
                Integer source = Integer.parseInt(elements[0]);
                Integer destination = Integer.parseInt(elements[1]);
                ts.vList.add(source);
                ts.vList.add(-1*source);
                ts.vList.add(destination);
                ts.vList.add(-1*destination);
                ts.addEdge((-1*source), destination);
                ts.addEdge((-1*destination), source);
                ts.visited.put(source, false);
                ts.visited.put(destination, false);
                ts.visited.put((-1*source), false);
                ts.visited.put((-1*destination), false);
			}
			ts.computeSCC();
			//ts.printMap();
			Boolean b = ts.checkSatisfiability();
			if(!b)
				System.out.println("0");
			else
				System.out.println("1");
			
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
	
	private void computeSCC() throws Exception
	{
		DFSLoopOne();
		for(Integer i : vList)
		{
			LinkedList<Integer> aList = vertexLists2.get(i);
			LinkedList<Integer> nList = new LinkedList<Integer>();
			if(!(aList==null))
			{
				for(int j=0;j<aList.size();j++)
				{
					nList.add(tempMap.get(aList.get(j)));
				}
			}
			newVertexLists.put(tempMap.get(i), nList);
		}
		//printMap();
		DFSLoopTwo();
		System.out.println("---4----");
		
	}
	
	private void addEdge(Integer source,Integer destination) throws Exception
	{
		LinkedList<Integer> dList = vertexLists.get(destination);
		if(dList == null)
			dList = new LinkedList<Integer>();
		dList.add(source);
		vertexLists.put(destination, dList);
		
		LinkedList<Integer> sList = vertexLists2.get(source);
		if(sList == null)
			sList = new LinkedList<Integer>();
		sList.add(destination);
		vertexLists2.put(source, sList);
		
		
	}
   
   private void DFSLoopOne() throws Exception
   {
	   TreeSet<Integer> treereverse = new TreeSet<Integer>();
	   treereverse=(TreeSet)vList.descendingSet();
	   for(Integer i : treereverse)
	   {
		   if(!visited.get(i))
			   DFSPassOne(i);
	   }
   }
   
   private void DFSLoopTwo()
   {
	   NavigableMap<Integer,Integer> nmap=tempMapTime.descendingMap();
	   Iterator<Map.Entry<Integer, Integer>> iter = nmap.entrySet().iterator();
	   //printMap();
	   while(iter.hasNext())
	   {
		   Map.Entry<Integer, Integer> pair = iter.next();
		   Integer i = pair.getKey();
		   if(!visitedFinished.get(i))
		   {
			   List<Integer> tList = new ArrayList<Integer>();
			   DFSPassTwo(i,tList);
		       List<Integer> tempVertexList = new ArrayList<Integer>();
		       if(!(tList==null))
		       {
			       for(Integer ii: tList)
			       {
			    	  // System.out.println("IIEXCEP "+ii);
				      tempVertexList.add(tempMapTime.get(ii));
			       }
		       }
		       finalMap.put(pair.getValue(), tempVertexList);
		   }
	   }
   }
   
   private void DFSPassOne(Integer source) throws Exception
   {
	   visited.put(source, true);
	   LinkedList<Integer> ll = vertexLists.get(source);
       if(!(ll==null))
       {
		   for(int i=0;i<ll.size();i++)
		   {
			   if(!visited.get(ll.get(i)))
				   DFSPassOne(ll.get(i));
		   }
       }
       //System.out.println("PUTTING "+source+" TIMEFINISGED "+timeFinished);
	   tempMap.put(source, timeFinished);
	   tempMapTime.put(timeFinished, source);
	   visitedFinished.put(timeFinished, false);
	   ++timeFinished;
   }
   
   private void DFSPassTwo(Integer source,List<Integer> fList)
   {
	  //System.out.println("SOURCE2IS "+source);
	   if(!fList.contains(source))
	      fList.add(source);
	   visitedFinished.put(source, true);
	   //System.out.println("SOURCEIS "+source);
	   LinkedList<Integer> ll = newVertexLists.get(source);
	   if(!(ll==null))
	   {
		   for(int i=0;i<ll.size();i++)
		   {
			   if(!visitedFinished.get(ll.get(i)))
				   DFSPassTwo(ll.get(i),fList);
		   }
	   }
   }
   
   /**
    * Loop through the final Map and generate output
    * @return
    */
   private boolean checkSatisfiability()
   {
	  boolean isSatisfied = true;
	  for(Map.Entry<Integer, List<Integer>> fm : finalMap.entrySet())
	  {
		  List<Integer> tList = fm.getValue();
		  Integer source = fm.getKey();
		  for(Integer compliment: tList)
		  {
			  if(compliment == (-1*source))
				  return false;
		  }
	  }
	  return isSatisfied;
   }
   
   private void printIt()
   {
	   Iterator<Map.Entry<Integer, LinkedList<Integer>>> iter = vertexLists2.entrySet().iterator();
	   while(iter.hasNext())
	   {
		   Map.Entry<Integer, LinkedList<Integer>> pair = iter.next();
		   System.out.println("Vertex "+pair.getKey());
		   for(Integer iii : pair.getValue())
			   System.out.println("vertices "+iii+" , ");
		   System.out.println("======");
	   }

   }
   private void printMap()
   {
	   Iterator<Map.Entry<Integer, Boolean>> testIt = visitedFinished.entrySet().iterator();
	   while(testIt.hasNext())
	   {
		   Map.Entry<Integer, Boolean> mt = testIt.next();
		   System.out.println("VERTEX "+mt.getKey()+"-->"+mt.getValue());
	   }
   }
   
}
