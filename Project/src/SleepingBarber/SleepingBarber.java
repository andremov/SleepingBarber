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
    public static Stoplight animReady;
    public static Stoplight haircutReady;
    public static int canAddCustomer = 0;

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
    
    public static SleepingBarber sb;
    
    public static int maxX;
    public static int maxY;
    
    Screen screen;

    JButton addCustomerBtn;
    JButton addBarberBtn;

    public static void main(String[] args) {
	barberReady = new Stoplight(0, Stoplight.MODE_COUNTER);
	accessSeats = new Stoplight(1, Stoplight.MODE_BINARY);
	animReady = new Stoplight(0, Stoplight.MODE_BINARY);
	custReady = new Stoplight(0, Stoplight.MODE_COUNTER);
	haircutReady = new Stoplight(0, Stoplight.MODE_BINARY);

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

	sb = new SleepingBarber();

	while (!uiReady) {
	    Tools.quickThreadSleep(10);
	}

	sb.createBasicMap();
	createMapImage();

	enterPoint = new Point(20, 500);
	exitPoint = new Point(800, 500);
	
	enterTransition = new Point(360, 500);
	exitTransition = new Point(450, 500);
	
	enterDoor = new Point(360, 400);
	exitDoor = new Point(450, 400);
	
	toBarberTransition = new Point(400, 200);
	fromBarberTransition = new Point(530, 230);

	addWaitingSeat(250, 100);
	addWaitingSeat(340, 100);
	addWaitingSeat(430, 100);
	addWaitingSeat(520, 100);

	addBarberSeat(650, 210);

	sb.setVisible(true);
	sb.startScreenThread();

    }

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
	int rows = 40;
	int columns = 40;

	for (int i = 0; i < rows; i++) {
	    for (int j = 0; j < columns; j++) {
		tiles.add(new Tile(j, i, 3));
	    }
	}
    }

//    public void addBarber() {
//	Barber b = new Barber(new Point(400, 200));
//	new Thread(b).start();
//	people.add(b);
//    }
    
    public void addCustomer() {
	canAddCustomer = 10;
	addCustomerBtn.setEnabled(false);
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
	setResizable(false);
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
	maxX = 0;
	maxY = 0;

	for (int i = 0; i < tiles.size(); i++) {
	    if (tiles.get(i).getX() > maxX) {
		maxX = tiles.get(i).getX();
	    }
	    if (tiles.get(i).getY() > maxY) {
		maxY = tiles.get(i).getY();
	    }
	}

	mapImage = new BufferedImage(maxX * Assets.TILE_SIZE, maxY * Assets.TILE_SIZE, BufferedImage.TYPE_INT_ARGB);
	Graphics g = mapImage.getGraphics();
	
	for (int i = 0; i < tiles.size(); i++) {
	    g.drawImage(Assets.getTile(tiles.get(i).getId()), tiles.get(i).getX() * Assets.TILE_SIZE, tiles.get(i).getY() * Assets.TILE_SIZE, null);
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
	    int y1 = o1.getY() + (o1 instanceof Person? 80 : 0);
	    int y2 = o2.getY() + (o2 instanceof Person? 80 : 0);
	    
	    if (y1 < y2) {
		return -1;
	    } else if (y2 < y1) {
		return 1;
	    } else {
		return 1;
	    }
	});
	
	for (DisplayObject orderedDisplayObject : orderedDisplayObjects) {
	    g.drawImage(orderedDisplayObject.getImage(), orderedDisplayObject.getDisplayX() + displaceX, orderedDisplayObject.getDisplayY() + displaceY, null);
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

    public static BufferedImage getOverlay(int displaceX, int displaceY) {
	BufferedImage img = Tools.newImage(3);
	Graphics g = img.getGraphics();

//	int h = Tools.getScreenSize().height;
//	int w = Tools.getScreenSize().width;

	Color green = Color.green;
	Color red = Color.red;
	Color white = Color.white;
	
	ArrayList<Object[]> p = new ArrayList<>();
	
	p.add( new Object[]{ enterPoint.x, enterPoint.y, white, "In" } );
	p.add( new Object[]{ exitPoint.x, exitPoint.y, white, "Out" } );
	
	p.add( new Object[]{ enterDoor.x, enterDoor.y, white, "Entrance" } );
	p.add( new Object[]{ exitDoor.x, exitDoor.y, white, "Exit" } );
	
	p.add( new Object[]{ enterTransition.x, enterTransition.y, white, "In_Trans" } );
	p.add( new Object[]{ exitTransition.x, exitTransition.y, white, "Out_Trans" } );
	
	p.add( new Object[]{ toBarberTransition.x, toBarberTransition.y, white, "To_Barber" } );
	p.add( new Object[]{ fromBarberTransition.x, fromBarberTransition.y, white, "From_barber" } );
	
	for (int i = 0; i < occupiedBarberSeats.size(); i++) {
	    p.add( new Object[]{ occupiedBarberSeats.get(i).x, occupiedBarberSeats.get(i).y, red, "Barber_Occupied" } );
	}
	
	for (int i = 0; i < freeBarberSeats.size(); i++) {
	    p.add( new Object[]{ freeBarberSeats.get(i).x, freeBarberSeats.get(i).y, green, "Barber_Free" } );
	}
	
	
	for (int i = 0; i < occupiedWaitingSeats.size(); i++) {
	    p.add( new Object[]{ occupiedWaitingSeats.get(i).x, occupiedWaitingSeats.get(i).y, red, "Waiting_Occupied" } );
	}
	
	for (int i = 0; i < freeWaitingSeats.size(); i++) {
	    p.add( new Object[]{ freeWaitingSeats.get(i).x, freeWaitingSeats.get(i).y, green, "Waiting_Free" } );
	}
	
	for (Object[] p1 : p) {
	    int x = (int) p1[0];
	    int y = (int) p1[1];
	    Color c = (Color) p1[2];
	    String n = (String) p1[3];
	    g.setColor(c);
	    g.fillRect(x+displaceX-5,y+displaceY-5,10,10);
	    
	    g.drawString(n, x+displaceX-(2*n.length()), y+displaceY+20);
	}
	
	
	return img;
    }

    public static BufferedImage getImage(int dx, int dy, int iy) {
	BufferedImage img = Tools.newImage(1);
	Graphics g = img.getGraphics();
	
	
	
	if (!sb.addCustomerBtn.isEnabled()) {
	    canAddCustomer--;
	    if (canAddCustomer <= 0) {
		sb.addCustomerBtn.setEnabled(true);
	    }
	}
	
	try {
	    g.drawImage(getDisplay(dx, dy), 0, 0, null);
	} catch (Exception e) {
	    e.printStackTrace();
	}
	
	try {
	    g.drawImage(getInterface(iy), 0, 0, null);
	} catch (Exception e) {}
	
	try {
	    g.drawImage(getOverlay(dx,dy), 0, 0, null);
	} catch (Exception e) {}

	return img;
    }
}
