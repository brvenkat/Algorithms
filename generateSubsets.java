package Algorithms2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class generateSubsets {
	List<BitSet> lBit;
	private static final int NUM_VERTICES = 25;
	public static void main(String args[])
	{
		String line ="";
		generateSubsets gs = new generateSubsets();
		System.out.println("Enter the subset size you need");
		try {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		line = br.readLine();
		} catch(Exception e)
		{
			System.out.println("Error "+e);
			return;
		}
		gs.generateSubset(Integer.parseInt(line));
		//gs.printAllPermutations();
		//System.out.println("Subset size is "+gs.lBit.size());
	}
	
	public List<BitSet> generateSubset(int m)
	{
		lBit = new ArrayList<BitSet>();
        List<Integer> oneSubset = new ArrayList<Integer>();
        oneSubset.add(1);
        subsetHelper(1, m, 2, oneSubset);
        return lBit;
	}
	
	private void subsetHelper(int depth,int m,int j,List<Integer> oneSubset)
	{
		if(depth == m)
		{
			BitSet bs = new BitSet();
			for(Integer i:oneSubset)
			{		
				bs.set(i);
			}
			lBit.add(bs);
			return;
		}
		for(int i=j;i<=NUM_VERTICES;i++)
		{
			oneSubset.add(i);
			subsetHelper(depth+1, m, i+1, oneSubset);
			oneSubset.remove(oneSubset.size()-1);
		}
	}

	private void printAllPermutations()
	{
		System.out.println("The subsets are ");
        for(BitSet bs : lBit)
        {
        	System.out.println(bs);
        }
	}
}
