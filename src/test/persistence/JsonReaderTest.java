package persistence;

import model.TankGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testReadNonExistentFile() {
        JsonReader jsonReader = new JsonReader("./data/thisFileDoesNotExist.json");
        try {
            TankGame game = jsonReader.read();
            fail("Expected IO exception");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReadSavedGame() {
        JsonReader jsonReader = new JsonReader("./data/testReaderSavedGame.json");
        try {
            TankGame game = jsonReader.read();
            assertEquals(39, game.xboundary);
            assertEquals(22, game.yboundary);
            assertEquals(2, game.getPlayerOneScore());
            assertEquals(0, game.getPlayerTwoScore());
            assertEquals(29, game.getPlayerOne().getXcoord());
            assertEquals(15, game.getPlayerOne().getYcoord());
            assertEquals(3, game.getPlayerOne().getHealth());
            assertEquals(0, game.getPlayerOne().getCoolDown());
            assertEquals(8, game.getPlayerTwo().getXcoord());
            assertEquals(10, game.getPlayerTwo().getYcoord());
            assertEquals(2, game.getPlayerTwo().getHealth());
            assertEquals(0, game.getPlayerTwo().getCoolDown());

        } catch (IOException e) {
            fail("IOException should not be thrown");
        }
    }
}
