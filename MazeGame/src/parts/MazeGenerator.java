package parts;


//Generates the maze.
public class MazeGenerator {
	private Node[][] maze;
	//length and height of maze
	private int rowSize;
	private int colSize;
	//startPosition:
	private int row;	
	private int col;
	
	MazeGenerator(int rowSize, int colSize, int row, int col) {		
		new Stack(rowSize, colSize);									
		this.row = row;
		this.col = col;
		buildMaze();												
	}
	
	public void buildMaze() {
		for(int i = 0; i < rowSize*colSize; i++) {
			newNode(row,col);				
		}
	}
	
	public void newNode(int row, int col) {
		boolean status = false;
		int dir = -1;
		//Check if has cornered itself. If true, go backwards using stack until it finds a place with null
		while(maze[--row][col] != null || maze[++row][col] != null || maze[row][--col] != null || maze[row][++col] != null) {
			Stack.pop(row,col);
		}
		
		//randomly select which direction to go. then check if valid
		while(status) {
			dir = (int)Math.random()*4;
			switch(dir) {
			case 0:	//left
				if(maze[row][--col] == null) {
					--col;
				}	
				break;
			case 1://up
				if(maze[--row][col] == null) {
					--row;
				}	
				break;
			case 2://right
				if(maze[row][++col] == null) {
					++col;
				}	
				break;
			case 3://down
				if(maze[++row][col] == null) {
					++row;
				}	
				break;
			default:
					
			}
			
		}
		maze[row][col] = new Node(true,row,col);			//creates new Node at the given place
	}
}

