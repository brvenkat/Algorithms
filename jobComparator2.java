package Algorithms2;

public class jobComparator2 implements Comparable<jobComparator2>,jobComparison{
	public Integer jobId;
	public Integer weight;
	public Integer completionTime;
	public float divisor;
	
	public jobComparator2(Integer id,Integer weight,Integer completionTime)
	{
		this.jobId = id;
		this.weight = weight;
		this.completionTime = completionTime;
		this.divisor = (float)weight/completionTime;
	}
	
	@Override
	public int compareTo(jobComparator2 that)
	{
		if(this.divisor == that.divisor)
	    {
			if(this.weight < that.weight)
			   return 1;
			else
				return -1;
	    }
		else if(this.divisor < that.divisor)
			return 1;
		return -1;
	}
	
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof jobComparator2) {
			jobComparator2 j1 = (jobComparator2)o;
			return (this.divisor == j1.divisor && this.weight == j1.weight);
		}
		return false;
	}
	
}
