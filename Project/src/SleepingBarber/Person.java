/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Andrés Movilla
 */
public class Person extends DisplayObject {

    private final Color darkBlue = new Color(0, 124, 189);
    private final Color lightBlue = new Color(155, 221, 255);

    private final Color darkRed = new Color(227, 95, 52);
    private final Color lightRed = new Color(236, 119, 120);

    protected final Model model;
    private ArrayList<Point> goals;
    private final float walkingSpeed = 7.3f;
    private float xStep, yStep;
    private float remSteps;
    private boolean readyForAction;

    String status;
    final String name;

    public Person(Point p) {
	super(p.getX(), p.getY());
	goals = new ArrayList<Point>();
	model = Tools.createModel(this instanceof Barber);
	name = Tools.randomName(this instanceof Barber);
	status = "";
	readyForAction = true;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public void addGoal(Point goal) {
	goals.add(goal);

	if (!isReadyForAction()) {
	    return;
	}
	
	moveToGoal();
    }
    
    private void moveToGoal() {
	setReadyForAction(false);

	float dX = goals.get(0).getX() - x;
	float dY = goals.get(0).getY() - y;

	float deltaH = (float) Math.sqrt((Math.pow(dX, 2)) + (Math.pow(dY, 2)));

	remSteps = deltaH / walkingSpeed;

	xStep = dX / remSteps;
	yStep = dY / remSteps;

	if (Math.abs(dX) > Math.abs(dY)) {
	    if (dX > 0) {
		model.facing = Model.FACING_RIGHT;
	    } else {
		model.facing = Model.FACING_LEFT;
	    }
	} else {
	    if (dY > 0) {
		model.facing = Model.FACING_DOWN;
	    } else {
		model.facing = Model.FACING_UP;
	    }
	}
	model.walking = true;
    }

    private void update() {
	if (goals.isEmpty()) {
	    model.walking = false;
	    setReadyForAction(true);
	    return;
	}
	
	if (remSteps == 0) {
	    moveToGoal();
	}
	
	if (remSteps < 2) {
	    x = goals.get(0).getX();
	    y = goals.get(0).getY();
	    goals.remove(0);
	    remSteps = 0;
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

	g.setColor(this instanceof Barber ? darkBlue : darkRed);
	g.fillRoundRect(padding, padding, Tools.getInterfaceSize() - (padding * 2), Tools.getInterfacePersonModuleSize() - (padding * 2), radius, radius);

	g.setColor(this instanceof Barber ? lightBlue : lightRed);
	g.fillRoundRect((padding + border), (padding + border), Tools.getInterfaceSize() - ((padding + border) * 2), Tools.getInterfacePersonModuleSize() - ((padding + border) * 2), 10, 10);

	g.setColor(Color.black);
//	g.setFont(new Font("Arial",12,Font.PLAIN));

	g.drawString(this.name, 10, 20);

	g.drawLine(10, 23, Tools.getInterfaceSize() - 10, 23);

//	g.setFont(new Font("Arial", 14, Font.PLAIN));
	g.drawString(this.status, 12, 40);

	return img;
    }

    @Override
    public BufferedImage getImage() {
	update();
	return model.getDisplay();
    }

    /**
     * @return the readyForAction
     */
    public boolean isReadyForAction() {
	return readyForAction;
    }

    /**
     * @param readyForAction the readyForAction to set
     */
    public void setReadyForAction(boolean readyForAction) {
	this.readyForAction = readyForAction;
    }

}
