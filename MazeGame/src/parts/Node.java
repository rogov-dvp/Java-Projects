package parts;

/*
 	Each element in the maze is a node.
 
 	in 8-bit binary
	0000 1000 = 	 	up border (8)
	0000 0100 = right-side border (4)
	0000 0010 =  down-side border (2)
	0000 0001 =  left-side border (1)

boundaries example:
	 _ _ _ _ _ _ _ _
	|_|_ _ _ _ _ _|_|				
	| |           | |		
	| |           | |		
	| |           | |		
	| |           | |		
	|_|_ _ _ _ _ _|_|		
	|_|_ _ _ _ _ _|_|		
	
------------------------------------------
Use in stack:
pointer --> (x,y)
if((++x,y) != null || (x,++y) != null || (--x,y) != null || (x,--y) != null) {
Then go backwards in stack and check again
}

------------------------------------------
*/	

public class Node {	
	//coordinates
	private int x;
	private int y;
	private byte border;			 //Binary value dictates what type of border it is.
	private Node next;				 //For Stack
	
	//links from node
	private Node up;
	private Node right;
	private Node down;
	private Node left;
	private boolean isBorder = false;
	
	//pathFinder attributes:
	private boolean visited = false;
	private int counter;
	private boolean selected = false;
	
//constructors	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
//boundaries border-type constructors
	public Node(int x, int y, byte bin) {					//When creating borders, use (5,10,15)
		this.x = x;
		this.y = y;
		isBorder = true;
		switch(bin) {					
		case 5:  border = 0b101;		//(5)  0000 0101, vertical passage border (used in left/right side)
			break;
		case 10: border = 0b1010;		//(10) 0000 1010, horizontal passage border (used in up/down side)
			break;
		case 15: border = 0b1111;		//(15) 0000 1111, for box like squares
			break;
		}
	}
//Borders
//Get border value
	public byte getBorder() {
		return border;
	}
//Set border value; Should be used only when all 4 sides are not null(this is checked in stack)
	public void setBorder() {	
		byte sum = 0b00000000;
		if(up == null)
			sum|=0b1000;
		if(right == null)
			sum|=0b100;
		if(down == null)
			sum|=0b10;
		if(left == null)
			sum|=0b1;
		border = sum;	//(range from 0-15)
	}
	
//	Adding surrounding nodes
	public void setUp(Node up) {			//In stack, update the nodes through these methods
		this.up = up;
	}
	public void setRight(Node right) {
		this.right = right;
	}
	public void setDown(Node down) {
		this.down = down;
	}
	public void setLeft(Node left) {
		this.left = left;
	}
	
//  Getting nodes	
	public Node getUp() {
		return up;
	}
	public Node getRight() {
		return right;
	}
	public Node getDown() {
		return down;
	}
	public Node getLeft() {
		return left;
	}
//	Getting coordinates
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
//  Set and get Next
	public Node getNext() {
		return next;
	}
	public void setNext(Node N) {
		next = N;
	}
// set and get border
	public boolean isBorder() {
		return isBorder;
	}
//pathFinder
	public boolean isVisited() {
		return visited;
	}
	public void setVisited(boolean visited) {
		this.visited = visited;
	}
	public int getCounter() {
		return counter;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
}
