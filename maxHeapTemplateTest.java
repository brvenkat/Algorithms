package Algorithms;

public class maxHeapTemplateTest {
	public static void main(String args[])
	{
	   maxHeapTemplate<Integer> lowHeap = new maxHeapTemplate<>();

	   lowHeap.insert(34);
	   lowHeap.insert(54);
	   lowHeap.insert(12);
	   
	   Integer k = lowHeap.extractMin();
	   System.out.println("Min Element "+k);
	   
	   lowHeap.insert(7);
	   
	   System.out.println("Min Element now "+lowHeap.extractMin());
	   
	   lowHeap.insert(776);
	   lowHeap.insert(654);
	   
	   System.out.println("Now min element "+lowHeap.extractMin());
	   
	   lowHeap.insert(12);
	   
	   System.out.println("Now min element "+lowHeap.extractMin());
	   lowHeap.insert(1);
	   lowHeap.insert(1);
	   lowHeap.insert(1);
	   
	   System.out.println("No Min Element "+lowHeap.extractMin());
	   System.out.println("No Now Min Element "+lowHeap.extractMin());
	   
	   System.out.println("New New min Element "+lowHeap.extractMin());
	}
}
