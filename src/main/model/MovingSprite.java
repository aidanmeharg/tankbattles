package model;

public abstract class MovingSprite {

    protected int xcoord;
    protected int ycoord;
    protected int dx;
    protected int dy;

    // EFFECTS: constructs a new subtype of MovingSprite at (x,y) with velocity (dx, dy)
    public MovingSprite(int x, int y, int dx, int dy) {
        this.xcoord = x;
        this.ycoord = y;
        this.dx = dx;
        this.dy = dy;
    }

    // MODIFIES: this
    // EFFECTS: moves this by dx and dy
    public void move() {
        this.xcoord += this.dx;
        this.ycoord += this.dy;
    }

    // MODIFIES: this
    // EFFECTS: sets direction to given dx dy
    public void setDirection(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // MODIFIES: this
    // EFFECTS: sets coordinates to (x, y)
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
