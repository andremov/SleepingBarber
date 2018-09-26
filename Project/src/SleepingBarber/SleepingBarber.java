/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author andresmovilla
 */
public class SleepingBarber extends JFrame {

    public static Stoplight barberReady;
    public static Stoplight accessSeats;
    public static Stoplight custReady;

    public static int freeSeats;

    public static boolean uiReady = false;

    public static void main(String[] args) {
	barberReady = new Stoplight(0, Stoplight.MODE_BINARY);
	accessSeats = new Stoplight(1, Stoplight.MODE_BINARY);
	custReady = new Stoplight(0, Stoplight.MODE_COUNTER);
	freeSeats = 10;

	new SleepingBarber();

	while (!uiReady) {

	}
	
	new Barber();
	
	while (true) {
	    
	}
	
    }

    //////////////////////////////////////////////////////////////////////////////
    //				    	JFRAME					//	
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
	setSize(200, 350);
	setTitle("Sleeping Barber Problem");
	setDefaultCloseOperation(EXIT_ON_CLOSE);

	numBarbers = 0;
	numCustomers = 0;

	init();

	setVisible(true);
    }

    public void init() {
	barberReadyLabel = new JLabel("uno");
	barberReadyLabel.setSize(80,80);
	barberReadyLabel.setLocation(5,5);
	barberReadyLabel.setBackground(Color.yellow);
	add(barberReadyLabel);
	
	accessSeatsLabel = new JLabel("dos");
	accessSeatsLabel.setSize(80,80);
	accessSeatsLabel.setLocation(95,5);
	accessSeatsLabel.setBackground(Color.yellow);
	add(accessSeatsLabel);
	
	custReadyLabel = new JLabel("tres");
	custReadyLabel.setSize(80,80);
	custReadyLabel.setLocation(5,95);
	custReadyLabel.setBackground(Color.yellow);
	add(custReadyLabel);
	
	freeSeatsLabel = new JLabel("cuatro");
	freeSeatsLabel.setSize(80,80);
	freeSeatsLabel.setLocation(95,95);
	freeSeatsLabel.setBackground(Color.yellow);
	add(freeSeatsLabel);
	
	addCustomer = new JButton("Add Customer");
	addCustomer.setSize(170,80);
	addCustomer.setLocation(5,185);
	add(addCustomer);
	
	uiReady=true;
    }

}
