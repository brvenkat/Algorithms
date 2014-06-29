package Algorithms;

import java.util.HashMap;
import java.util.List;

public class twoSumClass {
	
	private static twoSumClass sumClass = new twoSumClass();
	private twoSumClass() {
		
	}
	
	public static twoSumClass getInstance()
	{
		return sumClass;
	}
	
	public static final int ONE_MILLION = 1000000;
	//public static final int ONE_MILLION = 100000;
	
	public void detectTarget(Long[] arrayListNumbers,Long targetSum,List<Long> foundList,HashMap<Long,List<Long>> twoSumMap)
	{
		if(foundList.contains(targetSum))
			return;
		for(int i=0;i<ONE_MILLION;i++)
		{
			if(arrayListNumbers[i] == null)
				return;
			Long firstValue = arrayListNumbers[i];
			Long secondValue = targetSum - firstValue;
			if(twoSumMap.containsKey(secondValue) && (!secondValue.equals(firstValue)))
			{		
				if(!foundList.contains(targetSum))
				   foundList.add(targetSum);
			}
		}
	  return;
	}

}
