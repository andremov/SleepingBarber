/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author andresmovilla
 */
public class SleepingBarber extends JFrame implements Runnable {

	public static Stoplight barberReady;
	public static Stoplight accessSeats;
	public static Stoplight custReady;
	
	public static int freeSeats;
	
	public static boolean uiReady = false;
	
	public static void main(String[] args) {
		barberReady = new Stoplight(0,Stoplight.MODE_BINARY);
		accessSeats = new Stoplight(1,Stoplight.MODE_BINARY);
		custReady = new Stoplight(0,Stoplight.MODE_COUNTER);
		freeSeats = 10;
		
		new SleepingBarber();
		
		while (!uiReady) {
			
		}
	}

	//////////////////////////////////////////////////////////////////////////////
	//								JFRAME										//	
	//////////////////////////////////////////////////////////////////////////////
	
	
	JLabel barberReadyLabel;
	JLabel accessSeatsLabel;
	JLabel custReadyLabel;
	JLabel freeSeatsLabel;
	
	JButton addCustomer;
	JButton addBarber;
	
	int numBarbers;
	int numCustomers;
	
	public SleepingBarber() {
		
		setLocationRelativeTo(null);
		setLayout(null);
		setSize(300,300);
		setTitle("Sleeping Barber Problem");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		numBarbers = 0;
		numCustomers = 0;
		
		init();
		
		setVisible(true);
	}
	
	public void init() {
		
	}

	@Override
	public void run() {
		
	}
	

}
