package model;

/*
 * represents a single player tank game (against a simple bot)
 * NPC will fire missile whenever possible and move towards the player controlled tank
 */

public class OnePlayerGame extends TankGame {

    private int turningCoolDown;
    private final int turnDelay;

    public static final int EASY_TURN_DELAY = 20;
    public static final int MEDIUM_TURN_DELAY = 15;
    public static final int DIFFICULT_TURN_DELAY = 10;


    // EFFECTS: constructs a new game with given turn delay time for bot
    //          and turningCoolDown initially set to 10
    public OnePlayerGame(int xboundary, int yboundary, int turnDelay) {
        super(xboundary, yboundary);
        turningCoolDown = 10;
        this.turnDelay = turnDelay;

    }

    @Override
    public void tick() {
        controlBot();
        super.tick();
    }

    // MODIFIES: this
    // EFFECTS: controls behaviour of simple bot opponent
    private void controlBot() {
        int displacementX = playerOne.xcoord - playerTwo.xcoord;
        int displacementY = playerOne.ycoord - playerTwo.ycoord;
        if (turningCoolDown <= 0) {
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
            playerFireMissile(playerTwo);
            turningCoolDown = turnDelay;
        }
        turningCoolDown--;

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

}
