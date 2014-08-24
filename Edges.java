package Algorithms2;

public class Edges implements Comparable<Edges>{
	
	int source;
	int destination;
	long weight;
	
	public Edges(int source,int destination,long weight)
	{
		this.source = source;
		this.destination = destination;
		this.weight = weight;
	}
	
	@Override
	public int compareTo(Edges that)
	{
		if(this.weight < that.weight)
			return -1;
		else
			return 1;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Edges)
		{
			Edges e = (Edges)o;
			return(e.source == this.source && e.destination == this.destination && e.weight == this.weight);
		}
		
		return false;
	}

}
