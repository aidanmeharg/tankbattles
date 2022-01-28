package model;

import java.util.ArrayList;

/*
 * represents the 2 player tank game
 */

public class TankGame {

    public static final int TICKS_PER_SECOND = 10;

    private final Tank playerOne;
    private final Tank playerTwo;
    private final ArrayList<Missile> missiles;
    private final int xboundary;
    private final int yboundary;
    private boolean ended;

    // EFFECTS: constructs a new tank game with boundaries and 2 players
    //          missiles is empty and P1 at (4,4), P2 at opposite corner
    public TankGame(int xboundary, int yboundary) {
        this.xboundary = xboundary;
        this.yboundary = yboundary;
        missiles = new ArrayList<>();
        this.playerOne = new Tank(4, 4, 0, 0);
        this.playerTwo = new Tank(xboundary - 4, yboundary - 4, 0, 0);
    }

    // MODIFIES: this
    // EFFECTS: progresses game state
    public void tick() {
        playerOne.moveTank();
        playerTwo.moveTank();
        for (Missile next : missiles) {
            next.moveMissile();
        }
        handlePlayerBoundaries(playerOne);
        handlePlayerBoundaries(playerTwo);
        handleCollisions(playerOne);
        handleCollisions(playerTwo);
        filterBoundaryMissiles();
        if (checkGameOver()) {
            ended = true;
        }
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
    // EFFECTS: fires a new missile from player
    public void playerFireMissile(Tank player) {
        if (player.getDx() == 0) {
            if (player.getDy() > 0) {
                Missile missile = new Missile(player.getXcoord(), player.getYcoord() + 1,
                        0, Missile.MISSILE_SPEED);
                missiles.add(missile);
            } else if (player.getDy() < 0) {
                Missile missile = new Missile(player.getXcoord(), player.getYcoord() - 1,
                        0, - Missile.MISSILE_SPEED);
                missiles.add(missile);
            }
        } else if (player.getDy() == 0) {
            if (player.getDx() > 0) {
                Missile missile = new Missile(player.getXcoord() + 1, player.getYcoord(),
                        Missile.MISSILE_SPEED, 0);
                missiles.add(missile);
            } else if (player.getDx() < 0) {
                Missile missile = new Missile(player.getXcoord() - 1, player.getYcoord(),
                        - Missile.MISSILE_SPEED, 0);
                missiles.add(missile);
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
    private boolean outsideScreenBounds(int x, int y) {
        return x < 0
                || y < 0
                || x > xboundary
                || y > yboundary;
    }

    // MODIFIES: this
    // EFFECTS: checks for missiles that have hit the given player
    //          then deals damage and removes appropriate missiles
    private void handleCollisions(Tank player) {
        ArrayList<Missile> toRemove = new ArrayList<>();
        for (Missile next : missiles) {
            if (next.getXcoord() == player.getXcoord()) {
                if (next.getYcoord() == player.getYcoord()) {
                    player.decreaseHealth();
                    toRemove.add(next);
                }
            }
        }
        missiles.removeAll(toRemove);
    }

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

    public boolean getGameOver() {
        return checkGameOver();
    }


}