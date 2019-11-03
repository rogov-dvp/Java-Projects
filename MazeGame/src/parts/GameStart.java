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
	private MazeGenerator mazeGen;											//Maze
	//maze dimensions
	private int mazeX = 10;			//Width						
	private int mazeY = 10;			//Height 
	
	//The 4 borders and anchor coordinates:		(100,50)				//for easier changes
	private int n = 32;		//ball size and jump increments
	private int nCalc = 26;
	//calculations for ancX and ancY
	private int calcY1 = (600-(nCalc*(mazeY+2)))/2; 
	
	private int ancX = 100;
	private int ancY = calcY1;
	
	
	
	
	
	private int ancXplus = ancX+n;
	private int ancYplus = ancY+n;
	
	//player character
	private int playerWidth = ancX+n;											//player row
	private int playerHeight = 250;											//player column
	//up-border
	
	
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
		
///constructors
	public GameStart() {
		mazeGen = new MazeGenerator(mazeX,mazeY);
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
		for(int i = 0; i < mazeY+2; i++) {					//horizontal. x-axis
			for(int j = 0; j < mazeX+2; j++) {				//vertical. y-axis
				byte bits =	 (byte) (mazeGen.maze[j][i].getBorder());
				if((bits&0b1000) == 0b1000)
				g.drawLine(upX1+n*(i), upY1+n*(j), upX2+n*(i), upY2+n*(j));						//Up border
				
				if((bits&0b100) == 0b100)
				g.drawLine(rX1+n*(i), rY1+n*(j), rX2+n*(i), rY2+n*(j));							//Right border
				
				if((bits&0b10) == 0b10)
				g.drawLine(bX1+n*(i), bY1+n*(j), bX2+n*(i), bY2+n*(j));							//Bottom border
		
				if((bits&0b1) == 0b1)
				g.drawLine(lX1+n*(i), lY1+n*(j), lX2+n*(i), lY2+n*(j));				//Left border
			}
		}
		}catch  (NullPointerException e) {
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
