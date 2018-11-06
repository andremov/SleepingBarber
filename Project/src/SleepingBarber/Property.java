/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author Andrés Movilla
 */
public class Property extends JPanel {
    

    public Property(int moduleNumber) {
	setBounds(Tools.getModuleSize(moduleNumber));
	setLayout(null);
	setOpaque(true);
	setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
	setBackground(new Color(172, 195, 210));
	
	JTextArea lb = new JTextArea("Cristhyan De Marchena\nAndrés Movilla");
	lb.setFont(new java.awt.Font("Arial",java.awt.Font.BOLD,15));
	lb.setEditable(false);
	lb.setOpaque(false);
	lb.setLocation(10,10);
	lb.setSize(250,50);
	add(lb);

	setVisible(true);
    }
}
