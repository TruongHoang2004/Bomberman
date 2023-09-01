package main.bomb;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import main.GamePanel;

/**
 * BombManager
 */
public class BombManager {

    public GamePanel gp;
    Bomb[] bombs;
    public boolean[][] bombMap;
    public int[][] explodeMap;
    public int maxBombInTime;
    BufferedImage bombImage;

    public BufferedImage explodeImage;
    public BufferedImage[] centerOfExplode;
    public BufferedImage[][] edgeOfExplode;
    public BufferedImage[][] endOfExplode;

    public BombManager(GamePanel gp) {
        this.gp = gp;
        maxBombInTime = 1;
        bombs = new Bomb[10];
        for (int i = 0; i < 10; ++i) {
            bombs[i] = new Bomb(this);
        }

        explodeMap = new int[gp.maxScreenRow][gp.maxScreenCol];
        centerOfExplode = new BufferedImage[4];
        endOfExplode = new BufferedImage[4][4];
        edgeOfExplode = new BufferedImage[4][4];

        bombMap = new boolean[gp.maxScreenRow][gp.maxScreenCol];
        for (int i = 0; i < gp.maxScreenRow; ++i) {
            for (int j = 0; j < gp.maxScreenCol; ++j) {
                bombMap[i][j] = false;
                explodeMap[i][j] = 0;
            }
        }
        loadImage();
    }

    void loadImage() {
        try {
            bombImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/bomb/bomb.png")));
            explodeImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/bomb/explode.png")));
            for ( int i = 0; i < 4; ++i ) {
                int it = i * 240;
                int x = it % 480;
                int y = (it / 480) * 240;

                centerOfExplode[i] = explodeImage.getSubimage(x + 96, y + 96, 48, 48);

                edgeOfExplode[i][0] = explodeImage.getSubimage(x + 48,  y +96,   48, 48);
                edgeOfExplode[i][1] = explodeImage.getSubimage(x + 96,  y + 48,  48, 48);
                edgeOfExplode[i][2] = explodeImage.getSubimage(x + 144, y + 96,  48, 48);
                edgeOfExplode[i][3] = explodeImage.getSubimage(x + 96,  y + 144, 48, 48);

                endOfExplode[i][0] = explodeImage.getSubimage(x, y + 96, 48, 48);
                endOfExplode[i][1] = explodeImage.getSubimage(x + 96, y, 48, 48);
                endOfExplode[i][2] = explodeImage.getSubimage(x + 192, y + 96, 48, 48);
                endOfExplode[i][3] = explodeImage.getSubimage(x + 96, y + 192, 48, 48);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < maxBombInTime; ++i) {
            if (bombs[i].status.equals("countdown")) {
                BufferedImage curImage = bombImage.getSubimage(gp.tileSize * bombs[i].drawSpriteNum(), 0, gp.tileSize, gp.tileSize);
                g2.drawImage(curImage, bombs[i].Col * gp.tileSize, bombs[i].Row * gp.tileSize, gp.tileSize, gp.tileSize, null);
            }
        }

        for ( int i = 0; i < gp.maxScreenRow; ++i ) {
            for ( int j = 0;j < gp.maxScreenCol; ++j ) {
                if ( explodeMap[i][j] >= 10 ) {
                    int tmp = explodeMap[i][j];
                    if ( tmp < 100 ) {
                        g2.drawImage(centerOfExplode[tmp % 10], j * gp.tileSize, i * gp.tileSize, gp.tileSize, gp.tileSize, null);
                    } else if ( tmp / 100 == 2 ) {
                        tmp %= 100;
                        g2.drawImage(edgeOfExplode[tmp/10][tmp%10], j * gp.tileSize, i * gp.tileSize, gp.tileSize, gp.tileSize, null);
                    } else if ( tmp / 100 == 1 ) {
                        tmp %= 100;
                        g2.drawImage(endOfExplode[tmp/10][tmp%10], j * gp.tileSize, i * gp.tileSize, gp.tileSize, gp.tileSize, null  );
                    }

                }
            }
        }
    }

    public void placeBomb(int x, int y, int eRadius) {
        for (int i = 0; i < maxBombInTime; ++i) {
            if (bombs[i].status.equals("idle")) {
                bombs[i].placeBomb(x, y, eRadius);
                break;
            }
        }
    }

    public void update() {
        for (int i = 0; i < maxBombInTime; ++i) {
            bombs[i].update();
        }
    }
}