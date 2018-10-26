/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author Andrés Movilla
 */
public abstract class Assets {

    public static final int TILE_SIZE = 16;
    public static final int SPRITE_SIZE = 64;
    
    private static BufferedImage tiles;
    private static BufferedImage[] humanParts;
    private static final int[] maxParts = {12, 12, 16, 12};
    
    public static int PART_HAIR = 0;
    public static int PART_HEAD = 1;
    public static int PART_SHIRT = 2;
    public static int PART_PANTS = 3;

    public static void loadAssets() {

	try {
	    
	    tiles = ImageIO.read(new File("assets/tiles.png"));
	    humanParts = new BufferedImage[4];
	    humanParts[0] = ImageIO.read(new File("assets/hairs.png"));
	    humanParts[1] = ImageIO.read(new File("assets/heads.png"));
	    humanParts[2] = ImageIO.read(new File("assets/shirts.png"));
	    humanParts[3] = ImageIO.read(new File("assets/pants.png"));

	} catch (Exception e) { }
	
    }
    
    public static BufferedImage getTile(int id) {
	return tiles.getSubimage((id%4)*TILE_SIZE, (id/4)*TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
    
    public static BufferedImage getHumanPart(int id) {
	return humanParts[id].getSubimage(0, (int) Math.floor(Math.random()*maxParts[id])*TILE_SIZE, SPRITE_SIZE, TILE_SIZE);
    }

}
