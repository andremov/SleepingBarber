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
public class Person extends DisplayObject {

    private Color darkBlue = new Color(	0, 124, 189);
    private Color lightBlue = new Color(155, 221, 255);
    
    private Color darkRed = new Color(227, 95, 52);
    private Color lightRed = new Color(236, 119, 120);
    
    private final Model m;
    private Point goal;
    private final float walkingSpeed = 4.2f;
    private float xStep, yStep;
    private float remSteps;
    protected boolean readyForAction;
    
    String status;
    final String name;

    public Person(Point p) {
	super(p.getX(), p.getY());
	m = Tools.createModel(this instanceof Barber);
	name = Tools.randomName(this instanceof Barber);
	status = "";
	readyForAction = true;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public void setGoal(Point goal) {
	if (!readyForAction) {
	    System.err.println("WHAT IS HAPPENING");
	    return;
	}
	
	readyForAction = false;
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
	    readyForAction = true;
	} else {
	    remSteps--;
	    x += xStep;
	    y += yStep;
	}
    }

    public BufferedImage getInterfaceImage() {
	BufferedImage img = new BufferedImage(Tools.getInterfaceSize(), Tools.getInterfacePersonModuleSize(), BufferedImage.TYPE_INT_ARGB);
	Graphics g = img.getGraphics();

	int padding = 3;
	int border = 3;
	int radius = 20;
	
	g.setColor(this instanceof Barber? darkBlue : darkRed);
	g.fillRoundRect(padding, padding, Tools.getInterfaceSize() - (padding * 2), Tools.getInterfacePersonModuleSize() - (padding * 2), radius, radius);
	
	g.setColor(this instanceof Barber? lightBlue : lightRed);
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

    @Override
    public BufferedImage getImage() {
	update();
	return m.getDisplay();
    }

}
