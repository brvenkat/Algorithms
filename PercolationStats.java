
public class PercolationStats {
	
	private int experimentSize;
	private double[] numberOfSites;
	
   public PercolationStats(int N,int T) throws Exception
   {
	   if(T<=0)
		   throw new IllegalArgumentException("Pleaase enter positive values");
	   experimentSize = T;
	   numberOfSites = new double[T];
	   Percolation p;
	   for(int i=0;i<T;i++)
	   {
		   p = new Percolation(N);
		   int numberOfSitesOpened=0;
		   while(!p.percolates())
		   {
			   int randomRow = StdRandom.uniform(1, N+1);
			   int randomColumn = StdRandom.uniform(1, N+1);
			   while(p.isOpen(randomRow, randomColumn))
			   {
				   randomRow = StdRandom.uniform(1, N+1);
				   randomColumn = StdRandom.uniform(1, N+1);
			   }
			   p.open(randomRow, randomColumn);
			   ++numberOfSitesOpened;
		   }
		   numberOfSites[i] = ((double)(numberOfSitesOpened))/((double)(N*N));
	   }
   }
   
   public double mean()    
   {
	   double meanValue = StdStats.mean(numberOfSites);
	   return meanValue;
   }
   
   public double stddev()    
   {
	   if(experimentSize == 1)
		   return Double.NaN;
	   double deviationStandard = StdStats.var(numberOfSites);
	   return deviationStandard;
   }
   
   public double confidenceLo()
   {
	   double lowConfidence=0.0;
	   if(experimentSize > 1)
		   lowConfidence = (mean() - ((1.96*stddev())/Math.sqrt(experimentSize)));
	   return lowConfidence;
   }
   
   public double confidenceHi()   
   {
	  double highConfidence=0.0;
	  if(experimentSize > 1)
		  highConfidence = (mean() + ((1.96*stddev())/Math.sqrt(experimentSize)));
	  return highConfidence;
			  
   }

   public static void main(String[] args)  throws Exception
   {
	   int N = Integer.parseInt(args[0]);
	   int T = Integer.parseInt(args[1]);
	   Stopwatch sw = new Stopwatch();
	   PercolationStats ps = new PercolationStats(T,N);
	   System.out.println("Time elapsed is "+sw.elapsedTime());
	   System.out.println("Mean = "+ps.mean());
	   System.out.println("\n stddev = "+ps.stddev());
	   System.out.println("\n 95% confidence interval="+ps.confidenceLo()+","+ps.confidenceHi());
   }

}
