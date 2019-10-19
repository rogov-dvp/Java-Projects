package objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

												//Key Listener: user's inputs
												//ActionListener: Ball
public class Gameplay extends JPanel implements KeyListener, ActionListener{
	private boolean play = false; //will game start by itself? false -> no
	private int score = 0;		  //Score?
	
	private int totalBricks = 21; //Amount of bricks
	
	private Timer timer;			  //Time
	private int delay = 8;		  //Timer delay
	
	private int playerX = 310;	  //Starting position of platform
	
	private int ballposX = (int)((Math.random()*620)+40);   //Ball starting X position
	private int ballposY = (int)((Math.random()*101)+350);   //Ball starting Y position
	private int ballXdir = -2;	  //negative direction is upwards	
	private	int ballYdir = -4;	  
	
	private MapGenerator map;
	
	public Gameplay() {
		map = new MapGenerator(3,7);				//row,col : Change # amount of bricks 
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);	
		timer = new Timer(delay, this);
		timer.start();
	}
	
	//Sets: starting map, lose, and win.
	public void paint(Graphics g) {
		//background
		g.setColor(Color.black);
		g.fillRect(1,1, 692, 592);
		
		//drawing map
		map.draw((Graphics2D)g);
		
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0,0,3, 592);				//x,y,width,height
		g.fillRect(0,0,692, 3);
		g.fillRect(683,0,3, 592);			
		
		//scores
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD, 25));
		g.drawString(""+score,590,30);					
		
		//The paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100 ,8);
		
		//the ball 
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);
		
		if(totalBricks <= 15) {		//Only need to hit 6 to win game. For demonstration purposes
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.GREEN);
			g.setFont(new Font("serif",Font.BOLD, 30));
			g.drawString("Excellent, You Win!!", 190, 300);
			g.drawString("Score: "+score, 270, 335);		//Note: distances are measured from the top
		
			g.setColor(Color.BLUE);
			g.setFont(new Font("serif",Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 370);

		}
		
		if(ballposY > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.RED);
			g.setFont(new Font("serif",Font.BOLD, 30));
			g.drawString("Game Over, Score: " + score, 190, 300);
			
			g.setFont(new Font("serif",Font.BOLD, 20));
			g.drawString("Press Enter to Restart", 230, 330);
		}
		
		g.dispose();
	}
		
	
	
	@Override
	public void actionPerformed(ActionEvent e) {		
		timer.start();
		//ball
		if(play) {
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))) {
				ballYdir = -ballYdir;
			}
			
			A: for(int i=0; i<map.map.length;i++) {				//first map var is from this class
				for(int j = 0; j< map.map[0].length;j++) {		//second map is from MapGenerator class
					if(map.map[i][j] > 0) {
						int brickX = j*map.brickWidth + 80;
						int brickY = i*map.brickHeight + 50;	
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						Rectangle rect = new Rectangle(brickX,brickY,brickWidth,brickHeight); //invis boarder to detect ball
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;
						
						if(ballRect.intersects(brickRect)) {		//if ball hits invis rect, then change brick value to 0
							map.setBrickValue(0, i, j);
							totalBricks--;
							score += 5;
							
						if(ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width)
							ballXdir = -ballXdir;
						else
							ballYdir = -ballYdir;
						
						break A;
						}
					}
				}
			}
			
			ballposX += ballXdir;
			ballposY += ballYdir;
			if(ballposX<0) {
				ballXdir = -ballXdir;
			}
			if(ballposY<0) {
				ballYdir = -ballYdir;
			}
			if(ballposX > 670) {
				ballXdir = -ballXdir;
			}
		}
		
		repaint(); 
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX >= 583) //check that it doesn't go outside of frame
				playerX = 583;
			 else
				moveRight();
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX <= 3) //check that it doesnt go outside of frame
				playerX = 3;
			 else
				moveLeft();
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballposX = (int)((Math.random()*620)+40);
				ballposY = (int)((Math.random()*101)+350);
				ballXdir = -2;
				ballYdir = -4;
				playerX = 310;
				score = 0;
				totalBricks = 21;		
				map = new MapGenerator(3,7);
				
				repaint();	
			}
		
	}
	}
	public void moveRight() {
		play=true;
		playerX+=50;
	}
	public void moveLeft() {
		play=true;
		playerX-=50;

	}

	
}

