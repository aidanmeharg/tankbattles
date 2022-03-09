package ui;

/*
 * Panel in which the tank game is rendered.
 */

import model.Missile;
import model.Tank;
import model.TankGame;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private TankGame game;
    private static final Color PLAYER_ONE_COLOUR = new Color(203, 5, 5);
    private static final Color PLAYER_TWO_COLOUR = new Color(51, 182, 51);
    private static final Color MISSILE_COLOUR = new Color(246, 170, 0);

    // EFFECTS: constructs game panel with specified dimensions and background colour
    public GamePanel(TankGame g) {
        setPreferredSize(new Dimension(g.xboundary, g.yboundary));
        setBackground(Color.BLACK);
        this.game = g;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGame(g);
        if (game.isEnded()) {
            endScreen(g);
        }
    }

    // MODIFIES: g
    // EFFECTS: displays result of the game
    private void endScreen(Graphics g) {
        Color saved = g.getColor();
        g.setColor(new Color(250, 227, 227));
        g.setFont(new Font("American Typewriter", 20, 30));
        FontMetrics fm = g.getFontMetrics();
        centreString("GAME OVER", g, fm, game.yboundary / 2 - 25);
        g.setColor(game.getWinningColor());
        centreString(game.getResult(), g, fm, game.yboundary / 2 + 25);
        g.setColor(saved);
    }

    // MODIFIES: g
    // EFFECTS: centres given string at y coordinate
    private void centreString(String str, Graphics g, FontMetrics fm, int y) {
        int width = fm.stringWidth(str);
        g.drawString(str, (game.xboundary - width) / 2, y);
    }

    // MODIFIES: g
    // EFFECTS: draws the tank game onto g
    private void drawGame(Graphics g) {
        drawPlayers(g);
        drawMissiles(g);
    }

    private void drawPlayers(Graphics g) {
        drawTank(g, game.playerOne, PLAYER_ONE_COLOUR);
        drawTank(g, game.playerTwo, PLAYER_TWO_COLOUR);
    }

    // MODIFIES: g
    // EFFECTS: draws tank of specified colour onto g
    private void drawTank(Graphics g, Tank tank, Color color) {
        Color saved = g.getColor();
        g.setColor(color);
        g.fillRect(tank.getXcoord() - Tank.TANK_WIDTH / 2, tank.getYcoord() - Tank.TANK_HEIGHT / 2,
                Tank.TANK_WIDTH, Tank.TANK_HEIGHT);
        g.setColor(saved);
    }

    // MODIFIES: g
    // EFFECTS: draws game missiles onto g
    private void drawMissiles(Graphics g) {
        for (Missile m : game.getMissiles()) {
            drawMissile(g, m);
        }
    }

    // MODIFIES: g
    // EFFECTS: draws a single missile onto g
    private void drawMissile(Graphics g, Missile m) {
        Color saved = g.getColor();
        g.setColor(MISSILE_COLOUR);
        g.fillOval(m.getXcoord() - Missile.MISSILE_WIDTH / 2, m.getYcoord() - Missile.MISSILE_HEIGHT / 2,
                Missile.MISSILE_WIDTH, Missile.MISSILE_HEIGHT);
        g.setColor(saved);
    }

}
