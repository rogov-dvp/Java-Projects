package parts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
//
import javax.swing.Timer;
import javax.swing.JPanel;

public class GameStart extends JPanel implements KeyListener, ActionListener{
//attributes
	private boolean play = false;		//Game does not start on open
	
	private Timer timer;
	private int delay = 8;
	
	//player character
	private int playerWidth = 40;											//player row
	private int playerHeight = 300;											//player column
	private int n = 20;														//ball size and jump increments
	
	//maze dimensions
	private int mazeDim = 600;											//One row-side of boundaries
	private int mazeN = 4;
	//line start
	private final int rectXOrigin = playerWidth;
	private final int rectYOrigin = playerHeight;
	private int rectX = playerWidth;		
	private int rectY = playerHeight;
		
	
	private MazeGenerator maze;		//For create a maze
	
//constructors
	public GameStart() {
//		maze = new MazeGenerator(mazeN,mazeN);
		addKeyListener(this);		
		setFocusable(true); 					//by default
		setFocusTraversalKeysEnabled(false);	//tab and shift are disabled	
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		//background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 700, 600);
		
		//Wall
		g.setColor(Color.YELLOW);
//		g.drawRect(lsX1, lsY1, lsX2, lsY2);		//x1 -> x2
		for(int i = 0; i < mazeN; i++) {
			for(int j = 0; j < mazeN; j++) {
				g.setColor(Color.YELLOW);
				g.drawRect(rectX, rectY, 20, 20);
				rectX +=20;
			}
				rectY +=20;
				rectX=rectXOrigin;
		}
		rectY=rectYOrigin;

		
		//character
		g.setColor(Color.WHITE);
		g.fillOval(playerWidth,playerHeight,n,n);	//40,300,n,n
		
	
		
	}
	
//methods	
	@Override
	public void keyReleased(KeyEvent arg0) {}
	@Override
	public void keyTyped(KeyEvent arg0) {}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();	
		//
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
			if(playerHeight< mazeDim)
				moveUp();							//player moves up
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			if(playerWidth > 0)
				moveLeft();							//player moves left
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||e.getKeyCode() == KeyEvent.VK_D) {
			if(playerWidth < mazeDim)
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
