package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

/*
 * represents the 2 player tank game
 */

public class TankGame implements Writable {

    public static final int TICKS_PER_SECOND = 10;
    public static final int MAX_SCORE = 3;

    private final Tank playerOne;
    private final Tank playerTwo;
    private final ArrayList<Missile> missiles;
    private final int xboundary;
    private final int yboundary;

    private int playerOneScore;
    private int playerTwoScore;


    // EFFECTS: constructs a new tank game with boundaries and 2 players
    //          missiles is empty and P1 at (4,4), P2 at opposite corner
    public TankGame(int xboundary, int yboundary) {
        this.xboundary = xboundary;
        this.yboundary = yboundary;
        this.missiles = new ArrayList<>();
        this.playerOne = new Tank(4, 4, 0, 0);
        this.playerTwo = new Tank(xboundary - 4, yboundary - 4, 0, 0);
        this.playerOneScore = 0;
        this.playerTwoScore = 0;
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
    private void resetGame() {
        if (playerOne.getHealth() < 1) {
            this.playerTwoScore++;
        } else {
            this.playerOneScore++;
        }
        resetTanks();
    }

    // MODIFIES: this
    // EFFECTS: returns both players to initial positions with starting health
    private void resetTanks() {
        this.playerOne.setCoordinates(4, 4);
        this.playerOne.setDirection(0,0);
        this.playerTwo.setCoordinates(xboundary - 4, yboundary - 4);
        this.playerTwo.setDirection(0,0);

        this.playerOne.setHealth(Tank.STARTING_HEALTH);
        this.playerTwo.setHealth(Tank.STARTING_HEALTH);
    }

    // MODIFIES: this
    // EFFECTS: checks if player exits boundaries and deals damage accordingly
    private void handlePlayerBoundaries(Tank tank) {
        if (tank.getXcoord() < 0) {
            tank.setCoordinates(0, tank.getYcoord());
            tank.setDirection(Tank.TANK_SPEED, tank.getDy());
            tank.decreaseHealth();
        } else if (tank.getXcoord() > xboundary) {
            tank.setCoordinates(xboundary, tank.getYcoord());
            tank.setDirection(-Tank.TANK_SPEED, tank.getDy());
            tank.decreaseHealth();
        } else if (tank.getYcoord() < 0) {
            tank.setCoordinates(tank.getXcoord(), 0);
            tank.setDirection(tank.getDx(), Tank.TANK_SPEED);
            tank.decreaseHealth();
        } else if (tank.getYcoord() > yboundary) {
            tank.setCoordinates(tank.getXcoord(), yboundary);
            tank.setDirection(tank.getDx(), -Tank.TANK_SPEED);
            tank.decreaseHealth();
        }
    }

    // MODIFIES: this
    // EFFECTS: fires a new missile from player if coolDown = 0
    public void playerFireMissile(Tank player) {
        if (player.getCoolDown() == 0) {
            if (player.getDx() == 0 && player.getDy() > 0) {
                Missile missile = new Missile(player.getXcoord(), player.getYcoord() + 1, 0, Missile.MISSILE_SPEED);
                missiles.add(missile);
                player.resetCoolDown();
            } else if (player.getDy() < 0) {
                Missile missile = new Missile(player.getXcoord(), player.getYcoord() - 1, 0, -Missile.MISSILE_SPEED);
                missiles.add(missile);
                player.resetCoolDown();
            } else if (player.getDy() == 0 && player.getDx() > 0) {
                Missile missile = new Missile(player.getXcoord() + 1, player.getYcoord(), Missile.MISSILE_SPEED, 0);
                missiles.add(missile);
                player.resetCoolDown();
            } else if (player.getDx() < 0) {
                Missile missile = new Missile(player.getXcoord() - 1, player.getYcoord(), -Missile.MISSILE_SPEED, 0);
                missiles.add(missile);
                player.resetCoolDown();
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: removes missiles that have exited screen bounds
    private void filterBoundaryMissiles() {
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
    private void handlePlayerMissileCollisions(Tank player) {
        ArrayList<Missile> toRemove = new ArrayList<>();
        for (Missile next : missiles) {
            if (player.checkTankHitByMissile(next)) {
                player.decreaseHealth();
                toRemove.add(next);
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
    private boolean checkGameOver() {
        return playerOne.getHealth() < 1 || playerTwo.getHealth() < 1;
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


    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("playerOne", this.playerOne.toJson());
        json.put("playerTwo", this.playerTwo);
        json.put("playerOneScore", this.playerOneScore);
        json.put("playerTwoScore", this.playerTwoScore);
        json.put("xboundary", this.xboundary);
        json.put("yboundary", this.yboundary);
        json.put("missiles", missilesToJson());

        return json;
    }

    // EFFECTS: returns missiles in JSON array format
    private JSONArray missilesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Missile next : missiles) {
            jsonArray.put(next.toJson());
        }
        return jsonArray;
    }
}
