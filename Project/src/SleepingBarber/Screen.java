/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 *
 * @author Andrés Movilla
 */
public class Screen extends Canvas implements Runnable {

    int mapDisplaceX, mapDisplaceY, mapCurX, mapCurY;
    int startMapDragX = -1, startMapDragY = -1;
    boolean draggingMap = false;

    int interfaceDisplaceY, interfaceCurY;
    int startInterfaceDragY = -1;
    boolean draggingInterface = false;

    float dragNerf = 1.8f;

    public Screen() {
	setBounds(Tools.getModuleSize(1));

	addMouseMotionListener(new MouseMotionAdapter() {
	    @Override
	    public void mouseDragged(MouseEvent e) {
		if (draggingMap) {
		    int moveX = e.getX() - startMapDragX;
		    int moveY = e.getY() - startMapDragY;

		    mapCurX = (int) (moveX / dragNerf);
		    mapCurY = (int) (moveY / dragNerf);
		} else if (draggingInterface) {
		    int moveY = e.getY() - startInterfaceDragY;

		    interfaceCurY = (int) (moveY / dragNerf);
		}
	    }

	});

	addMouseListener(new MouseAdapter() {

	    @Override
	    public void mouseReleased(MouseEvent e) {
		if (draggingMap) {
		    int moveX = e.getX() - startMapDragX;
		    int moveY = e.getY() - startMapDragY;

		    mapCurX = (int) (moveX / dragNerf);
		    mapCurY = (int) (moveY / dragNerf);

		    mapDisplaceX += mapCurX;
		    mapDisplaceY += mapCurY;

		    startMapDragX = -1;
		    startMapDragY = -1;

		    mapCurX = 0;
		    mapCurY = 0;
		} else if (draggingInterface) {
		    int moveY = e.getY() - startInterfaceDragY;

		    interfaceCurY = (int) (moveY / dragNerf);

		    interfaceDisplaceY += interfaceCurY;

		    startInterfaceDragY = -1;

		    interfaceCurY = 0;
		}

		draggingInterface = false;
		draggingMap = false;
	    }

	    @Override
	    public void mousePressed(MouseEvent e) {
		if (e.getX() > Tools.getInterfaceSize()) {
		    startMapDragX = e.getX();
		    startMapDragY = e.getY();
		    draggingMap = true;
		} else {
		    startInterfaceDragY = e.getY();
		    draggingInterface = true;
		}
	    }

	});

	addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		    case KeyEvent.VK_UP:
			mapDisplaceY --;
			break;
			
		    case KeyEvent.VK_RIGHT:
			mapDisplaceX ++;
			break;
		
		    case KeyEvent.VK_LEFT:
			mapDisplaceX --;
			break;
		
		    case KeyEvent.VK_DOWN:
			mapDisplaceY ++;
			break;
			
		}
	    }
	});
    }

    @Override
    public void run() {
	createBufferStrategy(2);
	while (true) {

	    Graphics g = getBufferStrategy().getDrawGraphics();
	    
//	    System.out.println(mapCurX + mapDisplaceX);
	    mapCurX = 0;
	    mapDisplaceX = 0;
	    if (mapCurX + mapDisplaceX > 0) {
		mapCurX = -mapDisplaceX;
	    }

	    if (mapCurY + mapDisplaceY > 0) {
		mapCurY = -mapDisplaceY;
	    }

	    if (mapCurX + mapDisplaceX < -SleepingBarber.maxX) {
		mapCurX = -SleepingBarber.maxX-mapDisplaceX;
	    }

	    if (mapCurY + mapDisplaceY < -SleepingBarber.maxY) {
		mapCurY = -SleepingBarber.maxY-mapDisplaceY;
	    }
	    
	    if (interfaceDisplaceY + interfaceCurY < 0) {
		interfaceCurY = -interfaceDisplaceY;
	    }

	    if (interfaceDisplaceY + interfaceCurY > Tools.getInterfacePersonModuleSize() * SleepingBarber.people.size()) {
		interfaceCurY = -interfaceDisplaceY;
	    }

	    g.drawImage(SleepingBarber.getImage(mapDisplaceX + mapCurX, mapDisplaceY + mapCurY, interfaceDisplaceY + interfaceCurY), 0, 0, null);

	    getBufferStrategy().show();

	    try {
		Thread.sleep(100);
	    } catch (Exception e) {

	    }
	}
    }

}
