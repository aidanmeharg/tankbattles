package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TankGameTest {

    private TankGame game;

    @BeforeEach
    void setup() {
        game = new TankGame(100, 100);
    }

    @Test
    void testConstructor() {
        assertEquals(0, game.getMissiles().size());
        assertFalse(game.isEnded());

        // check player one
        assertEquals(Tank.TANK_WIDTH, game.getPlayerOne().getXcoord());
        assertEquals(Tank.TANK_HEIGHT, game.getPlayerOne().getYcoord());
        assertEquals(0, game.getPlayerOne().getDx());
        assertEquals(0, game.getPlayerOne().getDy());
        assertEquals(0, game.getPlayerOneScore());

        // check player two
        assertEquals(100 - Tank.TANK_WIDTH, game.getPlayerTwo().getXcoord());
        assertEquals(100 - Tank.TANK_HEIGHT, game.getPlayerTwo().getYcoord());
        assertEquals(0, game.getPlayerTwo().getDx());
        assertEquals(0, game.getPlayerTwo().getDy());
        assertEquals(0, game.getPlayerTwoScore());

    }

    @Test
    void testTick() {
        game.getPlayerOne().setDirection(Tank.TANK_SPEED, 0);
        game.getPlayerTwo().setDirection(- Tank.TANK_SPEED, 0);
        game.tick();

        // check player one
        assertEquals(Tank.TANK_WIDTH + Tank.TANK_SPEED, game.getPlayerOne().getXcoord());
        assertEquals(Tank.TANK_HEIGHT, game.getPlayerOne().getYcoord());
        assertEquals(Tank.TANK_SPEED, game.getPlayerOne().getDx());
        assertEquals(0,game.getPlayerOne().getDy());
        assertFalse(game.isEnded());

        // check player two
        assertEquals(100 - Tank.TANK_WIDTH - Tank.TANK_SPEED, game.getPlayerTwo().getXcoord());
        assertEquals(100 - Tank.TANK_HEIGHT, game.getPlayerTwo().getYcoord());
        assertEquals(- Tank.TANK_SPEED, game.getPlayerTwo().getDx());
        assertEquals(0, game.getPlayerTwo().getDy());
        assertFalse(game.isEnded());

    }


    @Test
    void testHandleLeftBoundary() {
        game.getPlayerOne().setCoordinates(Tank.TANK_SPEED, 0);
        game.getPlayerOne().setDirection(- Tank.TANK_SPEED, 0);
        game.tick();

        assertEquals(0, game.getPlayerOne().getXcoord());
        assertEquals(- Tank.TANK_SPEED, game.getPlayerOne().getDx());
        assertEquals(Tank.STARTING_HEALTH, game.getPlayerOne().getHealth());

        game.tick();
        assertEquals(0, game.getPlayerOne().getXcoord());
        assertEquals(Tank.TANK_SPEED, game.getPlayerOne().getDx());
        assertEquals(Tank.STARTING_HEALTH - 1, game.getPlayerOne().getHealth());

    }

    @Test
    void testHandleRightBoundary() {
        game.getPlayerOne().setCoordinates(100 - Tank.TANK_SPEED, 0);
        game.getPlayerOne().setDirection(Tank.TANK_SPEED, 0);
        game.tick();

        assertEquals(100, game.getPlayerOne().getXcoord());
        assertEquals(Tank.TANK_SPEED, game.getPlayerOne().getDx());
        assertEquals(Tank.STARTING_HEALTH, game.getPlayerOne().getHealth());

        game.tick();

        assertEquals(100, game.getPlayerOne().getXcoord());
        assertEquals(- Tank.TANK_SPEED, game.getPlayerOne().getDx());
        assertEquals(Tank.STARTING_HEALTH - 1, game.getPlayerOne().getHealth());
    }

    @Test
    void testHandleTopBoundary() {
        game.getPlayerOne().setCoordinates(0, Tank.TANK_SPEED);
        game.getPlayerOne().setDirection(0, - Tank.TANK_SPEED);
        game.tick();

        assertEquals(0, game.getPlayerOne().getYcoord());
        assertEquals(- Tank.TANK_SPEED, game.getPlayerOne().getDy());
        assertEquals(Tank.STARTING_HEALTH, game.getPlayerOne().getHealth());

        game.tick();

        assertEquals(0, game.getPlayerOne().getYcoord());
        assertEquals(Tank.TANK_SPEED, game.getPlayerOne().getDy());
        assertEquals(Tank.STARTING_HEALTH - 1, game.getPlayerOne().getHealth());
    }

    @Test
    void testHandleBottomBoundary() {
        game.getPlayerOne().setCoordinates(0, 100 - Tank.TANK_SPEED);
        game.getPlayerOne().setDirection(0, Tank.TANK_SPEED);
        game.tick();

        assertEquals(100, game.getPlayerOne().getYcoord());
        assertEquals(Tank.TANK_SPEED, game.getPlayerOne().getDy());
        assertEquals(Tank.STARTING_HEALTH, game.getPlayerOne().getHealth());

        game.tick();

        assertEquals(100, game.getPlayerOne().getYcoord());
        assertEquals(- Tank.TANK_SPEED, game.getPlayerOne().getDy());
        assertEquals(Tank.STARTING_HEALTH - 1, game.getPlayerOne().getHealth());
    }

    @Test
    void testPlayerFireMissileNoDirection() {
        game.playerFireMissile(game.getPlayerOne());
        assertEquals(0, game.getMissiles().size());
    }

    @Test
    void testPlayerFireMissileRight() {
        game.getPlayerOne().setDirection(Tank.TANK_SPEED, 0);
        game.playerFireMissile(game.getPlayerOne());

        assertEquals(1, game.getMissiles().size());
        assertEquals(2 * Tank.TANK_WIDTH, game.getMissiles().get(0).getXcoord());
        assertEquals(Tank.TANK_HEIGHT, game.getMissiles().get(0).getYcoord());
        assertEquals(Missile.MISSILE_SPEED, game.getMissiles().get(0).getDx());
        assertEquals(0, game.getMissiles().get(0).getDy());
    }

    @Test
    void testPlayerFireMissileLeft() {
        game.playerOne.setCoordinates(50, 50);
        game.getPlayerOne().setDirection(- Tank.TANK_SPEED, 0);
        game.playerFireMissile(game.getPlayerOne());

        assertEquals(1, game.getMissiles().size());
        assertEquals(50 - Tank.TANK_WIDTH, game.getMissiles().get(0).getXcoord());
        assertEquals(50, game.getMissiles().get(0).getYcoord());
        assertEquals(- Missile.MISSILE_SPEED, game.getMissiles().get(0).getDx());
        assertEquals(0, game.getMissiles().get(0).getDy());
    }

    @Test
    void testPlayerFireMissileUp() {
        game.playerOne.setCoordinates(50, 50);
        game.getPlayerOne().setDirection(0, - Tank.TANK_SPEED);
        game.playerFireMissile(game.getPlayerOne());

        assertEquals(1, game.getMissiles().size());
        assertEquals(50, game.getMissiles().get(0).getXcoord());
        assertEquals(50 - Tank.TANK_WIDTH, game.getMissiles().get(0).getYcoord());
        assertEquals(- Missile.MISSILE_SPEED, game.getMissiles().get(0).getDy());
        assertEquals(0, game.getMissiles().get(0).getDx());
    }

    @Test
    void testPlayerFireMissileDown() {
        game.getPlayerOne().setDirection(0, Tank.TANK_SPEED);
        game.playerFireMissile(game.getPlayerOne());

        assertEquals(1, game.getMissiles().size());
        assertEquals(Tank.TANK_WIDTH, game.getMissiles().get(0).getXcoord());
        assertEquals(2 * Tank.TANK_HEIGHT, game.getMissiles().get(0).getYcoord());
        assertEquals(Missile.MISSILE_SPEED, game.getMissiles().get(0).getDy());
        assertEquals(0, game.getMissiles().get(0).getDx());
    }


    @Test
    void testPlayerFireMissileCoolDown() {
        game.getPlayerOne().setDirection(Tank.TANK_SPEED, 0);
        game.playerFireMissile(game.getPlayerOne());
        game.playerFireMissile(game.getPlayerOne());
        game.playerFireMissile(game.getPlayerOne());

        assertEquals(1, game.getMissiles().size());


    }

    @Test
    void testFilterBoundaryMissiles() {
        game.getPlayerOne().setCoordinates(Missile.MISSILE_SPEED + 1,0);
        game.getPlayerOne().setDirection(- Tank.TANK_SPEED, 0);
        game.playerFireMissile(game.getPlayerOne());
        game.tick();

        assertEquals(1, game.getMissiles().size());

        game.tick();

        assertEquals(0, game.getMissiles().size());
    }

    @Test
    void testCheckGameOverPlayerTwoWins() {
        game.getPlayerOne().setHealth(1);
        game.setPlayerTwoScore(TankGame.MAX_SCORE - 1);
        assertFalse(game.isEnded());

        game.getPlayerOne().decreaseHealth();
        game.tick();
        assertTrue(game.isEnded());
    }

    @Test
    void testCheckGameOverPlayerOneWins() {
        game.getPlayerTwo().setHealth(1);
        game.setPlayerOneScore(TankGame.MAX_SCORE - 1);
        assertFalse(game.isEnded());

        game.getPlayerTwo().decreaseHealth();
        game.tick();
        assertTrue(game.isEnded());
    }

    @Test
    void testPlayerTanksCollide() {
        game.getPlayerTwo().setCoordinates(4, 4);
        game.tick();
        assertEquals(Tank.STARTING_HEALTH - 1, game.getPlayerOne().getHealth());
        assertEquals(Tank.STARTING_HEALTH - 1, game.getPlayerTwo().getHealth());
    }

    @Test
    void testPlayerTanksSameXButNoCollision() {
        game.playerTwo.setCoordinates(4, 5);
        game.tick();
        assertEquals(Tank.STARTING_HEALTH, game.playerOne.getHealth());
        assertEquals(Tank.STARTING_HEALTH, game.playerTwo.getHealth());
    }

    @Test
    void testResetGameBothTanksDestroyed() {
        game.getPlayerOne().setHealth(1);
        game.getPlayerTwo().setHealth(1);
        game.getPlayerTwo().setCoordinates(4, 4);
        game.tick();

        assertEquals(1, game.getPlayerOneScore());
        assertEquals(1, game.getPlayerTwoScore());
    }

    @Test
    void testHandlePlayerMissileCollisions() {
        game.getPlayerOne().setCoordinates(0,0);
        game.getPlayerOne().setDirection(Tank.TANK_SPEED, 0);
        game.getPlayerTwo().setCoordinates(Missile.MISSILE_SPEED + Tank.TANK_SPEED + 1, 0);
        game.getPlayerTwo().setDirection(- Tank.TANK_SPEED,  0);

        game.playerFireMissile(game.getPlayerOne());

        game.tick();

        assertEquals(Tank.STARTING_HEALTH, game.getPlayerOne().getHealth());
        assertEquals(Tank.STARTING_HEALTH - 1, game.getPlayerTwo().getHealth());
        assertEquals(0, game.getMissiles().size());
    }

    @Test
    void testOutsideScreenBoundsTopLeftCorner() {
        assertFalse(game.outsideScreenBounds(0, 0));
    }

    @Test
    void testOutsideScreenBoundsTopRightCorner() {
        assertFalse(game.outsideScreenBounds(100, 0));
    }

    @Test
    void testOutsideScreenBoundsBottomLeftCorner() {
        assertFalse(game.outsideScreenBounds(0, 100));
    }

    @Test
    void testOutsideScreenBoundsBottomRightCorner() {
        assertFalse(game.outsideScreenBounds(100, 100));
    }

    @Test
    void testOutsideScreenBoundsTop() {
        assertTrue(game.outsideScreenBounds(100, -1));
    }

    @Test
    void testOutsideScreenBoundsBottom() {
        assertTrue(game.outsideScreenBounds(100, 101));
    }

    @Test
    void testOutsideScreenBoundsLeft() {
        assertTrue(game.outsideScreenBounds(-1, 50));
    }

    @Test
    void testOutsideScreenBoundsRight() {
        assertTrue(game.outsideScreenBounds(101, 50));
    }

}
