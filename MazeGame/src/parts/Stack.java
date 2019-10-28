package parts;

public class Stack{
	private Node top;		//pointer to top of stack
	
	Stack(Node N) {
		top.setNext(N);
		N.setNext(null);	
	}
	
	public void add(Node N) {				//Add node to stack
		if(top.getNext() == null) {
			top.setNext(N);
		} else {
			Node temp = top.getNext();
			top.setNext(N);
			N.setNext(temp); 
		}
	}
	public Node pop() {						//pops Node out of stack. RETURNS TOP OF STACK
		if(top.getNext() == null) {System.out.println("Stack empty");return null;} 
		else {
		Node N = top.getNext();
		top = N.getNext();
		N.setNext(null);
		return top;
		}
		
	}
}
