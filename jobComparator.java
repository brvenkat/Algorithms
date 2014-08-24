package Algorithms2;

import graphAlgorithms.distance;

public class jobComparator implements Comparable<jobComparator>,jobComparison{
	
	public Integer jobId;
	public Integer weight;
	public Integer completionTime;
	public Integer difference;
	
	public jobComparator(Integer id,Integer weight,Integer completionTime)
	{
		this.jobId = id;
		this.weight = weight;
		this.completionTime = completionTime;
		this.difference = weight - completionTime;
	}
	
	@Override
	public int compareTo(jobComparator that)
	{
		if(this.difference == that.difference)
	    {
			if(this.weight < that.weight)
			   return 1;
			else
				return -1;
	    }
		else if(this.difference < that.difference)
			return 1;
		return -1;
	}
	
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof jobComparator) {
			jobComparator j1 = (jobComparator)o;
			return (this.difference == j1.difference && this.weight == j1.weight);
		}
		return false;
	}
}
