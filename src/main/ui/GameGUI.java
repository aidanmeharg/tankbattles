package ui;

import model.OnePlayerGame;
import model.TankGame;
import persistence.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class GameGUI extends JFrame {

    private static final int REFRESH_INTERVAL = 10;
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final String JSON_STORE = "./data/savedgame.json";

    private TankGame game;
    private final MenuPanel menuPanel;
    private GamePanel gamePanel;
    private ScorePanel scorePanel;
    private JsonReader jsonReader = new JsonReader(JSON_STORE);



    public GameGUI() {
        super("Tank Battles");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        menuPanel = new MenuPanel(this);
        add(menuPanel);

        pack();
        centerOnScreen();
        setVisible(true);
    }

    public void startNewOnePlayerGame() {
        game = new OnePlayerGame(FRAME_WIDTH, FRAME_HEIGHT, 90);
        initializeGame();

    }

    private void initializeGame() {
        remove(menuPanel);
        gamePanel = new GamePanel(game);
        scorePanel = new ScorePanel(game);
        add(gamePanel);
        add(scorePanel, BorderLayout.NORTH);
        gamePanel.addKeyListener(new KeyHandler());
        gamePanel.requestFocus();
        pack();
        centerOnScreen();
        addTimer();
    }

    public void startNewTwoPlayerGame() {
        game = new TankGame(FRAME_WIDTH, FRAME_HEIGHT);
        initializeGame();
    }

    // EFFECTS: loads the saved game from source file
    public void loadTwoPlayerGame() {
        try {
            game = jsonReader.read();
            initializeGame();

        } catch (IOException e) {
            System.out.println("unable to read from file");
        }
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