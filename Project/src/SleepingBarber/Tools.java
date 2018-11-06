/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Andrés Movilla
 */
public abstract class Tools {

    public static Dimension getScreenSize() {
	return new Dimension(750, 540);
    }
    
    public static void quickThreadSleep(int time) {
	try {
	    Thread.sleep(time);
	} catch (Exception e) {
	    
	}
    }

    public static int getInterfaceSize() {
	return 200;
    }
    
    public static int getInterfacePersonModuleSize() {
	return 50;
    }
    
    public static Dimension getWindowSize() {
	Dimension d = getScreenSize();
	d.height += 100;
	d.width += 36;
	return d;
    }
    
    public static BufferedImage scaleImage(BufferedImage original, float scale) {
	int newW = (int)(original.getWidth()*scale);
	int newH = (int)(original.getHeight()*scale);
	Image a = original.getScaledInstance(newW, newH, 0);
	BufferedImage result = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);
	Graphics g = result.getGraphics();
	g.drawImage(a,0,0,null);
	
	return result;
    }

    public static BufferedImage newImage(int id) {
	Dimension d = getScreenSize();
	switch (id) {
	    case 1:
		return new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
	    case 2:
		return new BufferedImage(getInterfaceSize(), d.height, BufferedImage.TYPE_INT_ARGB);
	    case 3:
		return new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
	    case 4:
		return new BufferedImage(Assets.FRAME_WIDTH+100, Assets.FRAME_HEIGHT+50, BufferedImage.TYPE_INT_ARGB);
	    default:
		return null;
	}
    }

    public static Rectangle getModuleSize(int id) {
	switch (id) {
	    case 1:
		return new Rectangle(10, 10, 750, 500);
	    case 2:
		return new Rectangle(10, 520, 200, 70);
	    case 3:
		return new Rectangle(220, 520, 160, 70);
	    case 4:
		return new Rectangle(390, 520, 160, 70);
	    case 5:
		return new Rectangle(560, 520, 200, 70);
	    default:
		return null;
	}
    }
    
    public static String randomName(boolean isBarber) {
	String fn;
	String ln;
	
	String[] fns = {
	    "Archie", "Veronica", "Betty", "Natalie", "Hillary", "Jack", "Jill", "Raphael",
	    "Michael", "Angel", "Xyzzy", "Leonardo", "Adam", "Alice", "Bob", "Barry", "Charlie", "Marty"
	};
	
	String[] lns = {
	    "Andrews", "Daniels", "Benson", "Lodge", "Cooper", "Smith", "Jones", "McFly", "Brown", "Black"
	};
	
	fn = isBarber? "Barber" : fns[(int) (Math.random()*fns.length)];
	ln = lns[(int) (Math.random()*lns.length)];
	
	return fn + " " + ln;
    }

    public static String joinStringArray(String[] array, boolean doNL) {
	String result = "";
	for (int i = 0; i < array.length; i++) {
	    result += array[i] + (doNL ? "\n" : "");
	}

	return (doNL ? result.substring(0, result.length() - 1) : result);
    }

    public static String[] splitCharacters(String source) {
	return source.split("(?!^)");
    }

    public static String[] splitLines(String source) {
	return source.split("\\n");
    }

    public static double log2(double a) {
	return Math.log10(a) / Math.log10(2);
    }
}
