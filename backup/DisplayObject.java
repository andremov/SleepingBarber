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
    private final int modelX, modelY;

    public DisplayObject(int x, int y) {
	this.x = x;
	this.y = y;
	
	
	modelX = -(Assets.FRAME_WIDTH) + ((this instanceof Person)? -100 : 0);
	modelY = -(Assets.FRAME_HEIGHT)*2;
    }
    
    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }
    
    public int getDisplayX() {
	return x+modelX;
    }

    public int getDisplayY() {
	return y+modelY;
    }
    
    public abstract BufferedImage getImage();
    
}
