package main.tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
    public GamePanel gp;
    public Tile[] tile;
    public int[][] mapTileNum;

    public TileManager(GamePanel gp) {
        this.gp = gp;

        tile = new Tile[10];

        getTileImage();
        mapTileNum = new int[gp.maxScreenRow][gp.maxScreenCol];
        loadMap();
    }

    public void getTileImage() {
        try {
            tile[0] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tile/dirt_00.png"))), false);
            tile[1] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tile/fixed_wall_01.png"))), true);
            tile[2] = new Tile(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/tile/brick_wall_02.png")))
                    .getSubimage(0, 0, 48, 48), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadMap() {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/map/map_01.txt"))));
            for (int i = 0; i < gp.maxScreenRow; ++i) {
                String line = br.readLine();
                String[] number = line.split(" ");
                for (int j = 0; j < gp.maxScreenCol; ++j) {
                    mapTileNum[i][j] = Integer.parseInt(number[j]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2) {
        for (int i = 0; i < gp.maxScreenRow; ++i) {
            for (int j = 0; j < gp.maxScreenCol; ++j) {
                g2.drawImage(tile[mapTileNum[i][j]].image, j * gp.tileSize, i * gp.tileSize, gp.tileSize, gp.tileSize,
                        null);
            }
        }
    }
}
