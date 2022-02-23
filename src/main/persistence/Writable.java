package persistence;

import org.json.JSONObject;

public interface Writable {

    // EFFECTS: returns this as a Json object
    JSONObject toJson();
}
