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

    public void forceWait() {
	try {
	    Thread.sleep(1000);
	} catch (Exception e) {
	}
    }

    public String toString() {
	if (currentState == 0) {
	    return "Customer State 0: Waiting to check seats.";
	}

	if (currentState == 1) {
	    return "Customer State 1: Ready for barber.";
	}

	if (currentState == 2) {
	    return "Customer State 2: Waiting for barber";
	}

	if (currentState == 3) {
	    return "Customer State 3: Left.";
	}

	if (currentState == 4) {
	    return "Customer State 4: Getting hair cut.";
	}

	if (currentState == 5) {
	    return "Customer State 5: Finished hair cut.";
	}

	return "Customer State -1: Error.";
    }

}
