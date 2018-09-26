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
public class Customer implements Runnable {

	int currentState;
	
	public Customer() {
		currentState = -1;
	}
	
	@Override
	public void run() {
		while (true) {
			
			currentState = 0;
			SleepingBarber.accessSeats.SLwait();
			
			if (SleepingBarber.freeSeats > 0) {
				
				SleepingBarber.freeSeats -= 1;
				currentState = 1;
				SleepingBarber.custReady.SLsignal();
				currentState = 2;
				SleepingBarber.accessSeats.SLsignal();
				currentState = 3;
				SleepingBarber.barberReady.SLwait();
				// TODO: hair cut
			} else {
				currentState = 4;
				SleepingBarber.accessSeats.SLsignal();
				// TODO: leave
			}
			
			
		}
	}
	
	public String toString() {
		if (currentState == 0) 
			return "State 0: Waiting for seats.";
		
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
	
	
	
	
	/*
		def Customer():
	*/
}
