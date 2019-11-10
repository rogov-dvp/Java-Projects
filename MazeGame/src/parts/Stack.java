package parts;

public class Stack{
	private Node top = new Node(-1, -1);		//pointer to top of stack
	Stack(Node N) {
		top.setNext(N);
		N.setNext(null);
	}
	Stack() {
	}
	
	public void add(Node N) {				//Add node to stack
		if(top.getNext() == null) {
			top.setNext(N);
		} else {
			N.setNext(top.getNext());
			top.setNext(N); 
		}
	}
	public int checkDiffX() {
		if(top.getNext().getNext() == null) {System.out.println("Stack empty");return -2;} 
		else {
			return top.getNext().getNext().getX() - top.getNext().getX();			
		}
	}
	public int checkDiffY() {
		if(top.getNext().getNext() == null) {System.out.println("Stack empty");return -2;} 
		else {
			return top.getNext().getNext().getY() - top.getNext().getY();			
	}
	}
	public Node pop() {						//pops Node out of stack. Returns the popped Node
		if(top.getNext() == null) {return null;} 
		else {
			Node temp = top.getNext();	
			top.setNext(temp.getNext());
			temp.setNext(null);		
			return temp;
		}
		
	}
	public boolean isEmpty() {
		return (top.getNext() == null)?true:false;
	}
	
}
