package objects;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame obj = new JFrame();
		obj.setBounds(10,10,700,600);		//Window size (x,y,width, height); where x,y are top-left coord.
		Gameplay gamePlay = new Gameplay();
		obj.setTitle("Breakout Ball");		//Name of window
		obj.setResizable(false);			//isResizable -> no
		obj.setVisible(true);				//is visible to user -> yes
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//Required to close the game
	
		obj.add(gamePlay);
	}	
	
}
