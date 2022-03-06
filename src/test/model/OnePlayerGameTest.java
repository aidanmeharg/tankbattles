package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OnePlayerGameTest {

    OnePlayerGame game;

    @BeforeEach
    void setup() {
        game = new OnePlayerGame(100, 100, 15);
    }

    @Test
    void testConstructor() {
        assertEquals(10, game.getTurningCoolDown());
        assertEquals(15, game.getTurnDelay());
    }

    @Test
    void testBotMoveLeftAndFireAfterDelay() {
        game.playerOne.setCoordinates(4, 90);
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
        game.playerOne.setCoordinates(5, 70);
        game.playerTwo.setCoordinates(4, 4);
        game.setTurningCoolDown(0);
        game.tick();
        assertEquals(0, game.playerTwo.dx);
        assertEquals(Tank.TANK_SPEED, game.playerTwo.dy);
    }
}
