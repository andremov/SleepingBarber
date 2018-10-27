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
	    SleepingBarber.freeWaitingSeat();

	    currentState = 2;
	    this.setStatus("Ready to cut hair.");
	    SleepingBarber.barberReady.SLsignal();
	    SleepingBarber.accessSeats.SLsignal();
	    
	    this.setStatus("Ready to cut hair.");
	    

	}
    }

}
