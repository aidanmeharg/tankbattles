package model;

/*
 * represents a missile that can be fired from a tank
 */


import org.json.JSONObject;
import persistence.Writable;

public class Missile extends MovingSprite implements Writable {

    // INVARIANT: MISSILE_SPEED must be > TANK_SPEED
    public static final int MISSILE_SPEED = 2 * Tank.TANK_SPEED;
    public static final int MISSILE_WIDTH = 10;
    public static final int MISSILE_HEIGHT = 10;


    // EFFECTS: constructs a new missile at given location with given direction
    public Missile(int x, int y, int dx, int dy) {
        super(x, y, dx, dy);
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("xcoord", this.xcoord);
        jsonObject.put("ycoord", this.ycoord);
        jsonObject.put("dx", this.dx);
        jsonObject.put("dy", this.dy);

        return jsonObject;
    }

}
