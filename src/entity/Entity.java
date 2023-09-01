package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
    public int x, y, width, height;
    public int speed;
    public boolean collisionOn = false;
    public Rectangle solidArea;

    public BufferedImage[] up;
    public BufferedImage[] down;
    public BufferedImage[] left;
    public BufferedImage[] right;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
}
