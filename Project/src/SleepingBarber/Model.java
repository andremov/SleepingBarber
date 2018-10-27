/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SleepingBarber;

import java.awt.Graphics;
import java.awt.Image;
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

    Image hair;
    Image head;
    Image shirt;
    Image pants;

    boolean walking;
    boolean scissors;
    int facing;

    final int x = 5;
    final int[] breathAnim = {1, 2, 3, 4, 5, 5, 4, 3, 2};
    final int[] walkAnim = {1, 2, 3, 4, 5, 5, 4, 3, 2};
    final int hairY = 0;
    final int headY = 4;
    final int shirtY = 45;
    final int pantsY = 61;

    final int[] scissorsX = {20, 60, 60, 20};
    final int scissorsY = 50;

    int frame;
    final int maxFrame;

    public Model(Image hair, Image head, Image shirt, Image pants, boolean s) {
	this.hair = hair;
	this.head = head;
	this.shirt = shirt;
	this.pants = pants;
	walking = false;
	facing = 0;
	frame = 0;
	scissors = s;
	maxFrame = breathAnim.length * 2;
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
	g.fillRect(0, 0, 300, 300);
	frame++;
	frame = frame % maxFrame;

	int realFrame = (int) (frame / 2);
	int dY = walking ? walkAnim[realFrame] : breathAnim[realFrame];

	if (scissors) {
	    if (facing == FACING_RIGHT || facing == FACING_UP) {
		g.drawImage(Assets.getScissors(), scissorsX[facing], scissorsY, null);
	    }
	}

	g.drawImage(pants, -(facing * Assets.SCALED_TILE_SIZE), pantsY, null);
	g.drawImage(shirt, -(facing * Assets.SCALED_TILE_SIZE), shirtY - (dY / 2), null);
	g.drawImage(head, -(facing * Assets.SCALED_TILE_SIZE), headY + dY, null);
	g.drawImage(hair, -(facing * Assets.SCALED_TILE_SIZE), hairY + dY, null);

	if (scissors) {
	    if (facing == FACING_DOWN || facing == FACING_LEFT) {
		g.drawImage(Assets.getScissors(), scissorsX[facing], scissorsY, null);
	    }
	}

	return img;
    }

}
