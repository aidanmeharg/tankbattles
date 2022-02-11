package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MissileTest {

    private Missile missile;
    private static final int TEST_X = 20;
    private static final int TEST_Y = 22;
    private static final int TEST_DX = 4;
    private static final int TEST_DY = 0;

    @BeforeEach
    void setup() {missile = new Missile(TEST_X, TEST_Y, TEST_DX, TEST_DY);}

    @Test
    void testConstructor() {
        assertEquals(TEST_X, missile.getXcoord());
        assertEquals(TEST_Y, missile.getYcoord());
        assertEquals(TEST_DX, missile.getDx());
        assertEquals(TEST_DY, missile.getDy());
    }

    @Test
    void testSetCoordinates() {
        missile.setCoordinates(12, 14);
        assertEquals(12, missile.getXcoord());
        assertEquals(14, missile.getYcoord());
    }

    @Test
    void testSetDirection() {
        missile.setDirection(0, 4);
        assertEquals(0, missile.getDx());
        assertEquals(4, missile.getDy());
    }

    @Test
    void testMoveMissileRight() {
        missile.setDirection(TEST_DX, 0);
        missile.move();

        assertEquals(TEST_X + TEST_DX, missile.getXcoord());
        assertEquals(TEST_Y, missile.getYcoord());
    }

    @Test
    void testMoveMissileLeft() {
        missile.setDirection(0, TEST_DY);
        missile.move();

        assertEquals(TEST_X, missile.getXcoord());
        assertEquals(TEST_Y + TEST_DY, missile.getYcoord());
    }


}
