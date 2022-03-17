package ui;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {

    private GameGUI gameGUI;

    public MenuPanel(GameGUI gameGUI) {
        this.gameGUI = gameGUI;
        JButton newOnePlayerGameButton = new JButton("New 1 Player Game");
        newOnePlayerGameButton.addActionListener(e -> {
            gameGUI.startNewOnePlayerGame();
        });
        JButton newTwoPlayerGameButton = new JButton("New 2 Player Game");
        newTwoPlayerGameButton.addActionListener(e -> {
            gameGUI.startNewTwoPlayerGame();
        });
        JButton loadSavedTwoPlayerGameButton = new JButton("Load Saved 2P Game");
        loadSavedTwoPlayerGameButton.addActionListener(e -> {
            gameGUI.loadTwoPlayerGame();
        });


        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        setLayout(new GridLayout(3, 1));
        add(newOnePlayerGameButton);
        add(newTwoPlayerGameButton);
        add(loadSavedTwoPlayerGameButton);
    }
}
