package parts;

import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		
	JFrame obj = new JFrame();							//Frame created
	obj.setBounds(10,10,700,600);						//Frame bounds. (x,y,width,height)
	GameStart gamePlay = new GameStart();					//Creates game
	obj.setTitle("Name of the Maze");	//Name of Game
	obj.setResizable(false);							//User can't resize screen
	obj.setVisible(true);								//Visible to user
	obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//Close the game

	obj.add(gamePlay);

	}
}
