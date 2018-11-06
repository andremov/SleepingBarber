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
public abstract class Seat extends Point {
    
    private boolean free;
    private final int id;

    public Seat(int x, int y, int id) {
	super(x, y);
	this.id = id;
	this.free = true;
    }

    /**
     * @return the free
     */
    public boolean isFree() {
	return free;
    }

    /**
     * @param free the free to set
     */
    public void setFree(boolean free) {
	this.free = free;
    }

    /**
     * @return the id
     */
    public int getId() {
	return id;
    }
    

    @Override
    public String toString() {
	return (this instanceof BarberSeat? "Barber": "Waiting")+ "_"+(isFree()? "Free" : "Occupied" );
    }
    
    

    public java.awt.Color toColor() {
	return isFree()? java.awt.Color.green : java.awt.Color.red;
    }
    
}
