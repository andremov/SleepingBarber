/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author Andrés Movilla
 */
public abstract class Assets {

    public static final int TILE_SIZE = 48;
    
    public static final int SPRITE_WIDTH = 160;
    public static final int SPRITE_HEIGHT = 256;
    
    public static final int FRAME_WIDTH = 32;
    public static final int FRAME_HEIGHT = 64;

    private static BufferedImage[] tiles;
//    private static BufferedImage[][] humanParts;
    
    private static BufferedImage scissors;
    private static BufferedImage waitingChair;
    private static BufferedImage barberChair;
    
    private static BufferedImage[] hairStyles;
    private static BufferedImage[] skinColors;
    private static BufferedImage[] armorColors;
    private static BufferedImage[] gildColors;
    private static BufferedImage[] tightsColors;
    
    private static final int[] MAX_PARTS = {3, 8, 2, 7, 7, 2};
    public static int PART_HAIR_COLOR = 0;
    public static int PART_HAIR_STYLE = 1;
    public static int PART_SKIN_COLOR = 2;
    public static int PART_ARMOR_COLOR = 3;
    public static int PART_ARMOR_GILD_COLOR = 4;
    public static int PART_TIGHTS_COLOR = 5;

//    public static final float SIZE_MULT = 3;

//    public static final int SCALED_TILE_SIZE = (int) (TILE_SIZE * SIZE_MULT);
//    public static final int SCALED_SPRITE_SIZE = (int) (SPRITE_SIZE * SIZE_MULT);

    public static void loadAssets() throws Exception {
	BufferedImage tileset;
//	BufferedImage[] loadedParts;

	// LOAD TILESET
	tileset = ImageIO.read(new File("assets/terrain.png"));
	
	// SPLIT TILESET
	int xTiles = tileset.getWidth() / TILE_SIZE;
	int yTiles = tileset.getHeight() / TILE_SIZE;
	tiles = new BufferedImage[xTiles * yTiles];
	int c = 0;
	for (int j = 0; j < yTiles; j++) {
	    for (int i = 0; i < xTiles; i++) {
		tiles[c] = tileset.getSubimage(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
		c = c + 1;
	    }
	}
	
	// LOAD HUMAN PARTS
	hairStyles = new BufferedImage[MAX_PARTS[PART_HAIR_STYLE]];
	for (int i = 0; i < hairStyles.length; i++) {
	    hairStyles[i] = ImageIO.read(new File("assets/hair"+(i+1)+".png"));
	}
	
	skinColors = new BufferedImage[MAX_PARTS[PART_SKIN_COLOR]];
	for (int i = 0; i < skinColors.length; i++) {
	    skinColors[i] = ImageIO.read(new File("assets/base"+(i+1)+".png"));
	}
	
	armorColors = new BufferedImage[MAX_PARTS[PART_ARMOR_COLOR]];
	for (int i = 0; i < armorColors.length; i++) {
	    armorColors[i] = ImageIO.read(new File("assets/armor"+(i+1)+".png"));
	}
	
	gildColors = new BufferedImage[MAX_PARTS[PART_ARMOR_GILD_COLOR]];
	for (int i = 0; i < gildColors.length; i++) {
	    gildColors[i] = ImageIO.read(new File("assets/gilded"+(i+1)+".png"));
	}
	
	tightsColors = new BufferedImage[MAX_PARTS[PART_TIGHTS_COLOR]];
	for (int i = 0; i < tightsColors.length; i++) {
	    tightsColors[i] = ImageIO.read(new File("assets/tight"+(i+1)+".png"));
	}
	
	//LOAD OTHERS
	barberChair = ImageIO.read(new File("assets/chair1.png"));
//	barberChair = barberChair.getScaledInstance(SCALED_TILE_SIZE, (int) (64*SIZE_MULT*0.6f), 0);
	
	waitingChair = ImageIO.read(new File("assets/chair2.png"));
//	waitingChair = waitingChair.getScaledInstance(SCALED_TILE_SIZE, (int) (64*SIZE_MULT*0.6f), 0);

	scissors = ImageIO.read(new File("assets/scissors.png"));
//	scissors = scissors.getScaledInstance((int) (SCALED_TILE_SIZE / 1.8f), (int) (SCALED_TILE_SIZE / 1.8f), 0);



    }

    public static BufferedImage getTile(int id) {
	return Tools.scaleImage(tiles[id],2);
    }

    public static BufferedImage getScissors() {
	return scissors;
    }

    public static BufferedImage getWaitingChair() {
	return waitingChair;
    }

    public static BufferedImage getBarberChair() {
	return barberChair;
    }

    public static Model createHumanModel(boolean isBarber) {

	BufferedImage hair = hairStyles[(int)(Math.random()*MAX_PARTS[PART_HAIR_STYLE])];
	hair = hair.getSubimage(((int)(Math.random()*3))*SPRITE_WIDTH, 0, SPRITE_WIDTH, SPRITE_HEIGHT);
	BufferedImage hair2 = hairStyles[(int)(Math.random()*MAX_PARTS[PART_HAIR_STYLE])];
	hair2 = hair2.getSubimage(((int)(Math.random()*3))*SPRITE_WIDTH, 0, SPRITE_WIDTH, SPRITE_HEIGHT);

	BufferedImage skin = skinColors[(int)(Math.random()*MAX_PARTS[PART_SKIN_COLOR])];
	BufferedImage tights = tightsColors[(int)(Math.random()*MAX_PARTS[PART_TIGHTS_COLOR])];
	BufferedImage armor = armorColors[(int)(Math.random()*MAX_PARTS[PART_ARMOR_COLOR])];
	BufferedImage gild = gildColors[(int)(Math.random()*MAX_PARTS[PART_ARMOR_GILD_COLOR])];
	
	int gender = (int)(Math.random()*2);
	Model m;
	if (isBarber) {
	    m = new Model(skin, tights, armor, gild, hair, gender);
	} else {
	    m = new Model(skin, tights, armor, gild, hair, hair2, gender);
	}
	
	return m;
    }

}

