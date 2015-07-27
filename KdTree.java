
public class KdTree {
	
	   private Node root;
	 
	   private class Node 
	   {
		   private Point2D p;
		   private Node lb;  
		   private Node rt; 
		   private int N;
		   private double xcoordinate;
		   private double ycoordinate;
		   private double xmin;
		   private double ymin;
		   private double xmax;
		   private double ymax;
		   
		   public Node(Point2D p,Node left,Node right,int N,double xcoordinate,double ycoordinate,double xmin,double ymin,double xmax,double ymax)
		   {
			   this.p = p;
			   this.lb = left;
			   this.rt = right;
			   this.N = N;
			   this.xcoordinate = xcoordinate;
			   this.ycoordinate = ycoordinate;
			   this.xmin = xmin;
			   this.xmax = xmax;
			   this.ymin = ymin;
			   this.ymax = ymax;
		   }
	   }
	   
	   // construct an empty set of points 
	   public KdTree()      
	   {
		     
	   }
	   
	   // is the set empty? 
	   public boolean isEmpty()   
	   {
		   return (size()==0);
	   }
	   
	   // number of points in the set 
	   public  int size()  
	   {
		   return size(root);
	   }
	   
	   private int size(Node x)
	   {
		   if(x==null) return 0;
		   return x.N;
	   }
	   
	   // add the point to the set (if it is not already in the set)
	   public  void insert(Point2D p)
	   {
		   if(p==null)
			   throw new java.lang.NullPointerException();
		   root = insert(root,p,0,0,1,1, true);
	   }
	   
	   private Node insert(Node currentNode,Point2D p,double xmin,double ymin,double xmax,double ymax,boolean isVertical)
	   {
		  if(currentNode==null)
			  return new Node(p,null,null,1,p.x(),p.y(),xmin,ymin,xmax,ymax);
		  if(p.equals(currentNode.p))
			  return currentNode;
		  double xcoord = currentNode.xcoordinate;
		  double ycoord = currentNode.ycoordinate;
		  if(isVertical)
		  {
			  if(xcoord > p.x())
				  currentNode.lb = insert(currentNode.lb,p,xmin, ymin, xcoord, ymax,false) ;
			  else
				  currentNode.rt = insert(currentNode.rt,p,xcoord, ymin, xmax,ymax,false) ;  
		  }
		  else
		  {
			  if(ycoord > p.y())
				  currentNode.lb = insert(currentNode.lb,p,xmin, ymin, xmax, ycoord,true) ;
			  else
				  currentNode.rt = insert(currentNode.rt,p,xmin, ycoord, xmax, ymax,true) ; 
		  }
		  currentNode.N = 1+size(currentNode.lb)+size(currentNode.rt);
		  return currentNode;
	   }
	   
	   // does the set contain point p? 
	   public boolean contains(Point2D p)     
	   {
		   if(p==null)
			   throw new java.lang.NullPointerException();
		   if(root==null)
			   return false;
		   return contains(root,p,true);
	   }
	   
	   private boolean contains(Node currentNode,Point2D p,boolean isVertical)
	   {
		   if(currentNode == null)
			   return false;
		   if(currentNode.p.equals(p))
			   return true;
		   if(isVertical)
		   {
				  if(currentNode.xcoordinate > p.x())
					  return contains(currentNode.lb,p,!isVertical) ;
				  else
					  return contains(currentNode.rt,p,!isVertical) ;
		   }
		   else
		   {
				  if(currentNode.ycoordinate > p.y())
					  return contains(currentNode.lb,p,!isVertical) ;
				  else
					  return contains(currentNode.rt,p,!isVertical) ;
		   }
	   }
	   
	   // draw all points to standard draw 
	   public void draw()       
	   {
		   draw(root,0,0,1,1,true);
	   }
	   
	   private void draw(Node n,double x0,double y0,double x1,double y1,boolean isVertical)
	   {
		   if(n==null)
			   return;
		   double xcoordinate = n.p.x();
		   double ycoordinate = n.p.y();
		   StdDraw.setPenColor(StdDraw.BLACK);
		   StdDraw.setPenRadius(.01);
		   StdDraw.point(xcoordinate, ycoordinate);
		   if(isVertical)
		   {
			   StdDraw.setPenColor(StdDraw.RED);
			   StdDraw.setPenRadius();
			  
			   StdDraw.line(xcoordinate, y0, xcoordinate, y1);
			  // System.out.println("X0 "+x0+" Y0 "+y0+ " X1 "+(x1-n.p.x())+ " Y1 "+y1 );
			   draw(n.lb,x0,y0,xcoordinate,y1,!isVertical);
			   draw(n.rt,xcoordinate,y0,x1,y1,!isVertical);
		   }
		   else
		   {
			   StdDraw.setPenColor(StdDraw.BLUE);
			   StdDraw.setPenRadius();
			  // System.out.println("X0 "+x0+" Y0 "+n.p.y()+ " X1 "+x1+ " Y1 "+n.p.y() );
			   StdDraw.line(x0, ycoordinate, x1, ycoordinate);
			   draw(n.lb,x0,y0,x1,ycoordinate,!isVertical);
			   draw(n.rt,x0,ycoordinate,x1,y1,!isVertical);
		   }
	   }
	   
	   // all points that are inside the rectangle 
	   public Iterable<Point2D> range(RectHV rect)     
	   {
		   if(rect==null)
			   throw new java.lang.NullPointerException();
		   LinkedQueue<Point2D> insidePoints = new LinkedQueue<Point2D>();
		   range(root,rect.xmin(),rect.ymin(),rect.xmax(),rect.ymax(),insidePoints,true);
		   return insidePoints;
	   }
	   
	   private void range(Node n,double xmin,double ymin,double xmax,double ymax,LinkedQueue<Point2D> pointsQueue,boolean isVertical)
	   {
		   if(n==null)
			   return;
		   RectHV rect = new RectHV(xmin,ymin,xmax,ymax);
		   if(rect.contains(n.p))
		   {
			   pointsQueue.enqueue(n.p);
		   }
		   
		   RectHV smallerRect;
		   
		   if(isVertical)
			   smallerRect = new RectHV(n.xmin, n.ymin, n.xcoordinate, n.ymax);
		   else
			   smallerRect = new RectHV(n.xmin, n.ymin, n.xmax, n.ycoordinate);
		   
		   if(smallerRect.intersects(rect))
		   {
			   range(n.lb,xmin,ymin,xmax,ymax,pointsQueue,!isVertical);
			   range(n.rt,xmin,ymin,xmax,ymax,pointsQueue,!isVertical);
		   }
		   else
		   {
			   if(isVertical)
			   {
				   if(xmax < n.xcoordinate)
					   range(n.lb,xmin,ymin,xmax,ymax,pointsQueue,!isVertical);
				   else
					   range(n.rt,xmin,ymin,xmax,ymax,pointsQueue,!isVertical); 
			   }
			   else
			   {
				   if(ymax < n.ycoordinate)
					   range(n.lb,xmin,ymin,xmax,ymax,pointsQueue,!isVertical);
				   else
					   range(n.rt,xmin,ymin,xmax,ymax,pointsQueue,!isVertical); 
			   }
		   } 
	   } 
	   
	   // a nearest neighbor in the set to point p; null if the set is empty 
	   public Point2D nearest(Point2D p)    
	   {
		   if(p==null)
			   throw new java.lang.NullPointerException();
		   if(root == null)
			   return null;
		   RectHV rect = new RectHV(0,0,1,1);
		   return nearest(root,p,root.p,rect,true);
	   }
	   
	   private Point2D nearest(Node n, Point2D p,Point2D closestPoint,RectHV rect, boolean isVertical)
	   {
		   if(n == null)
			   return closestPoint;
		   if(n.p.distanceSquaredTo(p) < closestPoint.distanceSquaredTo(p))
			   closestPoint = n.p;
		   RectHV leftSubtree = null;
		   RectHV rightSubtree = null;
		   if(isVertical)
		   {
			   leftSubtree = new RectHV(n.xmin, n.ymin, n.xcoordinate, n.ymax);
			   rightSubtree = new RectHV(n.xcoordinate, n.ymin, n.xmax,n.ymax);
		   }
		   else
		   {
			   leftSubtree = new RectHV(n.xmin, n.ymin, n.xmax, n.ycoordinate);
			   rightSubtree = new RectHV(n.xmin, n.ycoordinate, n.xmax, n.ymax);
		   }
		   if(leftSubtree.contains(p))
		   {
			   closestPoint = nearest(n.lb, p,closestPoint,leftSubtree, !isVertical);
			   if(closestPoint.distanceSquaredTo(p) > rightSubtree.distanceSquaredTo(p))
				   closestPoint = nearest(n.rt, p,closestPoint,rightSubtree, !isVertical);
		   }
		   else
		   {
			   closestPoint = nearest(n.rt, p,closestPoint,rightSubtree, !isVertical);
			   if(closestPoint.distanceSquaredTo(p) > leftSubtree.distanceSquaredTo(p))
				   closestPoint = nearest(n.lb, p,closestPoint,leftSubtree, !isVertical);
		   
		   }
		   return closestPoint;
	   }

	   // unit testing of the methods (optional) 
	   public static void main(String[] args)  
	   {
		   KdTree kt = new KdTree();
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
		  kt.draw();
		   Point2D pqr = kt.nearest(pq);
		   pqr.draw();
		   System.out.println(" x coordinate "+pqr.x()+ " y "+pqr.y()); 
	   }

}
