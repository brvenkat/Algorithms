package Algorithms;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Problem1 {
	public static void main(String[] args) {
		 
		BufferedReader br = null;
 
		List<Long> aList = new ArrayList<Long>();
		try {
 
			String sCurrentLine;
 
			br = new BufferedReader(new FileReader("C:\\JavaCode\\CodeExample\\bin\\Algorithms\\IntegerArray.txt"));
 
			while ((sCurrentLine = br.readLine()) != null) {
				aList.add(Long.parseLong(sCurrentLine));
			}
			Problem1 p1 = new Problem1();
			p1.countInversions(aList,aList.size());
 
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
	
	private class inversionHolder {
		long numInversions;
		List<Long> inversionList;
		public inversionHolder() {
			numInversions=0;
			inversionList = new ArrayList<Long>();
		}
		
		public inversionHolder(int n,List<Long> newList)
		{
			numInversions = n;
			inversionList = newList;
		}
	}
	
	private void countInversions(List<Long> aList,int n)
	{
	
		inversionHolder holder = inversionsHelper(aList,n);
		System.out.println("The number of inversions is "+holder.numInversions);
		
	}
	
	private inversionHolder inversionsHelper(List<Long> aList,int n)
	{
		if(n==1)
		{
			int total=0;
			List<Long> returnList = new ArrayList<Long>();
			returnList.add(aList.get(0));
			inversionHolder invHolder = new inversionHolder(total,returnList);
			return invHolder;
		}
		List<Long> aLeft = new ArrayList<Long>();
		List<Long> aRight = new ArrayList<Long>();
		if(n%2 ==0)
		{
			for(int i=0;i<n/2;i++)
				aLeft.add(aList.get(i));
			for(int i = n/2;i < n;i++)
				aRight.add(aList.get(i));
		}
		else
		{
			for(int i=0;i<(n+1)/2;i++)
				aLeft.add(aList.get(i));
			for(int i = (n+1)/2;i < n;i++)
				aRight.add(aList.get(i));			
		}
		inversionHolder invLeft = inversionsHelper(aLeft,aLeft.size());
		inversionHolder invRight = inversionsHelper(aRight,aRight.size());
		inversionHolder finalInversion = countSplitInversion(invLeft.inversionList,invRight.inversionList);	
		finalInversion.numInversions = finalInversion.numInversions + invLeft.numInversions+invRight.numInversions;
		return finalInversion;
		
	}
	
	private inversionHolder countSplitInversion(List<Long> aLeft,List<Long> aRight)
	{
		List<Long> aTotal = new ArrayList<Long>();
		int i=0;
		int j=0;
		int numCountInversions=0;
        while(i< aLeft.size() && j<aRight.size())
        {
            if(aLeft.get(i) < aRight.get(j))
            {
            	aTotal.add(aLeft.get(i));
            	++i;
            }
            else
            {
            	aTotal.add(aRight.get(j));
            	++j;
            	numCountInversions += aLeft.size()-i;
            }
        }
    	while(i < aLeft.size())
    	{
    	    aTotal.add(aLeft.get(i));
    	    ++i;
    	}
    	while(j < aRight.size())
    	{
    	    aTotal.add(aRight.get(j));
    	    ++j;
    	}        	
		inversionHolder finalInvHolder = new inversionHolder(numCountInversions,aTotal);
		return finalInvHolder;
	}
}
