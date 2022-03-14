package ui;

import model.OnePlayerGame;
import model.TankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameGUI extends JFrame {

    private static final int REFRESH_INTERVAL = 10;
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;

    private final TankGame game;
    private final MenuPanel menuPanel;
    private final GamePanel gamePanel;
    private final ScorePanel scorePanel;



    public GameGUI() {
        super("Tank Battles");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        game = new OnePlayerGame(FRAME_WIDTH, FRAME_HEIGHT, 100);
        gamePanel = new GamePanel(game);
        scorePanel = new ScorePanel(game);
        menuPanel = new MenuPanel(this);
        add(menuPanel);

        pack();
        centerOnScreen();
        setVisible(true);
    }

    public void startNewOnePlayerGame() {
        remove(menuPanel);
        add(gamePanel);
        add(scorePanel, BorderLayout.NORTH);
        gamePanel.addKeyListener(new KeyHandler());
        gamePanel.requestFocus();
        pack();
        centerOnScreen();
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
        new GameGUI();
    }

}
