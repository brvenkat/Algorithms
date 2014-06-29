package Algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Problem6P1New {
	public static void main(String args[]) {
		int ONE_MILLION = 1000000;
		//int ONE_MILLION = 100000;
		HashMap<Long,List<Long>> twoSumMap = new HashMap<Long,List<Long>>();
		BufferedReader br = null;
        List<Long> foundList = new ArrayList<Long>();
        Long[] arrayListNumbers;
        int counter=0;
		try {
			String sCurrentLine;
			arrayListNumbers = new Long[ONE_MILLION];
			Long startTime = System.currentTimeMillis();
			br = new BufferedReader(new FileReader("C:\\JavaCode\\CodeExample\\src\\Algorithms\\algo1-programming_prob-2sum.txt"));
			while ((sCurrentLine = br.readLine()) != null) 
			{
                  Long newEntry = Long.parseLong(sCurrentLine);
                  if(twoSumMap.containsKey(newEntry))
                     continue;
                  else
                  {
                	  List<Long> newList = new ArrayList<Long>();
                	  newList.add(newEntry);
                	  twoSumMap.put(newEntry, newList);
                	  arrayListNumbers[counter] = newEntry;
                	  ++counter;
                  }
			}
			System.out.println("Completed 1");

			twoSumClass twoClass = twoSumClass.getInstance();
			for(Long t = -10000L;t<=10000;t++)
			    twoClass.detectTarget(arrayListNumbers, t, foundList, twoSumMap);
			Long endTime = System.currentTimeMillis();
			System.out.println("The distinct sum is "+foundList.size());	
			System.out.println("Time Elapsed "+(endTime-startTime));
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
}
