package app.parsers;

import app.domain.users.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Parser for user objects.
 * @author jonathan
 */
public class UnsafeUserParser implements Parser<User> {
    @Override
    public String toJSON(User object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    @Override
    public User toObject(String json) {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        return null;
    }
    
}
