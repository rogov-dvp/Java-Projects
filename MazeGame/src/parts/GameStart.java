package parts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;

import javax.swing.JPanel;

public class GameStart extends JPanel implements KeyListener, ActionListener{
//attributes
	private boolean play = false;		//Game does not start on open
	
	private Timer timer;
	private int delay = 8;
	
	private int playerRow;												//player row
	private int playerCol;												//player column
	private int[][] player = new int[playerRow][playerCol];				//player position
	
	private int mazeRowBorder;											//One side of Boundaries
	private int mazeColBorder;											//One side of Boundaries
	private int[][] finishSquare;										//"finish line"
	private boolean[][] isWall;											//shows that part of the array[][] is considered a wall 
	
	private MazeGenerator maze;		//For create a maze
	
//constructors
	public GameStart() {
		maze = new MazeGenerator();	//Generate maze. Parameters will be chosen by user
		addKeyListener(this);		//
		setFocusable(true); 		//by default
		setFocusTraversalKeysEnabled(false);	//tab and shift are disabled	
//		timer = new Timer(delay, this);
//		timer.start();
	}
	
	public void paint(Graphics g) {
		//background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 700, 600);
		
		//Drawing Maze
//		maze.draw((Graphics2D)g);
		
		//character
		g.setColor(Color.WHITE);
		g.fillOval(40,300,16,16);
		
		//If player reaches finish line.
//		if(player[25][25] == finishSquare[25][25]) {		//25 are example, if 
//			play = false;									//Stop game
//			g.setColor(Color.GREEN);						//Message color
//			g.setFont(new Font("serif",Font.BOLD,30));		//Message font
//			g.drawString("You win!",190,330);				//Win message
//															//add timer?
//		}
		//maybe add a time limit, if they choose to have one
	}
	
//methods	
	@Override
	public void keyReleased(KeyEvent arg0) {
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {				
			if(!isWall[playerRow][playerCol--])								//if no wall
				playerCol--;						//Player moves up. decrement because vertical axis are flipped
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(!isWall[playerRow][playerCol++])
				playerCol++;						//Player moves down 
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(!isWall[playerRow--][playerCol])
				playerRow--;						//player moves left
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(!isWall[playerRow++][playerCol])
				playerRow++;							//player moves right
		}

	}


}
