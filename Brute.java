
public class Brute {
	
	private Point[] pointArray;
	
	public static void main(String args[]) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Brute b = new Brute();
        b.pointArray = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();
            b.pointArray[i] = p;
        }
        b.drawLineSegments();
	}
	
	private void drawLineSegments()
	{
		int arrayLength = pointArray.length;
		for(int i=0;i<arrayLength;i++)
		{
			for(int j=0;j<arrayLength;j++)
			{
				for(int k=0;k<arrayLength;k++)
				{
					for(int l=0;l<arrayLength;l++)
					{
						Point p = pointArray[i];
						Point q = pointArray[j];
						Point r = pointArray[k];
						Point s = pointArray[l];
						if(p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(q) == p.slopeTo(s) && p.compareTo(q) < 0 && q.compareTo(r) < 0 && r.compareTo(s) < 0)
						{
							System.out.println(p.toString()+"->"+q.toString()+"->"+r.toString()+"->"+s.toString());
							p.drawTo(s);
						}
					}
				}
			}
		}
	}

}
