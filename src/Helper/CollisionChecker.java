package Helper;

import entity.Alien;
import entity.Bomb;
import entity.Player;
import entity.Shot;
import main.GamePanel;

import java.awt.*;
import java.util.List;

public class CollisionChecker {
    GamePanel gp;

    public CollisionChecker(GamePanel gp) {
        this.gp = gp;
    }

    public boolean checkCollision(Alien o1, Alien o2) {
        Rectangle r1 = new Rectangle(o1.getX(), o1.getY(), GamePanel.tileSize, GamePanel.tileSize);
        Rectangle r2 = new Rectangle(o2.getX(), o2.getY(), GamePanel.tileSize, GamePanel.tileSize);

        return r1.intersects(r2);
    }

    public boolean checkCollision(Alien alien, Player player) {
        Rectangle r1 = new Rectangle(alien.getX(), alien.getY(), GamePanel.tileSize, GamePanel.tileSize);
        Rectangle r2 = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getWidth());
        return r1.intersects(r2);
    }

    public boolean checkCollision(Shot bullet, Alien alien) {
        int width = bullet.getWidth();
        int height = bullet.getWidth();
        Rectangle r1 = new Rectangle(bullet.getX(), bullet.getY(), width, height);
        Rectangle r2 = new Rectangle(alien.getX(), alien.getY(), GamePanel.tileSize, GamePanel.tileSize);
        return r1.intersects(r2);
    }

    public boolean checkCollision(Bomb bomb, Player player) {
        Rectangle r1 = new Rectangle(bomb.getX(), bomb.getY(), bomb.getWidth(), bomb.getHeight());
        Rectangle r2 = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getWidth());
        return r1.intersects(r2);
    }


    public boolean isPositionSafe(Alien newAlien, List<Alien> aliens) {
        for (Alien alien : aliens) {
            if (checkCollision(newAlien, alien)) {
                return false;
            }
        }
        return true;
    }
}
