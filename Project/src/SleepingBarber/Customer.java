/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

/**
 * 
 * @author Cristhyan De Marchena    - 200082385
 * @author Andrés Movilla           - 200081896
 */
public class Customer extends Person implements Runnable {

    int currentState;
    int id;
    int sittingChairId;

    public Customer(int id, Point p) {
	super(p);
	this.id = id;
    }

    @Override
    public void run() {

	currentState = 0;
	this.setStatus("Arriving...");

	while (!isReadyForAction()) {
	    Tools.quickThreadSleep(10);
	}
	
	this.setStatus("Checking seats...");
	SleepingBarber.accessSeats.SLwait();
	
	
	Tools.quickThreadSleep(1000);

	if (SleepingBarber.getNumFreeSeats() > 0) {

	    this.setStatus("Walking to waiting seat...");
	    Seat ws = SleepingBarber.assignWaitingSeat();
	    this.addGoal(ws);
	    this.sittingChairId = ws.getId();
	    SleepingBarber.accessSeats.SLsignal();
	    while (!isReadyForAction()) {
		Tools.quickThreadSleep(10);
	    }
	    
	    SleepingBarber.custReady.SLsignal();
	    this.model.setSitting(true);
	    this.setStatus("Sitting down...");
	    Tools.quickThreadSleep(1000);
	
	    this.setStatus("Waiting for barber.");
	    
	    SleepingBarber.barberReady.SLwait();
	    SleepingBarber.freeWaitingSeat(sittingChairId);
	    
	    this.model.setSitting(false);
	    this.setStatus("Walking to barber.");
	    this.addGoal(SleepingBarber.toBarberTransition);
	    this.addGoal(SleepingBarber.assignBarberSeat());
	    while (!isReadyForAction()) {
		Tools.quickThreadSleep(10);
	    }
	    SleepingBarber.animReady.SLsignal();
	    
	    this.model.setSitting(true);
	    this.setStatus("Getting hair cut.");
	    SleepingBarber.haircutReady.SLwait();
	    
	    this.model.changeHair();
	    this.model.setSitting(false);
	    this.addGoal(SleepingBarber.fromBarberTransition);
	} else {
	    SleepingBarber.accessSeats.SLsignal();
	}
	
	this.setStatus("Leaving...");
	this.addGoal(SleepingBarber.exitDoor);
	this.addGoal(SleepingBarber.exitTransition);
	this.addGoal(SleepingBarber.exitPoint);
	while (!isReadyForAction()) {
	    Tools.quickThreadSleep(10);
	}
	
	SleepingBarber.deleteCustomer(id);
    }

}
