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
	//final variables:
	//frame size
	private final int FRAMEWIDTH = 1100;
	private final int FRAMEHEIGHT = 600;
	//dimensions
	private final int N = 32;
	private final int MAX_BLOCK_VISIBILITY = 3;								//Max blocks a character can see ahead
	private final int MAZEX = 14;
	private final int MAZEY = 14;
	private final int COORROW = MAZEY/2+1;
	private final int COORCOL = 1;
	
//attributes
	private boolean play = false;		//Must press Enter to begin	
	private int counter = 0;			//Based on counter, Used for starting page, reseting.
	private Timer timer;
	private int delay = 8;
	
	
	//maze & dimensions
	private MazeGenerator maze;										//maze reference
	
	//anchor coordinates:		
	private int nCalc = 26;
	//ancX and ancY
	private int calcY = (600-(nCalc*(MAZEY+2)))/2; 					//calculations for centering
	private int calcX = (1100-(nCalc*(MAZEX+2)))/2; 				//calculations for centering
	private int ancX = calcX - 100;									//anchor X coordinates
	private int ancY = calcY - 61;									//anchor Y coordinates
	private int ancXplus = ancX+N;									//anchor point + n. pixel X coordinates. For convenience use at border attributes
	private int ancYplus = ancY+N;									//anchor point + n. pixel Y coordinates. For convenience use at border attributes
	
	//player characters
	private final int PLAYERX = ancX+N;
	private final int PLAYERY = ancY+(N*MAZEY)/2+N;
	private int playerX = PLAYERX;									//playerX pixel location
	private int playerY = PLAYERY;						//playerY pixel location	
	private int coorRow = COORROW;								//PlayerRow coordinates
	private int coorCol = COORCOL;										//PlayerCol coordinates
	
	//In-game Objects and locations
	private int ballEndX = ancX+MAZEX*N+N/4;
	private int ballEndY = ancY+(N*MAZEY)/2+N/4 +N;
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
		maze = new MazeGenerator(MAZEX,MAZEY);
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
			g.fillRect(0, 0, FRAMEWIDTH, FRAMEHEIGHT);
			//title
			g.setColor(titleColor);
			g.setFont(new Font(font,Font.BOLD, 50));
			g.drawString("Malzar's Maze", (int)(FRAMEWIDTH/3), (int)(FRAMEHEIGHT/3)); 
			
			g.setColor(enterColor);
			g.setFont(new Font(font,Font.CENTER_BASELINE,36));
			g.drawString("Press Enter", (int)(FRAMEWIDTH/3.2)+84, (int)(FRAMEHEIGHT/3)+80);
			
		//options page 1: Want visibility?
		} else if(!play && counter==1) {	
			//background
			g.setColor(optionsColor);
			g.fillRect(0,0,FRAMEWIDTH,FRAMEHEIGHT);
			//title
			g.setColor(titleColor);
			g.setFont(new Font(font,Font.BOLD, 50));
			g.drawString("Options", (int)(FRAMEWIDTH/3)+60, (int)(FRAMEHEIGHT/3)); 
			//Asks if want reduced visibility
			g.setColor(titleColor);
			g.setFont(new Font(font,Font.BOLD, 25));
			g.drawString("- Reduce Visiblity:", (int)(FRAMEWIDTH/3)+70, (int)(FRAMEHEIGHT/3)+75); 
			//1 = yes, 2 = no
			g.setColor(titleColor);
			g.setFont(new Font(font,Font.BOLD, 25));
			g.drawString("1 = Yes", (int)(FRAMEWIDTH/3)+70, (int)(FRAMEHEIGHT/3)+150); 
			g.setColor(titleColor);
			g.setFont(new Font(font,Font.BOLD, 25));
			g.drawString("2 = No", (int)(FRAMEWIDTH/3)+180, (int)(FRAMEHEIGHT/3)+150); 

			
			
		} else {
		//Maze Background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0,FRAMEWIDTH, FRAMEHEIGHT);
				
		//character
		g.setColor(startColor);				//Color.WHITE also good
		g.fillOval(playerX,playerY,N,N);	
		
		//Build borders | up,right,bottom,left
		g.setColor(Color.YELLOW);
		for(int i = 0; i < MAZEY+2; i++) {					//horizontal. x-axis
			for(int j = 0; j < MAZEX+2; j++) {				//vertical. y-axis
				byte bits =	 (byte) (maze.maze[j][i].getBorder());
				if((bits&0b1000) == 0b1000)
				g.drawLine(upX1+N*(i), upY1+N*(j), upX2+N*(i), upY2+N*(j));						//Up border
				
				if((bits&0b100) == 0b100)
				g.drawLine(rX1+N*(i), rY1+N*(j), rX2+N*(i), rY2+N*(j));							//Right border
				
				if((bits&0b10) == 0b10)
				g.drawLine(bX1+N*(i), bY1+N*(j), bX2+N*(i), bY2+N*(j));							//Bottom border
		
				if((bits&0b1) == 0b1)
				g.drawLine(lX1+N*(i), lY1+N*(j), lX2+N*(i), lY2+N*(j));				//Left border
			}
		}
		
		//Reduce visibility
		if(visibility) { 
		g.setColor(Color.DARK_GRAY);
		Area a1 = new Area(new Rectangle2D.Double(0, 0, 1100, 600)); 							//grey screen around 
//		Area a2 = new Area(new Rectangle2D.Double(playerX-120/2, playerY-120/2, n*3, n*3)); 		//spotlight around player
		Area a3 = new Area(new Ellipse2D.Double(ballEndX-30, ballEndY-30, 75, 75));				//spotlight around finish line
		Area p = new Area(new Rectangle2D.Double(playerX,playerY,N+2,N+2)); 
//		a1.subtract(a2);
		a1.subtract(p);		//Player is always visible
		
		boolean validUp = true;
		boolean validRight = true;
		boolean validDown = true;
		boolean validLeft = true;
		//subtracting boxing around player based on walls, i = 0 up |i = 1 right | i = 2 down | i = 3 left 
		for(int i = 0; i < 4; i++) {			//direction
			for(int j = 0; j<MAX_BLOCK_VISIBILITY; j++) {			//# of blocks
								
				//up
				if(validUp && maze.maze[coorRow-j][coorCol].getUp() != null) {
					Area out = new Area(new Rectangle2D.Double(playerX,playerY-(j+1)*N,N+2,N+2));
					a1.subtract(out);
				} else {
					validUp = false;
				}
				
				//right
				if(validRight && maze.maze[coorRow][coorCol+j].getRight() != null) {
					Area out = new Area(new Rectangle2D.Double(playerX+(j+1)*N,playerY,N+2,N+2));
					a1.subtract(out);
				} else {
					validRight = false;
				}
				//down
				if(validDown && maze.maze[coorRow+j][coorCol].getDown() != null) {
					Area out = new Area(new Rectangle2D.Double(playerX,playerY+(j+1)*N,N+2,N+2));
					a1.subtract(out);
				} else {
					validDown = false;
				}
				//left
				if(validLeft && maze.maze[coorRow][coorCol-j].getLeft() != null) {						
					Area out = new Area(new Rectangle2D.Double(playerX-(j+1)*N,playerY,N+2,N+2));
					a1.subtract(out);
				} else {
					validLeft = false;
				}
				
			}
			validUp = true;
			validRight = true;
			validDown = true;
			validLeft = true;
		}
		
		
//		a1.subtract(a2);	//subtract player ball circle from Rect
		a1.subtract(a3);	//subtract finish line circle from Rect
		g2d.fill(a1);
		} else {
		winStringX = 820;
		winStringY = ballEndY-15;
		restartStringX = 765;
		restartStringY = ballEndY+35;
		}
		//character visibility
		
		//finish line ball. Place AFTER visibility reducer
		g.setColor(Color.RED);
		g.fillOval(ballEndX, ballEndY, N/2, N/2);
		if(playerX == (ballEndX-N/4) && playerY == (ballEndY-N/4)) {
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
		if(playerX == (ballEndX-N/4) && playerY == (ballEndY-N/4)) {
			play = false;
		}
		repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) 	//exit game. maybe give a prompt
			System.exit(0);
			
		if(e.getKeyCode() == KeyEvent.VK_UP) {	
			if(maze.maze[coorRow][coorCol].getUp() != null && play == true)
				moveUp();							//player moves up 
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(maze.maze[coorRow][coorCol].getDown() != null && play == true)
				moveDown();							//player moves down
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(maze.maze[coorRow][coorCol].getLeft() != null && play == true)
				moveLeft();							//player moves left
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
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
			case 2:					//regenerate maze
				if(!play) {
					coorRow = COORROW;
					coorCol = COORCOL;
					playerX = PLAYERX;									//playerX pixel location
					playerY = PLAYERY;						//playerY pixel location						
					maze = new MazeGenerator(MAZEX,MAZEY);
					play = true;
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
		playerY+=N;		
	}
	public void moveUp() {
		coorRow--;
		playerY-=N;						
	}
	public void moveLeft() {
		coorCol--;
		playerX-=N;						
	}
	public void moveRight() {
		coorCol++;
		playerX+=N;							
	}

}
