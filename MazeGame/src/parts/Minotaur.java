package parts;

/*
This class is the Minotaur.
Its purposes is to find the closest route to the player and move to towards the player
Dijkstra's algorithm for finding shortest path
*/
public class Minotaur {
	private int coorRow;
	private int coorCol;
	
	public Minotaur(int coorX,  int coorY, int playerRow, int playerCol, Node[][] maze) {
		coorRow = coorX;
		coorCol = coorY;
		pathFinder(maze, playerRow, playerCol);
	}
		
	
	public void pathFinder(Node[][] maze, int playerRow, int playerCol) {
		QueuePath queue = new QueuePath();
		Node origin = maze[coorRow][coorCol];
		int counter = 0;
		//Find all paths until it reaches player's coordinates (coorX,coorY)
			do {
				if(maze[coorRow][coorCol].getUp() != null && maze[coorRow][coorCol].getUp().isVisited() == false) {
					maze[coorRow][coorCol].getUp().setCounter(++counter);	//based on distance from original point, counter increases
					maze[coorRow][coorCol].getUp().setVisited(true);		//set as visited
					queue.enqueue(maze[coorRow][coorCol].getUp());			//Add to stack
				}
				if(maze[coorRow][coorCol].getRight() != null && maze[coorRow][coorCol].getRight().isVisited() == false) {
					maze[coorRow][coorCol].getRight().setCounter(++counter);
					maze[coorRow][coorCol].getRight().setVisited(true);
					queue.enqueue(maze[coorRow][coorCol].getRight());
				}
				if(maze[coorRow][coorCol].getDown() != null && maze[coorRow][coorCol].getDown().isVisited() == false) {
					maze[coorRow][coorCol].getDown().setCounter(++counter);
					maze[coorRow][coorCol].getDown().setVisited(true);
					queue.enqueue(maze[coorRow][coorCol].getDown());
				}
				if(maze[coorRow][coorCol].getLeft() != null && maze[coorRow][coorCol].getLeft().isVisited() == false) {
					maze[coorRow][coorCol].getLeft().setCounter(++counter);
					maze[coorRow][coorCol].getLeft().setVisited(true);
					queue.enqueue(maze[coorRow][coorCol].getLeft());
				}
				System.out.println("-------------------------------------");
//				System.out.println(maze[coorRow][coorCol]);
//				System.out.println(maze[coorRow][coorCol].getX());
//				System.out.println(maze[coorRow][coorCol].getY());
//				System.out.println(maze[coorRow][coorCol].getUp());
				System.out.println("node: "+ maze[coorRow][coorCol] + "," 
						+ maze[coorRow][coorCol].getX() +" , " + maze[coorRow][coorCol].getY());
				System.out.println("up:" + maze[coorRow][coorCol].getUp());
				System.out.println("right:" + maze[coorRow][coorCol].getRight());
				System.out.println("down:" + maze[coorRow][coorCol].getDown());
				System.out.println("left:" + maze[coorRow][coorCol].getLeft());
				System.out.println("player: " + playerRow + " , " + playerCol);
				System.out.println();
				

				maze[coorRow][coorCol] = queue.dequeue(); 
				coorRow = maze[coorRow][coorCol].getX();
				coorCol = maze[coorRow][coorCol].getY();
				
			} while(!(coorCol == playerCol && coorRow == playerRow));
			
			
			
			
			
			
			
			//Selecting the correct path
//			maze[coorRow][coorCol] = maze[coorX][coorY];		//Start from 
			coorRow = playerRow;
			coorCol = playerCol;
			while(!(origin.getX()== coorRow && origin.getY() == coorCol)) {
				maze[coorRow][coorCol].setSelected(true);		//players position is the path ending
				counter--;
				if(maze[coorRow][coorCol].getUp() != null && maze[coorRow][coorCol].getUp().getCounter() == counter) {
					coorRow--;
				} else if (maze[coorRow][coorCol].getRight() != null && maze[coorRow][coorCol].getRight().getCounter() == counter) {
					coorCol++;
				} else if (maze[coorRow][coorCol].getDown() != null && maze[coorRow][coorCol].getDown().getCounter() == counter) {
					coorRow++;
				} else if (maze[coorRow][coorCol].getRight() != null && maze[coorRow][coorCol].getLeft().getCounter() == counter) {
					coorCol--;
				} else 
					System.exit(1);
				System.out.println("hehexd");
					maze[coorRow][coorCol].setSelected(true);	//select that node as the right path
			}
	}

	
}
