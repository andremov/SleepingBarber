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
public class Customer extends Person implements Runnable {

    int currentState;
    int id;

    public Customer(int id, Point p) {
	super(p);
	this.id = id;
    }

    @Override
    public void run() {

	currentState = 0;
	this.setStatus("Arriving...");
	
	while (!readyForAction) { }
	
	SleepingBarber.accessSeats.SLwait();

	if (SleepingBarber.freeSeats > 0) {

	    this.setStatus("Sitting down.");
	    SleepingBarber.freeSeats -= 1;
	    this.setGoal(SleepingBarber.assignWaitingSeat());
	    SleepingBarber.accessSeats.SLsignal();
	    while (!readyForAction) { }
	    
	    SleepingBarber.custReady.SLsignal();

	    this.setStatus("Waiting for barber.");
	    SleepingBarber.barberReady.SLwait();

	    this.setGoal(SleepingBarber.assignBarberSeat());
	    while (!readyForAction) { }
	    
	    this.setStatus("Getting hair cut.");
	    try {
		Thread.sleep(5000);
	    } catch (Exception e) { }
	    SleepingBarber.freeBarberSeat();
	} else {
	    SleepingBarber.accessSeats.SLsignal();
	}
	this.setStatus("Leaving...");
	this.setGoal(SleepingBarber.exitDoor);
	while (!readyForAction) { }
	this.setGoal(SleepingBarber.exitPoint);
	while (!readyForAction) { }
	SleepingBarber.deleteCustomer(id);
    }

}
