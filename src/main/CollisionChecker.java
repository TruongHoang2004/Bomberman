package main;

import entity.Entity;

public class CollisionChecker {

    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public void checkTile(Entity entity) {
        int leftX = entity.x + entity.solidArea.x;
        int rightX = entity.x + entity.solidArea.width + entity.solidArea.x - 1;
        int topY = entity.y + entity.solidArea.y;
        int bottomY = entity.y + entity.solidArea.height + entity.solidArea.y - 1;

        int leftCol = leftX / gp.tileSize;
        int rightCol = rightX / gp.tileSize;
        int topRow = topY / gp.tileSize;
        int bottomRow = bottomY / gp.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up" -> {
                topRow = (topY - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileset.mapTileNum[topRow][leftCol];
                tileNum2 = gp.tileset.mapTileNum[topRow][rightCol];
                if (gp.tileset.tile[tileNum1].collision || gp.tileset.tile[tileNum2].collision ||
                    gp.bomb.bombMap[topRow][leftCol] || gp.bomb.bombMap[topRow][rightCol]  ) {
                    entity.collisionOn = true;
                }
            }
            case "down" -> {
                bottomRow = (bottomY + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileset.mapTileNum[bottomRow][leftCol];
                tileNum2 = gp.tileset.mapTileNum[bottomRow][rightCol];
                if (gp.tileset.tile[tileNum1].collision || gp.tileset.tile[tileNum2].collision ||
                        gp.bomb.bombMap[bottomRow][leftCol] || gp.bomb.bombMap[bottomRow][rightCol]) {
                    entity.collisionOn = true;
                }
            }
            case "left" -> {
                leftCol = (leftX - entity.speed) / gp.tileSize;
                tileNum1 = gp.tileset.mapTileNum[topRow][leftCol];
                tileNum2 = gp.tileset.mapTileNum[bottomRow][leftCol];
                if (gp.tileset.tile[tileNum1].collision || gp.tileset.tile[tileNum2].collision ||
                        gp.bomb.bombMap[topRow][leftCol] || gp.bomb.bombMap[bottomRow][leftCol]) {
                    entity.collisionOn = true;
                }
            }
            case "right" -> {
                rightCol = (rightX + entity.speed) / gp.tileSize;
                tileNum1 = gp.tileset.mapTileNum[topRow][rightCol];
                tileNum2 = gp.tileset.mapTileNum[bottomRow][rightCol];
                if (gp.tileset.tile[tileNum1].collision || gp.tileset.tile[tileNum2].collision ||
                        gp.bomb.bombMap[topRow][rightCol] || gp.bomb.bombMap[bottomRow][rightCol]) {
                    entity.collisionOn = true;
                }
            }
        }
    }
}
