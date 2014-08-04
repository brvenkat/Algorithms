package Algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class Problem3New {
	private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");
	public static void main(String[] args) {
		BufferedReader br = null;
		GraphClass g ;
		try {
			 
			String sCurrentLine;
			int maximum = 999;
			for(int j=0;j<1000;j++)
			{
		   g = new GraphClass(201);
			br = new BufferedReader(new FileReader("C:\\JavaCode\\CodeExample\\src\\Algorithms\\kargerMinCut.txt"));
 
			while ((sCurrentLine = br.readLine()) != null) {
				String[] elements = WHITESPACE_PATTERN.split(sCurrentLine);
				for(int i=1;i<elements.length;i++)
				{
					Integer source = Integer.parseInt(elements[0]);
					Integer destination = Integer.parseInt(elements[i]);
					g.addEdge(source, destination);
				}
			}
			System.out.println("Number of Edges is "+g.getEdgeId());
			
			int numEdges = g.getNumEdges();
			//System.out.println("Num edges is "+numEdges);

			while(g.getNumVertices() > 2)
			{
			    int randomNumber = (int)(Math.random()*numEdges+1);
			    Integer aNumber = new Integer(randomNumber);
			   //System.out.println("Edge Chosen is "+randomNumber);
		    	//g.printIt();
			    while(!g.contractEdge(aNumber))
			    {
				    randomNumber = (int)(Math.random()*numEdges+1);
				    aNumber = new Integer(randomNumber);
			    }
			}
			if(maximum > g.getMinimumCut())
				maximum = g.getMinimumCut();
			g.clear();
			}
			System.out.println("The number of minimum cuts is "+maximum);
		} catch (IOException e) {
			e.printStackTrace();
		}  catch(Exception e) {
			System.out.println("Error "+e.getMessage());
		}
		finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
        }
	}


