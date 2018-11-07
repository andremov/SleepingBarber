/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * 
 * @author Cristhyan De Marchena    - 200082385
 * @author Andrés Movilla           - 200081896
 */
public class Placeholder extends JPanel {

    public Placeholder(int moduleNumber) {
	setBounds(Tools.getModuleSize(moduleNumber));
	setLayout(null);
	setOpaque(true);
	setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
	setBackground(new Color(172, 195, 210));

//	JLabel lb = new JLabel(moduleNumber+"");
//	lb.setLocation(10,10);
//	lb.setSize(100,50);
//	add(lb);

	setVisible(true);
    }
    
}
