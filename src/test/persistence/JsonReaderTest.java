package persistence;

import model.OnePlayerGame;
import model.TankGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testReadNonExistentFile() {
        JsonReader jsonReader = new JsonReader("./data/thisFileDoesNotExist.json");
        try {
            TankGame game = jsonReader.readTwoPlayerGame();
            fail("Expected IO exception");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReadSavedTwoPlayerGame() {
        JsonReader jsonReader = new JsonReader("./data/testReaderSavedGame.json");
        try {
            TankGame game = jsonReader.readTwoPlayerGame();
            assertEquals(39, game.xboundary);
            assertEquals(22, game.yboundary);
            assertEquals(1, game.getPlayerOneScore());
            assertEquals(0, game.getPlayerTwoScore());
            assertEquals(15, game.getPlayerOne().getXcoord());
            assertEquals(13, game.getPlayerOne().getYcoord());
            assertEquals(2, game.getPlayerOne().getHealth());
            assertEquals(6, game.getPlayerOne().getCoolDown());
            assertEquals(23, game.getPlayerTwo().getXcoord());
            assertEquals(7, game.getPlayerTwo().getYcoord());
            assertEquals(3, game.getPlayerTwo().getHealth());
            assertEquals(5, game.getPlayerTwo().getCoolDown());
            assertEquals(2, game.getMissiles().size());

        } catch (IOException e) {
            fail("IOException should not be thrown");
        }
    }

    @Test
    void testReadSavedOnePlayerGame() {
        JsonReader jsonReader = new JsonReader("./data/testReaderSavedOnePlayerGame.json");
        try {
            OnePlayerGame game = jsonReader.readOnePlayerGame();
            assertEquals(800, game.xboundary);
            assertEquals(600, game.yboundary);
            assertEquals(0, game.getPlayerOneScore());
            assertEquals(1, game.getPlayerTwoScore());
            assertEquals(611, game.getPlayerOne().getXcoord());
            assertEquals(311, game.getPlayerOne().getYcoord());
            assertEquals(2, game.getPlayerOne().getHealth());
            assertEquals(0, game.getPlayerOne().getCoolDown());
            assertEquals(498, game.getPlayerTwo().getXcoord());
            assertEquals(550, game.getPlayerTwo().getYcoord());
            assertEquals(3, game.getPlayerTwo().getHealth());
            assertEquals(14, game.getPlayerTwo().getCoolDown());
            assertEquals(3, game.getMissiles().size());
            assertEquals(80, game.getTurnDelay());
            assertEquals(4, game.getTurningCoolDown());

        } catch (IOException e) {
            fail("IOException should not be thrown");
        }
    }
}
