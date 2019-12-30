package parts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.geom.Area;

public interface PaintingTypes {
	
	//Graphics
	public void fillRectangle(Color c, int x, int y, int length, int height);
	public void fillOval(Color c, int x, int y, int length, int height);
	public void paintString(Color c, Font f, String s, int x, int y);
	public void paintLine(Color c, int x1,int y1,int x2,int y2);
	
	
}
