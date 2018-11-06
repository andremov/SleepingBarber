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
    public static final int FACING_UP = 2;
    public static final int FACING_LEFT = 3;

    public static final int GENDER_FEMALE = 0;
    public static final int GENDER_MALE = 1;

    BufferedImage hair;
    BufferedImage hair2;

    BufferedImage skin;
    BufferedImage tights;
    BufferedImage armor;
    BufferedImage gild;

    boolean walking;
    boolean scissors;
    boolean sitting;
    int facing;
    int gender;

    final int maxFrame = 5;
    final int hairDisplace = 6;

    final int[] scissorsX = {40, 60, 60, 40};
    final int scissorsY = 35;

    int curFrame;

    public Model(BufferedImage skin, BufferedImage tights, BufferedImage armor, BufferedImage gild, BufferedImage hair, int gender) {
	this.hair = hair;
	this.skin = skin;
	this.tights = tights;
	this.armor = armor;
	this.gild = gild;
	walking = false;
	facing = 0;
	curFrame = 0;
	scissors = true;
	this.gender = gender;
    }

    public Model(BufferedImage skin, BufferedImage tights, BufferedImage armor, BufferedImage gild, BufferedImage hair, BufferedImage hair2, int gender) {
	this.hair = hair;
	this.hair2 = hair2;
	this.skin = skin;
	this.tights = tights;
	this.armor = armor;
	this.gild = gild;
	walking = false;
	facing = 0;
	curFrame = 0;
	scissors = false;
	this.gender = gender;
    }

    public void setWalking(boolean walking) {
	this.walking = walking;
    }

    public void setFacing(int facing) {
	this.facing = facing;
    }

    public void setSitting(boolean sitting) {
	this.sitting = sitting;
	if (sitting) {
	    facing = FACING_DOWN;
	}
    }

    public void changeHair() {
	BufferedImage t = this.hair;
	this.hair = this.hair2;
	this.hair2 = t;
    }

    public BufferedImage getDisplay() {
	BufferedImage img = Tools.newImage(4);
	Graphics g = img.getGraphics();
	
	if (sitting) {
	    facing = FACING_DOWN;
	}

	if (this.walking) {
	    curFrame++;
	} else {
	    curFrame = 0;
	}

	if (curFrame >= maxFrame*2) {
	    curFrame = 1;
	}

//	int dY = walking ? walkAnim[realFrame] : breathAnim[realFrame];
	if (scissors) {
	    if (facing == FACING_RIGHT || facing == FACING_UP) {
		g.drawImage(Assets.getScissors(), scissorsX[facing], scissorsY, null);
	    }
	}

	int frameX = ((curFrame/2) * Assets.FRAME_WIDTH);
	int genderX = (Assets.FRAME_WIDTH * maxFrame * gender);
	int frameY = (facing * Assets.FRAME_HEIGHT);

	g.drawImage(skin.getSubimage(frameX + genderX, frameY, Assets.FRAME_WIDTH, Assets.FRAME_HEIGHT), 50, hairDisplace, null);
	g.drawImage(tights.getSubimage(frameX + genderX, frameY, Assets.FRAME_WIDTH, Assets.FRAME_HEIGHT), 50, hairDisplace, null);
	g.drawImage(armor.getSubimage(frameX + genderX, frameY, Assets.FRAME_WIDTH, Assets.FRAME_HEIGHT), 50, hairDisplace, null);
	g.drawImage(gild.getSubimage(frameX + genderX, frameY, Assets.FRAME_WIDTH, Assets.FRAME_HEIGHT), 50, hairDisplace, null);
	g.drawImage(hair.getSubimage(frameX, frameY, Assets.FRAME_WIDTH, Assets.FRAME_HEIGHT), 50, 0, null);

	if (scissors) {
	    if (facing == FACING_DOWN || facing == FACING_LEFT) {
		g.drawImage(Assets.getScissors(), scissorsX[facing], scissorsY, null);
	    }
	}

	
	return Tools.scaleImage(img, 2);
    }

}
