package parts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class Painting extends JPanel implements PaintingTypes{

	private Graphics g;
	private int FRAMEWIDTH;
	private int FRAMEHEIGHT;
	private int N;
	private String font;
	Color enterColor;	//~Very light beige
	Color optionsColor;
	Color startColor; 	//~Orange/brown
	Color textColor;
	Color titleColor;	//~Dark Hazelnut color
	Color winColor;		//~Greenish
	
	public Painting(Graphics g, int FRAMEWIDTH, int FRAMEHEIGHT, int N, String font,
			Color enterColor,Color optionsColor,Color startColor,Color textColor,Color titleColor,Color winColor) {
		this.g = g;
		this.FRAMEWIDTH=FRAMEWIDTH;
		this.FRAMEHEIGHT=FRAMEHEIGHT;
		this.N= N;
		this.font = font;
		//colors
		this.enterColor = enterColor;
		this.optionsColor = optionsColor;
		this.startColor = startColor;
		this.textColor = textColor;
		this.titleColor = titleColor;
		this.winColor = winColor;
	}
	public void paint(Graphics g) {}
	
	
	public void titlePage() {
		fillRectangle(startColor,0,0,FRAMEWIDTH, FRAMEHEIGHT);
		paintString(titleColor,new Font(font,Font.BOLD, 50),"Malzar's Maze", (int)(FRAMEWIDTH/3), (int)(FRAMEHEIGHT/3));
		paintString(enterColor,new Font(font,Font.CENTER_BASELINE,36),"Press Enter", (int)(FRAMEWIDTH/3.2)+84, (int)(FRAMEHEIGHT/3)+80);
	}
	public void objectiveOfGamePage() {
		//background
		fillRectangle(new Color(41,33,33),0,0,FRAMEWIDTH,FRAMEHEIGHT);
		//title
		paintString(textColor,new Font(font,Font.BOLD, 50),"Objective of Game", FRAMEWIDTH/2-230, FRAMEHEIGHT/6);
		//mini-background
		fillRectangle(new Color(238,223,166),FRAMEWIDTH/2-320, (int)(FRAMEHEIGHT/6)+45, 630, 385);
		//content
		paintString(new Color(24, 24, 22),new Font(font,Font.ITALIC, 15),"Date: July 14, 1644  ~ hypothesized", FRAMEWIDTH/2-280, FRAMEHEIGHT/4+15);
		paintString(new Color(24, 24, 22),new Font(font,Font.ITALIC, 15),"Time: Unknown ", FRAMEWIDTH/2-280, FRAMEHEIGHT/4+35);		
		paintString(new Color(24, 24, 22),new Font(font,Font.ITALIC, 15),"EXPEDITION JOURNAL", FRAMEWIDTH/2+120, FRAMEHEIGHT/4+15);	
		paintString(new Color(24, 24, 22),new Font(font,Font.ITALIC, 15),"Page 134 / 140", FRAMEWIDTH/2+120, FRAMEHEIGHT/4+35);		

		
		paintString(new Color(24, 24, 22),new Font(font,Font.ITALIC, 20),"\"Last day in months of adventuring deep underground", FRAMEWIDTH/2-300, FRAMEHEIGHT/3+25);
		paintString(new Color(24, 24, 22),new Font(font,Font.ITALIC, 20),"the unexplored Malzar's Maze, I have arrived to", FRAMEWIDTH/2-300, FRAMEHEIGHT/3+60);
		paintString(new Color(24, 24, 22),new Font(font,Font.ITALIC, 20),"what I think is the treasure chamber. Finally,", FRAMEWIDTH/2-300, FRAMEHEIGHT/3+95);
		paintString(new Color(24, 24, 22),new Font(font,Font.ITALIC, 20),"the time for me to collect the two white keys and", FRAMEWIDTH/2-300, FRAMEHEIGHT/3+130);
		paintString(new Color(24, 24, 22),new Font(font,Font.ITALIC, 20),"the gold key if to make this adventure a success.", FRAMEWIDTH/2-300, FRAMEHEIGHT/3+165);

		paintString(new Color(24, 24, 22),new Font(font,Font.ITALIC, 20),"However, haste is of the essence.", FRAMEWIDTH/2-300, FRAMEHEIGHT/3+215);
		paintString(new Color(24, 24, 22),new Font(font,Font.ITALIC, 20),"The bellow sounds rattling the surrounding stones ", FRAMEWIDTH/2-270, FRAMEHEIGHT/3+250);
		paintString(new Color(24, 24, 22),new Font(font,Font.ITALIC, 20),"are undoubtedly approaching...\"", FRAMEWIDTH/2, FRAMEHEIGHT/3+285);

	}
	public void descriptionPage() {
		fillRectangle(optionsColor,0,0,FRAMEWIDTH,FRAMEHEIGHT);
		//Lines to separate 'Controls' and 'Gameplay' and subtitles
		paintLine(titleColor,FRAMEWIDTH/2, 0, FRAMEWIDTH/2, FRAMEHEIGHT);
		paintLine(titleColor,120, (int)(FRAMEHEIGHT/3)+2, 435, (int)(FRAMEHEIGHT/3)+2);
		paintLine(titleColor,FRAMEWIDTH/2+50, (int)(FRAMEHEIGHT/3)+2, FRAMEWIDTH-50, (int)(FRAMEHEIGHT/3)+2);
		//(mini-background layer)=>content
		fillRectangle(titleColor, 315, (int)(FRAMEHEIGHT/3)+20, 120, 190);
		fillRectangle(titleColor, FRAMEWIDTH/2+210, (int)(FRAMEHEIGHT/3)+20, 70, 190);
		fillRectangle(titleColor, FRAMEWIDTH/2+320, (int)(FRAMEHEIGHT/3)+20, 180, 190);
		
		//(Controls)=>title
		paintString(titleColor,new Font(font,Font.BOLD, 50),"Controls", (int)(1.2*FRAMEWIDTH/8), (int)(FRAMEHEIGHT/6));
		//(Controls)=>subtitles
		paintString(titleColor,new Font(font,Font.BOLD, 28),"Key", 150, (int)(FRAMEHEIGHT/3));
		paintString(titleColor,new Font(font,Font.BOLD, 28),"Action", 220, (int)(FRAMEHEIGHT/3));
		//(Controls)=>content
		paintString(titleColor,new Font(font,Font.BOLD, 20),"Up-arrow:", 120, (int)(FRAMEHEIGHT/3)+50);
		paintString(titleColor,new Font(font,Font.BOLD, 20),"Right-arrow:", 120, (int)(FRAMEHEIGHT/3)+100);
		paintString(titleColor,new Font(font,Font.BOLD, 20),"Down-arrow:", 120, (int)(FRAMEHEIGHT/3)+150);
		paintString(titleColor,new Font(font,Font.BOLD, 20),"Left-arrow:", 120, (int)(FRAMEHEIGHT/3)+200);
		paintString(textColor,new Font(font,Font.BOLD, 20),"UP", 335, (int)(FRAMEHEIGHT/3)+50);
		paintString(textColor,new Font(font,Font.BOLD, 20),"RIGHT", 335, (int)(FRAMEHEIGHT/3)+100);
		paintString(textColor,new Font(font,Font.BOLD, 20),"DOWN", 335, (int)(FRAMEHEIGHT/3)+150);
		paintString(textColor,new Font(font,Font.BOLD, 20),"LEFT", 335, (int)(FRAMEHEIGHT/3)+200);
		
		//(Gameplay)=>title
		paintString(titleColor,new Font(font,Font.BOLD, 50),"Gameplay", (int)(5.2*FRAMEWIDTH/8), (int)(FRAMEHEIGHT/6));
		//(Object)=>subtitle
		paintString(titleColor,new Font(font,Font.BOLD, 28),"Objects", FRAMEWIDTH/2+50, (int)(FRAMEHEIGHT/3));
		//(Object)=>Content
		paintString(titleColor,new Font(font,Font.BOLD, 25),"Player:", FRAMEWIDTH/2+50, (int)(FRAMEHEIGHT/3)+50);
		paintString(titleColor,new Font(font,Font.BOLD, 25),"White key:", FRAMEWIDTH/2+50, (int)(FRAMEHEIGHT/3)+100);
		paintString(titleColor,new Font(font,Font.BOLD, 25),"Gold key:", FRAMEWIDTH/2+50, (int)(FRAMEHEIGHT/3)+150);
		paintString(titleColor,new Font(font,Font.BOLD, 25),"Minotaur:", FRAMEWIDTH/2+50, (int)(FRAMEHEIGHT/3)+200);
		//drawing of object
		fillOval(startColor,FRAMEWIDTH/2+228,(int)(FRAMEHEIGHT/3)+30,N,N);	//player
		fillOval(Color.WHITE,FRAMEWIDTH/2+236,(int)(FRAMEHEIGHT/3)+85,N/2,N/2);
		fillOval(new Color(255,215,0),FRAMEWIDTH/2+236,(int)(FRAMEHEIGHT/3)+130,N/2,N/2);
		fillOval(Color.RED,FRAMEWIDTH/2+228,(int)(FRAMEHEIGHT/3)+170,N,N);
		
		//Description of object
		//player description
		paintString(textColor,new Font(font,Font.PLAIN,15),"This is you. Controls", FRAMEWIDTH/2+320+10, (int)(FRAMEHEIGHT/3)+40);
		paintString(textColor,new Font(font,Font.PLAIN,15),"affect this object", FRAMEWIDTH/2+320+10, (int)(FRAMEHEIGHT/3)+60);
		//white key description
		paintString(textColor,new Font(font,Font.PLAIN,15),"Collect two of these", FRAMEWIDTH/2+320+10, (int)(FRAMEHEIGHT/3)+86);
		paintString(textColor,new Font(font,Font.PLAIN,15),"keys first", FRAMEWIDTH/2+320+10, (int)(FRAMEHEIGHT/3)+106);
		//gold key description
		paintString(textColor,new Font(font,Font.PLAIN,15),"Collect this key last to", FRAMEWIDTH/2+320+10, (int)(FRAMEHEIGHT/3)+132);
		paintString(textColor,new Font(font,Font.PLAIN,15),"win the game", FRAMEWIDTH/2+320+10, (int)(FRAMEHEIGHT/3)+152);
		//Minotaur description
		paintString(textColor,new Font(font,Font.PLAIN,15),"Chases you. Get caught", FRAMEWIDTH/2+320+10, (int)(FRAMEHEIGHT/3)+180);
		paintString(textColor,new Font(font,Font.PLAIN,15),"and you lose", FRAMEWIDTH/2+320+10, (int)(FRAMEHEIGHT/3)+200);
		
		//('Enter to continue')=>Naviation
		fillRectangle(titleColor,FRAMEWIDTH-250, FRAMEHEIGHT-100, 230, 50);
		paintString(textColor,new Font(font,Font.PLAIN,20),"'Enter' to continue",FRAMEWIDTH-215,FRAMEHEIGHT-70);
		//('Backspace' to back)=>Naviation
		fillRectangle(titleColor,20, FRAMEHEIGHT-100, 230, 50);
		paintString(textColor,new Font(font,Font.PLAIN,20),"'Backspace' to back",42,FRAMEHEIGHT-70);
	}
	public void difficultyOptionPage() {
		//Background
		fillRectangle(optionsColor,0,0,FRAMEWIDTH,FRAMEHEIGHT);
		//title
		paintString(titleColor,new Font(font,Font.BOLD, 50),"Select Difficulty", FRAMEWIDTH/2-200, FRAMEHEIGHT/6);
		//subtitle
		paintString(titleColor,new Font(font,Font.BOLD, 30),"Minotaur speed depends on your selected difficulty",FRAMEWIDTH/2-345, FRAMEHEIGHT/6+45);
		//mini-backgrounds for content
		fillRectangle(titleColor,FRAMEWIDTH/2-310, (int)(FRAMEHEIGHT/6)+130, 610, 220);
		
		//Diffculty-settings/content
		paintString(titleColor,new Font(font,Font.BOLD, 20),"Press key (1 - 4) to start game:",FRAMEWIDTH/2-310, FRAMEHEIGHT/6+120);
		paintString(Color.GREEN,new Font(font,Font.BOLD, 20),"(1) - Easy",FRAMEWIDTH/2-280, FRAMEHEIGHT/6+170);
		paintString(Color.CYAN,new Font(font,Font.BOLD, 20),"(2) - Normal",FRAMEWIDTH/2-280, FRAMEHEIGHT/6+220);
		paintString(Color.BLUE,new Font(font,Font.BOLD, 20),"(3) - Hard",FRAMEWIDTH/2-280, FRAMEHEIGHT/6+270);
		paintString(Color.RED,new Font(font,Font.BOLD, 20),"(4) - Impossible",FRAMEWIDTH/2-280, FRAMEHEIGHT/6+320);
		paintString(Color.GREEN,new Font(font,Font.PLAIN, 20),":: You can do this with you eyes closed",FRAMEWIDTH/2-80, FRAMEHEIGHT/6+170);
		paintString(Color.CYAN,new Font(font,Font.PLAIN, 20),":: Okay you'll probably need to peak a bit.",FRAMEWIDTH/2-80, FRAMEHEIGHT/6+220);
		paintString(Color.BLUE,new Font(font,Font.PLAIN, 20),":: Are you ready to run?",FRAMEWIDTH/2-80, FRAMEHEIGHT/6+270);
		paintString(Color.RED,new Font(font,Font.PLAIN, 20),":: You will not win. See you in heaven.",FRAMEWIDTH/2-80, FRAMEHEIGHT/6+320);
		
		//'Backspace' to back
		fillRectangle(titleColor,20, FRAMEHEIGHT-100, 230, 50);
		paintString(textColor,new Font(font,Font.PLAIN,20),"'Backspace' to back",42,FRAMEHEIGHT-70);


	}
	
	
	@Override
	public void fillOval(Color c, int x, int y, int length, int height) {
		g.setColor(c);				
		g.fillOval(x,y,length,height);	
	}	
	@Override
	public void fillRectangle(Color c, int x, int y, int length, int height) {
		g.setColor(c);
		g.fillRect(x, y, length, height);
	}
	@Override
	public void paintLine(Color c, int x1, int y1, int x2, int y2) {
		g.setColor(c);
		g.drawLine(x1, y1, x2, y2);
	}
	@Override
	public void paintString(Color c, Font f, String s, int x, int y) {
		g.setColor(c);
		g.setFont(f);
		g.drawString(s, x, y); 
	}


	

		
}
