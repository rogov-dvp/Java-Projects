package parts;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		
	JFrame obj = new JFrame();							//Frame created
	obj.setBounds(10,10,700,600);						//Frame bounds. (x,y,width,height)
	GameStart gamePlay = new GameStart();					//Creates game
	obj.setTitle("Labyrinth");	//Name of the Game
	obj.setResizable(false);							//User can't resize screen
	obj.setVisible(true);								//Visible to user
	obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//Stops the game when window is closed

	obj.add(gamePlay);

	}
}
