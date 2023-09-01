package main.bomb;

/**
 * Bomb
 */
public class Bomb {

    BombManager bm;
    Exploded explode;
    public int Row, Col;
    public String status;
    public int explodeRadius;
    int countDown;
    final int countDownTime = 3;

    Bomb(BombManager bm) {
        this.bm = bm;
        status = "idle";
        explode = new Exploded(bm);
    }

    void placeBomb(int x, int y, int eRadius) {
        if (x % bm.gp.tileSize > bm.gp.tileSize / 2) {
            Col = x / bm.gp.tileSize + 1;
        } else {
            Col = x / bm.gp.tileSize;
        }

        if (y % bm.gp.tileSize > bm.gp.tileSize / 2) {
            Row = y / bm.gp.tileSize + 1;
        } else {
            Row = y / bm.gp.tileSize;
        }
        if (!bm.bombMap[Row][Col]) {
            bm.bombMap[Row][Col] = true;
            status = "countdown";
            countDown = bm.gp.FPS * countDownTime;
            explodeRadius = eRadius;
            System.out.println("placed");
        }
    }

    void update() {
        if (status.equals("countdown")) {
            countDown--;
        }
        if (countDown == 0 && status.equals("countdown")) {
            status = "idle";
            explode.blast(Row, Col, explodeRadius);
            bm.bombMap[Row][Col] = false;
        }
        explode.update();
    }

    int drawSpriteNum() {
        int spriteNum = countDown / (bm.gp.FPS / 6);
        return spriteNum % 3;
    }

}