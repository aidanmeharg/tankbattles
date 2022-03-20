package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;
import ui.AudioPlayer;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/*
 * represents the 2 player tank game
 */

public class TankGame implements Writable {

    private final AudioPlayer audioPlayer = new AudioPlayer();
    private static final String BOUNDARY_COLLISION_SOUND = "./data/soundeffect1hit.wav";
    private static final String MISSILE_FIRE_SOUND = "./data/soundeffect3shooting.wav";
    private static final String TANK_HIT_SOUND = "./data/soundeffec2shot.wav";


    public static final int MAX_SCORE = 3;

    public final Tank playerOne;
    public final Tank playerTwo;
    protected final ArrayList<Missile> missiles;
    public final int xboundary;
    public final int yboundary;

    private int playerOneScore;
    private int playerTwoScore;
    private boolean resultReceived;


    // EFFECTS: constructs a new tank game with boundaries and 2 players
    //          missiles is empty players at opposite corners
    public TankGame(int xboundary, int yboundary) {
        this.xboundary = xboundary;
        this.yboundary = yboundary;
        this.missiles = new ArrayList<>();
        this.playerOne = new Tank(Tank.TANK_WIDTH, Tank.TANK_HEIGHT, 0, 0);
        this.playerTwo = new Tank(xboundary - Tank.TANK_WIDTH, yboundary - Tank.TANK_HEIGHT, 0, 0);
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
        resultReceived = false;
    }

    // EFFECTS: constructs a TankGame in a specified state
    public TankGame(int xboundary, int yboundary, Tank playerOne, Tank playerTwo,
                    int playerOneScore, int playerTwoScore) {
        this.xboundary = xboundary;
        this.yboundary = yboundary;
        this.missiles = new ArrayList<>();
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.playerOneScore = playerOneScore;
        this.playerTwoScore = playerTwoScore;
    }

    // MODIFIES: this
    // EFFECTS: progresses game state
    public void tick() {
        decreasePlayerCoolDown();
        playerOne.move();
        playerTwo.move();
        for (Missile next : missiles) {
            next.move();
        }
        handlePlayerBoundaries(playerOne);
        handlePlayerBoundaries(playerTwo);
        handlePlayerMissileCollisions(playerOne);
        handlePlayerMissileCollisions(playerTwo);
        filterBoundaryMissiles();
        if (checkGameOver()) {
            resetGame();
        }
    }

    // MODIFIES: this
    // EFFECTS: returns the tanks to initial positions and increments their scores accordingly
    protected void resetGame() {
        if (playerOne.getHealth() < 1
                && playerTwo.getHealth() < 1) {
            this.playerOneScore++;
            this.playerTwoScore++;
        } else if (playerOne.getHealth() < 1) {
            this.playerTwoScore++;
        } else {
            this.playerOneScore++;
        }
        resetTanks();
        missiles.clear();
    }

    // MODIFIES: this
    // EFFECTS: returns both players to initial positions with starting health
    private void resetTanks() {
        this.playerOne.setCoordinates(Tank.TANK_WIDTH, Tank.TANK_HEIGHT);
        this.playerOne.setDirection(0, 0);
        this.playerTwo.setCoordinates(xboundary - Tank.TANK_WIDTH, yboundary - Tank.TANK_HEIGHT);
        this.playerTwo.setDirection(0, 0);

        this.playerOne.setHealth(Tank.STARTING_HEALTH);
        this.playerTwo.setHealth(Tank.STARTING_HEALTH);
    }

    // MODIFIES: this
    // EFFECTS: checks if player exits boundaries and deals damage accordingly
    protected void handlePlayerBoundaries(Tank tank) {
        if (tank.getXcoord() - (Tank.TANK_WIDTH / 2) < 0) {
            tank.setCoordinates(Tank.TANK_WIDTH / 2, tank.getYcoord());
            tank.setDirection(Tank.TANK_SPEED, tank.getDy());
            tank.decreaseHealth();
            audioPlayer.playSound(BOUNDARY_COLLISION_SOUND, false);
        } else if (tank.getXcoord() + (Tank.TANK_WIDTH / 2) > xboundary) {
            tank.setCoordinates(xboundary - (Tank.TANK_WIDTH / 2), tank.getYcoord());
            tank.setDirection(-Tank.TANK_SPEED, tank.getDy());
            tank.decreaseHealth();
            audioPlayer.playSound(BOUNDARY_COLLISION_SOUND, false);
        } else if (tank.getYcoord() - (Tank.TANK_HEIGHT / 2) < 0) {
            tank.setCoordinates(tank.getXcoord(), Tank.TANK_HEIGHT / 2);
            tank.setDirection(tank.getDx(), Tank.TANK_SPEED);
            tank.decreaseHealth();
            audioPlayer.playSound(BOUNDARY_COLLISION_SOUND, false);
        } else if (tank.getYcoord() + (Tank.TANK_HEIGHT / 2) > yboundary) {
            tank.setCoordinates(tank.getXcoord(), yboundary - (Tank.TANK_HEIGHT / 2));
            tank.setDirection(tank.getDx(), -Tank.TANK_SPEED);
            tank.decreaseHealth();
            audioPlayer.playSound(BOUNDARY_COLLISION_SOUND, false);
        }
    }

    // MODIFIES: this
    // EFFECTS: fires a new missile from player if coolDown = 0
    public void playerFireMissile(Tank player) {
        if (player.getCoolDown() == 0) {
            if (player.getDx() == 0 && player.getDy() > 0) {
                Missile missile = new Missile(player.getXcoord(), player.getYcoord() + Tank.TANK_HEIGHT,
                        0, Missile.MISSILE_SPEED);
                missiles.add(missile);
                player.resetCoolDown();
            } else if (player.getDy() < 0) {
                Missile missile = new Missile(player.getXcoord(), player.getYcoord() - Tank.TANK_HEIGHT,
                        0, -Missile.MISSILE_SPEED);
                missiles.add(missile);
                player.resetCoolDown();
            } else if (player.getDy() == 0 && player.getDx() > 0) {
                Missile missile = new Missile(player.getXcoord() + Tank.TANK_WIDTH,
                        player.getYcoord(), Missile.MISSILE_SPEED, 0);
                missiles.add(missile);
                player.resetCoolDown();
            } else if (player.getDx() < 0) {
                Missile missile = new Missile(player.getXcoord() - Tank.TANK_WIDTH,
                        player.getYcoord(), -Missile.MISSILE_SPEED, 0);
                missiles.add(missile);
                player.resetCoolDown();
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: removes missiles that have exited screen bounds
    protected void filterBoundaryMissiles() {
        ArrayList<Missile> toRemove = new ArrayList<>();
        for (Missile next : missiles) {
            if (outsideScreenBounds(next.getXcoord(), next.getYcoord())) {
                toRemove.add(next);
            }
        }
        missiles.removeAll(toRemove);
    }

    // EFFECTS: produces true if given position is outside of screen bounds
    public boolean outsideScreenBounds(int x, int y) {
        return x < 0
                || y < 0
                || x > xboundary
                || y > yboundary;
    }

    // MODIFIES: this
    // EFFECTS: checks for missiles that have hit the given player
    //          then deals damage and removes appropriate missiles
    protected void handlePlayerMissileCollisions(Tank player) {
        ArrayList<Missile> toRemove = new ArrayList<>();
        for (Missile next : missiles) {
            if (player.checkTankHitByMissile(next)) {
                player.decreaseHealth();
                toRemove.add(next);
                audioPlayer.playSound(TANK_HIT_SOUND, false);
            }
        }
        missiles.removeAll(toRemove);
    }


    // MODIFIES: this
    // EFFECTS: decreases the coolDown times of player 1 and 2
    public void decreasePlayerCoolDown() {
        playerOne.decreaseCoolDown();
        playerTwo.decreaseCoolDown();
    }

    // EFFECTS: returns true if game is over (namely if player 1 or 2 has 0 health)
    protected boolean checkGameOver() {
        return playerOne.getHealth() < 1 || playerTwo.getHealth() < 1;
    }

    // EFFECTS: returns result of the game
    public String getStringResult() {
        if (getPlayerTwoScore() >= TankGame.MAX_SCORE
                && getPlayerOneScore() >= TankGame.MAX_SCORE
                && !resultReceived) {
            resultReceived = true;
            return "DRAW";
        } else if (getPlayerOneScore() >= TankGame.MAX_SCORE) {
            resultReceived = true;
            return "RED WINS";
        } else {
            resultReceived = true;
            return "GREEN WINS";
        }
    }

    // EFFECTS: returns colour of winning tank
    public Color getWinningColor() {
        if (getPlayerTwoScore() >= TankGame.MAX_SCORE
                && getPlayerOneScore() >= TankGame.MAX_SCORE
                && !resultReceived) {
            return Color.WHITE;
        } else if (getPlayerOneScore() >= TankGame.MAX_SCORE) {
            return Color.RED;
        } else {
            return Color.GREEN;
        }
    }

    // MODIFIES: this
    // EFFECTS: handles user input and key commands
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
    public void keyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_W:
                this.playerOne.setDirection(0, -Tank.TANK_SPEED);
                break;
            case KeyEvent.VK_S:
                this.playerOne.setDirection(0, Tank.TANK_SPEED);
                break;
            case KeyEvent.VK_A:
                this.playerOne.setDirection(-Tank.TANK_SPEED, 0);
                break;
            case KeyEvent.VK_D:
                this.playerOne.setDirection(Tank.TANK_SPEED, 0);
                break;
            case KeyEvent.VK_SPACE:
                if (playerOne.getCoolDown() == 0) {
                    audioPlayer.playSound(MISSILE_FIRE_SOUND, false);
                }
                this.playerFireMissile(playerOne);
                break;
            case KeyEvent.VK_UP:
                this.playerTwo.setDirection(0, -Tank.TANK_SPEED);
                break;
            case KeyEvent.VK_DOWN:
                this.playerTwo.setDirection(0, Tank.TANK_SPEED);
                break;
            case KeyEvent.VK_LEFT:
                this.playerTwo.setDirection(-Tank.TANK_SPEED, 0);
                break;
            case KeyEvent.VK_RIGHT:
                this.playerTwo.setDirection(Tank.TANK_SPEED, 0);
                break;
            case KeyEvent.VK_COMMA:
                if (playerOne.getCoolDown() == 0) {
                    audioPlayer.playSound(MISSILE_FIRE_SOUND, false);
                }
                this.playerFireMissile(playerTwo);
                break;
            case KeyEvent.VK_R:
                if (isEnded()) {
                    resetTanks();
                    this.playerOneScore = 0;
                    this.playerTwoScore = 0;
                }

        }
    }

    // getters
    public ArrayList<Missile> getMissiles() {
        return this.missiles;
    }

    public Tank getPlayerOne() {
        return this.playerOne;
    }

    public Tank getPlayerTwo() {
        return this.playerTwo;
    }

    public boolean isEnded() {
        return playerOneScore >= MAX_SCORE || playerTwoScore >= MAX_SCORE;
    }

    public void setPlayerOneScore(int score) {
        this.playerOneScore = score;
    }

    public int getPlayerOneScore() {
        return playerOneScore;
    }

    public int getPlayerTwoScore() {
        return playerTwoScore;
    }

    public void setPlayerTwoScore(int score) {
        this.playerTwoScore = score;
    }

    public boolean getResultReceived() {
        return resultReceived;
    }

    public void setResultReceived(Boolean result) {
        this.resultReceived = result;
    }


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("playerOneX", this.playerOne.xcoord);
        json.put("playerOneY", this.playerOne.ycoord);
        json.put("playerOneHealth", this.playerOne.getHealth());
        json.put("playerOneCoolDown", this.playerOne.getCoolDown());
        json.put("playerTwoX", this.playerTwo.xcoord);
        json.put("playerTwoY", this.playerTwo.ycoord);
        json.put("playerTwoHealth", this.playerTwo.getHealth());
        json.put("playerTwoCoolDown", this.playerTwo.getCoolDown());
        json.put("playerOneScore", this.playerOneScore);
        json.put("playerTwoScore", this.playerTwoScore);
        json.put("xboundary", this.xboundary);
        json.put("yboundary", this.yboundary);
        json.put("missiles", missilesToJson());
        json.put("resultReceived", this.resultReceived);

        return json;
    }

    // EFFECTS: returns this games missiles in a Json array
    private JSONArray missilesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Missile next : missiles) {
            jsonArray.put(next.toJson());
        }
        return jsonArray;
    }


}
