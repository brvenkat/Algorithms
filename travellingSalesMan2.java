package Algorithms2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class travellingSalesMan2 {
	public static int NUM_VERTICES;
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
    HashMap<Integer,double[]> verticesMap;
    
    HashMap<String,double[]> mapOne;
    HashMap<String,double[]> mapTwo;
    
    HashMap<BitSet,double[]> mapOneBit;
    HashMap<BitSet,double[]> mapTwoBit;
    int[] binaryArray;
    

	public travellingSalesMan2()
	{
		verticesMap = new HashMap<Integer,double[]>();
			
		mapOneBit = new HashMap<BitSet,double[]>();
		mapTwoBit = new HashMap<BitSet,double[]>();
		
		binaryArray = new int[NUM_VERTICES+1];
	}
	
	public static void main(String args[])
	{
		BufferedReader br = null;
		int i=0;
		String sCurrentLine;
		travellingSalesMan2 ts = null;
		try 
		{
		br = new BufferedReader(new FileReader("C:\\JavaCode\\CodeExample\\src\\Algorithms2\\tsp.txt"));
		while ((sCurrentLine = br.readLine()) != null) 
		{
			if(i==0)
			{
				String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
				NUM_VERTICES = Integer.parseInt(elements[0]);
				ts = new travellingSalesMan2();
				++i;
				continue;
			}
			String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
            double[] tempArray = new double[2];
            tempArray[0] = Double.parseDouble(elements[0]);
            tempArray[1] = Double.parseDouble(elements[1]);
            ts.verticesMap.put(i, tempArray);
            ++i;
		}
		ts.init();
		ts.computePath();
		double minimum = ts.findMinimum();
		System.out.println("The min Value is "+minimum);
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			System.out.println("Exception "+e.getMessage());;
		}
		finally 
		{
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private double computeDistance(int i,int j)
	{
		double[] first = verticesMap.get(i);
		double[] second = verticesMap.get(j);
		double xvalue = (first[0]-second[0])*(first[0]-second[0]);
		double yvalue = (first[1]-second[1])*(first[1]-second[1]);
		double distance = Math.sqrt(xvalue+yvalue);
		return distance;
	}
	
	private void init()
	{
         double[] kValues = new double[NUM_VERTICES+1];
         for(int i=0;i<NUM_VERTICES+1;i++)
        	 kValues[i] = Double.MAX_VALUE;
         kValues[1] = 0;
         BitSet bs = new BitSet();
         bs.set(1);
         mapOneBit.put(bs, kValues);
	}
	
	private void computePath()
	{
		int m=2;
		generateSubsets gs = new generateSubsets();
		while(m<=NUM_VERTICES)
		{
			System.out.println("M IS WHAT? "+m);
			if(m > 2)
			{
				if(m%2==0)
				{
					mapTwoBit = new HashMap<BitSet,double[]>();
				}
				else
				{
					mapOneBit = new HashMap<BitSet,double[]>();
				}
			}
			List<BitSet> bsList = gs.generateSubset(m);
			for(BitSet bs: bsList)
			{
				BitSet bsTemp = new BitSet();
				bsTemp = (BitSet)bs.clone();
				for(int i=2;i<bsTemp.length();i++)
				{
					if(bsTemp.get(i))
					{
						bsTemp.flip(i);
						//System.out.println(bsTemp);
						computeBitAndPutValue(bs,bsTemp,m,i);
						bsTemp.flip(i);
					}
				}
			}
			//System.out.println("===============");
			if(m%2==0)
				mapOneBit = null;
			else
				mapTwoBit = null;
			++m;
		}
	}
	
	private void computeBitAndPutValue(BitSet origBs,BitSet bs,int m,int index)
	{
		double minValue = Double.MAX_VALUE;
		if(m%2==0)
		{
			for(int i=0;i<bs.length();i++)
			{
               if(bs.get(i))
               {
            	   Double existingPathValue = Double.MAX_VALUE;
            	   Double distance = Double.MAX_VALUE;
            	  // System.out.println("Trying to retrieve "+bs+" origBS "+origBs);
            	   if(mapOneBit.containsKey(bs))
            	   {
            		   double[] tempArray = mapOneBit.get(bs);
            		   existingPathValue = tempArray[i];
            		   distance = computeDistance(i, index);
            	   }
      			   if(minValue > existingPathValue + distance)
   					   minValue = existingPathValue+distance;
               }
			}
			if(mapTwoBit.containsKey(origBs))
			{
				double[] tempArray = mapTwoBit.get(origBs);
				tempArray[index] = minValue;
         	   //System.out.println("Putting to 2 "+origBs+" min "+minValue+" INDEX "+index);
				mapTwoBit.put(origBs, tempArray);
			}
			else
			{
		         double[] kValues = new double[NUM_VERTICES+1];
		         for(int i=0;i<NUM_VERTICES+1;i++)
		        	 kValues[i] = Double.MAX_VALUE;
		         kValues[index] = minValue;
	         	 //System.out.println("Putting to 2 "+origBs+" min "+minValue+" INDEX "+(index));
		         mapTwoBit.put(origBs, kValues);
			}
		}
		
		else
		{
			for(int i=0;i<bs.length();i++)
			{
               if(bs.get(i))
               {
            	   Double existingPathValue = Double.MAX_VALUE;
            	   Double distance = Double.MAX_VALUE;
            	  // System.out.println("Trying to retrieve "+bs);
            	   if(mapTwoBit.containsKey(bs))
            	   {
            		   double[] tempArray = mapTwoBit.get(bs);
            		   existingPathValue = tempArray[i];
            		   distance = computeDistance(i, index);
            	   }
      			   if(minValue > existingPathValue + distance)
   					   minValue = existingPathValue+distance;
               }
			}
			if(mapOneBit.containsKey(origBs))
			{
				double[] tempArray = mapOneBit.get(origBs);
				tempArray[index] = minValue;
         	   //System.out.println("Putting to 1 "+origBs+" min "+minValue+" INDEX "+(index+1));
				mapOneBit.put(origBs, tempArray);
			}
			else
			{
		         double[] kValues = new double[NUM_VERTICES+1];
		         for(int i=0;i<NUM_VERTICES+1;i++)
		        	 kValues[i] = Double.MAX_VALUE;
		         kValues[index] = minValue;
	         	   //System.out.println("Putting to 1 "+origBs+" min "+minValue+" INDEX "+(index));
		         mapOneBit.put(origBs, kValues);
			}
		}		
	}
	
	private double findMinimum()
	{
		double minValue = Double.MAX_VALUE;
		if(NUM_VERTICES%2==0)
		{
           for(Map.Entry<BitSet, double[]> pair: mapTwoBit.entrySet())
           {
                double[] theArray = pair.getValue();
                for(int i=2;i<=NUM_VERTICES;i++)
                {
                	double value = theArray[i];
                	double distance = computeDistance(i, 1);
                	//System.out.println("I1 is "+i+" value "+value+" distance "+distance);
 	        	    if(minValue > (value+distance))
 	        	       minValue = value+distance;
                }
           }
			
		}
		else
		{
	           for(Map.Entry<BitSet, double[]> pair: mapOneBit.entrySet())
	           {
	                double[] theArray = pair.getValue();
	                for(int i=2;i<=NUM_VERTICES;i++)
	                {
	                	double value = theArray[i];
	                	double distance = computeDistance(i, 1);
	                	//System.out.println("I2 is "+i+" value "+value+" distance "+distance);
	 	        	    if(minValue > (value+distance))
	 	        	       minValue = value+distance;
	                }
	           }			
		}
	  return minValue;
	}
	
	private void printIt(int m)
	{
		if(m%2==0)
		{
	           for(Map.Entry<String, double[]> pair: mapTwo.entrySet())
	           {
	        	   System.out.println("KEY "+pair.getKey());
	        	   for(int i=0;i<pair.getValue().length;i++)
	        	   {
	        		   System.out.println("VALUES "+pair.getValue()[i]);
	        	   }
	        	   System.out.println("--------------");
	           }
		}
		else
		{
	           for(Map.Entry<String, double[]> pair: mapOne.entrySet())
	           {
	        	   System.out.println("KEY "+pair.getKey());
	        	   for(int i=0;i<pair.getValue().length;i++)
	        	   {
	        		   System.out.println("VALUES "+pair.getValue()[i]);
	        	   }	  
	        	   System.out.println("--------------");
	           }
		}
	}
}
