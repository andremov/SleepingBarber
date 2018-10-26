/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Andrés Movilla
 */
public abstract class Tools {

    public static Dimension getScreenSize() {
	return new Dimension(600, 540);
    }

    public static int getInterfaceSize() {
	return 200;
    }

    public static Model createModel() {
	return new Model(Assets.getHumanPart(Assets.PART_HAIR), Assets.getHumanPart(Assets.PART_HEAD), Assets.getHumanPart(Assets.PART_SHIRT), Assets.getHumanPart(Assets.PART_PANTS));
    }

    public static Dimension getWindowSize() {
	Dimension d = getScreenSize();
	d.height += 100;
	d.width += 36;
	return d;
    }

    public static BufferedImage newImage(int id) {
	Dimension d = getScreenSize();
	switch (id) {
	    case 1:
		return new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
	    case 2:
		return new BufferedImage(getInterfaceSize(), d.height, BufferedImage.TYPE_INT_ARGB);
	    case 3:
		return new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
	    case 4:
		return new BufferedImage(16*4, 32*4, BufferedImage.TYPE_INT_ARGB);
	    default:
		return null;
	}
    }

    public static Rectangle getModuleSize(int id) {
	switch (id) {
	    case 1:
		return new Rectangle(10, 10, 600, 500);
	    case 2:
		return new Rectangle(10, 520, 125, 70);
	    case 3:
		return new Rectangle(145, 520, 160, 70);
	    case 4:
		return new Rectangle(315, 520, 160, 70);
	    case 5:
		return new Rectangle(485, 520, 125, 70);
	    default:
		return null;
	}
    }

    public static String joinStringArray(String[] array, boolean doNL) {
	String result = "";
	for (int i = 0; i < array.length; i++) {
	    result += array[i] + (doNL ? "\n" : "");
	}

	return (doNL ? result.substring(0, result.length() - 1) : result);
    }

    public static String[] splitCharacters(String source) {
	return source.split("(?!^)");
    }

    public static String[] splitLines(String source) {
	return source.split("\\n");
    }

    public static double log2(double a) {
	return Math.log10(a) / Math.log10(2);
    }
}
