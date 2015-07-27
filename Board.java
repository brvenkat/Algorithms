//To DO Update the manhattan distance once the new board is computed
public class Board {
	// construct a board from an N-by-N array of blocks (where blocks[i][j] = block in row i, column j)
	
	private int[][] initialBoard;
	private int gridSize;
	private int zeroLocationRow;
	private int zeroLocationColumn;
	private int manhattanDistance;
	public Board(int[][] blocks) 
	{
		gridSize = blocks[0].length;
		initialBoard = new int[gridSize][gridSize];
		manhattanDistance=0;
		for(int i=0;i<gridSize;i++)
		{
			for(int j=0;j<gridSize;j++)
			{
				initialBoard[i][j]=blocks[i][j];
				int numericValue = initialBoard[i][j];
				if(numericValue%gridSize == 0 && numericValue != 0)
				{
					if(i != (numericValue/gridSize-1) || j != gridSize-1)
						manhattanDistance+=Math.abs((gridSize-1)-j) + Math.abs((numericValue/gridSize)-i-1);
				}
				if(numericValue%gridSize != 0 && numericValue != 0)
				{
					if(i != ((numericValue/gridSize) ) || j != ((numericValue%gridSize)-1))
						manhattanDistance+=Math.abs((numericValue%gridSize-1)-j) + Math.abs((numericValue/gridSize)-i);
				}
				if(blocks[i][j] ==0)
				{
					zeroLocationRow=i;
					zeroLocationColumn=j;
				}
			}
		}
	}
	
	// board dimension N
	public int dimension()    
	{
		return gridSize;
	}
	
	// number of blocks out of place
	public int hamming() 
	{
		int hammingDistance=0;
		for(int i=0;i<gridSize;i++)
		{
			for(int j=0;j<gridSize;j++)
			{
				int numericValue = initialBoard[i][j];
				if(numericValue == 0)
					continue;
				if(numericValue%gridSize == 0)
				{
					if(i != (numericValue/gridSize-1) || j != gridSize-1)
						++hammingDistance;
				}
				else
				{
					if(i != (numericValue/gridSize) || j != ((numericValue%gridSize)-1))
						++hammingDistance;
				}
			}
		}
		return hammingDistance;
	}
	
	// sum of Manhattan distances between blocks and goal
	public int manhattan() 
	{
		return manhattanDistance;
	}
	
	// is this board the goal board?
	public boolean isGoal()       
	{
		return(this.hamming()==0);
	}
	
	// a board that is obtained by exchanging two adjacent blocks in the same row
	public Board twin()   
	{
		Board newBoard = new Board(this.initialBoard);
		int[][] tempBoard = newBoard.initialBoard;
		if(zeroLocationRow ==0)
		{
			int swap = tempBoard[zeroLocationRow+1][0];
			tempBoard[zeroLocationRow+1][0] = tempBoard[zeroLocationRow+1][1];
			tempBoard[zeroLocationRow+1][1] = swap;
		}
		else
		{
			int swap = tempBoard[zeroLocationRow-1][0];
			tempBoard[zeroLocationRow-1][0] = tempBoard[zeroLocationRow-1][1];
			tempBoard[zeroLocationRow-1][1] = swap;
		}
		return newBoard;
	}
	
	// does this board equal y?
	public boolean equals(Object y)     
	{
		if(y==this) return true;
		if(y==null) return false;
		if(y.getClass() != this.getClass()) return false;
		Board that = (Board)y;
		if(that.gridSize != this.gridSize)
			return false;
		for(int i=0;i<this.gridSize;i++)
		{
			for(int j=0;j<this.gridSize;j++)
			{
				if(this.initialBoard[i][j] != that.initialBoard[i][j])
					return false;
			}
		}
		return true;
	}
	
	// all neighboring boards
	public Iterable<Board> neighbors()  
	{
		LinkedQueue<Board> neighbourBoards = new LinkedQueue<Board>();
		if(isValidIndex(zeroLocationRow,zeroLocationColumn-1))
		{
			neighbourBoards.enqueue(createNeighbourBoard(zeroLocationRow, zeroLocationColumn-1));
			
		}
		if(isValidIndex(zeroLocationRow,zeroLocationColumn+1))
		{
			neighbourBoards.enqueue(createNeighbourBoard(zeroLocationRow, zeroLocationColumn+1));
			
		}
		if(isValidIndex(zeroLocationRow-1,zeroLocationColumn))
		{
			neighbourBoards.enqueue(createNeighbourBoard(zeroLocationRow-1, zeroLocationColumn));
			
		}
		if(isValidIndex(zeroLocationRow+1,zeroLocationColumn))
		{
			neighbourBoards.enqueue(createNeighbourBoard(zeroLocationRow+1, zeroLocationColumn));
			
		}
		return neighbourBoards;
	}
	
	private boolean isValidIndex(int row,int column)
	{
		return (row <= gridSize-1 && column <=gridSize-1 && row >=0 && column >=0);
	}
	
	private Board createNeighbourBoard(int row,int column)
	{
		Board newBoard = new Board(this.initialBoard);
		int[][] tempBoard = newBoard.initialBoard;
		int swap = tempBoard[row][column];
		tempBoard[row][column] = 0;
		tempBoard[zeroLocationRow][zeroLocationColumn] = swap;
		//swap moves from row,column to zeroLocationRow,ZerolocationColumn , so adjust the manhattan distance
		if(swap%gridSize == 0)
		{
				newBoard.manhattanDistance-=Math.abs((gridSize-1)-column) + Math.abs((swap/gridSize)-row-1);
				newBoard.manhattanDistance+=Math.abs((gridSize-1)-zeroLocationColumn) + Math.abs((swap/gridSize)-zeroLocationRow-1);
		}
		if(swap%gridSize != 0)
		{
			newBoard.manhattanDistance-=Math.abs((swap%gridSize-1)-column) + Math.abs((swap/gridSize)-row);
			newBoard.manhattanDistance+=Math.abs((swap%gridSize-1)-zeroLocationColumn) + Math.abs((swap/gridSize)-zeroLocationRow);
		}
		newBoard.zeroLocationRow = row;
		newBoard.zeroLocationColumn = column;
		return newBoard;
	}
	
	// string representation of this board (in the output format specified below)
	public String toString()       
	{
	    StringBuilder s = new StringBuilder();
	    s.append(gridSize + "\n");
	    for (int i = 0; i < gridSize; i++) {
	        for (int j = 0; j < gridSize; j++) {
	            s.append(String.format("%2d ", initialBoard[i][j]));
	        }
	        s.append("\n");
	    }
	    return s.toString();
	}

	public static void main(String[] args) 
	{
		int[][] testArray = new int[2][2];
		testArray[0][0]=1;
		testArray[0][1]=2;
		testArray[1][0]=3;
		testArray[1][1]=0;
		Board b = new Board(testArray);
		System.out.println(b.manhattanDistance);
		
	}

}
