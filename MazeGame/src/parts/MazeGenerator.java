package parts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

//Generates the maze.
public class MazeGenerator {
	Node[][] maze;									//maze of nodes
	
	
	MazeGenerator(int sizeX, int sizeY) {			//Set a minimal limit eg.(5,5)	Should be done in "StartGame" class
		int x = sizeX+2;							//Creates sizeX+2 amount of rows 
		int y = sizeY+2;							//Creates sizeY+2 amount of columns
		maze = new Node[x][y];						//Creates 2D maze
		createBoundaries(x,y);						//Creates Node boundaries of maze (walls of maze)
		createMaze(sizeX,sizeY);					//USES GIVEN SIZE.
		setAllBorders(sizeX,sizeY);
	}
	
public void createMaze(int x, int y) {
	int pointerX = x/2;
	int pointerY = y;
	List<Integer> array = new ArrayList<Integer>(Arrays.asList(1,2,3,4));
	Node current = new Node(pointerX,pointerY);								//First node
	Stack stack = new Stack(current);										//New Stack, with the first new node
	
	for(int i = 0; i < (x*y);i++) {											//repeats based on the area of the square(width*height)
	// maybe use this later?
	while(current.getUp() == null || current.getRight() == null ||
			current.getDown() == null || current.getLeft() == null) {
		current.setBorder();												// if current hits dead-end. set Node border and move back
		current = stack.pop();												//go backwards and set that node as current
	} 		
	
	Collections.shuffle(array);												//The direction it would go
		
		A: for(int j = 0; j < array.size(); j++) {							// if newNode has been reached or has gone thru array. 
																			// It must go thru since we checked that condition above
		switch(array.get(j)) {
		case 0:																//UP
			if(current.getUp() == null) {									//If null, it means that pointer has never been there
				Node newNode = new Node(--pointerX,pointerY);				//Creates newNode with updated values
				current.setUp(newNode);										//LINK-UP
				newNode.setDown(current);									//LINK-DOWN
				stack.add(newNode);											//Add Node to stack
				current = newNode;											//the pointer: current be newNode. So iteration can start over
			} 
			break A;
		case 1: 
			if(current.getRight() == null) {							    //RIGHT
				Node newNode = new Node(pointerX,++pointerY);
				current.setRight(newNode);									 
				newNode.setLeft(current);
				stack.add(newNode);	
				current = newNode;

			}	
			break A;
			
		case 2: 
			if(current.getDown() == null) {									 //DOWN
				Node newNode = new Node(++pointerX,pointerY);			
				current.setDown(newNode);
				newNode.setUp(current);
				stack.add(newNode);
				current = newNode;

			}	
			break A;
			
		case 3: 
			if(current.getLeft() == null) {									  //LEFT
				Node newNode = new Node(pointerX,pointerY--);
				current.setLeft(newNode);													
				newNode.setRight(current);
				stack.add(newNode);
				current = newNode;

			} 
			break A;	
		}
	}
	}
}

public void setAllBorders(int x, int y) {									//Since all Nodes are connected somehow
	for(int i = 0; i<x; i++) {												//Go thru each node and 
		for(int j = 0; j < y; j++) {										//finalize all borders.
		maze[i][j].setBorder();
		}
	}
}

public void createBoundaries(int x, int y) {
	//corner box borders
	maze[0][0] = new Node((byte)0b00001111);	//top-left
	maze[0][y] = new Node((byte)0b00001111);	//top-right
	maze[x][0] = new Node((byte)0b00001111);	//bottom-left
	maze[x][x] = new Node((byte)0b00001111);	//bottom-right

	//top-side and bottom-side
	for(int i = 0; i < x; i+=x) {
		for(int j = 1; j<(y-1);j++) {
			maze[i][j] = new Node((byte)0b00001010);
		}
	}
	//left-side and right-side
	for(int i = 1; i < y; i++) {
		for(int j = 0; j < x; j+=x) {
			maze[i][j] = new Node((byte)0b00000101);
			}	
		}
	}
}

