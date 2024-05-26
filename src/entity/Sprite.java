package entity;

import main.GamePanel;

import java.awt.image.BufferedImage;

public class Sprite {
    protected BufferedImage image;
    protected boolean dying;
    GamePanel gp;
    protected int x, y;

    public Sprite(GamePanel gp) {
        this.gp = gp;
    }
    public Sprite() {

    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
