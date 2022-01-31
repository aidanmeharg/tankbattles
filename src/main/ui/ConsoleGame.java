package ui;

import com.googlecode.lanterna.*;

import java.io.IOException;

import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogBuilder;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;

import model.Missile;
import model.Tank;
import model.TankGame;

/*
 * renders the simple console based tank game
 * much of this code is based upon Mazen Kotb's Snake Console game demo
 */

public class ConsoleGame {

    private TankGame game;
    private Screen screen;
    private WindowBasedTextGUI endGUI;


    // EFFECTS: begins a new game
    public void start() throws IOException, InterruptedException {
        screen = new DefaultTerminalFactory().createScreen();
        screen.startScreen();

        TerminalSize terminalSize = screen.getTerminalSize();

        game = new TankGame((terminalSize.getColumns() - 1) / 2,
                terminalSize.getRows() - 2);
        
        beginTicks();
    }

    // EFFECTS: begins the game cycle and ticks every TICKS_PER_SECOND
    //          until game has ended and endGUI has been exited.
    private void beginTicks() throws IOException, InterruptedException {
        while (!game.isEnded() || endGUI.getActiveWindow() != null) {
            tick();
            Thread.sleep(100L, TankGame.TICKS_PER_SECOND);
        }
        System.exit(0);
    }

    // MODIFIES: this
    // EFFECTS: updates the game by handling key commands, ticking the game, and rendering all elements
    private void tick() throws IOException {
        handleUserInput();
        game.tick();
        screen.setCursorPosition(new TerminalPosition(0, 0));
        screen.clear();
        renderGame();
        screen.refresh();
        screen.setCursorPosition(new TerminalPosition(screen.getTerminalSize().getColumns() - 1, 0));
        
    }

    // MODIFIES: this
    // EFFECTS: renders all elements of game onto terminal
    private void renderGame() {
        if (game.isEnded()) {
            if (endGUI == null) {
                drawEndScreen();
            }
            return;
        }

        drawHealthBars();
        drawPlayerOne();
        drawPlayerTwo(); 
        drawMissiles(); 
    }

    // MODIFIES: this
    // EFFECTS: draws player one onto terminal
    private void drawPlayerOne() {
        Tank playerOne = game.getPlayerOne();
        drawPosition(playerOne.getXcoord(), playerOne.getYcoord(), TextColor.ANSI.RED, '\u2588', true);
    }

    // MODIFIES: this
    // EFFECTS: draws player two onto terminal
    private void drawPlayerTwo() {
        Tank playerTwo = game.getPlayerTwo();
        drawPosition(playerTwo.getXcoord(), playerTwo.getYcoord(), TextColor.ANSI.BLUE, '\u2588', true);
    }

    // MODIFIES: this
    // EFFECTS: draws all missiles onto terminal
    private void drawMissiles() {
        for (Missile next : game.getMissiles()) {
            drawMissile(next);
        }
    }

    // MODIFIES: this
    // EFFECTS: draws a missile onto terminal
    private void drawMissile(Missile missile) {
        drawPosition(missile.getXcoord(), missile.getYcoord(), TextColor.ANSI.WHITE, '\u25B4', false);
    }

    // MODIFIES: this
    // EFFECTS: draws given character onto terminal at (x,y)
    //          if wide will draw twice
    private void drawPosition(int x, int y, TextColor color, char c, boolean wide) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(color);
        text.putString(x * 2, y + 1, String.valueOf(c));

        if (wide) {
            text.putString(x * 2 + 1, y + 1, String.valueOf(c));
        }
    }

    private String getResult() {
        if (game.getPlayerOne().getHealth() == 0) {
            String result = "BLUE WINS";
            return result;
        } else {
            return "RED WINS";
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the end screen and indicates the winner
    private void drawEndScreen() {
        endGUI = new MultiWindowTextGUI(screen);

        new MessageDialogBuilder()
                .setTitle("GAME OVER")
                .setText(getResult())
                .addButton(MessageDialogButton.Close)
                .build()
                .showDialog(endGUI);
    }

    // MODIFIES: this
    // EFFECTS: draws player's health onto game panel
    private void drawHealthBars() {
        TextGraphics redText = screen.newTextGraphics();
        redText.setForegroundColor(TextColor.ANSI.RED);
        redText.putString(10, 1, "RED HEALTH: " + String.valueOf(game.getPlayerOne().getHealth()));

        TextGraphics blueText = screen.newTextGraphics();
        blueText.setForegroundColor(TextColor.ANSI.BLUE);
        blueText.putString(55, 1, "BLUE HEALTH: " + String.valueOf(game.getPlayerTwo().getHealth()));


    }


    // MODIFIES: this
    // EFFECTS: handles user keyboard inputs
    // TODO: why do error messages appear when arrow keys are pressed?
    private void handleUserInput() throws IOException {
        KeyStroke keyStroke = screen.pollInput();
        if (keyStroke == null) {
            return;
        }
        if (keyStroke.getKeyType() == KeyType.ArrowUp) {
            return;
        }
        if (keyStroke.getCharacter() == 'w') {
            game.getPlayerOne().setDirection(0, -Tank.TANK_SPEED);
        }
        if (keyStroke.getCharacter() == 's') {
            game.getPlayerOne().setDirection(0, Tank.TANK_SPEED);
        }
        if (keyStroke.getCharacter() == 'a') {
            game.getPlayerOne().setDirection(-Tank.TANK_SPEED, 0);
        }
        if (keyStroke.getCharacter() == 'd') {
            game.getPlayerOne().setDirection(Tank.TANK_SPEED, 0);
        }
        if (keyStroke.getCharacter() == 'c') {
            game.playerFireMissile(game.getPlayerOne());
        }
        if (keyStroke.getCharacter() == 'i') {
            game.getPlayerTwo().setDirection(0, - Tank.TANK_SPEED);
        }
        if (keyStroke.getCharacter() == 'k') {
            game.getPlayerTwo().setDirection(0, Tank.TANK_SPEED);
        }
        if (keyStroke.getCharacter() == 'j') {
            game.getPlayerTwo().setDirection(- Tank.TANK_SPEED, 0);
        }
        if (keyStroke.getCharacter() == 'l') {
            game.getPlayerTwo().setDirection(Tank.TANK_SPEED, 0);
        }
        if (keyStroke.getCharacter() == 'n') {
            game.playerFireMissile(game.getPlayerTwo());
        }
    }
}
