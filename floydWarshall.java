package Algorithms2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class floydWarshall {
	
	int[][][] shortestPathArray;
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
	private static int NUM_VERTICES;
	
	public floydWarshall()
	{
		this.shortestPathArray = new int[NUM_VERTICES+1][NUM_VERTICES+1][NUM_VERTICES+1];
		for(int i=1;i<NUM_VERTICES+1;i++)
		{
			for(int j=0;j<NUM_VERTICES+1;j++)
			{
				if(i==j)
					shortestPathArray[i][j][0] = 0;
				else
				    shortestPathArray[i][j][0] = Integer.MAX_VALUE;
			}
		}
	}
	
	public static void main(String args[])
	{
		BufferedReader br = null;
		String sCurrentLine;
		int i=0;
		floydWarshall fw = null;
        try
        {
			br = new BufferedReader(new FileReader("C:\\JavaCode\\CodeExample\\src\\Algorithms2\\g3.txt"));
			while ((sCurrentLine = br.readLine()) != null) 
			{
				if(i==0)
				{
					String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
					NUM_VERTICES = Integer.parseInt(elements[0]);
                    fw = new floydWarshall();
                    ++i;
                    continue;
				}
				String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
				int sourceVertex = Integer.parseInt(elements[0]);
				int destinationVertex = Integer.parseInt(elements[1]);
				int edgeCost = Integer.parseInt(elements[2]);
				fw.shortestPathArray[sourceVertex][destinationVertex][0] = edgeCost;
				
			}
			int minValue = fw.runFloydWarshall();
			boolean hasCycle = fw.detectCycle();
			if(hasCycle)
                System.out.println("Graph has a negative cycle");
			else
				System.out.println("The minimum value is "+minValue);

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
	
	private int runFloydWarshall()
	{
		for(int k=1;k<NUM_VERTICES+1;k++)
		{
			for(int i=1;i<NUM_VERTICES+1;i++)
			{
				for(int j=1;j<NUM_VERTICES+1;j++)
				{
					int caseOne = shortestPathArray[i][j][k-1];
					if(shortestPathArray[i][k][k-1] < Integer.MAX_VALUE && shortestPathArray[k][j][k-1] < Integer.MAX_VALUE)
					{
					  int caseTwo = shortestPathArray[i][k][k-1]+shortestPathArray[k][j][k-1];
					  shortestPathArray[i][j][k] = (caseOne < caseTwo?caseOne:caseTwo);
					}
					else
						shortestPathArray[i][j][k] = caseOne;
				}
			}
		}
		
		int minimumValue = findMinimum();
		return minimumValue;
	}

	private int findMinimum()
	{
		int minimum =Integer.MAX_VALUE;
		for(int i=1;i<NUM_VERTICES+1;i++)
		{
			for(int j=1;j<NUM_VERTICES+1;j++)
			{
				if(minimum > shortestPathArray[i][j][NUM_VERTICES] && i != j)
				{
					minimum = shortestPathArray[i][j][NUM_VERTICES];
					//System.out.println("I "+i+" J "+j+" NUM VER "+NUM_VERTICES);
				}
			}
		}
		return minimum;
	}
	
	private boolean detectCycle()
	{
		boolean hasCycle = false;
		for(int i=0;i<NUM_VERTICES+1;i++)
		{
			if(shortestPathArray[i][i][NUM_VERTICES] < 0)
			{
				hasCycle = true;
				break;
			}
		}
		return hasCycle;
	}
}
