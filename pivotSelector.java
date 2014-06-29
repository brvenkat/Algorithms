package Algorithms;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class pivotSelector {
	
	private static pivotSelector ps = new pivotSelector();
	
	private pivotSelector() {
		
	}
	
	public static pivotSelector getInstance() {
		if(ps == null)
			ps = new pivotSelector();
		return ps;
	}
	
	public class pivotHolder {
		long number;
		int index;
		public pivotHolder(long num,int index) {
			this.number = num;
			this.index = index;
		}
	}
	
	public long chooseDefaultPivot(List<Long> arrayNumber,int left)
	{
		return arrayNumber.get(left);
	}
	
	public long chooseFinalElementAsPivot(List<Long> arrayNumber,int right)
	{
		return arrayNumber.get(right);
	}
	
	public pivotHolder chooseMedianPivot(List<Long> arrayNumber,int left,int right) {
		List<Long> medianList = new ArrayList<Long>();
	
		int size = (right-left+1);
		int middleElement=0;
		if(size%2==0)
			middleElement= (size/2)-1;
		else
			middleElement = size/2;
		middleElement = left+middleElement;
		//System.out.println("left Entry "+arrayNumber.get(left));
		//System.out.println("right Entry "+arrayNumber.get(right));
		//System.out.println("middle Entry "+arrayNumber.get(middleElement));
		medianList.add(arrayNumber.get(left));
		medianList.add(arrayNumber.get(right));
		medianList.add(arrayNumber.get(middleElement));
		Collections.sort(medianList);

		int tempIndex =0;
		
		if(medianList.get(1) == arrayNumber.get(left))
			tempIndex = left;
		else if(medianList.get(1) == arrayNumber.get(right))
			tempIndex = right;
		else
		    tempIndex = middleElement;
		pivotHolder ph = new pivotHolder(medianList.get(1),tempIndex);
		return ph;
	}

}
