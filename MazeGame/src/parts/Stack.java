package parts;

public class Stack<Node>{
	private Node[] stack;
	private static int top;										//static. Only need to make one maze
	private int capacity;
	
	Stack(int row, int col){
		capacity = row*col;
		stack = (Node[]) (new Object[capacity]);				//make sure to  not have row != 0 or col != 0. maybe make pre-sized mazes (difficulty)
		top = -1;
	}
	
	//insert a Node
	public  void push(Node N, int row, int col) {				//Stacks are increased and pointer changes.
		stack[++top] = N;
		top++;
	}
	//remove Node from stack
	public static void pop(int row, int col) {							//removing stack. No need to get rid of node. We want to keep it there.
		top--;
	}
}
