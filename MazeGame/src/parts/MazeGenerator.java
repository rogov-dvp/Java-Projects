package parts;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;

//Generates the maze.
public class MazeGenerator {
	private static int[][] maze;
	private int rowSize;
	private int colSize;
		
	MazeGenerator(int rowSize, int colSize) {		
		new Stack(rowSize, colSize);				//used for capactiy
		this.rowSize = rowSize+2;		//extra one row and column at top and bottom
		this.colSize = colSize+2;
		maze = new int[rowSize][colSize];
		buildMaze();												
	}
	
	public void buildMaze() {
		
		for(int i=0; i < rowSize;i++) {
			for(int j=0; j < colSize;i++) {
				//Some kind of selector code
				int rand = (int)(Math.random()*2);
				maze[i][j] = rand;
				
				}
		}
		
	}
	public static int[][] getType() {
		return maze;
	}
	
	//make boundaries.
/*		for(int i = 0; i < rowSize; i++) {
			maze[i][0] = new Node(true);		//Creates a wall on the left side
			maze[i][colSize-2]=new Node(true);	//Creates a wall on the right side
		}
		for(int i = 0; i <colSize; i++) {
			maze[0][i] = new Node(true);		//Creates a wall on the top side
			maze[rowSize-1][i] = new Node(true);	//Creates a wall on the bottom side
		}
		create inside inside
		for(int i = 0; i < rowSize*colSize; i++) {
			newNode(rowStart,colStart);		//method to randomly generate a maze		
		}*/
		
		
		/*			
			
	}
	public void buildWalls() {
		
	}
	

	public void newNode(int row, int col) {
		boolean status = false;
		//used to go in a specific way. 0=left,1=up,2=right,3=down
		int dir = -1;
		
		maze[row][col] = new Node(true,row,col);			//creates new Node at the given place


//		//Check if has cornered itself. If true, go backwards using stack until it finds a place with null
//		while(maze[--row][col] != null || maze[++row][col] != null || maze[row][--col] != null || maze[row][--col] != null) {
//			Stack.pop(row,col);
//		}
		
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
	}*/
}

