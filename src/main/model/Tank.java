package model;

/*
 * represents a tank that has a position, movement direction, health status,
 * and cooldown time required before next missile can be shot
 * (coolDown should be set to zero when new tank is initialized)
 */


public class Tank extends MovingSprite {

    private int health;
    private int coolDown;


    // COOL_DOWN_TIME and STARTING_HEALTH must be >= 0
    public static final int COOL_DOWN_TIME = 50;
    public static final int TANK_SPEED = 3;
    public static final int STARTING_HEALTH = 3;
    public static final int TANK_WIDTH = 50;
    public static final int TANK_HEIGHT = 70;


    // EFFECTS: constructs a new tank object
    public Tank(int x, int y, int dx, int dy) {
        super(x, y, dx, dy);
        this.health = STARTING_HEALTH;
        this.coolDown = 0;
    }

    // EFFECTS: constructs a new tank with additional parameters
    public Tank(int x, int y, int dx, int dy, int health, int coolDown) {
        super(x, y, dx, dy);
        this.health = health;
        this.coolDown = coolDown;
    }


    // getters
    public int getHealth() {
        return this.health;
    }

    public int getCoolDown() {
        return this.coolDown;
    }

    // MODIFIES: this
    // EFFECTS: decreases tank health by 1
    public void decreaseHealth() {
        this.health--;
    }

    // MODIFIES: this
    // EFFECTS: decreases cannon cool down by 1 if it is not at zero already
    public void decreaseCoolDown() {
        if (this.coolDown > 0) {
            this.coolDown--;
        }
    }

    // EFFECTS: returns true if this tank is hit by missile
    public boolean checkTankHitByMissile(Missile missile) {
        return this.xcoord - (TANK_WIDTH / 2) < missile.getXcoord()
                && missile.getXcoord() < this.xcoord + (TANK_WIDTH / 2)
                && this.ycoord - (TANK_HEIGHT / 2) < missile.getYcoord()
                && missile.getYcoord() < this.ycoord + (TANK_HEIGHT / 2);
    }

    // MODIFIES: this
    // EFFECTS: sets tank's coolDown time to COOL_DOWN_TIME
    public void resetCoolDown() {
        this.coolDown = COOL_DOWN_TIME;
    }


    // REQUIRES: health >= 0
    // MODIFIES: this
    // EFFECTS: sets tank health to given value
    public void setHealth(int health) {
        this.health = health;
    }

}
