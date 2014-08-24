package Algorithms2;

public class clusterInfo {
	
	private int Id;
	private boolean isAssigned;
	
	public clusterInfo(int Id,boolean isAssigned)
	{
		this.Id = Id;
		this.isAssigned = isAssigned;
	}
	
	public boolean getisAssigned()
	{
		return this.isAssigned;
	}
	
	public int getClusterId()
	{
		return this.Id;
	}
	
	public void setId(int Id)
	{
		this.Id = Id;
	}
	
	public void setIsAssigned(boolean isAssigned)
	{
		this.isAssigned = isAssigned;
	}

}
