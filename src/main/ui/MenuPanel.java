package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {

    private GameGUI gameGUI;

    public MenuPanel(GameGUI gameGUI) {
        this.gameGUI = gameGUI;
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(e -> {
            gameGUI.startNewOnePlayerGame();
        });

        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        add(newGameButton);
    }
}
