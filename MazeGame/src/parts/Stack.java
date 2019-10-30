package parts;

public class Stack{
	private Node top = new Node(-1, -1);		//pointer to top of stack
	private int counter = 0;
	Stack(Node N) {
		top.setNext(N);
		N.setNext(null);
		counter++;
	}
	
	public void add(Node N) {				//Add node to stack
		if(top.getNext() == null) {
			top.setNext(N);
			counter++;
		} else {
			N.setNext(top.getNext());
			top.setNext(N); 
			counter++;
		}
	}
	public int checkDiffX() {
		if(top.getNext() == null) {System.out.println("Stack empty");return -1;} 
		else {
			return top.getNext().getNext().getX() - top.getNext().getX();			//beautiful code lol
		}
	}
	public int checkDiffY() {
		if(top.getNext() == null) {System.out.println("Stack empty");return -1;} 
		else {
			return top.getNext().getNext().getY() - top.getNext().getY();			//beautiful code lol
	}
	}
	public Node pop() {						//pops Node out of stack. Returns the popped Node
		if(top.getNext() == null) {System.out.println("Stack empty");return null;} 
		else {
			Node temp = top.getNext();	
			System.out.println(temp.getX() +","+ temp.getY());
			top.setNext(temp.getNext());
			temp.setNext(null);		
			counter--;
			return temp;
		}
		
	}
	public int size() {
		return counter;
	}
}
