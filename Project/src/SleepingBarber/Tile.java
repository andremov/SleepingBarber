/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

/**
 *
 * @author Andrés Movilla
 */
public class Tile {

    private int x,y;
    int id;

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
    
    
}
