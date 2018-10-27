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

    public static ArrayList<Point> points;

    public static ArrayList<Tile> tiles;

    public static BufferedImage mapImage;

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
	points = new ArrayList<>();
	tiles = new ArrayList<>();

	try {
	    Assets.loadAssets();
	} catch (Exception e) {
	    System.out.println("Error!");
	}

	SleepingBarber sb = new SleepingBarber();

	while (!uiReady) {
	}

	sb.createBasicMap();
	createMapImage();

	points.add(new Point(-100, 150));

	points.add(new Point(200, 150));

	sb.addBarber();

	sb.setVisible(true);
	sb.startScreenThread();

    }

    public void createBasicMap() {
	int[][] map = new int[][]{
	    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
	    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2},
	    {1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1, 2},
	    {1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1, 2},
	    {1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1, 2},
	    {1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1, 2},
	    {1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1, 2},
	    {1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1, 2},
	    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2},
	    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
	    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
	    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
	    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
	    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},};

	for (int i = 0; i < map.length; i++) {
	    for (int j = 0; j < map[i].length; j++) {
		tiles.add(new Tile(j, i, map[i][j]));
	    }
	}
    }

    public void addBarber() {
	Barber b = new Barber(new Point(400, 200));
	new Thread(b).start();
	people.add(b);

    }

    public void addCustomer() {
	addCustomerBtn.setEnabled(false);
	Customer c = new Customer(customerCount, points.get(0));
	c.setGoal(points.get(1));
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
	addBarberBtn.setEnabled(false);
	add(addBarberBtn);

	screen = new Screen();
	add(screen);

	uiReady = true;
    }

    public void startScreenThread() {
	new Thread(screen).start();
    }

    public static void createMapImage() {
	int maxX = 0;
	int maxY = 0;
	for (int i = 0; i < tiles.size(); i++) {
	    if (tiles.get(i).getX() > maxX) {
		maxX = tiles.get(i).getX();
	    }
	    if (tiles.get(i).getY() > maxY) {
		maxY = tiles.get(i).getY();
	    }
	}

	mapImage = new BufferedImage(maxX * Assets.SCALED_TILE_SIZE, maxY * Assets.SCALED_TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
	Graphics g = mapImage.getGraphics();

	for (int i = 0; i < tiles.size(); i++) {
	    g.drawImage(Assets.getTile(tiles.get(i).getId()), tiles.get(i).getX() * Assets.SCALED_TILE_SIZE, tiles.get(i).getY() * Assets.SCALED_TILE_SIZE, null);
	}

    }

    public static BufferedImage getDisplay(int displaceX, int displaceY) {
	BufferedImage img = Tools.newImage(3);
	Graphics g = img.getGraphics();

	int h = Tools.getScreenSize().height;
	int w = Tools.getScreenSize().width;

	g.setColor(Color.GREEN);
	g.fillRect(0, 0, w, h);

	g.drawImage(mapImage, displaceX, displaceY, null);

	ArrayList<Person> ordered = new ArrayList<>();
	while (people.size() > 0) {
	    int minY = 10000;
	    int pos = 0;
	    for (int i = 0; i < people.size(); i++) {
		if (people.get(i).getY() < minY) {
		    minY = people.get(i).getY();
		    pos = i;
		}
	    }
	    ordered.add(people.get(pos));
	    people.remove(pos);
	}

	people = ordered;
	for (int i = 0; i < people.size(); i++) {
	    g.drawImage(people.get(i).getDisplay(), people.get(i).getX() + displaceX, people.get(i).getY() + displaceY, null);
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

    public static BufferedImage getImage(int dx, int dy) {
	BufferedImage img = Tools.newImage(1);
	Graphics g = img.getGraphics();

	g.drawImage(getDisplay(dx, dy), 0, 0, null);
	//g.drawImage(getInterface(), 0, 0, null);

	return img;
    }
}
