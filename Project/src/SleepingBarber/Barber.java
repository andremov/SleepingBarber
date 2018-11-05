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

    public Barber(Point p) {
	super(p);
    }

    @Override
    public void run() {
	while (true) {

	    this.setStatus("Waiting for customers.");
	    SleepingBarber.custReady.SLwait();

	    this.setStatus("Waiting to check seats.");
	    SleepingBarber.accessSeats.SLwait();
	    
	    this.setStatus("Checking seats.");
	    SleepingBarber.freeWaitingSeat();
	    SleepingBarber.accessSeats.SLsignal();

	    this.setStatus("Ready to cut hair.");
	    SleepingBarber.barberReady.SLsignal();
	    
	    
	    this.setStatus("Waiting for customer to sit.");
	    SleepingBarber.animReady.SLwait();
	    
	    this.setStatus("Cutting hair.");
	    Tools.quickThreadSleep(10000);
	    
	    SleepingBarber.haircutReady.SLsignal();
	    SleepingBarber.freeBarberSeat();

	}
    }

}
