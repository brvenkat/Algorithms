package Algorithms2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class twoSAT {
	
	public static int NUM_VARIABLES;
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
	public boolean[] variableArray;
	public Clause[] expressionArray;
	
	public twoSAT()
	{
		variableArray = new boolean[NUM_VARIABLES+1];
		expressionArray = new Clause[NUM_VARIABLES+1];
		for(int i=0;i<NUM_VARIABLES+1;i++)
		{
			if(i%3==0)
			  variableArray[i] = true;
			else
			  variableArray[i] = false;
		}
	}
	
	public static void main(String args[])
	{
		BufferedReader br = null;
		int i=0;
		String sCurrentLine;
		twoSAT ts = null;
		try 
		{
			br = new BufferedReader(new FileReader("C:\\JavaCode\\CodeExample\\src\\Algorithms2\\2sat3.txt"));
			while ((sCurrentLine = br.readLine()) != null) 
			{
				if(i==0)
				{
					String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
					NUM_VARIABLES = Integer.parseInt(elements[0]);
					ts = new twoSAT();
					++i;
					continue;
				}
				String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
                Integer firstValue = Integer.parseInt(elements[0]);
                Integer secondValue = Integer.parseInt(elements[1]);
                Clause c = new Clause(firstValue,secondValue);
                ts.expressionArray[i] = c;
                ++i;
			}
			System.out.println("DOne reading file");
            boolean b = ts.computeExpression();
            System.out.println("Satisfiability "+b);
			
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
	
	public boolean computeExpression()
	{
		boolean isSatisfiable = false;
		
		int j=0;
		int limit = (int)(Math.log(NUM_VARIABLES)/Math.log(2))+1;
		while(j<=limit)
		{
			int inner=0;
			while(inner<=2*NUM_VARIABLES*NUM_VARIABLES)
			{
				boolean found = true;
				int breakpoint=1;
				for(int i=1;i<NUM_VARIABLES+1;i++)
				{
					Clause c = expressionArray[i];
					int first = c.firstOne;
					int second = c.secondOne;
					if(first<0 && second > 0)
					{
						first = first*-1;
						if(!variableArray[first] || variableArray[second])
							continue;
						else
						{
							found = false;
							breakpoint = i;
							break;
						}
					}
					if(first>0 && second < 0)
					{
						second = second*-1;
						if(!variableArray[second] || variableArray[first])
							continue;
						else
						{
							found = false;
							breakpoint = i;
							break;
						}
					}
					if(first>0 && second > 0)
					{
						if(variableArray[second] || variableArray[first])
							continue;
						else
						{
							found = false;
							breakpoint = i;
							break;
						}
					}
					if(first<0 && second < 0)
					{
						first = first*-1;
						second=second*-1;
						if(!variableArray[second] || !variableArray[first])
							continue;
						else
						{
							found = false;
							breakpoint = i;
							break;
						}
					}
				}
				if(!found)
				{
					//flip one of the variable in boolean array
					Clause c = expressionArray[breakpoint];
					int flipper=0;
					if(inner%2==0)
						flipper = c.firstOne;
					else
						flipper = c.secondOne;
					if(flipper <0)
						flipper = flipper*-1;
					boolean b = variableArray[flipper];
					variableArray[flipper] = !b;
				}
				else
				{
				   isSatisfiable = true;
				   return isSatisfiable;
				}
				++inner;
			}
			++j;
		}
		
		return isSatisfiable;
	}

}
