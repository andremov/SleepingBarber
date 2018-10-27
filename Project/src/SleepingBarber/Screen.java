/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 *
 * @author Andrés Movilla
 */
public class Screen extends Canvas implements Runnable {

    int dx, dy, curx, cury;
    float dragNerf = 1.8f;

    int startDragX = -1, startDragY = -1;

    public Screen() {
	setBounds(Tools.getModuleSize(1));

	addMouseMotionListener(new MouseMotionAdapter() {
	    @Override
	    public void mouseDragged(MouseEvent e) {
		int moveX = e.getX() - startDragX;
		int moveY = e.getY() - startDragY;

		if (curx + dx <= 0) {
		    curx = (int) (moveX / dragNerf);
		} else {
		    curx = -dx;
		}

		if (cury + dy <= 0) {
		    cury = (int) (moveY / dragNerf);
		} else {
		    cury = -dy;
		}
	    }

	});

	addMouseListener(new MouseAdapter() {

	    @Override
	    public void mouseReleased(MouseEvent e) {
		int moveX = e.getX() - startDragX;
		int moveY = e.getY() - startDragY;

		if (curx + dx <= 0) {
		    curx = (int) (moveX / dragNerf);
		} else {
		    curx = -dx;
		}

		if (cury + dy <= 0) {
		    cury = (int) (moveY / dragNerf);
		} else {
		    cury = -dy;
		}

		dx += curx;
		dy += cury;

		startDragX = -1;
		startDragY = -1;

		curx = 0;
		cury = 0;
	    }

	    @Override
	    public void mousePressed(MouseEvent e) {
		startDragX = e.getX();
		startDragY = e.getY();
	    }

	});
    }

    @Override
    public void run() {
	createBufferStrategy(2);
	while (true) {

	    Graphics g = getBufferStrategy().getDrawGraphics();

	    g.drawImage(SleepingBarber.getImage(dx + curx, dy + cury), 0, 0, null);

	    getBufferStrategy().show();

	    try {
		Thread.sleep(100);
	    } catch (Exception e) {

	    }
	}
    }

}
