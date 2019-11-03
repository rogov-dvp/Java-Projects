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

//
import javax.swing.Timer;
import javax.swing.JPanel;

/*
Game: Mazlzahar's Maze (Maze with more features such as less visibility, collect keys, have "monsters" chasing)
Developer: Alex Rogov
British Columbia, Canada
email: alexrogov182@gmail.com	
*/
public class GameStart extends JPanel implements KeyListener, ActionListener{
//attributes
	private boolean play = false;		//Must press Enter to begin	
	private int counter = 0;			//Based on counter, Used for starting page, reseting.
	private Timer timer;
	private int delay = 8;
	
	//frame size
	private int frameWidth = 1100;
	private int frameHeight = 600;
	
	//maze & dimensions
	private MazeGenerator maze;										//maze reference
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
	private boolean visibility = false;
	
	//win string coordinates if no reduced visibility
	
	private int winStringX = 320; 
	private int winStringY = 225;
	private int restartStringX = 260;
	private int restartStringY = 275;
	
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
		String font = "Helvetica";
		Color titleColor = new Color(54, 29, 11);	//~Dark Hazelnut color
		Color startColor = new Color(227, 128, 57); //~Orange/brown
		Color optionsColor = new Color(201, 159, 66);
		Color winColor = new Color(81, 245, 92);	//~Greenish
		Color enterColor = new Color(255, 232, 181);//~Very light beige
		
		//opening page
		if(!play && counter == 0) {	
			//start background 
			g.setColor(startColor);
			g.fillRect(0, 0, frameWidth, frameHeight);
			//title
			g.setColor(titleColor);
			g.setFont(new Font(font,Font.BOLD, 50));
			g.drawString("Malzar's Maze", (int)(frameWidth/3), (int)(frameHeight/3)); 
			
			g.setColor(enterColor);
			g.setFont(new Font(font,Font.CENTER_BASELINE,36));
			g.drawString("Press Enter", (int)(frameWidth/3.2)+84, (int)(frameHeight/3)+80);
			
		//options page 1: Want visibility?
		} else if(!play && counter==1) {	
			//background
			g.setColor(optionsColor);
			g.fillRect(0,0,frameWidth,frameHeight);
			//title
			g.setColor(titleColor);
			g.setFont(new Font(font,Font.BOLD, 50));
			g.drawString("Options", (int)(frameWidth/3)+60, (int)(frameHeight/3)); 
			//Asks if want reduced visibility
			g.setColor(titleColor);
			g.setFont(new Font(font,Font.BOLD, 25));
			g.drawString("- Reduce Visiblity:", (int)(frameWidth/3)+70, (int)(frameHeight/3)+75); 
			//1 = yes, 2 = no
			g.setColor(titleColor);
			g.setFont(new Font(font,Font.BOLD, 25));
			g.drawString("1 = Yes", (int)(frameWidth/3)+70, (int)(frameHeight/3)+150); 
			g.setColor(titleColor);
			g.setFont(new Font(font,Font.BOLD, 25));
			g.drawString("2 = No", (int)(frameWidth/3)+180, (int)(frameHeight/3)+150); 

			
			
		} else {
		//background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0,frameWidth, frameHeight);
				
		//character
		g.setColor(startColor);				//Color.WHITE also good
		g.fillOval(playerX,playerY,n,n);	//40,300,n,n
		
		//Build borders | up,right,bottom,left
		g.setColor(Color.YELLOW);
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
		if(visibility) { 
		g.setColor(Color.DARK_GRAY);
		Area a1 = new Area(new Rectangle2D.Double(0, 0, 1100, 600)); 
		Area a3 = new Area(new Ellipse2D.Double(ballEndX-30, ballEndY-30, 75, 75));
		Area a2 = new Area(new Ellipse2D.Double(playerX-120/2, playerY-120/2, 150, 150)); 
		a1.subtract(a2);	//subtract player ball circle from Rect
		a1.subtract(a3);	//subtract finish line circle from Rect
		g2d.fill(a1);
		} else {
		//finish line ball. Place AFTER visibility reducer
		winStringX = 820;
		winStringY = ballEndY-15;
		restartStringX = 765;
		restartStringY = ballEndY+35;
		}
		g.setColor(Color.RED);
		g.fillOval(ballEndX, ballEndY, n/2, n/2);
		if(playerX == (ballEndX-n/4) && playerY == (ballEndY-n/4)) {
			g.setColor(winColor);
			g.setFont(new Font(font,Font.BOLD, 45));
			g.drawString("You Win", winStringX, winStringY);
			
			g.setColor(startColor);
			g.setFont(new Font(font,Font.CENTER_BASELINE,30));
			g.drawString("Press Enter to restart", restartStringX, restartStringY);
		}
		
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
		if(playerX == (ballEndX-n/4) && playerY == (ballEndY-n/4)) {
			//sleeper time?
			play = false;
		}
		
		
		repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) 	//exit game. maybe give a prompt
			System.exit(0);
			
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {	
			if(maze.maze[coorRow][coorCol].getUp() != null && play == true)
				moveUp();							//player moves up 
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			if(maze.maze[coorRow][coorCol].getDown() != null && play == true)
				moveDown();							//player moves down
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			if(maze.maze[coorRow][coorCol].getLeft() != null && play == true)
				moveLeft();							//player moves left
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT ||e.getKeyCode() == KeyEvent.VK_D) {
			if(maze.maze[coorRow][coorCol].getRight() != null && play == true)
				moveRight();						//player moves right
		}
		if(e.getKeyCode() == KeyEvent.VK_1) {
			if(counter==1) {
			visibility = true;
			play = true;
			counter++;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_2) {
			if(counter == 1) {
			visibility = false;
			play = true;
			counter++;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			System.out.println(counter);
			System.out.println(play);
			switch(counter) {		//Changes screens or restarts game
			case 0:					//Leaves title page
				counter++;
				break;
//			case 1:
//				play = true;
//				counter++;
//				break;
			case 2:					//regenerate maze
				if(!play) {
					counter = 0;
					maze = new MazeGenerator(mazeX,mazeY);
					play = true;
					playerX = ancX+n;									//playerX pixel location
					playerY = ancY+(n*mazeY)/2+n;						//playerY pixel location	
					repaint();
				}
				break;
				
			}
		}

	}
	//players move based on 'n' size and direction.
	//Note: moving down means incrementing and moving up is decrementing
	public void moveDown() {
		coorRow++;
		playerY+=n;		
	}
	public void moveUp() {
		coorRow--;
		playerY-=n;						
	}
	public void moveLeft() {
		coorCol--;
		playerX-=n;						
	}
	public void moveRight() {
		coorCol++;
		playerX+=n;							
	}

}
