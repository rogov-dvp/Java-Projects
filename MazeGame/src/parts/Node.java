package parts;

//Each element in the maze is a node.
public class Node {
	private boolean isLinked = false;		//If linked to a path.
	private int row;						//Which row it represents
	private int col;						//Which column it represents
	
	public Node(int row, int col) {
		this.row = row;
		this.col = col;
	}
	public Node(boolean isLinked, int row, int col) {
		this.isLinked = isLinked;
		this.row = row;
		this.col = col;
	}
	//getters and setters
	public boolean isLinked() {
		return isLinked;
	}
	public void setLinked(boolean isLinked) {
		this.isLinked = isLinked;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	
}
