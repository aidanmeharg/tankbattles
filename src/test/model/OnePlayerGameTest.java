package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OnePlayerGameTest {

    OnePlayerGame game;

    @BeforeEach
    void setup() {
        game = new OnePlayerGame(800, 800, 15);
    }

    @Test
    void testConstructor() {
        assertEquals(10, game.getTurningCoolDown());
        assertEquals(15, game.getTurnDelay());
    }

    @Test
    void testBotMoveLeftAndFireAfterDelay() {
        game.playerOne.setCoordinates(100, 90);
        game.playerTwo.setCoordinates(700, 95);
        for (int i = 0; i < 10; i++) {
            game.tick();
        }
        assertEquals(0, game.playerTwo.dx);
        assertEquals(0, game.playerTwo.dy);

        game.tick();
        assertEquals(- Tank.TANK_SPEED, game.playerTwo.dx);
        assertEquals(0, game.playerTwo.dy);
        assertEquals(1, game.getMissiles().size());
    }

    @Test
    void testBotMoveUpSameXAndYDisplacement() {
        game.playerTwo.setCoordinates(700, 700);
        game.playerOne.setCoordinates(100, 100);
        game.setTurningCoolDown(0);
        game.tick();
        assertEquals(0, game.playerTwo.dx);
        assertEquals(- Tank.TANK_SPEED, game.playerTwo.dy);
        assertEquals(1, game.getMissiles().size());
    }

    @Test
    void testBotMoveRight() {
        game.playerOne.setCoordinates(40, 4);
        game.playerTwo.setCoordinates(4, 4);
        game.setTurningCoolDown(0);
        game.tick();
        assertEquals(Tank.TANK_SPEED, game.playerTwo.dx);
        assertEquals(0, game.playerTwo.dy);
    }

    @Test
    void testBotMoveDown() {
        game.playerOne.setCoordinates(400, 70);
        game.playerTwo.setCoordinates(401, 10);
        game.setTurningCoolDown(0);
        game.tick();
        assertEquals(0, game.playerTwo.dx);
        assertEquals(Tank.TANK_SPEED, game.playerTwo.dy);
    }

    @Test
    void testLeftBoundaryBotTurningCoolDown() {
        game.playerTwo.setCoordinates(Tank.TANK_WIDTH / 2 + 1, 200);
        game.tick();
        assertEquals(14, game.getTurningCoolDown());
    }

    @Test
    void testRightBoundaryBotTurningCoolDown() {
        game.playerTwo.setCoordinates(800 - Tank.TANK_WIDTH / 2 - 1, 200);
        game.tick();
        assertEquals(14, game.getTurningCoolDown());
    }

    @Test
    void testBottomBoundaryBotTurningCoolDown() {
        game.playerTwo.setCoordinates(200, 800 - Tank.TANK_HEIGHT / 2 - 1);
        game.tick();
        assertEquals(14, game.getTurningCoolDown());
    }

    @Test
    void testTopBoundaryBotTurningCoolDown() {
        game.playerTwo.setCoordinates(200, Tank.TANK_HEIGHT / 2 + 1);
        game.tick();
        assertEquals(14, game.getTurningCoolDown());
    }
}
