package model;

/*
 * represents a single player tank game (against a simple bot)
 * NPC will fire missile whenever possible and move towards the player controlled tank
 */

import org.json.JSONObject;

public class OnePlayerGame extends TankGame {

    private int turningCoolDown;
    private final int turnDelay;

    public static final int EASY_TURN_DELAY = 100;
    public static final int MEDIUM_TURN_DELAY = 80;
    public static final int DIFFICULT_TURN_DELAY = 60;


    // EFFECTS: constructs a new game with given turn delay time for bot
    //          and turningCoolDown initially set to 10
    public OnePlayerGame(int xboundary, int yboundary, int turnDelay) {
        super(xboundary, yboundary);
        turningCoolDown = 10;
        this.turnDelay = turnDelay;

    }

    // EFFECTS: constructs new game in specified state
    public OnePlayerGame(int xboundary, int yboundary, int turnDelay, int turningCoolDown,
                         Tank playerOne, Tank playerTwo, int playerOneScore, int playerTwoScore) {
        super(xboundary, yboundary, playerOne, playerTwo, playerOneScore, playerTwoScore);
        this.turnDelay = turnDelay;
        this.turningCoolDown = turningCoolDown;
    }

    @Override
    public void tick() {
        controlBot();
        super.tick();
    }

    // MODIFIES: this
    // EFFECTS: controls behaviour of simple bot opponent
    private void controlBot() {
        if (turningCoolDown <= 0 || isBotHittingWall()) {
            int displacementX = playerOne.xcoord - playerTwo.xcoord;
            int displacementY = playerOne.ycoord - playerTwo.ycoord;
            if (Math.abs(displacementX) > Math.abs(displacementY)) {
                if (displacementX < 0) {
                    playerTwo.setDirection(-Tank.TANK_SPEED, 0);
                } else {
                    playerTwo.setDirection(Tank.TANK_SPEED, 0);
                }
            } else if (displacementY < 0) {
                playerTwo.setDirection(0, -Tank.TANK_SPEED);
            } else {
                playerTwo.setDirection(0, Tank.TANK_SPEED);
            }
            turningCoolDown = turnDelay;
        }
        playerFireMissile(playerTwo);
        turningCoolDown--;

    }

    // EFFECTS: returns true if playerTwo is about to hit boundary
    private boolean isBotHittingWall() {
        return playerTwo.xcoord - (Tank.TANK_WIDTH / 2) - 1 <= 0
                || playerTwo.xcoord + (Tank.TANK_WIDTH / 2) + 1 >= xboundary
                || playerTwo.ycoord - (Tank.TANK_HEIGHT / 2) - 1 <= 0
                || playerTwo.ycoord + (Tank.TANK_HEIGHT / 2) + 1 >= yboundary;
    }

    public int getTurningCoolDown() {
        return turningCoolDown;
    }

    public void setTurningCoolDown(int newCool) {
        turningCoolDown = newCool;
    }

    public int getTurnDelay() {
        return turnDelay;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = super.toJson();
        json.put("turnDelay", this.turnDelay);
        json.put("turningCoolDown", this.turningCoolDown);
        return json;

    }

}
