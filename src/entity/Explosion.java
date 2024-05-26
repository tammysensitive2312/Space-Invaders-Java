package entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class Explosion extends Sprite{
    private int life = 20;

    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/Explosion.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        life--;
    }

    public boolean isAlive() {
        return life > 0;
    }

    public void draw(Graphics2D g2) {
        if (life > 0) {
            g2.drawImage(image, x, y, null);
        }
    }
}
