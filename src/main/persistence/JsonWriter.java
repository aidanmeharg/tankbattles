package persistence;

import model.TankGame;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*
 * represents a writer that saves the game in JSON form to file
 * (modeled from the Workroom App Json serialization demo)
 */

public class JsonWriter {

    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs a new writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens the writer and throws FileNotFoundException if file cannot be opened
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes game state in JSON format to file
    public void write(TankGame game) {
        JSONObject json = game.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes a string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
