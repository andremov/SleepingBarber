/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Andrés Movilla
 */
public class Model {

    public static final int FACING_DOWN = 0;
    public static final int FACING_RIGHT = 1;
    public static final int FACING_LEFT = 2;
    public static final int FACING_UP = 3;
    
    BufferedImage hair;
    BufferedImage head;
    BufferedImage shirt;
    BufferedImage pants;

    boolean walking;
    int facing;

    final int x = 5;
    final int[] hairY = {1, 2, 3, 4, 5, 5, 4, 3, 2};
    final int[] headY = {5, 6, 7, 8, 9, 9, 8, 7, 6, 5};
    final int[] shirtY = {41, 42, 43, 44, 45, 46, 45, 44, 43, 42};
    final int[] pantsY = {61, 61, 61, 61, 61, 61, 61, 61, 61, 61};
    int frame;
    final int maxFrame;

    public Model(BufferedImage hair, BufferedImage head, BufferedImage shirt, BufferedImage pants) {
	this.hair = hair;
	this.head = head;
	this.shirt = shirt;
	this.pants = pants;
	walking = false;
	facing = 0;
	frame = 0;
	maxFrame = hairY.length * 2;
    }

    public void setWalking(boolean walking) {
	this.walking = walking;
    }

    public void setFacing(int facing) {
	this.facing = facing;
    }

    public BufferedImage getDisplay() {
	BufferedImage img = Tools.newImage(4);
	Graphics g = img.getGraphics();

	frame += walking ? 2 : 1;
	frame = frame % maxFrame;

	int realFrame = (int) (frame / 2);

	g.drawImage(pants.getSubimage(facing * Assets.TILE_SIZE, 0, Assets.TILE_SIZE, Assets.TILE_SIZE), x, pantsY[realFrame], Assets.TILE_SIZE * 4, Assets.TILE_SIZE * 4, null);
	g.drawImage(shirt.getSubimage(facing * Assets.TILE_SIZE, 0, Assets.TILE_SIZE, Assets.TILE_SIZE), x, shirtY[realFrame], Assets.TILE_SIZE * 4, Assets.TILE_SIZE * 4, null);
	g.drawImage(head.getSubimage(facing * Assets.TILE_SIZE, 0, Assets.TILE_SIZE, Assets.TILE_SIZE), x, headY[realFrame], Assets.TILE_SIZE * 4, Assets.TILE_SIZE * 4, null);
	g.drawImage(hair.getSubimage(facing * Assets.TILE_SIZE, 0, Assets.TILE_SIZE, Assets.TILE_SIZE), x, hairY[realFrame], Assets.TILE_SIZE * 4, Assets.TILE_SIZE * 4, null);

	return img;
    }

}
