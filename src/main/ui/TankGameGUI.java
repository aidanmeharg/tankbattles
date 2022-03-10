package ui;

import model.TankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/*
 * main window where Tank Game is rendered and played
 */

public class TankGameGUI extends JFrame {

    private static final int REFRESH_INTERVAL = 10;
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private TankGame game;
    private GamePanel gamePanel;
    private ScorePanel scorePanel;

    // EFFECTS: constructs and sets up window for TankGame to be played
    public TankGameGUI() {
        super("Tank Battles");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(false);
        game = new TankGame(FRAME_WIDTH, FRAME_HEIGHT);
        gamePanel = new GamePanel(this.game);
        scorePanel = new ScorePanel(this.game);
        add(gamePanel);
        add(scorePanel, BorderLayout.NORTH);
        addKeyListener(new KeyHandler());
        pack();
        centerOnScreen();
        setVisible(true);
        addTimer();

    }

    // EFFECTS: adds a timer that ticks game every REFRESH_INTERVAL milliseconds
    private void addTimer() {
        Timer t = new Timer(REFRESH_INTERVAL, e -> {
            game.tick();
            gamePanel.repaint();
            scorePanel.update();


        });

        t.start();
    }

    // key handler to respond to user input
    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            game.keyPressed(e.getKeyCode());
        }
    }

    // MODIFIES: this
    // EFFECTS: centers frame on screen
    private void centerOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    public static void main(String[] args) {
        new TankGameGUI();
    }


}
