package NonDataStructure;

/**
 * Code to Convert ASCII to an Integer
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Math;

public class atoi {
	
	/**
	 * The main function keeps reading an input until the user hits exit
	 * It calls a helper function defined in atoi class that converts the given string into Integer
	 * If the given string is not in a proper format, it displays an appropriate message
	 * @param args
	 */
	public static void main(String args[])
	{
		String input ="";
		while(true)
		{
			try 
			{
				System.out.println("Enter a character in ASCII. Type exit to quit");
			    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			    input = bufferRead.readLine();
				if(input.equalsIgnoreCase("exit"))
					break;
		        atoi at = new atoi();
			    at.convertASCII(input);
			} 
			catch(NumberFormatException ne)
			{
				System.out.println(ne.getMessage());
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("Terminating ...");
	}
	
	/**
	 * Helper function to convert String into an Integer. 
	 * Throws a Numberformat Exception if any character in the input string cannot be converted to a number
	 * @param asciiString
	 */
	private void convertASCII(String asciiString)
	{
		int startindex = 0;
		int number=0;
		boolean isNegative = false;
		if(asciiString.charAt(0) == '-')
		{
			isNegative=true;
		    startindex = 1;
		}
		for(int i=startindex;i<asciiString.length();i++)
		{
			int charValue = asciiString.charAt(i) - '0';
			if(charValue < 0 || charValue > 9)
				throw new NumberFormatException("This is not an Integer ");
			number = number*10 + charValue;
		}
		if(isNegative)
			number = number * -1;
		
		System.out.println("The Numeris value of String is "+number);
	}

}
