package model;

/*
 * represents a missile that can be fired from a tank
 */

public class Missile {

    // INVARIANT: MISSILE_SPEED must be > TANK_SPEED
    public static final int MISSILE_SPEED = 2 * Tank.TANK_SPEED;

    private int xcoord;
    private int ycoord;
    private int dx;
    private int dy;

    // EFFECTS: constructs a new missile at given location with given direction
    public Missile(int x, int y, int dx, int dy) {
        this.xcoord = x;
        this.ycoord = y;
        this.dx = dx;
        this.dy = dy;
    }

    // MODIFIES: this
    // EFFECTS: moves missile by dx and dy
    public void moveMissile() {
        this.xcoord += this.dx;
        this.ycoord += this.dy;
    }

    // setters

    // MODIFIES: this
    // EFFECTS: sets missile direction to given dx dy
    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // MODIFIES: this
    // EFFECTS: sets missile coordinates to (x, y)
    public void setCoordinates(int x, int y) {
        this.xcoord = x;
        this.ycoord = y;
    }

    // getters

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
        return  this.dy;
    }



}
