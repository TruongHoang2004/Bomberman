package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, placePressed;

    public int upCode, downCode, leftCode, rightCode, placeCode;

    public KeyHandler() {
        upCode = KeyEvent.VK_W;
        downCode = KeyEvent.VK_S;
        leftCode = KeyEvent.VK_A;
        rightCode = KeyEvent.VK_D;
        placeCode = KeyEvent.VK_SPACE;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == upCode) {
            upPressed = true;
        }
        if (code == downCode) {
            downPressed = true;
        }
        if (code == leftCode) {
            leftPressed = true;
        }
        if (code == rightCode) {
            rightPressed = true;
        }
        if (code == placeCode) {
            placePressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == upCode) {
            upPressed = false;
        }
        if (code == downCode) {
            downPressed = false;
        }
        if (code == leftCode) {
            leftPressed = false;
        }
        if (code == rightCode) {
            rightPressed = false;
        }
        if (code == placeCode) {
            placePressed = false;
        }
    }
}
