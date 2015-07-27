import java.util.TreeSet;


public class PointSET {
	
	   private TreeSet<Point2D> pointsTree;
	
	   public PointSET()    
	   {
		   pointsTree = new TreeSet<Point2D>();
	   }
	   
	   public boolean isEmpty()  
	   {
		   return (pointsTree.isEmpty());
	   }
	   
	   public int size()             
	   {
		   return pointsTree.size();
	   }
	   
	   public void insert(Point2D p)   
	   {
		   if(p==null)
			   throw new java.lang.NullPointerException();
		   pointsTree.add(p);
	   }
	   
	   public boolean contains(Point2D p) 
	   {
		   if(p==null)
			   throw new java.lang.NullPointerException();
		   if(p.getClass() != Point2D.class)
			   return false;
		   return pointsTree.contains(p);
	   }
	   
	   public void draw() 
	   {
		   StdDraw.clear();
		   for(Point2D p: pointsTree)
		   {
			   StdDraw.setPenColor(StdDraw.BLACK);
			   StdDraw.setPenRadius(.01);
			   StdDraw.point(p.x(), p.y());
		   }
		   
	   }
	   
	   public Iterable<Point2D> range(RectHV rect) 
	   {
		   if(rect==null)
			   throw new java.lang.NullPointerException();
		   LinkedQueue<Point2D> insidePoints = new LinkedQueue<Point2D>();
		   for(Point2D p: pointsTree)
		   {
			   if(rect.contains(p))
				   insidePoints.enqueue(p);
		   }
		   return insidePoints;
	   }
	   
	   public Point2D nearest(Point2D p) 
	   {
		   if(p==null)
			   throw new java.lang.NullPointerException();
		   Point2D minPoint = null;
		   if(pointsTree ==null)
			   return minPoint;
		   double minDistance = Double.MAX_VALUE;
		   for(Point2D currentPoint: pointsTree)
		   {
			   Double currentDistance=currentPoint.distanceSquaredTo(p);
			   if(currentDistance<minDistance)
			   {
				   minPoint = currentPoint;
				   minDistance = currentDistance;
			   }
		   }
		   return minPoint;
	   }

	   public static void main(String[] args) 
	   {
		   PointSET kt = new PointSET();
		   Point2D p = new Point2D (0.406360,0.678100);
		   Point2D q = new Point2D(0.740024,0.021714);
		   kt.insert(p);
		   kt.insert(q);
		   p = new Point2D(0.010189,0.742363);
		   kt.insert(p);
		   p = new Point2D(0.147733,0.203388);
		   kt.insert(p);
		   p = new Point2D(0.537098,0.436150);
		   kt.insert(p);
		   Point2D pq = new Point2D(0.37,0.23);
		  // kt.draw();
		   Point2D pqr = kt.nearest(pq);
		   System.out.println(" x coordinate "+pqr.x()+ " y "+pqr.y()); 
	   }

}
