package model;

/*
 * represents a missile that can be fired from a tank
 */


public class Missile extends MovingSprite {

    // INVARIANT: MISSILE_SPEED must be > TANK_SPEED
    public static final int MISSILE_SPEED = 2 * Tank.TANK_SPEED;


    // EFFECTS: constructs a new missile at given location with given direction
    public Missile(int x, int y, int dx, int dy) {
        super(x, y, dx, dy);
    }

}
