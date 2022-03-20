package ui;

import model.OnePlayerGame;

import javax.swing.*;
import java.awt.*;


public class MenuPanel extends JPanel {

    private final GameGUI gameGUI;
    private final JButton newOnePlayerGameButton;
    private final JButton newTwoPlayerGameButton;
    private final JButton loadSavedTwoPlayerGameButton;
    private final JButton loadSavedOnePlayerGameButton;

    public MenuPanel(GameGUI gameGUI) {
        this.gameGUI = gameGUI;
        newOnePlayerGameButton = new JButton("New 1 Player Game");
        newOnePlayerGameButton.addActionListener(e -> setupDifficultyMenu());
        newTwoPlayerGameButton = new JButton("New 2 Player Game");
        newTwoPlayerGameButton.addActionListener(e -> gameGUI.startNewTwoPlayerGame());
        loadSavedTwoPlayerGameButton = new JButton("Load Saved 2P Game");
        loadSavedTwoPlayerGameButton.addActionListener(e -> gameGUI.loadTwoPlayerGame());
        loadSavedOnePlayerGameButton = new JButton("Load Saved 1P Game");
        loadSavedOnePlayerGameButton.addActionListener(e -> gameGUI.loadOnePlayerGame());


        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        setLayout(new GridLayout(4, 1));
        add(newOnePlayerGameButton);
        add(newTwoPlayerGameButton);
        add(loadSavedOnePlayerGameButton);
        add(loadSavedTwoPlayerGameButton);
    }

    private void setupDifficultyMenu() {
        remove(newOnePlayerGameButton);
        remove(newTwoPlayerGameButton);
        remove(loadSavedTwoPlayerGameButton);
        remove(loadSavedOnePlayerGameButton);
        JButton easy = new JButton("EASY");
        easy.addActionListener(e -> gameGUI.startNewOnePlayerGame(OnePlayerGame.EASY_TURN_DELAY));
        JButton medium = new JButton("MEDIUM");
        medium.addActionListener(e -> gameGUI.startNewOnePlayerGame(OnePlayerGame.MEDIUM_TURN_DELAY));
        JButton expert = new JButton("EXPERT");
        expert.addActionListener(e -> gameGUI.startNewOnePlayerGame(OnePlayerGame.DIFFICULT_TURN_DELAY));

        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        setLayout(new GridLayout(3, 1));
        add(easy);
        add(medium);
        add(expert);
        gameGUI.pack();
    }

}
