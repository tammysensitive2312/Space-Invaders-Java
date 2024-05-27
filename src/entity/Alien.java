package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class Alien extends Sprite{
    int speed;
    private Random random = new Random();

    public Alien(GamePanel gp) {
        super(gp);
        initAlien();
    }

    private void initAlien() {
        Random rand = new Random();
        this.x = rand.nextInt(GamePanel.screenWidth - GamePanel.tileSize); // Random x position
        this.y = rand.nextInt(GamePanel.screenHeight / 2 - GamePanel.tileSize * 3); // Start at the top of the screen
        this.speed = 1; // Set the speed of the alien

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/enemy.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void update() {
        y += speed; // Move the alien downward
        if (gp.level >= 4 && y > GamePanel.screenHeight) {
            speed = 2;
            resetPosition();
        }
        if (gp.level >= 10 && random.nextInt(100) < 1) {
            gp.alienShots.add(new Bomb(gp, x + GamePanel.tileSize / 2, y + GamePanel.tileSize));
        }
    }

    public void resetPosition() {
        this.y = 0;
        this.x = random.nextInt(GamePanel.screenWidth - GamePanel.tileSize);
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, GamePanel.tileSize, GamePanel.tileSize,null);
    }
}
