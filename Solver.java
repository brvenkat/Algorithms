
public class Solver {
	
	// find a solution to the initial board (using the A* algorithm)
	private Node startingNode;
	private Node endingNode;
	
	private class Node implements Comparable<Node>
	{
		private Board currentBoard;
		private int numMoves;
		private Node previous;
		
		@Override
		public int compareTo(Node that)
		{
			if((this.numMoves+(this.currentBoard.manhattan())) < (that.numMoves+(that.currentBoard.manhattan())))
				return -1;
			if((this.numMoves+(this.currentBoard.manhattan())) > (that.numMoves+(that.currentBoard.manhattan())))
				return 1;
			return 0;
		}
	}
	
    public Solver(Board initial)           
    {
    	startingNode = new Node();
    	startingNode.currentBoard = initial;
    	startingNode.numMoves = 0;
    	startingNode.previous = null;
		endingNode = new Node();
    }
    
    // is the initial board solvable?
    public boolean isSolvable()            
    {
    	boolean foundSolution = false;
    	boolean foundSolutionTwin = false;
    	Board twinBoard = startingNode.currentBoard.twin();
    	Node twinNode = new Node();
    	twinNode.currentBoard = twinBoard;
    	twinNode.numMoves = 0;
    	twinNode.previous = null;
		MinPQ<Node> solverPriorityQueue = new MinPQ<Node>();
		MinPQ<Node> solverPriorityQueueTwin = new MinPQ<Node>();
		solverPriorityQueue.insert(startingNode);
		solverPriorityQueueTwin.insert(twinNode);
		int i=0;
    	while(!foundSolution || !foundSolutionTwin)
    	{
    		if(i%2==0)
    		{
    	    	if(!solverPriorityQueue.isEmpty())
    	    	{
    	    		if(handlePriorityQueue(solverPriorityQueue))
    	    		{
    	    			foundSolution = true;
    	    			break;
    	    		}
    	    	}
    		}
    		else
    		{
    	    	if(!solverPriorityQueueTwin.isEmpty())
    	    	{
    	    		if(handlePriorityQueue(solverPriorityQueueTwin))
    	    		{
    	    			foundSolutionTwin = true;
    	    			break;
    	    		}
    	    	}
    		}
    		++i;
    	}
    	if(foundSolutionTwin)
    		return false;
    	return true;
    }
    
    private boolean handlePriorityQueue(MinPQ<Node> pq)
    {
		Node currentNode = pq.delMin();
		if(currentNode.currentBoard.manhattan() ==0)
		{
			endingNode = currentNode;
			return true;
		}
		Board presentBoard = currentNode.currentBoard;
		for(Board b : presentBoard.neighbors())
		{
			if(currentNode.previous !=null && b.equals(currentNode.previous.currentBoard))
				continue;
			Node n = new Node();
			n.currentBoard = b;
			n.numMoves = currentNode.numMoves+1;
			n.previous = currentNode;
			pq.insert(n);
		}
		return false;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()                     
    {
       if(!isSolvable())
    	   return -1;
       return endingNode.numMoves;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()      
    {
    	if(!isSolvable())
    		return null;
    	Stack<Board> outputStack = new Stack<Board>();
    	Node currentNode = endingNode;
    	while(currentNode != null)
    	{
    		outputStack.push(currentNode.currentBoard);
    		currentNode = currentNode.previous;
    	}
    	return outputStack;
    	
    }
    
    public static void main(String[] args)
    {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board); 
        } 
    }

}
