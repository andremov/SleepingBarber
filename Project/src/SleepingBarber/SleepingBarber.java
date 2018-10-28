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

    static int customerCount = 0;

    public static boolean uiReady = false;

    public static ArrayList<Person> people;

    public static ArrayList<Tile> tiles;

    public static BufferedImage mapImage;

    public static Point enterPoint;
    public static Point exitPoint;

    public static Point enterDoor;
    public static Point exitDoor;
    
    public static Point enterTransition;
    public static Point exitTransition;
    
    public static Point toBarberTransition;
    public static Point fromBarberTransition;

    public static ArrayList<BarberSeat> occupiedBarberSeats;
    public static ArrayList<BarberSeat> freeBarberSeats;

    public static ArrayList<WaitingSeat> occupiedWaitingSeats;
    public static ArrayList<WaitingSeat> freeWaitingSeats;

    public static ArrayList<DisplayObject> orderedDisplayObjects;

    Screen screen;

    JButton addCustomerBtn;
    JButton addBarberBtn;

    public static void deleteCustomer(int id) {
	for (int i = 0; i < people.size(); i++) {
	    if (people.get(i) instanceof Customer) {
		Customer c = (Customer) people.get(i);
		if (c.id == id) {
		    people.remove(i);
		}
	    }
	}
	for (int i = 0; i < orderedDisplayObjects.size(); i++) {
	    if (orderedDisplayObjects.get(i) instanceof Customer) {
		Customer c = (Customer) orderedDisplayObjects.get(i);
		if (c.id == id) {
		    orderedDisplayObjects.remove(i);
		}
	    }
	}
    }

    public static void freeBarberSeat() {
	BarberSeat b = occupiedBarberSeats.remove(0);
	freeBarberSeats.add(b);
    }

    public static void freeWaitingSeat() {
	WaitingSeat b = occupiedWaitingSeats.remove(0);
	freeWaitingSeats.add(b);
    }

    public static BarberSeat assignBarberSeat() {
	BarberSeat b = freeBarberSeats.remove(0);
	occupiedBarberSeats.add(b);
	return b;
    }

    public static WaitingSeat assignWaitingSeat() {
	WaitingSeat b = freeWaitingSeats.remove(0);
	occupiedWaitingSeats.add(b);
	return b;
    }

    public static void main(String[] args) {
	barberReady = new Stoplight(0, Stoplight.MODE_COUNTER);
	accessSeats = new Stoplight(1, Stoplight.MODE_BINARY);
	custReady = new Stoplight(0, Stoplight.MODE_COUNTER);

	orderedDisplayObjects = new ArrayList<>();
	people = new ArrayList<>();
	occupiedBarberSeats = new ArrayList<>();
	freeBarberSeats = new ArrayList<>();
	occupiedWaitingSeats = new ArrayList<>();
	freeWaitingSeats = new ArrayList<>();
	tiles = new ArrayList<>();

	try {
	    Assets.loadAssets();
	} catch (Exception e) {
	    System.err.println("Error loading assets!");
	}

	SleepingBarber sb = new SleepingBarber();

	while (!uiReady) {
	    Tools.quickThreadSleep(10);
	}

	sb.createBasicMap();
	createMapImage();

	enterPoint = new Point(-100, 200);
	enterTransition = new Point(400, 200);
	enterDoor = new Point(400, 170);

	exitDoor = new Point(450, 170);
	exitTransition = new Point(450, 200);
	exitPoint = new Point(800, 200);
	
	toBarberTransition = new Point(400, 100);
	fromBarberTransition = new Point(580, 150);

	addWaitingSeat(300, 60);
	addWaitingSeat(370, 60);
	addWaitingSeat(440, 60);
	addWaitingSeat(510, 60);

	addBarberSeat(600, 110);

	sb.setVisible(true);
	sb.startScreenThread();

    }

    public static void addWaitingSeat(int x, int y) {
	WaitingSeat ws = new WaitingSeat(x, y);
	freeWaitingSeats.add(ws);
	orderedDisplayObjects.add(ws);
    }

    public static void addBarberSeat(int x, int y) {
	BarberSeat bs = new BarberSeat(x, y);
	freeBarberSeats.add(bs);
	orderedDisplayObjects.add(bs);

	Barber b = new Barber(new Point(x + 25, y + 20));
	new Thread(b).start();
	people.add(b);
	orderedDisplayObjects.add(b);
    }

    public void createBasicMap() {
//	int[][] map = new int[][]{
//	    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//	    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2},
//	    {1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1, 2},
//	    {1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1, 2},
//	    {1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1, 2},
//	    {1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1, 2},
//	    {1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1, 2},
//	    {1, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 1, 2},
//	    {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2},
//	    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//	    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//	    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
//	    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
//	    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
//	};
	int rows = 20;
	int columns = 20;

	for (int i = 0; i < rows; i++) {
	    for (int j = 0; j < columns; j++) {
		tiles.add(new Tile(j, i, 2));
	    }
	}
    }

//    public void addBarber() {
//	Barber b = new Barber(new Point(400, 200));
//	new Thread(b).start();
//	people.add(b);
//    }
    public void addCustomer() {
	Customer c = new Customer(customerCount, enterPoint);
	c.addGoal(enterTransition);
	c.addGoal(enterDoor);
	new Thread(c).start();
	people.add(c);
	customerCount++;
	orderedDisplayObjects.add(c);
    }

    public SleepingBarber() {
	setSize(Tools.getWindowSize());
	setLocationRelativeTo(null);
	setLayout(null);
	setTitle("Sleeping Barber Problem");
	setDefaultCloseOperation(EXIT_ON_CLOSE);

	initUI();

	setVisible(false);
    }

    public void initUI() {

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
//	    addBarber();
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

    public static int getNumFreeSeats() {
	return freeWaitingSeats.size();
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

	orderedDisplayObjects.sort((DisplayObject o1, DisplayObject o2) -> {
	    if (o1.getY() < o2.getY()) {
		return -1;
	    } else if (o2.getY() < o1.getY()) {
		return 1;
	    } else {
		return 1;
	    }
	});

	for (int i = 0; i < orderedDisplayObjects.size(); i++) {
	    g.drawImage(orderedDisplayObjects.get(i).getImage(), orderedDisplayObjects.get(i).getX() + displaceX, orderedDisplayObjects.get(i).getY() + displaceY, null);
	}
	return img;
    }

    public static BufferedImage getInterface(int iy) {
	BufferedImage img = Tools.newImage(2);
	Graphics g = img.getGraphics();

	int h = Tools.getScreenSize().height;
	int w = Tools.getInterfaceSize();

	for (int i = 0; i < people.size(); i++) {
	    g.drawImage(people.get(i).getInterfaceImage(), 0, (i * Tools.getInterfacePersonModuleSize()) - iy, null);
	}

	return img;
    }

    public static BufferedImage getImage(int dx, int dy, int iy) {
	BufferedImage img = Tools.newImage(1);
	Graphics g = img.getGraphics();

	g.drawImage(getDisplay(dx, dy), 0, 0, null);
	g.drawImage(getInterface(iy), 0, 0, null);

	return img;
    }
}
