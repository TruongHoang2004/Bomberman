package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.tile.TileManager;

public class Player extends Entity {
    GamePanel gp;
    KeyHandler keyH;
    TileManager tm;
    int explodeRadius;

    public Player(GamePanel gp, KeyHandler keyH, TileManager tm) {
        this.gp = gp;
        this.keyH = keyH;
        this.tm = tm;

        explodeRadius = 1;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 48;
        y = 48;
        width = 48;
        height = 48;
        speed = 1;
        direction = "down";
        solidArea = new Rectangle(0, 0, 48, 48);
    }

    public void getPlayerImage() {
        up = new BufferedImage[3];
        down = new BufferedImage[3];
        left = new BufferedImage[3];
        right = new BufferedImage[3];

        try {
            for (int i = 0; i < 3; ++i) {
                up[i] = ImageIO
                        .read(Objects.requireNonNull(getClass().getResourceAsStream("/player/up_" + (i + 1) + ".png")));down[i] = ImageIO
                        .read(Objects.requireNonNull(getClass().getResourceAsStream("/player/down_" + (i + 1) + ".png")));left[i] = ImageIO
                        .read(Objects.requireNonNull(getClass().getResourceAsStream("/player/left_" + (i + 1) + ".png")));right[i] = ImageIO
                        .read(Objects.requireNonNull(getClass().getResourceAsStream("/player/right_" + (i + 1) + ".png")));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        if (keyH.downPressed || keyH.leftPressed || keyH.upPressed || keyH.rightPressed) {
            collisionOn = false;
            if (keyH.downPressed) {
                direction = "down";
                gp.cChecker.checkTile(this);
                if (!collisionOn) {
                    y += speed;
                } else
                    collisionOn = false;
            }
            if (keyH.upPressed) {
                direction = "up";
                gp.cChecker.checkTile(this);
                if (!collisionOn) {
                    y -= speed;
                } else
                    collisionOn = false;

            }
            if (keyH.rightPressed) {
                direction = "right";
                gp.cChecker.checkTile(this);
                if (!collisionOn) {
                    x += speed;
                } else
                    collisionOn = false;
            }
            if (keyH.leftPressed) {
                direction = "left";
                gp.cChecker.checkTile(this);
                if (!collisionOn) {
                    x -= speed;
                } else
                    collisionOn = false;
            }

            spriteCounter++;
            if (spriteCounter > gp.FPS / 3) {
                if (spriteNum == 2)
                    spriteNum = 0;
                else
                    spriteNum++;

                spriteCounter = 0;
            }
        } else {
            spriteNum = 1;
        }

        if (keyH.placePressed) {
            gp.bomb.placeBomb(x, y, explodeRadius);
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = switch (direction) {
            case "up" -> up[spriteNum];
            case "down" -> down[spriteNum];
            case "left" -> left[spriteNum];
            case "right" -> right[spriteNum];
            default -> null;
        };

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }

}