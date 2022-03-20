package persistence;

import model.Missile;
import model.Tank;
import model.TankGame;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    @Test
    void testWriteNonExistentFile() {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/my\0illegal:fileName.json");
            jsonWriter.open();
            fail("IO exception expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriteSavedGame() {
        try {
            Tank playerOne = new Tank(5, 6, 0, 0, 3, 2);
            Tank playerTwo = new Tank(35, 36, 0, 0, 2, 3);
            TankGame game = new TankGame(40, 45, playerOne, playerTwo, 2, 1);
            Missile missile = new Missile(15, 17, 2, 0);
            game.getMissiles().add(missile);

            JsonWriter jsonWriter = new JsonWriter("./data/testWriterSavedGame.json");
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testWriterSavedGame.json");
            game = jsonReader.read(false);
            assertEquals(40, game.xboundary);
            assertEquals(45, game.yboundary);
            assertEquals(2, game.getPlayerOneScore());
            assertEquals(1, game.getPlayerTwoScore());
            assertEquals(5, game.getPlayerOne().getXcoord());
            assertEquals(6, game.getPlayerOne().getYcoord());
            assertEquals(3, game.getPlayerOne().getHealth());
            assertEquals(2, game.getPlayerOne().getCoolDown());
            assertEquals(35, game.getPlayerTwo().getXcoord());
            assertEquals(36, game.getPlayerTwo().getYcoord());
            assertEquals(2, game.getPlayerTwo().getHealth());
            assertEquals(3, game.getPlayerTwo().getCoolDown());
            assertEquals(1, game.getMissiles().size());
        } catch (IOException e) {
            fail("IO exception should not have been thrown");
        }
    }
}
