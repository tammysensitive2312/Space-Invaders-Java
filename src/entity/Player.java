package entity;

import main.GamePanel;
import Helper.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.*;

public class Player extends Sprite{
    int width = GamePanel.tileSize;
    int height = GamePanel.tileSize;
    int speed = 4;
    public KeyHandler keyH;


    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public Player(GamePanel gamePanel, KeyHandler handler) {
        super(gamePanel);
        this.keyH = handler;
        setDefaultValues();
    }

    public void setDefaultValues() {
        x = GamePanel.screenWidth/2 - (GamePanel.tileSize/2);
        y = 576 - GamePanel.tileSize/2;
    }

    public void update() {
        move();
    }

    private void move() {
        if (keyH.leftPressed || keyH.rightPressed || keyH.upPressed || keyH.downPressed) {
            if (keyH.leftPressed) {
                x -= speed;
            } else if (keyH.rightPressed) {
                x += speed;
            } else if (keyH.upPressed) {
                y -= speed;
            } else {
                y += speed;
            }

            if (x <= 2) {
                x = 2;
            }
            if (x >= GamePanel.screenWidth - GamePanel.tileSize) {
                x = GamePanel.screenWidth - GamePanel.tileSize;
            }
            if (y <= 2) {
                y = 2;
            }
            if (y >= GamePanel.screenHeight - GamePanel.tileSize) {
                y = GamePanel.screenHeight - GamePanel.tileSize;
            }
        }
    }


    public void draw(Graphics2D g2) {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/UFO.png")));
            g2.drawImage(image, x, y, width, height, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}