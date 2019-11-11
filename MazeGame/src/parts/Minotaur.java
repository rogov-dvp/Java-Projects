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
		for(int i = 0; i <maze.length;i++) {
			for(int j = 0; j < maze[0].length;j++) {
				maze[i][j].setSelected(false);
				maze[i][j].setVisited(false);
			}
		}
		
		
		Node pointer = maze[coorRow][coorCol];
		pointer.setVisited(true);
		int originX = pointer.getX();
		int originY = pointer.getY();
		int counter = 0;
		//Find all paths until it reaches player's coordinates (coorX,coorY)
		System.out.println("player: (" + playerRow + "," + playerCol +")");
			do {
				counter = pointer.getCounter() + 1;
				System.out.println("counter: "+counter);

				if(maze[coorRow][coorCol].getUp() != null && maze[coorRow][coorCol].getUp().isVisited() == false) {
					maze[coorRow][coorCol].getUp().setCounter(counter);	//based on distance from original point, counter increases
					maze[coorRow][coorCol].getUp().setVisited(true);		//set as visited
					queue.enqueue(maze[coorRow][coorCol].getUp());			//Add to stack
				}
				if(maze[coorRow][coorCol].getRight() != null && maze[coorRow][coorCol].getRight().isVisited() == false) {
					maze[coorRow][coorCol].getRight().setCounter(counter);
					maze[coorRow][coorCol].getRight().setVisited(true);
					queue.enqueue(maze[coorRow][coorCol].getRight());
				}
				if(maze[coorRow][coorCol].getDown() != null && maze[coorRow][coorCol].getDown().isVisited() == false) {
					maze[coorRow][coorCol].getDown().setCounter(counter);
					maze[coorRow][coorCol].getDown().setVisited(true);
					queue.enqueue(maze[coorRow][coorCol].getDown());
				}
				if(maze[coorRow][coorCol].getLeft() != null && maze[coorRow][coorCol].getLeft().isVisited() == false) {
					maze[coorRow][coorCol].getLeft().setCounter(counter);
					maze[coorRow][coorCol].getLeft().setVisited(true);
					queue.enqueue(maze[coorRow][coorCol].getLeft());
				}
				
					pointer = queue.dequeue(); 
					coorRow = pointer.getX();
					coorCol = pointer.getY();
					System.out.println();
								
			} while(!(coorCol == playerCol && coorRow == playerRow));
			
			queue.makeEmpty();
			
			
			
			
			
			System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			//Selecting the correct path
//			maze[coorRow][coorCol] = maze[coorX][coorY];		//Start from 
			coorRow = playerRow;
			coorCol = playerCol;
			
			System.out.println("node: ("+ maze[coorRow][coorCol].getX() +"," + maze[coorRow][coorCol].getY() +"): " + maze[coorRow][coorCol]);
			if(maze[coorRow][coorCol].getUp() != null && maze[coorRow][coorCol].getUp().isVisited()) 
				System.out.println("up:" + maze[coorRow][coorCol].getUp().getCounter());
			else if(maze[coorRow][coorCol].getRight() != null && maze[coorRow][coorCol].getRight().isVisited())
				System.out.println("right:" + maze[coorRow][coorCol].getRight().getCounter());
			else if(maze[coorRow][coorCol].getDown() != null && maze[coorRow][coorCol].getDown().isVisited())
				System.out.println("down:" + maze[coorRow][coorCol].getDown().getCounter());
			else if(maze[coorRow][coorCol].getLeft() != null && maze[coorRow][coorCol].getLeft().isVisited())
				System.out.println("left:" + maze[coorRow][coorCol].getLeft().getCounter());
			
			
			counter--;	//to balance out
			System.out.println();
			System.out.println("counter: " + counter);
			while(!(originX == coorRow && originY == coorCol)) {
				maze[coorRow][coorCol].setSelected(true);		//players position is the path ending
				counter--;
				if((maze[coorRow][coorCol].getUp() != null && maze[coorRow][coorCol].getUp().isVisited())
						&& maze[coorRow][coorCol].getUp().getCounter() == counter) {
					maze[coorRow][coorCol].setSelected(true);
					coorRow--;
					System.out.println("Pathing Up");
				} else if ((maze[coorRow][coorCol].getRight() != null && maze[coorRow][coorCol].getRight().isVisited())
						&& maze[coorRow][coorCol].getRight().getCounter() == counter) {
					maze[coorRow][coorCol].setSelected(true);
					coorCol++;
					System.out.println("Pathing Right");
				} else if ((maze[coorRow][coorCol].getDown() != null && maze[coorRow][coorCol].getDown().isVisited())
						&& maze[coorRow][coorCol].getDown().getCounter() == counter) {
					maze[coorRow][coorCol].setSelected(true);
					coorRow++;
					System.out.println("Pathing Down");
				} else if ((maze[coorRow][coorCol].getLeft() != null && maze[coorRow][coorCol].getLeft().isVisited()) 
						&& maze[coorRow][coorCol].getLeft().getCounter() == counter) {
					maze[coorRow][coorCol].setSelected(true);
					coorCol--;
					System.out.println("Pathing Left");
				} else {
					GameStart.play = false;
					System.out.println("hehexd out");
					break;
				}
			}
	System.out.println("hehexd FINAL");
	}
	

	
}
