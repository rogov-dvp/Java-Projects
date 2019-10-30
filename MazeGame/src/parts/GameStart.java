package parts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

//
import javax.swing.Timer;
import javax.swing.JPanel;

public class GameStart extends JPanel implements KeyListener, ActionListener{
//attributes
	private boolean play = false;		//Game does not start on open
	
	private Timer timer;
	private int delay = 8;
	//frame size
	private int width = 1060;
	private int height = 530;
	
	//maze
	private MazeGenerator maze;											//Maze
	//maze dimensions
	private int mazeX = 8;														
	private int mazeY = 8;
	

	//The 4 borders and anchor coordinates:		(100,50)				//for easier changes
	private int n = 32;														//ball size and jump increments
	private int ancX = 100;
	private int ancY = 26;
	private int ancXplus = ancX+n;
	private int ancYplus = ancY+n;
	
	//player character
	private int playerWidth = ancX;											//player row
	private int playerHeight = 250;											//player column
	//up-border
	private int upX1 = ancX;
	private int upY1 = ancY;
	private int upX2 = ancXplus;								
	private int upY2 = ancY;
	
	//right-border
	private int rX1 = ancXplus;
	private int rY1 = ancY;
	private int rX2 = ancXplus;
	private int rY2 = ancYplus;
	
	//bottom-border
	private int bX1 = ancXplus;
	private int bY1 = ancYplus;
	private int bX2 = ancX;
	private int bY2 = ancYplus;
	
	//left-border
	private int lX1 = ancX; 
	private int lY1 = ancYplus;
	private int lX2 = ancX;
	private int lY2 = ancY;
		
//constructors
	public GameStart() {
		maze = new MazeGenerator(mazeX,mazeY);
		addKeyListener(this);		
		setFocusable(true); 					//by default
		setFocusTraversalKeysEnabled(false);	//tab and shift are disabled	
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		try {
		//background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0,1100, 600);
				
		//character
		g.setColor(Color.WHITE);
		g.fillOval(playerWidth,playerHeight,n,n);	//40,300,n,n
		
		//The four borders used
		g.setColor(Color.YELLOW);
		//up,right,bottom,left
		//Build borders;
		for(int i = 0; i < mazeY+2; i++) {					//Left-->Right
			for(int j = 0; j < mazeX+2; j++) {				//Up-->Bottom
//				if(maze.maze[j][i].getUp() == null)
				g.drawLine(upX1+n*(j), upY1+n*(i), upX2+n*(j), upY2+n*(i));						//Up border
				
//				if(maze.maze[j][i].getRight() == null)
				g.drawLine(rX1+n*(j), rY1+n*(i), rX2+n*(j), rY2+n*(i));							//Right border
//				if(Objects.equals(maze.maze[i][j].getDown(), null))
				g.drawLine(bX1+n*(j), bY1+n*(i), bX2+n*(j), bY2+n*(i));							//Bottom border
				
//				if(maze.maze[j][i].getLeft() == null)
				g.drawLine(lX1+n*(j), lY1+n*(i), lX2+n*(j), lY2+n*(i));				//Left border
			}
		}
		}catch  (NullPointerException e) {
//			System.out.println(e.getMessage());
			System.out.println(e.toString());
		} 
		
	}
	
//methods	
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();	
		//implement array

		repaint();
	}
	
	
	
	

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) 	//exit game. maybe give a prompt
			System.exit(0);
			
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {		
			if(playerHeight>0)
				moveDown();							//Reversed in Height. player pressed up to move Player moves down 
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			if(playerHeight< height)
				moveUp();							//player moves up
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			if(playerWidth > 0)
				moveLeft();							//player moves left
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||e.getKeyCode() == KeyEvent.VK_D) {
			if(playerWidth < width)
				moveRight();						//player moves right
		}

	}
	public void moveUp() {
		play = true;
		playerHeight+=n;
	}
	public void moveDown() {
		play = true;
		playerHeight-=n;						//Player moves down 
	}
	public void moveLeft() {
		play = true;
		playerWidth-=n;						//player moves left
	}
	public void moveRight() {
		play = true;
		playerWidth+=n;							//player moves right
	}

}
