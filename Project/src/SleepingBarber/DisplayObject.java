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

    public DisplayObject(int x, int y) {
	this.x = x;
	this.y = y;
    }
    
    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }
    
    public abstract BufferedImage getImage();
    
}
