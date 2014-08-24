package Algorithms2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class dynamicProgramming {
	
	long[][] knapSackArray;
	private static int KNAPSACK_SIZE;
	private static int KNAPSACK_NUMBER;
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

	public dynamicProgramming()
	{
	   knapSackArray = new long[KNAPSACK_SIZE+1][KNAPSACK_NUMBER+1];	
	   for(int i=0;i<KNAPSACK_SIZE;i++)
		   knapSackArray[i][0] = 0;
	}
	
	public static void main(String args[])
	{
		BufferedReader br = null;
		int i=0;
		String sCurrentLine;
		dynamicProgramming dp = null;
		try
		{
			br = new BufferedReader(new FileReader("C:\\JavaCode\\CodeExample\\src\\Algorithms2\\knapsack11.txt"));
			while ((sCurrentLine = br.readLine()) != null) 
			{
				/**
				 * Here i is column
				 */
				if(i==0)
				{
					++i;
					String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
					KNAPSACK_NUMBER = Integer.parseInt(elements[1]);
					KNAPSACK_SIZE = Integer.parseInt(elements[0]);
					dp = new dynamicProgramming();
					continue;
				}
				String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
				int elementValue = Integer.parseInt(elements[0]);
				int elementWeight = Integer.parseInt(elements[1]);
				/**
				 * j is the row here
				 */
                for(int j=0;j<KNAPSACK_SIZE+1;j++)
                {
                	if(j<elementWeight)
                	{
                		dp.knapSackArray[j][i] = dp.knapSackArray[j][i-1];
                	}
                	else
                	{
                		dp.knapSackArray[j][i] = ((dp.knapSackArray[j][i-1])> (dp.knapSackArray[j-elementWeight][i-1]+elementValue) ?(dp.knapSackArray[j][i-1]):(dp.knapSackArray[j-elementWeight][i-1]+elementValue));
                	}
                }
                ++i;
			}
			System.out.println("The optimal Value is "+dp.knapSackArray[KNAPSACK_SIZE][KNAPSACK_NUMBER]);

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
