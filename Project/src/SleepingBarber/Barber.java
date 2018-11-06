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

    final int assignedChair;
    
    public Barber(Point p, int chair) {
	super(p);
	this.assignedChair = chair;
    }

    @Override
    public void run() {
	while (true) {
	    
	    model.sitting = true;
	    this.setStatus("Waiting for customers.");
	    SleepingBarber.custReady.SLwait();

//	    this.setStatus("Waiting to check seats.");
//	    SleepingBarber.accessSeats.SLwait();
//	    
//	    this.setStatus("Checking seats.");
//	    SleepingBarber.freeWaitingSeat();
//	    SleepingBarber.accessSeats.SLsignal();

	    this.setStatus("Ready to cut hair.");
	    SleepingBarber.barberReady.SLsignal();
	    
	    
	    this.setStatus("Waiting for customer to sit.");
	    SleepingBarber.animReady.SLwait();
	    
	    model.sitting = false;
	    this.setStatus("Cutting hair.");
	    this.model.setFacing(Model.FACING_UP);
	    Tools.quickThreadSleep(10000);
	    
	    this.model.setFacing(Model.FACING_DOWN);
	    SleepingBarber.haircutReady.SLsignal();
	    SleepingBarber.freeBarberSeat(assignedChair);

	}
    }

}
