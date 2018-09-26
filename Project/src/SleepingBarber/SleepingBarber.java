/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    
    static int cc = 0;

    public static boolean uiReady = false;

    public static void main(String[] args) {
	barberReady = new Stoplight(0, Stoplight.MODE_BINARY);
	accessSeats = new Stoplight(1, Stoplight.MODE_BINARY);
	custReady = new Stoplight(0, Stoplight.MODE_COUNTER);
	freeSeats = 10;

	SleepingBarber sb = new SleepingBarber();

	while (!uiReady) {

	}

	addBarber();

	while (true) {
	    sb.update();

	    try {
		Thread.sleep(100);
	    } catch (Exception e) {
	    }
	}

    }

    public static void addBarber() {

	new Thread(new Barber()).start();

    }

    public static void addCustomer() {
	cc++;
	new Thread(new Customer(cc)).start();

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
	setSize(300, 350);
	setTitle("Sleeping Barber Problem");
	setDefaultCloseOperation(EXIT_ON_CLOSE);

	numBarbers = 0;
	numCustomers = 0;

	init();

	setVisible(true);
    }

    public void update() {
	barberReadyLabel.setText("Barber ready:\n" + barberReady.toString());
	barberReadyLabel.setBackground(barberReady.getColor());

	accessSeatsLabel.setText("Access Seats:\n" + accessSeats.toString());
	accessSeatsLabel.setBackground(accessSeats.getColor());

	custReadyLabel.setText("Customer ready:\n" + custReady.toString());
	custReadyLabel.setBackground(custReady.getColor());

	freeSeatsLabel.setText("# Free Seats:\n" + freeSeats);
	freeSeatsLabel.setBackground(freeSeats == 0 ? new Color(147, 61, 65) : new Color(154, 205, 50));
    }

    public void init() {
	barberReadyLabel = new JLabel("uno");
	barberReadyLabel.setSize(120, 80);
	barberReadyLabel.setLocation(5, 5);
	barberReadyLabel.setOpaque(true);
	barberReadyLabel.setBackground(Color.yellow);
	add(barberReadyLabel);

	accessSeatsLabel = new JLabel("dos");
	accessSeatsLabel.setSize(120, 80);
	accessSeatsLabel.setLocation(125, 5);
	accessSeatsLabel.setOpaque(true);
	accessSeatsLabel.setBackground(Color.yellow);
	add(accessSeatsLabel);

	custReadyLabel = new JLabel("tres");
	custReadyLabel.setSize(120, 80);
	custReadyLabel.setLocation(5, 95);
	custReadyLabel.setOpaque(true);
	custReadyLabel.setBackground(Color.yellow);
	add(custReadyLabel);

	freeSeatsLabel = new JLabel("cuatro");
	freeSeatsLabel.setSize(120, 80);
	freeSeatsLabel.setLocation(125, 95);
	freeSeatsLabel.setOpaque(true);
	freeSeatsLabel.setBackground(Color.yellow);
	add(freeSeatsLabel);

	addCustomer = new JButton("Add Customer");
	addCustomer.setSize(210, 80);
	addCustomer.setLocation(5, 185);
	addCustomer.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		addCustomer();
	    }
	});
	add(addCustomer);

	uiReady = true;
    }

}
