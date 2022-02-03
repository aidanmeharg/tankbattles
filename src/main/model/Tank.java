package model;

/*
 * represents a tank that has a position, movement direction, health status,
 * and cooldown time required before next missile can be shot
 * (coolDown should be set to zero when new tank is initialized)
 */

public class Tank {

    private int health;
    private int xcoord;
    private int ycoord;
    private int dx;
    private int dy;
    private int coolDown;


    // COOL_DOWN_TIME must be >= 0
    public static final int COOL_DOWN_TIME = 10;
    public static final int TANK_SPEED = 1;
    public static final int STARTING_HEALTH = 3;
    public static final int TANK_WIDTH = 4;
    public static final int TANK_HEIGHT = 2;


    // EFFECTS: constructs a new tank object
    public Tank(int x, int y, int dx, int dy) {
        this.xcoord = x;
        this.ycoord = y;
        this.dx = dx;
        this.dy = dy;
        this.health = STARTING_HEALTH;
        this.coolDown = 0;
    }

    // MODIFIES: this
    // EFFECTS: moves tank by speed in current direction
    public void moveTank() {
        this.xcoord += this.dx;
        this.ycoord += this.dy;
    }

    // getters
    public int getHealth() {
        return this.health;
    }

    public int getXcoord() {
        return this.xcoord;
    }

    public int getYcoord() {
        return this.ycoord;
    }

    public int getDx() {
        return this.dx;
    }

    public int getDy() {
        return this.dy;
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
        if (this.xcoord - (TANK_WIDTH / 2) < missile.getXcoord()
                && missile.getXcoord() < this.xcoord + (TANK_WIDTH / 2)
                && this.ycoord - (TANK_HEIGHT / 2) < missile.getYcoord()
                && missile.getYcoord() < this.ycoord + (TANK_HEIGHT / 2)) {
            return true;
        } else {
            return false;
        }
    }

    // MODIFIES: this
    // EFFECTS: sets tank's coolDown time to COOL_DOWN_TIME
    public void resetCoolDown() {
        this.coolDown = COOL_DOWN_TIME;
    }

    // MODIFIES: this
    // EFFECTS: sets tank to given direction
    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // MODIFIES: this
    // EFFECTS: sets tank xcoord and ycoord to (x, y)
    public void setCoordinates(int x, int y) {
        this.xcoord = x;
        this.ycoord = y;
    }

    // REQUIRES: health >= 0
    // MODIFIES: this
    // EFFECTS: sets tank health to given value
    public void setHealth(int health) {
        this.health = health;
    }



}
