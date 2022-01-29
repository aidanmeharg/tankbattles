package model;

/*
 * represents a tank that has a position, movement direction, and health status
 */

public class Tank {

    private int health;
    private int xcoord;
    private int ycoord;
    private int dx;
    private int dy;

    public static final int TANK_SPEED = 1;
    public static final int STARTING_HEALTH = 3;


    // EFFECTS: constructs a new tank object
    public Tank(int x, int y, int dx, int dy) {
        this.xcoord = x;
        this.ycoord = y;
        this.dx = dx;
        this.dy = dy;
        this.health = STARTING_HEALTH;
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

    // MODIFIES: this
    // EFFECTS: decreases tank health by 1
    public void decreaseHealth() {
        this.health--;
    }

    // MODIFIES: this
    // EFFECTS: sets tank to given direction
    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void setCoordinates(int x, int y) {
        this.xcoord = x;
        this.ycoord = y;
    }

    public void setHealth(int health) {
        this.health = health;
    }



}
