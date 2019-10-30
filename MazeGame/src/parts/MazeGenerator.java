package parts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//Generates the maze.
public class MazeGenerator {
	Node[][] maze;									//maze of nodes
	private int pointerX = 0;
	private int pointerY = 0;
	
	
	MazeGenerator(int sizeX, int sizeY) {			//Set a minimal limit eg.(25,13)	Should be done in "StartGame" class
		int x = sizeX+2;							//Creates sizeX+2 amount of rows 
		int y = sizeY+2;							//Creates sizeY+2 amount of columns
		maze = new Node[x][y];						//Sets size of maze
		createBoundaries(x,y);						//Creates Node boundaries of maze (walls of maze)
		createMaze(x,y);								//USES UPDATED SIZE.
		setAllBorders(x,y);
	}
	
public void createMaze(int x, int y) {
	pointerX = x/2;														//player starts half way on the left side
	pointerY = 1;
	List<Integer> array = new ArrayList<Integer>(Arrays.asList(0,1,2,3));
	Node current = new Node(pointerX,pointerY);								//First node
	maze[pointerX][pointerY] = current;
	Stack stack = new Stack(current);										//New Stack with the first new node
	

	B:	for(int i = 0; i < ((x-2)*(y-2));i++) {				//repeats based on the area of the square(width*height)
	
	Collections.shuffle(array);												//The direction it would go
		
	A: for(int j = 0; j < array.size(); j++) {							// if newNode has been reached or has gone thru array. 
				
		while(( maze[nextX(-1)][pointerY] != null && maze[pointerX][nextY(1)] != null &&
				maze[nextX(1)][pointerY] != null && maze[pointerX][nextY(-1)] != null)) {
	
			if(stack.checkDiffX() == -2) {
				break B;
			} else if(stack.checkDiffX() != 0) {
				pointerX = pointerX + stack.checkDiffX();	
			} else 	
				pointerY = pointerY + stack.checkDiffY();	
			
		current.setBorder();												// if current hits dead-end. set Node border and move back
		current = stack.pop(); 

		}

		switch(array.get(j)) {
		
		case 0:			
			if(maze[nextX(-1)][pointerY] == null && pointerX > 1) {									//If null, it means that pointer has never been there
				Node newNode = new Node(--pointerX,pointerY);				//Creates newNode with updated values
				current.setUp(newNode);										//LINK-UP
				newNode.setDown(current);									//LINK-DOWN
				stack.add(newNode);											//Add Node to stack
				maze[pointerX][pointerY] = newNode;						//Add Node to Maze
				current = newNode;											//the pointer: current be newNode. So iteration can start over
				break A;
			} 
			break;
			
		case 1: 
			if(maze[pointerX][nextY(1)] == null && pointerY < (y-2) ) {							    //RIGHT
				Node newNode = new Node(pointerX,++pointerY);
				current.setRight(newNode);									 
				newNode.setLeft(current);
				stack.add(newNode);	
				maze[pointerX][pointerY] = newNode;
				current = newNode;
				break A;
			}	
			break;
			
		case 2: 
			if(maze[nextX(1)][pointerY] == null && pointerX < (x-2)) {									 //DOWN
				Node newNode = new Node(++pointerX,pointerY);			
				current.setDown(newNode);
				newNode.setUp(current);
				stack.add(newNode);
				maze[pointerX][pointerY] = newNode;
				current = newNode;
				break A;
			}	
			break;
			
		case 3: 
			if(maze[pointerX][nextY(-1)] == null && pointerY > 1) {									  //LEFT
				Node newNode = new Node(pointerX,--pointerY);
				current.setLeft(newNode);													
				newNode.setRight(current);
				stack.add(newNode);
				maze[pointerX][pointerY] = newNode;
				current = newNode;
				break A;	
			} 
			break;
		}
		
	}
	}
}

public void setAllBorders(int x, int y) {									//original values are past
	for(int i = 1; i<x-1; i++) {												//Go thru each node and 
		for(int j = 1; j < y-1; j++) {										//finalize all borders.
			maze[i][j].setBorder();
		}
	}
}

public void createBoundaries(int x, int y) {
	//corner box borders
	maze[0][0] = new Node(x,y,(byte)0b00001111);	//top-left
	maze[0][y-1] = new Node(x,y,(byte)0b00001111);	//top-right
	maze[x-1][0] = new Node(x,y,(byte)0b00001111);	//bottom-left
	maze[x-1][y-1] = new Node(x,y,(byte)0b00001111);	//bottom-right

	//top-side and bottom-side
	for(int i = 0; i < x; i+=x-1) {
		for(int j = 1; j<y-1;j++) {
			maze[i][j] = new Node(i,j,(byte)0b00001010);
		}
	}
	//left-side and right-side
	for(int i = 1; i < x-1; i++) {
		for(int j = 0; j < y; j+=y-1) {
			maze[i][j] = new Node(i,j,(byte)0b00000101);
			}	
		}
	}
public int nextX(int num) {
	return pointerX + num;
}
public int nextY(int num) {
	return pointerY + num;
}

}

