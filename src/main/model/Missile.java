package model;

/*
 * represents a missile that can be fired from a tank
 */

import org.json.JSONObject;
import persistence.Writable;

public class Missile extends MovingSprite implements Writable {

    // INVARIANT: MISSILE_SPEED must be > TANK_SPEED
    public static final int MISSILE_SPEED = 2 * Tank.TANK_SPEED;


    // EFFECTS: constructs a new missile at given location with given direction
    public Missile(int x, int y, int dx, int dy) {
        super(x, y, dx, dy);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("xcoord", this.xcoord);
        json.put("ycoord", this.ycoord);
        json.put("dx", this.dx);
        json.put("dy", this.dy);
        return json;
    }
}
