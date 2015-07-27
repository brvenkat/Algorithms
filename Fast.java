import java.util.Arrays;

public class Fast {
	private Point[] pointArray;
	
	public static void main(String args[]) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Fast f = new Fast();
        f.pointArray = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();
            f.pointArray[i] = p;
        }
        f.drawLineSegments();
	}
	
	//Change the logic, the current point comes first
	private void drawLineSegments()
	{
		int arrayLength = pointArray.length;
		Point firstPoint = null;
		Point secondPoint = null;
		Arrays.sort(pointArray);
		if(arrayLength < 4)
			return;
		for(int i=0;i<arrayLength;i++)
		{
			Point[] tempPointArray = new Point[arrayLength];
			for(int m=0;m<arrayLength;m++)
				tempPointArray[m] = pointArray[m];
			Point currentPoint = pointArray[i];
			Arrays.sort(tempPointArray,pointArray[i].SLOPE_ORDER);
			int count =0;
			int j=0;
			int k=1;
			boolean isSmallestPoint = true;
			while(k<tempPointArray.length)
			{
				firstPoint = tempPointArray[j];
				secondPoint = tempPointArray[k];
				if(currentPoint.slopeTo(firstPoint) != currentPoint.slopeTo(secondPoint))
				{
					if(count >= 2 && isSmallestPoint)
					{						
						Arrays.sort(tempPointArray,j-count,j+1);
						currentPoint.drawTo(tempPointArray[j]);
						StdOut.print(currentPoint.toString());
						while(count >= 0)
						{
							StdOut.print("->");
							StdOut.print(tempPointArray[j-count].toString());
							--count;
						}
						StdOut.println();
					}
					count=0;
					isSmallestPoint=true;
				}
				else
				{
					if(currentPoint.compareTo(firstPoint) < 0 && currentPoint.compareTo(secondPoint) < 0)
					{
						//System.out.println("here??");
						++count;
					}
					else
						isSmallestPoint = false;
				}
				++j;
				++k;
			}
			if(count >= 2 && isSmallestPoint)
			{						
				Arrays.sort(tempPointArray,j-count,j+1);
				currentPoint.drawTo(tempPointArray[j]);
				StdOut.print(currentPoint.toString());
				while(count >= 0)
				{
					StdOut.print("->");
					StdOut.print(tempPointArray[j-count].toString());
					--count;
				}
				StdOut.println();
			}
		}
	}
}
