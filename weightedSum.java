package Algorithms2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class weightedSum {
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
	List<jobComparator> aList = new ArrayList<jobComparator>();
	List<jobComparator2> bList = new ArrayList<jobComparator2>();
	private int jobNumbers;
	
	public static void main(String[] args) {
		 
		BufferedReader br = null;
 
		try {
			weightedSum ws = new weightedSum();
			String sCurrentLine;
			int i=0;
 
			br = new BufferedReader(new FileReader("C:\\JavaCode\\CodeExample\\src\\Algorithms2\\jobs.txt"));
 
			while ((sCurrentLine = br.readLine()) != null) {
				if(i == 0)
					ws.jobNumbers = Integer.parseInt(sCurrentLine);
				else
				{
				   String[] jobString = WHITESPACE_PATTERN.split(sCurrentLine);
				   jobComparator j = new jobComparator(i, Integer.parseInt(jobString[0]), Integer.parseInt(jobString[1]));
				   jobComparator2 j2 = new jobComparator2(i, Integer.parseInt(jobString[0]), Integer.parseInt(jobString[1]));
				   ws.aList.add(j);
				   ws.bList.add(j2);
				}
				++i;
			}
            Collections.sort(ws.aList);
			long computedSum = ws.computeWeightedSum(ws.aList);
			System.out.println("The weighted computed Sum is "+computedSum);
			Collections.sort(ws.bList);
			long computedSum2 = ws.computeWeightedSum2(ws.bList);
			System.out.println("The weighted computed sum from second "+computedSum2);
 
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
 
	}
	
	private long computeWeightedSum(List<jobComparator> jobSchedule)
	{
		long sum = 0;
		long completionTime =0;
		for(jobComparator j : jobSchedule)
		{
			long timeTakenToComplete = (j.weight*(j.completionTime+completionTime));
			sum+=timeTakenToComplete;
			completionTime+=j.completionTime;
		}
		return sum;
	}
	
	private long computeWeightedSum2(List<jobComparator2> jobSchedule)
	{
		long sum = 0;
		long completionTime =0;
		for(jobComparator2 j : jobSchedule)
		{
			long timeTakenToComplete = (j.weight*(j.completionTime+completionTime));
			sum+=timeTakenToComplete;
			completionTime+=j.completionTime;
		}
		return sum;
	}

}
