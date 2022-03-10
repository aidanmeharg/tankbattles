package ui;

import model.TankGame;

import javax.swing.*;
import java.awt.*;

public class ScorePanel extends JPanel {

    private static final int LABEL_WIDTH = 250;
    private static final int LABEL_HEIGHT = 40;
    private final TankGame game;
    private final JLabel playerOneHealthDisplay;
    private final JLabel playerTwoHealthDisplay;
    private final JLabel gameScoreDisplay;

    // EFFECTS: constructs the score panel with labels
    public ScorePanel(TankGame game) {
        this.game = game;
        setBackground(new Color(97, 97, 201));
        playerOneHealthDisplay = new JLabel("RED HEALTH: " + (game.getPlayerOne().getHealth()));
        playerOneHealthDisplay.setFont(new Font("American Typewriter", Font.PLAIN, 15));
        playerOneHealthDisplay.setPreferredSize(new Dimension(300, LABEL_HEIGHT));
        playerTwoHealthDisplay = new JLabel("GREEN HEALTH: " + (game.getPlayerTwo().getHealth()));
        playerTwoHealthDisplay.setFont(new Font("American Typewriter", Font.PLAIN, 15));
        playerTwoHealthDisplay.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
        gameScoreDisplay = new JLabel(game.getPlayerOneScore() + " - " + game.getPlayerTwoScore());
        gameScoreDisplay.setPreferredSize(new Dimension(150, LABEL_HEIGHT));
        add(playerOneHealthDisplay);
        add(Box.createHorizontalStrut(10));
        add(gameScoreDisplay);
        add(Box.createHorizontalStrut(10));
        add(playerTwoHealthDisplay);
    }

    // MODIFIES: this
    // EFFECTS: updates the score panel based on current status of game
    public void update() {
        playerOneHealthDisplay.setText("RED HEALTH: " + (game.getPlayerOne().getHealth()));
        playerTwoHealthDisplay.setText("GREEN HEALTH: " + (game.getPlayerTwo().getHealth()));
        gameScoreDisplay.setText(game.getPlayerOneScore() + "-" + game.getPlayerTwoScore());
        repaint();
    }
}
