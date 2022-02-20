package persistence;

import org.json.JSONObject;

/*
 * represents a writable object that can be converted to Json format
 */

public interface Writable {

    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
