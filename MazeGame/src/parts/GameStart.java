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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

//
import javax.swing.Timer;
import javax.swing.JPanel;

/*
Game: Mazlzahar's Maze (Maze with more features such as less visibility, collect keys, have "monsters" chasing)
Developer: Alex Rogov
British Columbia, Canada
email: alexandre.rogov@gmail.com	

*/
public class GameStart extends JPanel implements KeyListener, ActionListener{
	//Constant Variables:
	//frame size
	private final int FRAMEWIDTH = 1100;
	private final int FRAMEHEIGHT = 600;
	//dimensions
	private final int N = 32;
	private final int MAX_BLOCK_VISIBILITY = 3;								
	//Maze size, 2D-array coordinates of player
	private final int MAZEX = 14;											
	private final int MAZEY = 14;											
	private final int COORROW = MAZEY/2+1;									
	private final int COORCOL = 1;											
	//Minotaur Speed
	private final int MINOSPEED = 25;								//Smaller number --> Faster Minotaur

	//attributes
	public static boolean play = false;		//Must press Enter to begin	
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
	private int playerY = PLAYERY;									//playerY pixel location	
	private int coorRow = COORROW;									//PlayerRow coordinates
	private int coorCol = COORCOL;									//PlayerCol coordinates
	
	//Minotaur
	private int minoX = PLAYERX;									//References Columns
	private int minoY = PLAYERY;									//References Rows
	private int minoRow = COORROW;
	private int minoCol = COORCOL;
	private boolean active = false;
	private int counterTimer = 0;
	private boolean minoVis = false;
	private int minoVisCounter = 0;
	
	//In-game Objects and locations
	private int ballEndX = ancX+MAZEX*N+N/4;
	private int ballEndY = ancY+(N*MAZEY)/2+N/4 +N;
	private boolean visibility = false;
	private int keysCollected = 0;
	private boolean keysActive = true;
	private int rand1 = 0;
	private int rand2 = 0;
	//key possible locations
	//key 1: top left corner
	private boolean keyOneActive = false;
	private int keyOneX = PLAYERX+N/4;						//x-pixel location 
	private int keyOneY = PLAYERY-N*MAZEY/2+N/4;			//y-pixel location
	//key 2: bottom left corner
	private boolean keyTwoActive = false;
	private int keyTwoX = PLAYERX+N/4;
	private int keyTwoY = PLAYERY+N*MAZEY/2-3*N/4;	
	//key 3: bottom right corner
	private boolean keyThreeActive = false;
	private int keyThreeX = PLAYERX+N*MAZEX-3*N/4;
	private int keyThreeY = PLAYERY+N*MAZEY/2-3*N/4;
	//key 4: right top corner
	private boolean keyFourActive = false;
	private int keyFourX = PLAYERX+N*MAZEX-3*N/4;
	private int keyFourY = PLAYERY-N*MAZEY/2+N/4;
	
	
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
		maze = new MazeGenerator(MAZEX,MAZEY);		//generates maze
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
		//Keys
		if(keyOneActive) {
		g.setColor(Color.WHITE);
		g.fillOval(keyOneX, keyOneY, N/2, N/2);
		} else if(keyTwoActive) {
		g.setColor(Color.WHITE);
		g.fillOval(keyTwoX, keyTwoY, N/2, N/2);
		} 
		if(keyThreeActive) {
		g.setColor(Color.WHITE);
		g.fillOval(keyThreeX, keyThreeY, N/2, N/2);
		} else if(keyFourActive) {
		g.setColor(Color.WHITE);
		g.fillOval(keyFourX, keyFourY, N/2, N/2);
		}
		
		//Minotaur
		if(active || minoVisCounter > 0) {
		g.setColor(Color.RED);
		g.fillOval(minoX, minoY, N, N);
		}
		//Reduce visibility
		if(visibility) { 
		g.setColor(Color.DARK_GRAY);
		Area a1 = new Area(new Rectangle2D.Double(0, 0, 1100, 600)); 							//grey screen around 
		Area a3 = new Area(new Ellipse2D.Double(ballEndX-30, ballEndY-30, 75, 75));				//spotlight around finish line
		Area p = new Area(new Rectangle2D.Double(playerX,playerY,N+1,N+1)); 
		
		if(keyOneActive && (minoVis ^ minoVisCounter==0)) {
		Area key1 = new Area(new Ellipse2D.Double(keyOneX-30,keyOneY-30,75,75));
		a1.subtract(key1);
		}else if(keyTwoActive && (minoVis ^ minoVisCounter==0)) {
		Area key2 = new Area(new Ellipse2D.Double(keyTwoX-30,keyTwoY-30,75,75));
		a1.subtract(key2);
		}
		if(keyThreeActive && (minoVis ^ minoVisCounter==0)) {
		Area key3 = new Area(new Ellipse2D.Double(keyThreeX-30,keyThreeY-30,75,75));
		a1.subtract(key3);
		} else if(keyFourActive && (minoVis ^ minoVisCounter==0)) {
		Area key4 = new Area(new Ellipse2D.Double(keyFourX-30,keyFourY-30,75,75));
		a1.subtract(key4);
		}
		

		
		a1.subtract(p);		//Player is always visible
				
		
		boolean validUp = true;
		boolean validRight = true;
		boolean validDown = true;
		boolean validLeft = true;
		//minotaur start vision
		if(minoVis) {
		for(int i =0; i < 4; i++) {
			for(int j = 0; j<1;j++) {
					Area m = new Area(new Rectangle2D.Double(minoX,minoY,N, N));
					a1.subtract(m);
					if(validUp && maze.maze[minoRow-j][minoCol].getUp() != null) {
						Area out = new Area(new Rectangle2D.Double(minoX,minoY-(j+1)*N,N+1,N+1));
						a1.subtract(out);
					} else {
						validUp = false;
					}
					//right
					if(validRight && maze.maze[minoRow][minoCol+j].getRight() != null) {
						Area out = new Area(new Rectangle2D.Double(minoX+(j+1)*N,minoY,N+1,N+1));
						a1.subtract(out);
					} else {
						validRight = false;
					}
					//down
					if(validDown && maze.maze[minoRow+j][minoCol].getDown() != null) {
						Area out = new Area(new Rectangle2D.Double(minoX,minoY+(j+1)*N,N+1,N+1));
						a1.subtract(out);
					} else {
						validDown = false;
					}
					//left
					if(validLeft && maze.maze[minoRow][minoCol-j].getLeft() != null) {						
						Area out = new Area(new Rectangle2D.Double(minoX-(j+1)*N,minoY,N+1,N+1));
						a1.subtract(out);
					} else {
						validLeft = false;
					}
					
					}
			}
		}
		
		
		//subtracting boxing around player based on walls, i = 0 up |i = 1 right | i = 2 down | i = 3 left 
		for(int i = 0; i < 4; i++) {			//direction
			for(int j = 0; j<MAX_BLOCK_VISIBILITY; j++) {			//# of blocks
		
				//up
				if(validUp && maze.maze[coorRow-j][coorCol].getUp() != null) {
					Area out = new Area(new Rectangle2D.Double(playerX,playerY-(j+1)*N,N+1,N+1));
					a1.subtract(out);
				} else {
					validUp = false;
				}
				//right
				if(validRight && maze.maze[coorRow][coorCol+j].getRight() != null) {
					Area out = new Area(new Rectangle2D.Double(playerX+(j+1)*N,playerY,N+1,N+1));
					a1.subtract(out);
				} else {
					validRight = false;
				}
				//down
				if(validDown && maze.maze[coorRow+j][coorCol].getDown() != null) {
					Area out = new Area(new Rectangle2D.Double(playerX,playerY+(j+1)*N,N+1,N+1));
					a1.subtract(out);
				} else {
					validDown = false;
				}
				//left
				if(validLeft && maze.maze[coorRow][coorCol-j].getLeft() != null) {						
					Area out = new Area(new Rectangle2D.Double(playerX-(j+1)*N,playerY,N+1,N+1));
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
		
		
		a1.subtract(a3);	//subtract finish line circle from Rect
		g2d.fill(a1);
		//Minotaur, If caught with reduced-visibility

		if(minoVisCounter > 1 && playerX == minoX && playerY == minoY) {
			g.setColor(new Color(84,0,0));
			g2d.fill(a1);
			g.setColor(Color.BLACK);
			g.fillRect(playerX-280,playerY-N/2,238,86);
			g.setColor(new Color(201, 201, 201));	//blood red-ish
			g.setFont(new Font(font,Font.BOLD, 30));
			g.drawString("Oh no,", playerX-210, playerY+N/2);
			g.drawString("You were slain!", playerX-270, playerY+3*N/2);
		}
		} else {
			if(minoVisCounter > 1 && playerX == minoX && playerY == minoY) {
				g.setColor(new Color(84,0,0));
				g.fillRect(playerX-280,playerY-N/2,238,86);
				g.setColor(new Color(201, 201, 201));	//blood red-ish
				g.setFont(new Font(font,Font.BOLD, 30));
				g.drawString("Oh no,", playerX-210, playerY+N/2);
				g.drawString("You were slain!", playerX-270, playerY+3*N/2);
			}
		winStringX = 820;
		winStringY = ballEndY-15;
		restartStringX = 765;
		restartStringY = ballEndY+35;
		
		}
		//character visibility
		
		//finish line ball. Place AFTER visibility reducer
		g.setColor(new Color(255,215,0));
		g.fillOval(ballEndX, ballEndY, N/2, N/2);
		if(playerX == (ballEndX-N/4) && playerY == (ballEndY-N/4) && keysCollected == 2) {
			g.setColor(winColor);
			g.setFont(new Font(font,Font.BOLD, 45));
			g.drawString("You Win", winStringX, winStringY);
			
			g.setColor(startColor);
			g.setFont(new Font(font,Font.CENTER_BASELINE,30));
			g.drawString("Press Enter to restart", restartStringX, restartStringY);
		}
		
		g.setColor(Color.GREEN);
		g.setFont(new Font(font,Font.BOLD, 20));
		g.drawString("Keys Collected: " + keysCollected, 40, 40);
		
		}
	}
	
//methods	
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();	
		//If player is at finish ball location and has collected enough keys. Stop game.
		if((playerX == (ballEndX-N/4) && playerY == (ballEndY-N/4)) && keysCollected == 2) {
			play = false;
			active = false;
		}
		//two keys get activated at beginning of round.
		if(keysActive) {
			rand1 = (int)(Math.random()*2+1); //1 or 2
			rand2 = (int)(Math.random()*2+3); //3 or 4
			
			if(rand1==1) keyOneActive = true;
			else if(rand1 == 2)keyTwoActive = true;
			if(rand2 == 3) keyThreeActive = true;
			else if(rand2 == 4)keyFourActive = true;
			keysActive = false;
		}
		//key 1 or 2
		if(rand1 == 1 && keyOneActive && playerX == keyOneX-N/4 && playerY == keyOneY-N/4) {
			keysCollected++;
			keyOneActive = false;
		} else if(rand1 == 2 && keyTwoActive &&  playerX == keyTwoX-N/4 && playerY == keyTwoY-N/4){
			keysCollected++;
			keyTwoActive = false;
		}
		if(rand2 == 3 && keyThreeActive && playerX == keyThreeX-N/4 && playerY == keyThreeY-N/4) {
			keysCollected++;
			keyThreeActive = false;
		} else if(rand2 == 4 && keyFourActive && playerX == keyFourX-N/4 && playerY == keyFourY-N/4){
			keysCollected++;
			keyFourActive = false;
		}
	
		
		//Minotaur catching player
		if((coorRow == minoRow && coorCol==minoCol && active)) {
			play = false;
			active = false;			
		}
		//code for having the Minotaur chase
		//Player has to move from starting position for counter to start
		counterTimer++;
		if(((coorRow > 10 || coorRow < 6) || coorCol > 3) && play && counterTimer >= MINOSPEED) {
			active = true;			
			new Minotaur(minoRow,minoCol,coorRow,coorCol,maze.maze);
			
			//Minotaur start visibility.
			if(minoVisCounter < 4) {
				minoVis = true;
				minoVisCounter++;
			} else 
				minoVis=false;

			if(maze.maze[minoRow][minoCol].getUp() != null && maze.maze[minoRow][minoCol].getUp().isSelected()) {
				minoRow--;
				minoY -= N;
			} else if(maze.maze[minoRow][minoCol].getRight() != null && maze.maze[minoRow][minoCol].getRight().isSelected()) {
				minoCol++;
				minoX += N;
			} else if(maze.maze[minoRow][minoCol].getDown() != null && maze.maze[minoRow][minoCol].getDown().isSelected()) {
				minoRow++;
				minoY += N;
			}else if(maze.maze[minoRow][minoCol].getLeft() != null && maze.maze[minoRow][minoCol].getLeft().isSelected()) {
				minoCol--;	
				minoX -= N;
			} else {
			}
			counterTimer=0;
		}
		if(counterTimer >= 70) {		//arbitrary number
			counterTimer = 0;
		}
		
		repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) 	//exit game. maybe give a prompt
			System.exit(0);
		if(e.getKeyCode() == KeyEvent.VK_V)
			visibility = !visibility;
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
					
					//If you want minotaur to start at last position, comment out the following 4 lines:
					minoX = PLAYERX;
					minoY = PLAYERY;
					minoRow = COORROW;
					minoCol = COORCOL;
					minoVisCounter = 0;
					
					//keys
					keysActive = true;
					keyOneActive = false;
					keyTwoActive = false;
					keyThreeActive = false;
					keyFourActive = false;
					keysCollected = 0;
					
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
