package parts;

//Each element in the maze is a node.
public class Node {
	private boolean isMarked;					//If it has been traced before. May not need? 
	private boolean isLinkedUp = false;
	private boolean isLinkedRight = false;
	private boolean isLinkedDown = false;
	private boolean isLinkedLeft = false;
	private int[] border = new int[14];			//type of borders it will use. 0-13

	public Node(boolean isMarked) {
		this.isMarked = isMarked;
	}
	
	//set type of borders
	public int setBorder() {	
			
	}
	
	public void setLinked(int dir) {		// 0=up,1=right,2=down,3=left
		switch(dir) {
		case 0:	isLinkedUp = true;
				break;
		case 1:	isLinkedRight = true;
				break;
		case 2:	isLinkedDown = true;
				break;
		case 3:	isLinkedLeft = true;
				break;
		default: 
				System.out.println("error in linking");
		}
	}
	
	public int getType() {
		return type;
	}
	
	
}
