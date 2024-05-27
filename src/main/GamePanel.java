package main;

import Helper.CollisionChecker;
import Helper.KeyHandler;
import Helper.Sound;
import entity.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GamePanel extends JPanel implements Runnable {
    static final int originalTileSize = 16;
    static final int scale = 3;
    public static final int tileSize = originalTileSize * scale; // 48x48 tile
    public static final int maxScreenCol = 16;
    public static final int maxScreenRow = 16;
    public static final int screenWidth = tileSize * maxScreenCol;
    public static final int screenHeight = tileSize * maxScreenRow;

    public int level;
    public int score;

    int FPS = 60;
    boolean gameOver = false;
    public KeyHandler key = new KeyHandler(this);
    CollisionChecker checker = new CollisionChecker(this);

    Player player = new Player(this, key);
    List<Shot> bullets;
    List<Alien> aliens;
    List<Explosion> explosions;
    public List<Bomb> alienShots;
    Thread gameThread;
    Sound music = new Sound();
    Sound se = new Sound();

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);

        this.addKeyListener(key);
        this.setFocusable(true);

        bullets = new ArrayList<>();
        aliens = new ArrayList<>();
        explosions = new ArrayList<>();
        alienShots = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            initializeAliens();
        }
    }

    public void launchGame() {
        requestFocusInWindow();
        gameThread = new Thread(this);
        gameThread.start();

        playMusic(0);
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        long drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000) {
                System.out.println("FPS = " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if (!key.enterPressed && !gameOver) {
            player.update();
            if (key.shooting) {
                shoot();
                key.shooting = false;
            }

            // bullet
            for (Shot bullet : bullets) {
                bullet.update();
            }

            for (Bomb alienShot : alienShots) {
                alienShot.update();
                if (checker.checkCollision(alienShot, player)) {
                    explosions.add(new Explosion(player.getX(), player.getY()));
                    playSE(3);
                    gameOver = true;
                }
            }
            // alien
            killEnemies();
            if (score >= level * 20) {
                levelUp();
            }
        }

        if (gameOver) {
            stopMusic();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        g2.setColor(Color.YELLOW);

        // gameOver
        if (gameOver) {
            String text = "GAME OVER";
            int x = getXforCenteredText(text, g2);
            int y = GamePanel.tileSize*3;
            g2.drawString(text, x, y);

            for (Explosion explosion : explosions) {
                explosion.draw(g2);
            }
        } else {
            // background
            try {
                BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/background.png")));
                g2.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(key.enterPressed) {
                String text = "PAUSED";
                int x = getXforCenteredText(text, g2);
                int y = GamePanel.tileSize*3;
                g2.drawString(text, x, y);
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
            g2.setColor(Color.WHITE);
            g2.drawString("Level: " + level, 20, 40);
            g2.drawString("Score: " + score, 20, 70);

            // player
            player.draw(g2);

            // bullet
            for (Shot bullet : new ArrayList<>(bullets)) {
                bullet.draw(g2);
            }

            // aliens
            for (Alien alien : new ArrayList<>(aliens)) {
                alien.draw(g2);
            }

            for (Bomb alienShot : new ArrayList<>(alienShots)) {
                alienShot.draw(g2);
            }

            // explosion effect
            for (Explosion explosion : explosions) {
                explosion.draw(g2);
            }
        }


        g2.dispose();
    }


    private void initializeAliens() {
        Alien alien;
        do {
            alien = new Alien(this);
        } while (!checker.isPositionSafe(alien, aliens));

        aliens.add(alien);
    }

    public void shoot() {
        int bulletX = player.getX() + player.getWidth() / 2;
        int bulletY = player.getY();
        bullets.add(new Shot(bulletX, bulletY));

        playSE(1);
    }

    private void killEnemies() {
        List<Alien> aliensToRemove = new ArrayList<>();
        for (Alien alien : aliens) {
            alien.update();
            if (alien.getY() >= screenHeight) {
                aliensToRemove.add(alien);
            } else if (checker.checkCollision(alien, player)) {
                explosions.add(new Explosion(player.getX(), player.getY()));
                playSE(3);
                gameOver = true;
            }
        }

        List<Shot> bulletToRemove = new ArrayList<>();
        for (Shot bullet : bullets) {
            for (Alien alien : aliens) {
                if (checker.checkCollision(bullet, alien)) {
                    bulletToRemove.add(bullet);
                    aliensToRemove.add(alien);
                    explosions.add(new Explosion(alien.getX(), alien.getY()));
                    playSE(2);
                    score++;
                }
            }
        }
        bullets.removeAll(bulletToRemove);
        aliens.removeAll(aliensToRemove);

        for (int i = 0; i < aliensToRemove.size(); i++) {
            initializeAliens();
        }

        List<Explosion> explosionsToRemove = new ArrayList<>();
        for (Explosion explosion : explosions) {
            explosion.update();
            if (!explosion.isAlive()) {
                explosionsToRemove.add(explosion);
            }
        }
        explosions.removeAll(explosionsToRemove);
    }

    private void levelUp() {
        level++;
        for (int i = 0; i < level; i++) {
            initializeAliens();
        }
    }

    private int getXforCenteredText(String text, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return GamePanel.screenWidth/2 - length/2;
    }

    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }

    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }

    public void stopMusic() {
        music.stop();
    }
}
