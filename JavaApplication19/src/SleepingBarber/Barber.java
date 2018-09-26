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
public class Barber implements Runnable {

	int currentState;
	
	public Barber() {
		currentState = 2;
	}

	@Override
	public void run() {
		while (true) {
			
			currentState = 0;
			SleepingBarber.custReady.SLwait();
			currentState = 1;
			SleepingBarber.accessSeats.SLwait();
			
			SleepingBarber.freeSeats += 1;
			
			currentState = 2;
			SleepingBarber.barberReady.SLsignal();
			currentState = 3;
			SleepingBarber.accessSeats.SLsignal();
			currentState = 4;
			// TODO: cut hair
		}
	}
	
	public String toString() {
		if (currentState == 0) 
			return "State 0: Waiting for customers.";
		
		if (currentState == 1)
			return "State 1: Waiting for seat.";
		
		if (currentState == 2)
			return "State 2: ?";
		
		if (currentState == 3)
			return "State 3: ?";
		
		if (currentState == 4)
			return "State 4: Ready.";
		
		return "State -1: Error.";
		
	}

}
