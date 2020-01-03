package parts;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Objects;


public class testing {
	public static void main(String[] args) {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(0);
		ScheduledExecutorService executor1 = Executors.newScheduledThreadPool(1);
		
		Runnable runPath = new Runnable() {
			public void run() {
				
				System.out.println("hehexd");
			}
		};
		Runnable runPath1 = new Runnable() {
			public void run() {
				System.out.println("hehexd2");
			}
		};
		executor.scheduleAtFixedRate(runPath,0, 1, TimeUnit.SECONDS);
		executor1.scheduleAtFixedRate(runPath1, 0, 1500, TimeUnit.MILLISECONDS);
		
		
		System.out.println("Helllllo");
		
	}
}