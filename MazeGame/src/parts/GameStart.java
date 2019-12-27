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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
Game: Mazlzahar's Maze (Maze with more features such as less visibility, collect keys, have "monsters" chasing)
Developer: Alex Rogov
British Columbia, Canada
email: alexandre.rogov@gmail.com	

UPDATE IDEAS:

Update graphics
Create page with controls
High-score
music
thicken walls
esc-key pauses game
Make gold key disappear
random in-game re-maze generator pick-up item
player two? keys: q-a-s-d controls
	- item which removes opponents visibility for a few seconds
	- items: speed boost, 
add strategy to the game?

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
	private int minospeed = 35;								//Smaller number --> Faster Minotaur. SLOWEST:70

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
	private int minoX = PLAYERX;									//mino x pixel location
	private int minoY = PLAYERY;									//mino y pixel location
	private int minoRow = COORROW;									//mino row location	
	private int minoCol = COORCOL;									//mino col location
	private boolean active = false;							//determines if mino is active
	private int counterTimer = 0;							//This timer is apart of dictating mino refresh rate/movement
	private boolean minoVis = false;						//Boolean for mino's visibility
	private int minoVisCounter = 0;							//Used for displaying how long mino is visible under reduced visibility
	
	//In-game Objects and locations
	private int ballEndX = ancX+MAZEX*N+N/4;				//finish line(or ball) x pixel location
	private int ballEndY = ancY+(N*MAZEY)/2+N/4 +N;			//finish line(or ball) y pixel location
	private boolean visibility = false;
	private int keysCollected = 0;		
	private boolean keysActive = true;						//determines if key is visible under reduced visibility
	private int rand1 = 0;									//used for randomely selecting one left side key
	private int rand2 = 0;									//used for randomely selecting one right side key
	//key possible locations
	//key 1: top left corner
	private boolean keyOneActive = false;					//To show if key is active or not
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
		Color textColor = new Color(255, 240, 194);

		//opening page
		if(!play && counter == 0) {	//---------------------------------------------------------------------------------
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
			
		//options page 1: Controls/Gameplay
		} else if(!play && counter == 1) {	//---------------------------------------------------------------------------------
			
			int x = 70;
			//background
			g.setColor(optionsColor);
			g.fillRect(0,0,FRAMEWIDTH,FRAMEHEIGHT);
			//Lines to separate 'Controls' and 'Objective' and black box
			g.setColor(titleColor);
			g.drawLine(FRAMEWIDTH/2, 0, FRAMEWIDTH/2, FRAMEHEIGHT);
			g.drawLine(50+x, (int)(FRAMEHEIGHT/3)+2, 50+x+315, (int)(FRAMEHEIGHT/3)+2);
			g.drawLine(FRAMEWIDTH/2+50, (int)(FRAMEHEIGHT/3)+2, FRAMEWIDTH-50, (int)(FRAMEHEIGHT/3)+2);
			//g.setColor(Color.BLACK);
			g.fillRect(245+x, (int)(FRAMEHEIGHT/3)+20, 50+x, 190);
			g.fillRect(FRAMEWIDTH/2+210, (int)(FRAMEHEIGHT/3)+20, 70, 190);
			g.fillRect(FRAMEWIDTH/2+320, (int)(FRAMEHEIGHT/3)+20, 180, 190);

			
			//(Controls)=>title
			g.setColor(titleColor);
			g.setFont(new Font(font,Font.BOLD, 50));
			g.drawString("Controls", (int)(1.2*FRAMEWIDTH/8), (int)(FRAMEHEIGHT/6)); 
			
			//(Controls)=>Content
			g.setColor(titleColor);
			g.setFont(new Font(font,Font.BOLD, 28));
			g.drawString("Key", 50+x+30, (int)(FRAMEHEIGHT/3));
			g.drawString("Action", 50+x+200, (int)(FRAMEHEIGHT/3));	
			
			g.setFont(new Font(font,Font.PLAIN, 20));
			g.drawString("Up-arrow:", 50+x, (int)(FRAMEHEIGHT/3)+50); 
			g.drawString("Right-arrow:", 50+x, (int)(FRAMEHEIGHT/3)+100); 
			g.drawString("Down-arrow:", 50+x, (int)(FRAMEHEIGHT/3)+150); 
			g.drawString("Left-arrow:", 50+x, (int)(FRAMEHEIGHT/3)+200); 
			g.setColor(textColor);
			g.setFont(new Font(font,Font.PLAIN, 20));
			g.drawString("UP", 265+x, (int)(FRAMEHEIGHT/3)+50);
			g.drawString("RIGHT", 265+x, (int)(FRAMEHEIGHT/3)+100);
			g.drawString("DOWN", 265+x, (int)(FRAMEHEIGHT/3)+150);
			g.drawString("LEFT", 265+x, (int)(FRAMEHEIGHT/3)+200);			

			
			//(Gameplay)=>title
			g.setColor(titleColor);
			g.setFont(new Font(font,Font.BOLD, 50));
			g.drawString("Gameplay", (int)(5.2*FRAMEWIDTH/8), (int)(FRAMEHEIGHT/6)); 
			//(Object)=>Content
			g.setColor(titleColor);
			g.setFont(new Font(font,Font.BOLD, 28));
			g.drawString("Objects", FRAMEWIDTH/2+50, (int)(FRAMEHEIGHT/3));
			//object
			g.setFont(new Font(font,Font.PLAIN, 25));
			g.drawString("Player:", FRAMEWIDTH/2+50, (int)(FRAMEHEIGHT/3)+50); 
			g.drawString("White key:", FRAMEWIDTH/2+50, (int)(FRAMEHEIGHT/3)+100); 
			g.drawString("Gold key:", FRAMEWIDTH/2+50, (int)(FRAMEHEIGHT/3)+150); 
			g.drawString("Minotaur:", FRAMEWIDTH/2+50, (int)(FRAMEHEIGHT/3)+200); 
			//drawing of object
			g.setColor(startColor);				
			g.fillOval(FRAMEWIDTH/2+228,(int)(FRAMEHEIGHT/3)+30,N,N);	//player
			g.setColor(Color.WHITE);
			g.fillOval(FRAMEWIDTH/2+236,(int)(FRAMEHEIGHT/3)+85,N/2,N/2);	//white key
			g.setColor(new Color(255,215,0));
			g.fillOval(FRAMEWIDTH/2+236,(int)(FRAMEHEIGHT/3)+130,N/2,N/2);	//gold key
			g.setColor(Color.RED);
			g.fillOval(FRAMEWIDTH/2+228,(int)(FRAMEHEIGHT/3)+170,N,N);	//minotaur
			
			//Description of object
			g.setFont(new Font(font,Font.PLAIN,15));
			g.setColor(textColor);
			//player description
			g.drawString("This is you. Controls", FRAMEWIDTH/2+320+10, (int)(FRAMEHEIGHT/3)+40);
			g.drawString("affect this object", FRAMEWIDTH/2+320+10, (int)(FRAMEHEIGHT/3)+60);
			//white key description
			g.drawString("Collect two of these", FRAMEWIDTH/2+320+10, (int)(FRAMEHEIGHT/3)+86);			
			g.drawString("keys first", FRAMEWIDTH/2+320+10, (int)(FRAMEHEIGHT/3)+106);			
			//gold key description
			g.drawString("Collect this key last to", FRAMEWIDTH/2+320+10, (int)(FRAMEHEIGHT/3)+132);			
			g.drawString("win the game", FRAMEWIDTH/2+320+10, (int)(FRAMEHEIGHT/3)+152);			
			//Minotaur description
			g.drawString("Chases you. Get caught", FRAMEWIDTH/2+320+10, (int)(FRAMEHEIGHT/3)+180);			
			g.drawString("and you lose", FRAMEWIDTH/2+320+10, (int)(FRAMEHEIGHT/3)+200);			

			//Press Enter to start
			g.setColor(titleColor);
			g.fillRect(FRAMEWIDTH-250, FRAMEHEIGHT-100, 230, 50);
			g.setColor(textColor);
			g.setFont(new Font(font,Font.PLAIN,20));
			g.drawString("'Enter' to continue",FRAMEWIDTH-215,FRAMEHEIGHT-70);
			
			//Press 'Back' to back
			g.setColor(titleColor);
			g.fillRect(20, FRAMEHEIGHT-100, 230, 50);
			g.setColor(textColor);
			g.setFont(new Font(font,Font.PLAIN,20));
			g.drawString("'Backspace' to back",42,FRAMEHEIGHT-70);
			
		//Difficulty options page:
		}else if(counter == 2) { //--------------------------------------------------------------------------------------------------
			//background
			g.setColor(optionsColor);
			g.fillRect(0,0,FRAMEWIDTH,FRAMEHEIGHT);
			//title/subtitle
			g.setColor(titleColor);
			g.setFont(new Font(font,Font.BOLD, 50));
			g.drawString("Select Difficulty", FRAMEWIDTH/2-200, FRAMEHEIGHT/6);
			g.setFont(new Font(font,Font.PLAIN, 30));
			g.drawString("Minotaur speed depends on your selected difficulty",FRAMEWIDTH/2-345, FRAMEHEIGHT/6+45);			
			//brown-box
			g.fillRect(FRAMEWIDTH/2-310, (int)(FRAMEHEIGHT/6)+130, 610, 220);

			
			g.setFont(new Font(font,Font.BOLD, 20));
			g.drawString("Press key (1 - 4) to start game:",FRAMEWIDTH/2-310, FRAMEHEIGHT/6+120);
			g.setColor(Color.GREEN);
			g.drawString("(1) - Easy",FRAMEWIDTH/2-280, FRAMEHEIGHT/6+170);
			g.setColor(Color.CYAN);
			g.drawString("(2) - Normal",FRAMEWIDTH/2-280, FRAMEHEIGHT/6+220);
			g.setColor(Color.BLUE);
			g.drawString("(3) - Hard",FRAMEWIDTH/2-280, FRAMEHEIGHT/6+270);
			g.setColor(Color.RED);
			g.drawString("(4) - Impossible",FRAMEWIDTH/2-280, FRAMEHEIGHT/6+320);
			g.setFont(new Font(font,Font.PLAIN, 20));
			g.setColor(Color.GREEN);
			g.drawString(":: You can do this with you eyes closed",FRAMEWIDTH/2-80, FRAMEHEIGHT/6+170);
			g.setColor(Color.CYAN);
			g.drawString(":: Okay you'll probably need to peak a bit.",FRAMEWIDTH/2-80, FRAMEHEIGHT/6+220);
			g.setColor(Color.BLUE);
			g.drawString(":: Are you ready to run?",FRAMEWIDTH/2-80, FRAMEHEIGHT/6+270);
			g.setColor(Color.RED);
			g.drawString(":: You will not win. See you in heaven.",FRAMEWIDTH/2-80, FRAMEHEIGHT/6+320);

			//Press 'Back' to back
			g.setColor(titleColor);
			g.fillRect(20, FRAMEHEIGHT-100, 230, 50);
			g.setColor(textColor);
			g.setFont(new Font(font,Font.PLAIN,20));
			g.drawString("'Backspace' to back",42,FRAMEHEIGHT-70);

			
		
		}else {//------------------------------------------------------------------------------------------------------------------------
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
		
		//Key visibility under reduced visibility
		if(keyOneActive) {
		Area key1 = new Area(new Ellipse2D.Double(keyOneX-N/2,keyOneY-N/2,1.5*N,1.5*N));
		a1.subtract(key1);
		}else if(keyTwoActive) {
		Area key2 = new Area(new Ellipse2D.Double(keyTwoX-N/2,keyTwoY-N/2,1.5*N,1.5*N));
		a1.subtract(key2);
		}
		if(keyThreeActive) {
		Area key3 = new Area(new Ellipse2D.Double((int)(keyThreeX-N/2),(int)(keyThreeY-N/2),1.5*N,1.5*N));
		a1.subtract(key3);
		} else if(keyFourActive) {
		Area key4 = new Area(new Ellipse2D.Double((int)(keyFourX-N/2),(int)(keyFourY-N/2),1.5*N,1.5*N));
		a1.subtract(key4);
		}
		

		
		a1.subtract(p);		//Player is always visible
				
		
		boolean validUp = true;
		boolean validRight = true;
		boolean validDown = true;
		boolean validLeft = true;
		//minotaur start vision. Has same vision as player. Difference is that it sees one block ahead max
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
			
			//Press Enter to start
			g.setColor(Color.BLACK);
			g.fillRect(FRAMEWIDTH-250, FRAMEHEIGHT-100, 230, 50);
			g.setColor(new Color(201, 201, 201));
			g.setFont(new Font(font,Font.PLAIN,20));
			g.drawString("'Enter' to restart",FRAMEWIDTH-205,FRAMEHEIGHT-70);
			

		}
		} else {
			if(minoVisCounter > 1 && playerX == minoX && playerY == minoY) {
				g.setColor(new Color(84,0,0));
				g.fillRect(playerX-280,playerY-N/2,238,86);
				g.setColor(new Color(201, 201, 201));	//blood red-ish
				g.setFont(new Font(font,Font.BOLD, 30));
				g.drawString("Oh no,", playerX-210, playerY+N/2);
				g.drawString("You were slain!", playerX-270, playerY+3*N/2);
				
				//Press Enter to start
				g.setColor(new Color(84,0,0));
				g.fillRect(FRAMEWIDTH-250, FRAMEHEIGHT-100, 230, 50);
				g.setColor(new Color(201, 201, 201));
				g.setFont(new Font(font,Font.PLAIN,20));
				g.drawString("'Enter' to restart",FRAMEWIDTH-205,FRAMEHEIGHT-70);

			}
		//Changes Win string location when no visibility
		winStringX = 820;
		winStringY = ballEndY-15;
		restartStringX = 765;
		restartStringY = ballEndY+35;
		
		}
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
		//Keys collected sign
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
		//two keys get activated at beginning of round. Only 1 key per side (1 for left side and 1 for right side)
		if(keysActive) {
			rand1 = (int)(Math.random()*2+1); //1 or 2
			rand2 = (int)(Math.random()*2+3); //3 or 4
			
			if(rand1==1) keyOneActive = true;
			else if(rand1 == 2)keyTwoActive = true;
			if(rand2 == 3) keyThreeActive = true;
			else if(rand2 == 4)keyFourActive = true;
			keysActive = false;
		}
		//key 1 or 2.
		if(rand1 == 1 && keyOneActive && playerX == keyOneX-N/4 && playerY == keyOneY-N/4) {
			keysCollected++;
			keyOneActive = false;
		} else if(rand1 == 2 && keyTwoActive &&  playerX == keyTwoX-N/4 && playerY == keyTwoY-N/4){
			keysCollected++;
			keyTwoActive = false;
		}
		//
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
		if(((coorRow > 10 || coorRow < 6) || coorCol > 3) && play && counterTimer >= minospeed) {
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
		if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
			if(counter==3) {
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
			}
			counter--;
			play=false;
			
		}
		if(e.getKeyCode() == KeyEvent.VK_1) {
			if(counter==2) {
			counter++;
			visibility = true;
			play = true;
			minospeed=55;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_2) {
			if(counter==2) {
			counter++;
			visibility = true;
			play = true;
			minospeed=34;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_3) {
			if(counter==2) {
			counter++;
			visibility = true;
			play = true;
			minospeed=24;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_4 && counter==2) {
			if(counter==2) {
			counter++;
			visibility = true;
			play = true;
			minospeed=12;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			switch(counter) {		//Changes screens or restarts game
			case 0:					//Leaves title page
				counter++;
				break;
			case 1:
				counter++;
				break;
			case 3:					//regenerate maze
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
