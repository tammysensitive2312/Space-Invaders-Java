package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Shot extends Sprite{

    public Shot(int x, int y) {
        this.setX(x);
        this.setY(y);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/bullet.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        this.y -= 2;
    }

    public int getWidth() {
        return GamePanel.tileSize / 2 - 2;
    }

    public Shot() {
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, getWidth(), getWidth(),null);
    }
}
