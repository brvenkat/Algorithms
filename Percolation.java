
public class Percolation {

	   private int percolationArray [][];
	   private WeightedQuickUnionUF weightedUnion;
	   private int arraySize;
	   private int virtualTop;
	   private int virtualBottom;
	   //0 implies blocked
	   //1 implies open
	   //2 implies full
	  // Virtual top is a[0][0] and virtual bottom is a[0][N]
	   public Percolation(int N) throws Exception{
		   if(N<=0)
			   throw new IllegalArgumentException("N should be positive");
		   virtualTop = 0;
		   arraySize = N;
		   percolationArray = new int[N+1][N+1];
		   weightedUnion = new WeightedQuickUnionUF((N+1)*(N+1)+1);
		   virtualBottom = (N+1)*(N+1);
	   }
	   
	   public void open(int i, int j)  {
		   if (i ==0 || j ==0)
			   throw new IndexOutOfBoundsException("Please try to open valid indices");
		   if(i> arraySize || j > arraySize)
			   throw new IndexOutOfBoundsException("Please try to open valid indices");
		   percolationArray[i][j] = 1;
		   if(i==1)
		   {
			   int currentIndex = xyTo1D(i,j);
			   weightedUnion.union(0, currentIndex);
			   
		   }
		   if(i==arraySize)
		   {
			   int currentIndex = xyTo1D(i,j);
			   weightedUnion.union(virtualBottom, currentIndex);
			   
		   }
		   connectOpenNeighbours(i, j);		  
	   }
	   
	   public boolean isOpen(int i, int j)   {
		   if (i ==0 || j ==0)
			   throw new IndexOutOfBoundsException("Please try to open valid indices");
		   if(i> arraySize || j > arraySize)
			   throw new IndexOutOfBoundsException("Please try to open valid indices");
		   return i > 0 && j >0 && percolationArray[i][j] == 1;
	   }
	   
	   public boolean isFull(int i, int j)   {
		   if (i ==0 || j ==0)
			   throw new IndexOutOfBoundsException("Please try to open valid indices");
		   if(i> arraySize || j > arraySize)
			   throw new IndexOutOfBoundsException("Please try to open valid indices");
		   boolean isFull = false;
		   int currentIndex = xyTo1D(i,j);
		   if(weightedUnion.connected(currentIndex, virtualTop))
			   isFull = true;
		   return isFull;
	   }
	   
	   public boolean percolates()   {
		   if(weightedUnion.connected(virtualTop, virtualBottom)) {
			   return true;
		   }
		   return false;
	   }
	   
	   private void connectOpenNeighbours(int i,int j)
	   {
		   int currentIndex = xyTo1D(i,j);
		   int neighbourIndex;
		   if(isValidIndex(i+1, j) && isOpen(i+1, j))
		   {
			   neighbourIndex = xyTo1D(i+1, j);
			   if(!weightedUnion.connected(currentIndex, neighbourIndex))
				   weightedUnion.union(currentIndex, neighbourIndex);
		   }
		   if(isValidIndex(i-1, j) && isOpen(i-1, j))
		   {
			   neighbourIndex = xyTo1D(i-1, j);
			   if(!weightedUnion.connected(currentIndex, neighbourIndex))
				   weightedUnion.union(currentIndex, neighbourIndex);
		   }
		   if(isValidIndex(i, j+1)  && isOpen(i, j+1))
		   {
			   neighbourIndex = xyTo1D(i, j+1);
			   if(!weightedUnion.connected(currentIndex, neighbourIndex))
				   weightedUnion.union(currentIndex, neighbourIndex);
		   }
		   if(isValidIndex(i, j-1) && isOpen(i, j-1))
		   {
			   neighbourIndex = xyTo1D(i, j-1);
			   if(!weightedUnion.connected(currentIndex, neighbourIndex))
				   weightedUnion.union(currentIndex, neighbourIndex);
		   }
	   }

	   public static void main(String[] args) {
		   try {
		   Percolation p = new Percolation(2);
		   p.open(1, 1);
		   p.open(1, 2);
		   int firstIndex = p.xyTo1D(1, 1);
		   int secondIndex = p.xyTo1D(1, 2);
		   System.out.println("Is connected "+p.weightedUnion.connected(firstIndex, secondIndex));
		   } catch(Exception e)
		   {
			   
		   }
	   }
	   
	   private int xyTo1D(int i,int j) {
		   return (i+1)*arraySize + j;
	   }
	   
	   
	   private boolean isValidIndex(int i,int j)
	   {
		   return ((i>=1 && i <= arraySize) && ( j>=1 && j <= arraySize));
	   }

}
