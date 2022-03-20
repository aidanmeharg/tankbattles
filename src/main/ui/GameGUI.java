package ui;

import model.OnePlayerGame;
import model.TankGame;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GameGUI extends JFrame {

    private static final int REFRESH_INTERVAL = 10;
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final String JSON_STORE = "./data/savedgame.json";
    private static final String ONE_PLAYER_JSON_STORE = "./data/savedOnePlayerGame.json";



    private final AudioPlayer audioPlayer = new AudioPlayer();
    private static final String GAME_MUSIC_PATH = "./data/aidangamenewbattle.wav";
    private static final String MENU_MUSIC_PATH = "./data/aidangamenewmenu.wav";

    private TankGame game;
    private final MenuPanel menuPanel;
    private GamePanel gamePanel;
    private ScorePanel scorePanel;
    private final JsonReader twoPlayerJsonReader = new JsonReader(JSON_STORE);
    private final JsonWriter twoPlayerJsonWriter = new JsonWriter(JSON_STORE);
    private final JsonReader onePlayerJsonReader = new JsonReader(ONE_PLAYER_JSON_STORE);
    private final JsonWriter onePlayerJsonWriter = new JsonWriter(ONE_PLAYER_JSON_STORE);

    private boolean onePlayer;



    // EFFECTS: initializes a new game window
    public GameGUI() {
        super("Tank Battles");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        menuPanel = new MenuPanel(this);
        add(menuPanel);
        pack();
        centerOnScreen();
        setVisible(true);
        audioPlayer.playSound(MENU_MUSIC_PATH, true);
    }

    // MODIFIES: this
    // EFFECTS: begins a new 1P game in this frame
    public void startNewOnePlayerGame(int difficulty) {
        game = new OnePlayerGame(FRAME_WIDTH, FRAME_HEIGHT, difficulty);
        onePlayer = true;
        initializeGame();
    }

    public void startNewTwoPlayerGame() {
        game = new TankGame(FRAME_WIDTH, FRAME_HEIGHT);
        onePlayer = false;
        initializeGame();
    }

    // EFFECTS: loads the saved game from source file
    public void loadTwoPlayerGame() {
        try {
            game = twoPlayerJsonReader.read(false);
            initializeGame();

        } catch (IOException e) {
            System.out.println("unable to read from file");
        }
    }

    public void loadOnePlayerGame() {
        try {
            game = onePlayerJsonReader.read(true);
            initializeGame();
        } catch (IOException e) {
            System.out.println("unable to read from file");
        }
    }

    private void initializeGame() {
        remove(menuPanel);
        audioPlayer.stopMusic();
        audioPlayer.playSound(GAME_MUSIC_PATH, true);
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
            if (e.getKeyCode() == KeyEvent.VK_T) {
                saveGame();
            }
        }
    }


    private void saveGame() {
        if (onePlayer) {
            try {
                onePlayerJsonWriter.open();
                onePlayerJsonWriter.write(game);
                onePlayerJsonWriter.close();
            } catch (FileNotFoundException e) {
                System.out.println("Game could not be saved to the file: " + JSON_STORE);
            }
        } else {
            try {
                twoPlayerJsonWriter.open();
                twoPlayerJsonWriter.write(game);
                twoPlayerJsonWriter.close();
            } catch (FileNotFoundException e) {
                System.out.println("Game could not be saved to the file: " + JSON_STORE);
            }
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
