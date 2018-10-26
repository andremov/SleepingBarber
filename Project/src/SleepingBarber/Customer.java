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

    public Customer(int id) {
	super();
	this.currentState = -1;
	this.id = id;
    }

    @Override
    public void run() {

	currentState = 0;
	System.out.println(this.id + ": Customer arrived.");
	SleepingBarber.accessSeats.SLwait();

	if (SleepingBarber.freeSeats > 0) {

	    System.out.println(this.id + ": Customer sits down.");
	    SleepingBarber.freeSeats -= 1;
	    currentState = 1;
	    SleepingBarber.custReady.SLsignal();
	    SleepingBarber.accessSeats.SLsignal();

	    currentState = 2;
	    System.out.println(this.id + ": Customer is waiting for barber.");
	    SleepingBarber.barberReady.SLwait();

	    System.out.println(this.id + ": Customer is getting hair cut.");
	    currentState = 4;

	    currentState = 5;
	    System.out.println(this.id + ": Customer finished hair cut.");

	} else {
	    System.out.println(this.id + ": Customer leaves.");
	    currentState = 3;
	    SleepingBarber.accessSeats.SLsignal();
	    // TODO: leave
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
