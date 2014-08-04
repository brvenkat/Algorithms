package Algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Problem6P1 {
	public static void main(String args[]) {
		int ONE_MILLION = 1000000;
		//int ONE_MILLION = 100;
		Problem6P1 p6 = new Problem6P1();
		HashMap<Long,List<Long>> twoSumMap = new HashMap<Long,List<Long>>();
		BufferedReader br = null;
		long finalCounter = 0;
        List<Long> foundList = new ArrayList<Long>();
        Long[] arrayListNumbers;
        int counter=0;
		try {
			String sCurrentLine;
			arrayListNumbers = new Long[ONE_MILLION];
			br = new BufferedReader(new FileReader("C:\\JavaCode\\CodeExample\\src\\Algorithms\\algo1-programming_prob-2sum1.txt"));
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
			
			for(int i=0;i<ONE_MILLION;i++)
			{
				//System.out.println("Looping at i"+i);
				Long firstValue = arrayListNumbers[i];
				if(firstValue == null)
					break;
				Long targetSum = -10000L;
				while(targetSum <= 10000)
				{
				    if(foundList.contains(targetSum))
				    	++targetSum;
				    else
				    	break;
				}
				if(foundList.size() >= 20000)
					break;
				//System.out.println("HERE111");
				while(targetSum <=10000)
				{
					if(foundList.contains(targetSum))
					{
					    ++targetSum;
						continue;
					}
					Long secondValue = targetSum - firstValue;
					if(twoSumMap.containsKey(secondValue) && (!(firstValue.equals(secondValue))))
					{
						foundList.add(targetSum);
						//System.out.println("Target Value Added "+targetSum+" first Value "+firstValue+" second Value "+secondValue);
					}
					++targetSum;
					//System.out.println("HERE333 "+targetSum);
				} 
			}
			System.out.println("Completed 2");

			/*Long targetSum = -10000L;
			while(targetSum <= 10000 && (!foundList.contains(targetSum)))
			{
				Iterator<Map.Entry<Long,List<Long>>> twoSumIterator = twoSumMap.entrySet().iterator();
				while(twoSumIterator.hasNext())
				{
					Map.Entry<Long, List<Long>> it = twoSumIterator.next();
					Long secondValue = (targetSum - it.getKey());
					if(twoSumMap.containsKey(secondValue) && (secondValue != it.getKey()))
					{
						//System.out.println("Target Sum "+targetSum+" Value "+it.getKey()+" Second Value "+secondValue);
						foundList.add(targetSum);
						++finalCounter;
					}
				}
				++targetSum;
			} */
			
			System.out.println("The distinct sum is "+foundList.size());
			//p5.printIt();
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
