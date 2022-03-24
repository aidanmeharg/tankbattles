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
        g.setFont(new Font("American Typewriter", Font.BOLD, 30));
        FontMetrics fm = g.getFontMetrics();
        centreString("GAME OVER", g, fm, game.yboundary / 2 - 50);
        g.setColor(game.getWinningColor());
        centreString(game.getStringResult(), g, fm, game.yboundary / 2);
        g.setColor(new Color(250, 227, 227));
        g.setFont(new Font("American Typewriter", Font.PLAIN, 15));
        FontMetrics metrics = g.getFontMetrics();
        centreString("hit r to run it back", g, metrics, game.yboundary / 2 + 25);
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
        drawPlayerOne(g);
        drawPlayerTwo(g);
        drawMissiles(g);
    }

    // MODIFIES: this, g
    // paints player one onto g
    private void drawPlayerOne(Graphics g) {
        String imgFileName;
        if (game.playerOne.getDx() < 0) {
            imgFileName = "./data/playeroneleft.png";
        } else if (game.playerOne.getDx() > 0) {
            imgFileName = "./data/playeroneright.png";
        } else if (game.playerOne.getDy() > 0) {
            imgFileName = "./data/playeronedown.png";
        } else {
            imgFileName = "./data/playeroneup.png";
        }
        int scaledWidth;
        int scaledHeight;
        if (game.playerOne.getDx() == 0) {
            scaledWidth = Tank.TANK_WIDTH;
            scaledHeight = Tank.TANK_HEIGHT;
        } else {
            scaledWidth = Tank.TANK_HEIGHT;
            scaledHeight = Tank.TANK_WIDTH;
        }
        ImageIcon imgIcon = new ImageIcon(new ImageIcon(imgFileName)
                .getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_DEFAULT));
        imgIcon.paintIcon(this, g, game.playerOne.getXcoord() - (scaledWidth / 2),
                game.playerOne.getYcoord() - (scaledHeight / 2));
    }

    // MODIFIES: this, g
    // paints player one onto g
    private void drawPlayerTwo(Graphics g) {
        String imgFileName;
        if (game.playerTwo.getDx() < 0) {
            imgFileName = "./data/playertwoleft.png";
        } else if (game.playerTwo.getDx() > 0) {
            imgFileName = "./data/playertworight.png";
        } else if (game.playerTwo.getDy() > 0) {
            imgFileName = "./data/playertwodown.png";
        } else {
            imgFileName = "./data/playertwoup.png";
        }
        int scaledWidth;
        int scaledHeight;
        if (game.playerTwo.getDx() == 0) {
            scaledWidth = Tank.TANK_WIDTH;
            scaledHeight = Tank.TANK_HEIGHT;
        } else {
            scaledWidth = Tank.TANK_HEIGHT;
            scaledHeight = Tank.TANK_WIDTH;
        }
        ImageIcon imgIcon = new ImageIcon(new ImageIcon(imgFileName)
                .getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_DEFAULT));
        imgIcon.paintIcon(this, g, game.playerTwo.getXcoord() - (scaledWidth / 2),
                game.playerTwo.getYcoord() - (scaledHeight / 2));
    }



    // MODIFIES: g
    // EFFECTS: draws game missiles onto g
    private void drawMissiles(Graphics g) {
        for (Missile m : game.getMissiles()) {
            drawMissile(g, m);
        }
    }

    // MODIFIES: this, g
    // EFFECTS: paints the missile image icon onto g
    private void drawMissile(Graphics g, Missile m) {
        String imgFileName;
        if (m.getDx() > 0) {
            imgFileName = "./data/gamebullet.png";
        } else if (m.getDx() < 0) {
            imgFileName = "./data/gameleftbullet.png";
        } else if (m.getDy() < 0) {
            imgFileName = "./data/gameupbullet.png";
        } else {
            imgFileName = "./data/gamedownbullet.png";
        }

        ImageIcon imgIcon = new ImageIcon(new ImageIcon(imgFileName)
                .getImage().getScaledInstance(Missile.MISSILE_WIDTH, Missile.MISSILE_HEIGHT, Image.SCALE_DEFAULT));
        imgIcon.paintIcon(this, g, m.getXcoord() - (Missile.MISSILE_WIDTH / 2),
                m.getYcoord() - (Missile.MISSILE_HEIGHT / 2));

    }

}
