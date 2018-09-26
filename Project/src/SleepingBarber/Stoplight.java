/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.Color;

/**
 *
 * @author andresmovilla
 */
public class Stoplight {

    static int MODE_BINARY = 0;
    static int MODE_COUNTER = 1;

    private int value;
    private int mode;

    public Stoplight(int value, int mode) {
	this.value = value;
	this.mode = mode;
    }

    public void SLsignal() {
	/*
	    Increments the value of semaphore variable by 1. After the 
	    increment, if the pre-increment value was negative (meaning there 
	    are processes waiting for a resource), it transfers a blocked process 
	    from the semaphore's waiting queue to the ready queue.
	 */

	// TODO: revisa esto
	while (value != 0) {
	    // WAITING
	}

	if (mode == 0) {
	    value = 1;
	} else if (mode == 1) {
	    value++;
	}
    }

    public void SLwait() {

	/*
	    If the value of semaphore variable is not negative, decrement it by 1. 
	    If the semaphore variable is now negative, the process executing wait is
	    blocked (i.e., added to the semaphore's queue) until the value is greater
	    or equal to 1. Otherwise, the process continues execution, having used a 
	    unit of the resource.
	 */
	// TODO: revisa esto
	while (value == 0) {
	    // WAITING
	    try {
		Thread.sleep(100);
	    } catch (Exception e) {
	    }
	}

	if (mode == 0) {
	    value = 0;
	} else if (mode == 1) {
	    value--;
	}

    }

    public Color getColor() {
	if (value == 0) {
	    return new Color(147, 61, 65);
	}

	return new Color(154, 205, 50);

    }

    public int getValue() {
	return value;
    }

    public String toString() {
	return "" + this.getValue();
    }

}
