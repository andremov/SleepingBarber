/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.Canvas;
import java.awt.Graphics;

/**
 *
 * @author Andrés Movilla
 */
public class Screen extends Canvas implements Runnable {

    public Screen() {
	setBounds(Tools.getModuleSize(1));
    }

    @Override
    public void run() {
	createBufferStrategy(2);
	while(true) {
	    
	    Graphics g = getBufferStrategy().getDrawGraphics();
	    
	    g.drawImage(SleepingBarber.getImage(), 0, 0, null);
	    
	    getBufferStrategy().show();
	    
	    try {
		Thread.sleep(100);
	    } catch (Exception e) {
		
	    }
	}
    }

    
    
    
}
