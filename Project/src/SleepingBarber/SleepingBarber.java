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

    public static ArrayList<Seat> seats;

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
	seats = new ArrayList<>();
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

	enterPoint = new Point(-20, 600);
	exitPoint = new Point(800, 600);

	enterTransition = new Point(360, 600);
	exitTransition = new Point(450, 600);

	enterDoor = new Point(360, 500);
	exitDoor = new Point(450, 500);

	toBarberTransition = new Point(400, 300);
	fromBarberTransition = new Point(530, 330);

	addBarberSeat(650, 310);

	addWaitingSeat(50, 200);
//	addWaitingSeat(340, 200);
//	addWaitingSeat(430, 200);
//	addWaitingSeat(520, 200);

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

    private static int findSeatIndex(int id) {
	for (int i = 0; i < seats.size(); i++) {
	    if (seats.get(i).getId() == id) {
		return i;
	    }
	}
	return -1;
    }

    private static Seat findSeat(int id) {
	for (int i = 0; i < seats.size(); i++) {
	    if (seats.get(i).getId() == id) {
		return seats.get(i);
	    }
	}
	return null;
    }

    private static Seat findFirstAvailableSeat(boolean barberSeat) {
	for (int i = 0; i < seats.size(); i++) {
	    if (seats.get(i) instanceof BarberSeat == barberSeat) {
		if (seats.get(i).isFree()) {
		    return seats.get(i);
		}
	    }
	}
	return null;
    }

    public static void freeBarberSeat(int id) {
	Seat s = findSeat(id);
	if (s.isFree()) {
	    System.err.println("Tried to free already free barber seat!");
	} else {
	    s.setFree(true);
	}
    }

    public static void freeWaitingSeat(int id) {
	Seat s = findSeat(id);
	if (s.isFree()) {
	    System.err.println("Tried to free already free seat!");
	} else {
	    s.setFree(true);
	}
    }

    public static BarberSeat assignBarberSeat() {
	Seat s = findFirstAvailableSeat(true);
	if (!s.isFree()) {
	    System.err.println("Tried to free already free seat!");
	    return null;
	} else {
	    s.setFree(false);
	    return (BarberSeat) s;
	}
    }

    public static WaitingSeat assignWaitingSeat() {
	Seat s = findFirstAvailableSeat(false);
	if (!s.isFree()) {
	    System.err.println("Tried to free already free seat!");
	    return null;
	} else {
	    s.setFree(false);
	    return (WaitingSeat) s;
	}
    }

    public static void addWaitingSeat(int x, int y) {
//	if (seats.size() < 8) {
	WaitingSeat ws = new WaitingSeat(x, y, seats.size());
	seats.add(ws);
	orderedDisplayObjects.add(ws);
//	}
	sb.addBarberBtn.setEnabled(seats.size() < 8);
    }

    public static void addBarberSeat(int x, int y) {
	BarberSeat bs = new BarberSeat(x, y, seats.size());
	seats.add(bs);
	orderedDisplayObjects.add(bs);

	Barber b = new Barber(new Point(x + 25, y + 20), bs.getId());
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
	int rows = 10;
	int columns = 9;

	for (int i = 0; i < rows; i++) {
	    for (int j = 0; j < columns; j++) {
		int id = 0;
		if (i == 0) {
		    if (j > 0) {
			id = 1;
		    } else if (j == columns - 1) {
			id = 2;
		    }
		} else {
		    id = 3;
		}
		tiles.add(new Tile(j, i, id));
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
	setTitle("Sleeping Barber Problem - Cristhyan De Marchena, Andrés Movilla");
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

	add(new Property(5));
	
	addCustomerBtn = new JButton("Add Customer");
	addCustomerBtn.setBounds(Tools.getModuleSize(3));
	addCustomerBtn.setFocusable(false);
	addCustomerBtn.addActionListener((ActionEvent e) -> {
	    addCustomer();
	});
	add(addCustomerBtn);

//	addBarberBtn = new JButton("Add Barber");
//	addBarberBtn.setBounds(Tools.getModuleSize(4));
//	addBarberBtn.setFocusable(false);
//	addBarberBtn.addActionListener((ActionEvent e) -> {
//	    addBarber();
//	});
//	addBarberBtn.setEnabled(false);
//	add(addBarberBtn);
	addBarberBtn = new JButton("Add Chair");
	addBarberBtn.setBounds(Tools.getModuleSize(4));
	addBarberBtn.setFocusable(false);
	addBarberBtn.addActionListener((ActionEvent e) -> {
	    addWaitingSeat(seats.get(seats.size() - 1).getX() + 100, seats.get(seats.size() - 1).getY());
	});
	addBarberBtn.setEnabled(true);
	add(addBarberBtn);

	screen = new Screen();
	add(screen);

	uiReady = true;
    }

    public void startScreenThread() {
	new Thread(screen).start();
    }

    public static int getNumFreeSeats() {
	int c = 0;
	for (int i = 0; i < seats.size(); i++) {
	    if (seats.get(i) instanceof WaitingSeat) {
		if (seats.get(i).isFree()) {
		    c++;
		}
	    }
	}
	return c;
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
	int ts = Assets.TILE_SIZE * 2;
	mapImage = new BufferedImage(maxX * ts, maxY * ts, BufferedImage.TYPE_INT_ARGB);
	Graphics g = mapImage.getGraphics();

	for (int i = 0; i < tiles.size(); i++) {
	    g.drawImage(Assets.getTile(tiles.get(i).getId()), tiles.get(i).getX() * ts, tiles.get(i).getY() * ts, null);
	}

	maxX = (maxX - 2) * (ts / 2);
	maxY = (maxY - 2) * (ts / 2);
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
	    int y1 = o1.getY() + (o1 instanceof Person ? 80 : 0);
	    int y2 = o2.getY() + (o2 instanceof Person ? 80 : 0);

	    if (y1 < y2) {
		return -1;
	    } else if (y2 < y1) {
		return 1;
	    } else {
		return 1;
	    }
	});

	orderedDisplayObjects.forEach((orderedDisplayObject) -> {
	    g.drawImage(orderedDisplayObject.getImage(), orderedDisplayObject.getDisplayX() + displaceX, orderedDisplayObject.getDisplayY() + displaceY, null);
	});

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

	p.add(new Object[]{enterPoint.x, enterPoint.y, white, "In"});
	p.add(new Object[]{exitPoint.x, exitPoint.y, white, "Out"});

	p.add(new Object[]{enterDoor.x, enterDoor.y, white, "Entrance"});
	p.add(new Object[]{exitDoor.x, exitDoor.y, white, "Exit"});

	p.add(new Object[]{enterTransition.x, enterTransition.y, white, "In_Trans"});
	p.add(new Object[]{exitTransition.x, exitTransition.y, white, "Out_Trans"});

	p.add(new Object[]{toBarberTransition.x, toBarberTransition.y, white, "To_Barber"});
	p.add(new Object[]{fromBarberTransition.x, fromBarberTransition.y, white, "From_barber"});

	for (int i = 0; i < seats.size(); i++) {
	    Seat s = seats.get(i);
	    p.add(new Object[]{s.x, s.y, s.toColor(), s.toString()});
	}

	p.forEach((p1) -> {
	    int x = (int) p1[0];
	    int y = (int) p1[1];
	    Color c = (Color) p1[2];
	    String n = (String) p1[3];
	    g.setColor(c);
	    g.fillRect(x + displaceX - 5, y + displaceY - 5, 10, 10);

	    g.drawString(n, x + displaceX - (2 * n.length()), y + displaceY + 20);
	});

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
	}

	try {
//	    g.drawImage(getInterface(iy), 0, 0, null);
	} catch (Exception e) {
	}

	try {
//	    g.drawImage(getOverlay(dx, dy), 0, 0, null);
	} catch (Exception e) {
	}

	return img;
    }
}
