/**
 * Code to compute String permutation 
 **/
 
package com.paypal.risk.idi.persistence.processor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class permuteString {
	List<String> listPermutable;
	public static void main(String args[])
	{
		String line ="";
		permuteString pString = new permuteString();
		System.out.println("Enter the String you want to see permuted");
		try {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		line = br.readLine();
		} catch(Exception e)
		{
			System.out.println("Error "+e);
			return;
		}
		pString.computePermutation(line);
	}
	
	public void computePermutation(String toBePermuted)
	{
		listPermutable = new ArrayList<String>();
		HashMap<Character,Boolean> usedMap = new HashMap<Character,Boolean>();
		StringBuilder tempBuilder = new StringBuilder();
		permutationHelper(0, tempBuilder, usedMap, toBePermuted);
		printAllPermutations();
	}
	
	private void permutationHelper(int recursionLevel,StringBuilder str,HashMap<Character,Boolean> usedMap,String origString)
	{
		if(recursionLevel == origString.length())
		{
			listPermutable.add(str.toString());
			return;
		}
		
		for(int j=0;j<origString.length();j++)
		{
			if(usedMap.containsKey(origString.charAt(j)) && usedMap.get(origString.charAt(j)))
                continue;
			usedMap.put(origString.charAt(j), true);
			str.append(origString.charAt(j));
			permutationHelper(recursionLevel+1, str, usedMap, origString);
			usedMap.put(origString.charAt(j), false);
		}
		if(recursionLevel >= 1)
		   str.delete(recursionLevel-1, str.length());
		else
			str = new StringBuilder();
	}
	
	private void printAllPermutations()
	{
		System.out.println("The permutations are ");
		for(String str: listPermutable)
			System.out.println(str+",");
	}

}
