package parts;

public class QueuePath {
	private Node top = new Node(-1,-1);
	private Node tail= new Node(-2,-2);;
	private int counter;		
	
	public QueuePath() {
		
	}
	
	public void enqueue(Node N) {
		if(counter <= 0) {				//<1 Node in queue
			tail.setNext(N);
			top.setNext(N);
		} else if (counter==1) {		//1 Node in queue
			N.setNext(top);
			tail.setNext(N);
		} else {						//>1 Nodes in queue
			N.setNext(tail.getNext());
			tail.setNext(N);
		}
//		System.out.println("queued: (" + N.getX() + "," + N.getY() + ") , " + N);
		counter++;
	}
	public Node dequeue() {
		if(top == null) { 
		System.out.println("Queue is empty");
			return null;
		} else if(counter==1) {
			Node temp = top.getNext();
			top.setNext(null);
			tail.setNext(null);
			counter--;
			return temp;
		} else {
			Node temp = top.getNext();
			top.setNext(tail.getNext());
			for(int i = 0; i < counter-2;i++) {
				top.setNext(top.getNext().getNext());
			}
			counter--;
			return temp;
		}
	}
	
	public Node peek() {
		if(top == null) {
		System.out.println("Queue is empty");
		return null;
		} else 
			return top.getNext();
	}
	public void makeEmpty() {
		top.setNext(null);
		tail.setNext(null);
	}
}
