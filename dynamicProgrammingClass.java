package Algorithms2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class dynamicProgrammingClass {
	long[][] knapSackArray;
	private static int KNAPSACK_SIZE;
	private static int KNAPSACK_NUMBER;
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

	
	public dynamicProgrammingClass()
	{
		knapSackArray = new long[KNAPSACK_SIZE+1][1];
	}
	
	public static void main(String args[])
	{
		BufferedReader br = null;
		int i=0;
		String sCurrentLine;
		dynamicProgrammingClass dp = null;
		try {
			long startTime = System.currentTimeMillis();
			br = new BufferedReader(new FileReader("C:\\JavaCode\\CodeExample\\src\\Algorithms2\\knapsack_big.txt"));
			while ((sCurrentLine = br.readLine()) != null) 
			{
				int j = KNAPSACK_SIZE;
				/**
				 * Here i is column
				 */
				if(i==0)
				{
					++i;
					String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
					KNAPSACK_NUMBER = Integer.parseInt(elements[1]);
					KNAPSACK_SIZE = Integer.parseInt(elements[0]);
					dp = new dynamicProgrammingClass();
					continue;
				}
				
				String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
				long elementValue = Long.parseLong(elements[0]);
				int elementWeight = Integer.parseInt(elements[1]);
				
				if(i==1)
				{
					while( j>= elementWeight)
					{
						dp.knapSackArray[j][0] = elementValue;
						--j;
					}
				}
				else
				{
					while( j>= elementWeight)
					{
						long firstValue = dp.knapSackArray[j][0];
						long secondValue = 0;
						if(j - elementWeight >= 0)
							secondValue+=dp.knapSackArray[j-elementWeight][0];
						secondValue+=elementValue;
						long valueInserted=(firstValue<secondValue?secondValue:firstValue);
						dp.knapSackArray[j][0] = valueInserted;
						--j;
					}
				}
				++i;
			}
			long endTime = System.currentTimeMillis();
            System.out.println("Time Elapsed "+(endTime-startTime)/1000);
			System.out.println("The maximum Value is "+dp.knapSackArray[KNAPSACK_SIZE][0]);
			
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

}
