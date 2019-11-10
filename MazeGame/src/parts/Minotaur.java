package parts;

/*
This class is the Minotaur.
Its purposes is to find the closest route to the player and move to towards the player
Dijkstra's algorithm for finding shortest path
*/
public class Minotaur {
	private int coorRow;
	private int coorCol;
	
	public Minotaur(int coorX,  int coorY) {
		setCoorRow(coorX);
		setCoorCol(coorY);
		
	}

	public int direction(int playerRow, int playerCol, Node[][] maze) {
/*	Algorithm:	
 * 
	- Mino always tries to converge into playerX and playerY. Priority: horizontal movement > vertical movement
	return value meanings:
		0: up
		1: right
		2: down
		3: left
*/		
		//Stack is to make sure it does go back in the same direction
		Stack stack = new Stack(maze[coorRow][coorCol]);
		
		if(coorCol < playerCol && maze[coorRow][coorCol].getRight() != null) { 					//go right
		
		}
		
		if(coorCol < playerCol && maze[coorRow][coorCol].getRight() != null) { 					//go right
			return 1;
		} else if (coorCol > playerCol && maze[coorRow][coorCol].getLeft() != null) { 			//go left
			return 3;
		} else if (coorCol == playerCol  && maze[coorRow][coorCol].getDown() != null) {
			if(coorRow < playerRow && maze[coorRow][coorCol].getLeft() != null) { 				//go down
				return 2;
			} else if(coorRow > playerRow) {													//go up
				return 0;
			} 
		}
		
		return 0;
		
	}
	
	
//getters and setters
	public int getX() {
		return coorRow;
	}
	public void setCoorRow(int coorRow) {
		this.coorRow = coorRow;
	}
	public int getY() {
		return coorCol;
	}
	public void setCoorCol(int coorCol) {
		this.coorCol = coorCol;
	}
	
}
