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
 * @author Cristhyan De Marchena    - 200082385
 * @author Andrés Movilla           - 200081896
 */
public class WaitingSeat extends Seat {
    
    public WaitingSeat(int x, int y, int id) {
	super(x, y,id);
    }
    
    @Override
    public BufferedImage getImage() {
	BufferedImage img = Tools.newImage(4);
	Graphics g = img.getGraphics();
	
	g.drawImage(Assets.getWaitingChair(), 0, 0, null);

	return Tools.scaleImage(img, 2);
    }
}
