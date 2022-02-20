package persistence;

import model.TankGame;
import model.Tank;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/*
 * represents a reader that reads a TankGame from JSON format in file
 * (modeled from the Workroom App Json serialization demo)
 */

public class JsonReader {

    private String source;

    // EFFECTS: constructs a reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads TankGame from file and returns it, throws IOException if errors occur in reading
    public TankGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseTankGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses TankGame from JSON object and returns it
    private TankGame parseTankGame(JSONObject jsonObject) {
        int xboundary = jsonObject.getInt("xboundary");
        int yboundary = jsonObject.getInt("yboundary");
        int playerOneScore = jsonObject.getInt("playerOneScore");
        int playerTwoScore = jsonObject.getInt("playerTwoScore");
        int playerOneX = jsonObject.getInt("playerOneX");
        int playerOneY = jsonObject.getInt("playerOneY");
        int playerOneHealth = jsonObject.getInt("playerOneHealth");
        int playerOneCoolDown = jsonObject.getInt("playerOneCoolDown");
        int playerTwoX = jsonObject.getInt("playerTwoX");
        int playerTwoY = jsonObject.getInt("playerTwoY");
        int playerTwoHealth = jsonObject.getInt("playerTwoHealth");
        int playerTwoCoolDown = jsonObject.getInt("playerTwoCoolDown");

        Tank playerOne = new Tank(playerOneX, playerOneY, 0, 0, playerOneHealth, playerOneCoolDown);
        Tank playerTwo = new Tank(playerTwoX, playerTwoY, 0, 0, playerTwoHealth, playerTwoCoolDown);

        TankGame game = new TankGame(xboundary, yboundary, playerOne, playerTwo,
                playerOneScore, playerTwoScore);

        return game;
    }



}
