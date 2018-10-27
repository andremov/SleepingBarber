/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Andrés Movilla
 */
public class Person {

    private Model m;
    private float x;
    private float y;
    private Point goal;
    private final float walkingSpeed = 4.2f;
    private float xStep, yStep;
    private float remSteps;
    
    String status;
    final String name;

    public Person(Point p) {
	m = Tools.createModel(this instanceof Barber);
	this.x = p.getX();
	this.y = p.getY();
	name = Tools.randomName(this instanceof Barber);
	status = "";
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public void setGoal(Point goal) {
	this.goal = goal;

	float dX = goal.getX() - x;
	float dY = goal.getY() - y;

	float deltaH = (float) Math.sqrt((Math.pow(dX, 2)) + (Math.pow(dY, 2)));

	float xPercent = dX / deltaH;
	float yPercent = dY / deltaH;

	xStep = walkingSpeed * xPercent;
	yStep = walkingSpeed * yPercent;

	remSteps = dX / xStep;

	if (Math.abs(dX) > Math.abs(dY)) {
	    if (dX > 0) {
		m.facing = Model.FACING_RIGHT;
	    } else {
		m.facing = Model.FACING_LEFT;
	    }
	} else {
	    if (dY > 0) {
		m.facing = Model.FACING_DOWN;
	    } else {
		m.facing = Model.FACING_UP;
	    }
	}
	m.walking = true;
    }

    public void update() {
	if (goal == null) {
	    return;
	}

	if (remSteps < 1) {
	    x = goal.getX();
	    y = goal.getY();
	    goal = null;
	    m.walking = false;
	} else {
	    remSteps--;
	    x += xStep;
	    y += yStep;
	}
    }

    public BufferedImage getDisplay() {
	update();
	return m.getDisplay();
    }

    public BufferedImage getInterfaceImage() {
	BufferedImage img = new BufferedImage(Tools.getInterfaceSize(), Tools.getInterfacePersonModuleSize(), BufferedImage.TYPE_INT_ARGB);
	Graphics g = img.getGraphics();

	int padding = 3;
	int border = 3;
	int radius = 20;
	
	g.setColor(Color.black);
	g.fillRoundRect(padding, padding, Tools.getInterfaceSize() - (padding * 2), Tools.getInterfacePersonModuleSize() - (padding * 2), radius, radius);
	
	g.setColor(Color.white);
	g.fillRoundRect((padding + border), (padding + border), Tools.getInterfaceSize() - ((padding + border) * 2), Tools.getInterfacePersonModuleSize() - ((padding + border) * 2), 10, 10);
	
	g.setColor(Color.black);
//	g.setFont(new Font("Arial",12,Font.PLAIN));
	
	g.drawString(this.name, 10, 20);

	g.drawLine(10, 23, Tools.getInterfaceSize() - 10, 23);

//	g.setFont(new Font("Arial", 14, Font.PLAIN));
	g.drawString(this.status, 12, 40);

	return img;
    }

    public int getX() {
	return (int) x;
    }

    public int getY() {
	return (int) y;
    }

}
