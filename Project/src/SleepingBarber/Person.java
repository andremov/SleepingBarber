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
public abstract class Person {

    Model m;
    private float x;
    private float y;
    Point goal;
    float walkingSpeed = 2.2f;
    float xStep, yStep;
    float remSteps;

    public Person(Point p) {
	m = Tools.createModel();
	this.x = p.getX();
	this.y = p.getY();
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

	remSteps = dX / (xStep * walkingSpeed);
    }

    public void update() {
	if (goal == null) {
	    return;
	}

	if (remSteps < 1) {
	    x = goal.getX();
	    y = goal.getY();
	    goal = null;
	} else {

	    x += xStep;
	    y += yStep;
	}
    }

    public BufferedImage getDisplay() {
	update();
	return m.getDisplay();
    }

    public int getX() {
	return (int) x;
    }

    public int getY() {
	return (int) y;
    }

}
