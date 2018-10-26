/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 *
 * @author andresmovilla
 */
public class SleepingBarber extends JFrame {

    public static Stoplight barberReady;
    public static Stoplight accessSeats;
    public static Stoplight custReady;

    public static int freeSeats;

    static int customerCount = 0;

    public static boolean uiReady = false;

    public static ArrayList<Person> people;

//    JLabel barberReadyLabel;
//    JLabel accessSeatsLabel;
//    JLabel custReadyLabel;
//    JLabel freeSeatsLabel;
    Screen screen;

    JButton addCustomerBtn;
    JButton addBarberBtn;

    int numBarbers;
    int numCustomers;

    public static void main(String[] args) {
	barberReady = new Stoplight(0, Stoplight.MODE_BINARY);
	accessSeats = new Stoplight(1, Stoplight.MODE_BINARY);
	custReady = new Stoplight(0, Stoplight.MODE_COUNTER);
	freeSeats = 10;

	people = new ArrayList<>();

	Assets.loadAssets();

	SleepingBarber sb = new SleepingBarber();

	while (!uiReady) {
	}

	addBarber();

	sb.setVisible(true);
	sb.startScreenThread();

    }

    public static void addBarber() {
	Barber b = new Barber();
	new Thread(b).start();
	people.add(b);

    }

    public static void addCustomer() {
	Customer c = new Customer(customerCount);
	new Thread(c).start();
	people.add(c);
	customerCount++;
    }

    public SleepingBarber() {
	setSize(Tools.getWindowSize());
	setLocationRelativeTo(null);
	setLayout(null);
	setTitle("Sleeping Barber Problem");
	setDefaultCloseOperation(EXIT_ON_CLOSE);

	numBarbers = 0;
	numCustomers = 0;

	init();

	setVisible(false);
    }

    public void init() {

//	add(new Placeholder(1));
//	add(new Placeholder(2));
//	add(new Placeholder(3));
//	add(new Placeholder(4));
//	add(new Placeholder(5));
	addCustomerBtn = new JButton("Add Customer");
	addCustomerBtn.setBounds(Tools.getModuleSize(3));
	addCustomerBtn.setFocusable(false);
	addCustomerBtn.addActionListener((ActionEvent e) -> {
	    addCustomer();
	});
	add(addCustomerBtn);

	addBarberBtn = new JButton("Add Barber");
	addBarberBtn.setBounds(Tools.getModuleSize(4));
	addBarberBtn.setFocusable(false);
	addBarberBtn.addActionListener((ActionEvent e) -> {
	    addBarber();
	});
	add(addBarberBtn);

	screen = new Screen();
	add(screen);

	uiReady = true;
    }

    public void startScreenThread() {
	new Thread(screen).start();
    }

    public static BufferedImage getDisplay() {
	BufferedImage img = Tools.newImage(3);
	Graphics g = img.getGraphics();

	int h = Tools.getScreenSize().height;
	int w = Tools.getScreenSize().width;

	g.setColor(Color.GREEN);
	g.fillRect(0, 0, w, h);

	for (int i = 0; i < people.size(); i++) {
	    g.drawImage(people.get(i).getDisplay(), 300, 100, null);
	}

	return img;
    }

    public static BufferedImage getInterface() {
	BufferedImage img = Tools.newImage(2);
	Graphics g = img.getGraphics();

	int h = Tools.getScreenSize().height;
	int w = Tools.getInterfaceSize();

	g.setColor(Color.red);
	g.fillRect(0, 0, w, h);

	return img;
    }

    public static BufferedImage getImage() {
	BufferedImage img = Tools.newImage(1);
	Graphics g = img.getGraphics();

	g.drawImage(getDisplay(), 0, 0, null);
	//g.drawImage(getInterface(), 0, 0, null);

	return img;
    }
}
