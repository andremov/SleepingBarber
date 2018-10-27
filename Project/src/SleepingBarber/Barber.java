/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

/**
 *
 * @author andresmovilla
 */
public class Barber extends Person implements Runnable {

    int currentState;

    public Barber(Point p) {
	super(p);
    }

    @Override
    public void run() {
	while (true) {

	    currentState = 0;
	    this.setStatus("Waiting for customers.");
	    SleepingBarber.custReady.SLwait();

	    currentState = 1;
	    this.setStatus("Waiting to check seats.");
	    SleepingBarber.accessSeats.SLwait();
	    this.setStatus("Checking seats.");
	    SleepingBarber.freeSeats += 1;

	    currentState = 2;
	    this.setStatus("Ready to cut.");
	    SleepingBarber.barberReady.SLsignal();
	    SleepingBarber.accessSeats.SLsignal();

	}
    }

    public void forceWait() {
	try {
	    Thread.sleep(1000);
	} catch (Exception e) {
	}
    }

    public String toString() {
	if (currentState == 0) {
	    return "Barber State 0: Waiting for customers.";
	}

	if (currentState == 1) {
	    return "Barber State 1: Waiting for seats.";
	}

	if (currentState == 2) {
	    return "Barber State 2: Cutting hair.";
	}

	return "Barber State -1: Error.";

    }

}
