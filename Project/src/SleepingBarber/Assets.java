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

    public static final int TILE_SIZE = 16;
    public static final int SPRITE_SIZE = 64;

    private static Image[] tiles;
    private static Image[][] humanParts;
    private static final int[] MAX_PARTS = {12, 12, 16, 12};

    public static int PART_HAIR = 0;
    public static int PART_HEAD = 1;
    public static int PART_SHIRT = 2;
    public static int PART_PANTS = 3;

    public static final float SIZE_MULT = 3;

    public static final int SCALED_TILE_SIZE = (int) (TILE_SIZE * SIZE_MULT);
    public static final int SCALED_SPRITE_SIZE = (int) (SPRITE_SIZE * SIZE_MULT);

    public static void loadAssets() throws Exception {
	BufferedImage tileset;
	BufferedImage[] loadedParts;

	// LOAD ALL IMAGES
	tileset = ImageIO.read(new File("assets/tiles.png"));
	loadedParts = new BufferedImage[4];
	loadedParts[0] = ImageIO.read(new File("assets/hairs.png"));
	loadedParts[1] = ImageIO.read(new File("assets/heads.png"));
	loadedParts[2] = ImageIO.read(new File("assets/shirts.png"));
	loadedParts[3] = ImageIO.read(new File("assets/pants.png"));

	// SPLIT TILESET
	int xTiles = tileset.getWidth() / TILE_SIZE;
	int yTiles = tileset.getHeight() / TILE_SIZE;

	tiles = new Image[xTiles * yTiles];
	int c = 0;
	for (int j = 0; j < yTiles; j++) {
	    for (int i = 0; i < xTiles; i++) {
		tiles[c] = tileset.getSubimage(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
		tiles[c] = tiles[c].getScaledInstance(SCALED_TILE_SIZE, SCALED_TILE_SIZE, 0);
		c = c + 1;
	    }
	}

	// SPLIT BODY PARTS
	int[] partsID = {PART_HAIR, PART_HEAD, PART_SHIRT, PART_PANTS};
	humanParts = new Image[partsID.length][20];
	for (int j = 0; j < partsID.length; j++) {
	    int part = partsID[j];
	    for (int i = 0; i < MAX_PARTS[part]; i++) {
		Image img = loadedParts[part].getSubimage(0, i * TILE_SIZE, SPRITE_SIZE, TILE_SIZE);
		humanParts[part][i] = img.getScaledInstance(SCALED_SPRITE_SIZE, SCALED_TILE_SIZE, 0);
	    }
	}

    }

    public static Image getTile(int id) {
	return tiles[id];
    }

    public static Image getHumanPart(int id) {
	return humanParts[id][(int) (Math.random() * MAX_PARTS[id])];
    }

}
