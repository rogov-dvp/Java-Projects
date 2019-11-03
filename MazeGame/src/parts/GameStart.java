package parts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;

//
import javax.swing.Timer;
import javax.swing.JPanel;

/*
Game: Maze but cooler (more features such as less visibility, "monster" chasing)
Developer: Alex Rogov
British Columbia, Canada
email: alexrogov182.rogov@gmail.com	
*/
public class GameStart extends JPanel implements KeyListener, ActionListener{
//attributes
	private boolean play = false;		
	
	private Timer timer;
	private int delay = 8;
	//frame size
//	private int width = 1060;
//	private int height = 530;
	
	//maze
	private MazeGenerator maze;										//maze reference
	//maze dimensions
	private int mazeX = 14;											//Width	of maze without borders
	private int mazeY = 14;											//Height of maze without borders
	
	//anchor coordinates:		
	private int n = 32;												//IMPORTANT. ball pixel size and jump increments
	private int nCalc = 26;
	//ancX and ancY
	private int calcY = (600-(nCalc*(mazeY+2)))/2; 					//calculations for centering
	private int calcX = (1100-(nCalc*(mazeX+2)))/2; 				//calculations for centering
	private int ancX = calcX - 100;									//anchor X coordinates
	private int ancY = calcY - 61;									//anchor Y coordinates
	private int ancXplus = ancX+n;									//anchor point + n. pixel X coordinates. For convenience use at border attributes
	private int ancYplus = ancY+n;									//anchor point + n. pixel Y coordinates. For convenience use at border attributes
	
	//player characters
	private int playerX = ancX+n;									//playerX pixel location
	private int playerY = ancY+(n*mazeY)/2+n;						//playerY pixel location	
	private int coorRow = mazeY/2+1;								//PlayerRow Starting coordinates based on mazeGenerator
	private int coorCol = 1;										//PlayerCol Starting coordinates based on mazeGenerator
	
	//In-game Objects and locations
	Node finish;													//finish line (coorRow, coorCol: located other side of Maze)		
	private int ballEndX = ancX+mazeX*n+n/4;
	private int ballEndY = ancY+(n*mazeY)/2+n/4;
	
	//One line is (x1,y1 --> x2,y2). 4 lines to make a box
	//---------------------------------------------------------------------------------------------------------------
	//         up-border         |        left-border       |       bottom-border      |        right-border		|
	private int upX1 = ancX;	 private int rX1 = ancXplus; private int bX1 = ancXplus; private int lX1 = ancX;
	private int upY1 = ancY; 	 private int rY1 = ancY;	 private int bY1 = ancYplus; private int lY1 = ancYplus;
	private int upX2 = ancXplus; private int rX2 = ancXplus; private int bX2 = ancX;	 private int lX2 = ancX;
	private int upY2 = ancY;	 private int rY2 = ancYplus; private int bY2 = ancYplus; private int lY2 = ancY;
	//---------------------------------------------------------------------------------------------------------------
	

	
///constructors
	public GameStart() {
		maze = new MazeGenerator(mazeX,mazeY);
		addKeyListener(this);		
		setFocusable(true); 					//by default
		setFocusTraversalKeysEnabled(false);	//tab and shift are disabled	
		timer = new Timer(delay, this);
		timer.start();
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		//background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0,1100, 600);
				
		//character
		g.setColor(Color.WHITE);
		g.fillOval(playerX,playerY,n,n);	//40,300,n,n
		
		//The four borders used
		g.setColor(Color.YELLOW);
		//up,right,bottom,left
		//Build borders;
		for(int i = 0; i < mazeY+2; i++) {					//horizontal. x-axis
			for(int j = 0; j < mazeX+2; j++) {				//vertical. y-axis
				byte bits =	 (byte) (maze.maze[j][i].getBorder());
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
		//Reduce visibility
		g.setColor(Color.DARK_GRAY);
		Area a1 = new Area(new Rectangle2D.Double(0, 0, 1100, 600)); //20 20 100 100
		Area a2 = new Area(new Ellipse2D.Double(playerX-120/2, playerY-120/2, 150, 150)); //50 50 100 10
		Area a3 = new Area(new Ellipse2D.Double(ballEndX-30, ballEndY-30, 75, 75));
		a1.subtract(a2);	//subtract player ball circle from Rect
		a1.subtract(a3);	//subtract finish line circle from Rect
		g2d.fill(a1);
		
		//finish line ball. Place AFTER visibility reducer
		g.setColor(Color.RED);
		g.fillOval(ballEndX, ballEndY, n/2, n/2);
		if(playerX == (ballEndX-n/4) && playerY == (ballEndY-n/4)) {
			play = false;
			g.setColor(Color.GREEN);
			g.setFont(new Font("serif",Font.BOLD, 40));
			g.drawString("You Win", 400, 50);
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
			if(maze.maze[coorRow][coorCol].getUp() != null )
				moveUp();							//player moves up 
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			if(maze.maze[coorRow][coorCol].getDown() != null )
				moveDown();							//player moves down
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			if(maze.maze[coorRow][coorCol].getLeft() != null )
				moveLeft();							//player moves left
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||e.getKeyCode() == KeyEvent.VK_D) {
			if(maze.maze[coorRow][coorCol].getRight() != null )
				moveRight();						//player moves right
		}

	}
	//players move based on 'n' size and direction.
	//Note: moving down means incrementing and moving up is decrementing
	public void moveDown() {
		play = true;
		coorRow++;
		playerY+=n;		
		
	}
	public void moveUp() {
		play = true;
		coorRow--;
		playerY-=n;						
	}
	public void moveLeft() {
		play = true;
		coorCol--;
		playerX-=n;						
	}
	public void moveRight() {
		play = true;
		coorCol++;
		playerX+=n;							
	}

}
