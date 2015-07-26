package app.parsers;

import app.domain.users.Email;
import app.domain.users.User;
import app.domain.utils.HashedString;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.text.ParseException;

/**
 * A parser for new users, to be used when registering.
 * @author jonathan
 */
public class NewUserParser extends UserParser {
    @Override
    public User fromJson(String json) throws ParseException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        
        try{
            User user = new User(
                0,
                object.get("username").getAsString(),
                new HashedString(object.get("password").getAsString(), false),
                new Email(object.get("email").getAsString()));
            
            return user;
        }catch(Exception e) {
            throw new ParseException(e.getMessage(), 0);
        }
    }
}
