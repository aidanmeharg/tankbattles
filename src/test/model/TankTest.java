package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TankTest {

    private Tank tank;
    private static final int TEST_X = 10;
    private static final int TEST_Y = 15;
    private static final int TEST_DX = 0;
    private static final int TEST_DY = 0;

    @BeforeEach
    void setup() {
        tank = new Tank(TEST_X, TEST_Y, TEST_DX, TEST_DY);
    }

    @Test
    void testConstructor() {
        assertEquals(TEST_X, tank.getXcoord());
        assertEquals(TEST_Y, tank.getYcoord());
        assertEquals(TEST_DX, tank.getDx());
        assertEquals(TEST_DY, tank.getDy());
        assertEquals(Tank.STARTING_HEALTH, tank.getHealth());
        assertEquals(0, tank.getCoolDown());
    }

    @Test
    void testDecreaseHealth() {
        assertEquals(Tank.STARTING_HEALTH, tank.getHealth());
        tank.decreaseHealth();
        assertEquals(Tank.STARTING_HEALTH - 1, tank.getHealth());
        tank.decreaseHealth();
        assertEquals(Tank.STARTING_HEALTH - 2, tank.getHealth());
    }

    @Test
    void testSetDirection() {
        // set right
        tank.setDirection(Tank.TANK_SPEED, 0);
        assertEquals(Tank.TANK_SPEED, tank.getDx());
        assertEquals(0, tank.getDy());

        // set left
        tank.setDirection(- Tank.TANK_SPEED, 0);
        assertEquals(- Tank.TANK_SPEED, tank.getDx());
        assertEquals(0, tank.getDy());

        // set down
        tank.setDirection(0, Tank.TANK_SPEED);
        assertEquals(0, tank.getDx());
        assertEquals(Tank.TANK_SPEED, tank.getDy());

        // set up
        tank.setDirection(0, - Tank.TANK_SPEED);
        assertEquals(0, tank.getDx());
        assertEquals(- Tank.TANK_SPEED, tank.getDy());

    }

    @Test
    void testMoveTankRight() {
        tank.setDirection(Tank.TANK_SPEED, 0);
        tank.moveTank();

        assertEquals(TEST_X + Tank.TANK_SPEED, tank.getXcoord());
        assertEquals(TEST_Y, tank.getYcoord());
    }

    @Test
    void testMoveTankLeft() {
        tank.setDirection(- Tank.TANK_SPEED, 0);
        tank.moveTank();

        assertEquals(TEST_X - Tank.TANK_SPEED, tank.getXcoord());
        assertEquals(TEST_Y, tank.getYcoord());
    }

    @Test
    void testMoveTankUp() {
        tank.setDirection(0, - Tank.TANK_SPEED);
        tank.moveTank();

        assertEquals(TEST_X, tank.getXcoord());
        assertEquals(TEST_Y - Tank.TANK_SPEED, tank.getYcoord());
    }

    @Test
    void testMoveTankDown() {
        tank.setDirection(0, Tank.TANK_SPEED);
        tank.moveTank();

        assertEquals(TEST_X, tank.getXcoord());
        assertEquals(TEST_Y + Tank.TANK_SPEED, tank.getYcoord());
    }

    @Test
    void testSetCoordinates() {
        tank.setCoordinates(20, 25);
        assertEquals(20, tank.getXcoord());
        assertEquals(25, tank.getYcoord());
    }

    @Test
    void testResetCoolDown() {
        tank.resetCoolDown();
        assertEquals(Tank.COOL_DOWN_TIME, tank.getCoolDown());
    }

    @Test
    void testDecreaseCoolDownAlreadyZero() {
        tank.decreaseCoolDown();
        assertEquals(0, tank.getCoolDown());
    }

    @Test
    void testDecreaseCoolDown() {
        tank.resetCoolDown();
        tank.decreaseCoolDown();
        assertEquals(Tank.COOL_DOWN_TIME - 1, tank.getCoolDown());

        tank.decreaseCoolDown();
        assertEquals(Tank.COOL_DOWN_TIME - 2, tank.getCoolDown());
    }

    @Test
    void testHitByMissileInXRangeOutYRange() {
        Missile missile = new Missile(3, 3 + Tank.TANK_HEIGHT, 3, 0);
        tank.setCoordinates(3, 3);
        assertFalse(tank.checkTankHitByMissile(missile));
    }

    @Test
    void testHitByMissileOutXRangeInYRange() {
        Missile missile = new Missile(3 + Tank.TANK_WIDTH, 3, 3, 0);
        tank.setCoordinates(3, 3);
        assertFalse(tank.checkTankHitByMissile(missile));
    }

    @Test
    void testHitByMissileTopBoundary() {
        Missile missile = new Missile(3, 3 - (Tank.TANK_HEIGHT / 2), 3, 0);
        tank.setCoordinates(3, 3);
        assertFalse(tank.checkTankHitByMissile(missile));
    }

    @Test
    void testHitByMissileBottomBoundary() {
        Missile missile = new Missile(3, 3 + (Tank.TANK_HEIGHT / 2), 3, 0);
        tank.setCoordinates(3, 3);
        assertFalse(tank.checkTankHitByMissile(missile));
    }

    @Test
    void testHitByMissileLeftBoundary() {
        Missile missile = new Missile(3 - (Tank.TANK_WIDTH / 2), 3, 3, 0);
        tank.setCoordinates(3, 3);
        assertFalse(tank.checkTankHitByMissile(missile));
    }

    @Test
    void testHitByMissileRightBoundary() {
        Missile missile = new Missile(3 + (Tank.TANK_WIDTH / 2), 3, 3, 0);
        tank.setCoordinates(3, 3);
        assertFalse(tank.checkTankHitByMissile(missile));
    }

    @Test
    void testHitByMissileInsideLeftBoundary() {
        Missile missile = new Missile(3 - (Tank.TANK_WIDTH / 3), 3, 3, 0);
        tank.setCoordinates(3, 3);
        assertTrue(tank.checkTankHitByMissile(missile));
    }

}