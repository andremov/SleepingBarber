/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.image.BufferedImage;

/**
 *
 * @author Andrés Movilla
 */
public abstract class Person {
    
    Model m;
    int x, y;

    public Person() {
	m = Tools.createModel();
    }
    
    public BufferedImage getDisplay() {
	return m.getDisplay();
    }
    
}
