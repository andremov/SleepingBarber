/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

/**
 * 
 * @author Cristhyan De Marchena    - 200082385
 * @author Andrés Movilla           - 200081896
 */
public class Tile {

    private final int x, y, id;

    public Tile(int x, int y, int id) {
	this.x = x;
	this.y = y;
	this.id = id;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    public int getId() {
	return id;
    }
    
    
}
