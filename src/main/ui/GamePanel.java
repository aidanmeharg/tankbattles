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

    private final TankGame game;
    private JProgressBar playerOneBar;
    private JProgressBar playerTwoBar;

    private static final ImageIcon P1_UP = new ImageIcon(new ImageIcon("./data/playeroneup.png")
            .getImage().getScaledInstance(Tank.TANK_WIDTH, Tank.TANK_HEIGHT, Image.SCALE_DEFAULT));

    private static final ImageIcon P1_DOWN = new ImageIcon(new ImageIcon("./data/playeronedown.png")
            .getImage().getScaledInstance(Tank.TANK_WIDTH, Tank.TANK_HEIGHT, Image.SCALE_DEFAULT));

    private static final ImageIcon P1_LEFT = new ImageIcon(new ImageIcon("./data/playeroneleft.png")
            .getImage().getScaledInstance(Tank.TANK_HEIGHT, Tank.TANK_WIDTH, Image.SCALE_DEFAULT));

    private static final ImageIcon P1_RIGHT = new ImageIcon(new ImageIcon("./data/playeroneright.png")
            .getImage().getScaledInstance(Tank.TANK_HEIGHT, Tank.TANK_WIDTH, Image.SCALE_DEFAULT));

    private static final ImageIcon P2_UP = new ImageIcon(new ImageIcon("./data/playertwoup.png")
            .getImage().getScaledInstance(Tank.TANK_WIDTH, Tank.TANK_HEIGHT, Image.SCALE_DEFAULT));

    private static final ImageIcon P2_DOWN = new ImageIcon(new ImageIcon("./data/playertwodown.png")
            .getImage().getScaledInstance(Tank.TANK_WIDTH, Tank.TANK_HEIGHT, Image.SCALE_DEFAULT));

    private static final ImageIcon P2_LEFT = new ImageIcon(new ImageIcon("./data/playertwoleft.png")
            .getImage().getScaledInstance(Tank.TANK_HEIGHT, Tank.TANK_WIDTH, Image.SCALE_DEFAULT));

    private static final ImageIcon P2_RIGHT = new ImageIcon(new ImageIcon("./data/playertworight.png")
            .getImage().getScaledInstance(Tank.TANK_HEIGHT, Tank.TANK_WIDTH, Image.SCALE_DEFAULT));

    private static final ImageIcon MISSILE_UP = new ImageIcon(new ImageIcon("./data/gameupbullet.png")
            .getImage().getScaledInstance(Missile.MISSILE_WIDTH, Missile.MISSILE_HEIGHT, Image.SCALE_DEFAULT));

    private static final ImageIcon MISSILE_DOWN = new ImageIcon(new ImageIcon("./data/gamedownbullet.png")
            .getImage().getScaledInstance(Missile.MISSILE_WIDTH, Missile.MISSILE_HEIGHT, Image.SCALE_DEFAULT));

    private static final ImageIcon MISSILE_RIGHT = new ImageIcon(new ImageIcon("./data/gamebullet.png")
            .getImage().getScaledInstance(Missile.MISSILE_WIDTH, Missile.MISSILE_HEIGHT, Image.SCALE_DEFAULT));

    private static final ImageIcon MISSILE_LEFT = new ImageIcon(new ImageIcon("./data/gameleftbullet.png")
            .getImage().getScaledInstance(Missile.MISSILE_WIDTH, Missile.MISSILE_HEIGHT, Image.SCALE_DEFAULT));






    // EFFECTS: constructs game panel with specified dimensions and background colour
    public GamePanel(TankGame g) {
        setPreferredSize(new Dimension(g.xboundary, g.yboundary));
        setBackground(Color.BLACK);
        this.game = g;
        playerOneBar = new JProgressBar(0, Tank.STARTING_HEALTH);
        playerOneBar.setPreferredSize(new Dimension(100, 50));
        playerTwoBar = new JProgressBar(0, Tank.STARTING_HEALTH);
        playerTwoBar.setPreferredSize(new Dimension(100, 50));
        add(playerOneBar);
        add(playerTwoBar);

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
        //drawHealthBars();
    }

    private void drawHealthBars(Graphics g) {
        Color saved = g.getColor();

        playerOneBar.setValue(game.playerOne.getHealth());
        playerOneBar.setLocation(game.playerOne.getXcoord() - 50, game.playerOne.getYcoord() - 75);
        playerOneBar.repaint();

        playerTwoBar.setValue(game.playerTwo.getHealth());
        playerTwoBar.setLocation(game.playerTwo.getXcoord() - 50, game.playerTwo.getYcoord() - 75);
        playerTwoBar.repaint();
    }


    // MODIFIES: this, g
    // EFFECTS: paints player one onto g
    private void drawPlayerOne(Graphics g) {
        ImageIcon imageIcon;
        if (game.playerOne.getDx() < 0) {
            imageIcon = P1_LEFT;
        } else if (game.playerOne.getDx() > 0) {
            imageIcon = P1_RIGHT;
        } else if (game.playerOne.getDy() > 0) {
            imageIcon = P1_DOWN;
        } else {
            imageIcon = P1_UP;
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
        imageIcon.paintIcon(this, g, game.playerOne.getXcoord() - (scaledWidth / 2),
                game.playerOne.getYcoord() - (scaledHeight / 2));
    }

    // MODIFIES: this, g
    // EFFECTS: paints player one onto g
    private void drawPlayerTwo(Graphics g) {
        ImageIcon imageIcon;
        if (game.playerTwo.getDx() < 0) {
            imageIcon = P2_LEFT;
        } else if (game.playerTwo.getDx() > 0) {
            imageIcon = P2_RIGHT;
        } else if (game.playerTwo.getDy() > 0) {
            imageIcon = P2_DOWN;
        } else {
            imageIcon = P2_UP;
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
        imageIcon.paintIcon(this, g, game.playerTwo.getXcoord() - (scaledWidth / 2),
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
        ImageIcon imageIcon;
        if (m.getDx() > 0) {
            imageIcon = MISSILE_RIGHT;
        } else if (m.getDx() < 0) {
            imageIcon = MISSILE_LEFT;
        } else if (m.getDy() < 0) {
            imageIcon = MISSILE_UP;
        } else {
            imageIcon = MISSILE_DOWN;
        }

        imageIcon.paintIcon(this, g, m.getXcoord() - (Missile.MISSILE_WIDTH / 2),
                m.getYcoord() - (Missile.MISSILE_HEIGHT / 2));

    }

}
