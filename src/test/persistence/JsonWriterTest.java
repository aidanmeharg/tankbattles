package persistence;

import model.Missile;
import model.OnePlayerGame;
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
    void testWriteSavedTwoPlayerGame() {
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
            game = jsonReader.readTwoPlayerGame();
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

    @Test
    void testWriteSavedOnePlayerGame() {
        try {
            Tank playerOne = new Tank(140, 250, 1, 0);
            Tank playerTwo = new Tank(500, 380, 0, -1);
            OnePlayerGame game = new OnePlayerGame(800, 600, 70, 12, playerOne, playerTwo,
                    1, 2);
            game.getMissiles().add(new Missile(315, 405, 3, 0));

            JsonWriter jsonWriter = new JsonWriter("./data/testWriterSavedOnePlayerGame.json");
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testWriterSavedOnePlayerGame.json");
            game = jsonReader.readOnePlayerGame();
            assertEquals(800, game.xboundary);
            assertEquals(600, game.yboundary);
            assertEquals(1, game.getPlayerOneScore());
            assertEquals(2, game.getPlayerTwoScore());
            assertEquals(140, game.getPlayerOne().getXcoord());
            assertEquals(250, game.getPlayerOne().getYcoord());
            assertEquals(3, game.getPlayerOne().getHealth());
            assertEquals(0, game.getPlayerOne().getCoolDown());
            assertEquals(500, game.getPlayerTwo().getXcoord());
            assertEquals(380, game.getPlayerTwo().getYcoord());
            assertEquals(3, game.getPlayerTwo().getHealth());
            assertEquals(0, game.getPlayerTwo().getCoolDown());
            assertEquals(1, game.getMissiles().size());
            assertEquals(70, game.getTurnDelay());
            assertEquals(12, game.getTurningCoolDown());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
