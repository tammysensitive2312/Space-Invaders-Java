package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Bomb extends Sprite{
    int width = GamePanel.tileSize / 2;
    int height = GamePanel.tileSize / 2;
    int speed = 3;
    GamePanel gp;

    public Bomb(GamePanel gp, int x, int y) {
        super(gp);
        this.x = x;
        this.y = y;
        this.gp = gp;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/white_bomb_with_black_background.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        y += speed;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, width, height,null);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
