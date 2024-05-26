package main;

import javax.swing.*;
import java.awt.*;


public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Space Invader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        StartPanel sp = new StartPanel(frame);
//        GamePanel gp = new GamePanel();
        frame.add(sp, BorderLayout.CENTER);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

//        gp.launchGame();

        sp.setupIntro();
    }
}
