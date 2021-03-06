package app.parsers;

import app.domain.users.User;
import app.domain.users.Email;
import app.domain.utils.HashedString;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.text.ParseException;

/**
 * Parser for user objects.
 * @author jonathan
 */
public class UserParser extends Parser<User> {

    @Override
    public String toJson(User user) throws ParseException {
        JsonObject object = new JsonObject();
        object.addProperty("id", user.getId());
        object.addProperty("username", user.getUsername());
        object.addProperty("password", user.getPassword().toString());
        object.addProperty("email", user.getEmail().toString());
        
        return object.toString();
    }

    @Override
    public User fromJson(String json) throws ParseException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        
        try{
            User user = new User(
                object.get("id").getAsLong(),
                object.get("username").getAsString(),
                new HashedString(object.get("password").getAsString(), false),
                new Email(object.get("email").getAsString()));
            
            return user;
        }catch(Exception e) {
            throw new ParseException(e.getMessage(), 0);
        }
    }

}
