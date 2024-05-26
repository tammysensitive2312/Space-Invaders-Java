package main;

import Helper.Sound;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class StartPanel extends JPanel implements KeyListener {
    JFrame frame;
    Sound music = new Sound();
    int menuOption = 0;

    public StartPanel(JFrame frame) {
        this.setPreferredSize(new Dimension(GamePanel.screenWidth, GamePanel.screenHeight));
        this.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.frame = frame;
        this.addKeyListener(this);
        this.setFocusable(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // background
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/background.png")));
            g2.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // title
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
        String text = "Space Invaders";
        int x = getXforCenteredText(text, g2);
        int y = GamePanel.tileSize*3;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x, y);

        // image
        try {
            BufferedImage image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/image/UFO.png")));
            x = GamePanel.screenWidth/2  - GamePanel.tileSize;
            y += GamePanel.tileSize*2;

            g2.drawImage(image, x, y, GamePanel.tileSize + 20, GamePanel.tileSize + 20, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // menu
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));

        text = "NEW GAME";
        x = getXforCenteredText(text, g2);
        y += GamePanel.tileSize*4;
        g2.drawString(text, x, y);

        if (menuOption == 0) {
            g2.drawString(">", x - GamePanel.tileSize, y); // Draw the pointer for NEW GAME
        }

        text = "QUIT";
        x = getXforCenteredText(text, g2);
        y += GamePanel.tileSize;
        g2.drawString(text, x, y);

        if (menuOption == 1) {
            g2.drawString(">", x - GamePanel.tileSize, y); // Draw the pointer for QUIT
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

    public void setupIntro() {
        playMusic(0);
    }

    private void startGame() {
        GamePanel gp = new GamePanel();
        frame.remove(this);
        frame.add(gp);
        frame.revalidate();
        gp.launchGame();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_UP) {
            menuOption = (menuOption - 1 + 2) % 2;
            repaint();
        } else if (keyCode == KeyEvent.VK_DOWN) {
            menuOption = (menuOption + 1) % 2;
            repaint();
        } else if (keyCode == KeyEvent.VK_ENTER) {
            if (menuOption == 0) {
                startGame();
            } else if (menuOption == 1) {
                System.exit(0);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
