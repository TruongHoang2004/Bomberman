package main.bomb;

public class Exploded {

    final int explodeTime = 1;
    int explodeCountDown;
    int row, col;
    BombManager bm;
    int eRadius;

    public Exploded(BombManager bm) {
        this.bm = bm;
    }

    void setDirection(int valOfCenter) {
        bm.explodeMap[row][col] = valOfCenter;
        boolean leftBlocked = false, rightBlocked = false, upBlocked = false, downBlocked = false;
        int edge = 0;
        for ( int i = 0; i < eRadius; ++i ) {
            if ( valOfCenter != 0 ) {
                edge = ((i + 1 == eRadius)? 1:2) * 10 + valOfCenter % 10;
            }

            //UP
            if ( row - i - 1 >= 0 && !upBlocked) {
                if (bm.gp.tileset.mapTileNum[row - 1 - i][col] == 0) {
                    bm.explodeMap[row - 1 - i][col] = edge * 10 + 1;
                } else {
                    upBlocked = true;
                }
            }

            //DOWN
            if ( row + i + 1 < bm.gp.maxScreenCol && !downBlocked) {
                if (bm.gp.tileset.mapTileNum[row + i + 1][col] == 0) {
                    bm.explodeMap[row + i + 1][col] = edge * 10 + 3;
                } else {
                    downBlocked = true;
                }
            }

            //LEFT
            if ( col - i - 1 >= 0 && !leftBlocked) {
                if ( bm.gp.tileset.mapTileNum[row][col - i - 1] == 0 ) {
                    bm.explodeMap[row][col - i - 1] = edge * 10;
                } else {
                    leftBlocked = true;
                }
            }

            //RIGHT
            if ( col + i + 1 < bm.gp.maxScreenCol && !rightBlocked) {
                if ( bm.gp.tileset.mapTileNum[row][col + i + 1] == 0 ) {
                    bm.explodeMap[row][col + i + 1] = edge * 10 + 2;
                } else {
                    rightBlocked = true;
                }
            }
        }
    }

    void blast(int Row, int Col, int er) {
        this.col = Col;
        this.row = Row;
        this.eRadius = er;
        explodeCountDown = bm.gp.FPS * explodeTime;
        setDirection(30);
    }

    int getSpriteNum() {
        int maxCountDown = explodeTime * bm.gp.FPS;
        int halfCountDown = maxCountDown/2;
        int res;
        if ( explodeCountDown > maxCountDown/2 ) {
            res = ( maxCountDown - explodeCountDown ) / (halfCountDown/4);
        } else {
            res = explodeCountDown / (halfCountDown/4);
        }
        if ( res == 4 ) { res = 3; }
        return res;
    }

    void update() {
        if ( explodeCountDown > 0 ) {
            explodeCountDown--;
            setDirection(30 + getSpriteNum());
        }
        else if ( explodeCountDown == 0 ) {
            clearExplode();
        }
    }

    void clearExplode() {
        explodeCountDown = -1;
        bm.explodeMap[row][col] = 0;
        setDirection(0);
        row = 0;
        col = 0;
        eRadius = 0;
    }
}
