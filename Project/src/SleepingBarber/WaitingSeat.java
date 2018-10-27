/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Andrés Movilla
 */
public class WaitingSeat extends Point {
    
    public WaitingSeat(int x, int y) {
	super(x, y);
    }
    
    @Override
    public BufferedImage getImage() {
	BufferedImage img = Tools.newImage(4);
	Graphics g = img.getGraphics();
	
	g.drawImage(Assets.getWaitingChair(), 0, 0, null);

	return img;
    }
    
}
