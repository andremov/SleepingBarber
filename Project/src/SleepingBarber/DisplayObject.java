/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.image.BufferedImage;

/**
 *
 * @author Andrés Movilla
 */
public abstract class DisplayObject {
    
    protected int x, y;
    protected int extraX, extraY;

    public DisplayObject(int x, int y) {
	this.x = x;
	this.y = y;
	
	if (this instanceof Point) {
	    extraX = 0;
	    extraY = -90;
	} else {
	    extraX = 0;
	    extraY = 0;
	}
    }
    
    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }
    
    public int getDisplayX() {
	return x+extraX;
    }

    public int getDisplayY() {
	return y+extraY;
    }
    
    public abstract BufferedImage getImage();
    
}
