package app.parsers;

import app.domain.pictures.Picture;
import app.domain.users.User;
import app.domain.utils.DateConverter;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

/**
 * Parser for Picture objects.
 * @author jonathan
 */
public class PictureParser extends Parser<Picture> {

    @Override
    public String toJson(Picture object) throws ParseException {
        byte[] encoded = Base64.getEncoder().encode(object.getImage());
        
        JsonObject json = new JsonObject();
        json.addProperty("id", object.getId());
        json.addProperty("data", new String(encoded));
        json.addProperty("likes", object.getLikes());
        json.addProperty("dislikes", object.getDislikes());
        json.addProperty("date", DateConverter.fromDate(object.getDate()));
        json.addProperty("owner", object.getOwner().getId());
        json.addProperty("expired", object.isExpired());
        
        JsonArray actors = new JsonArray();
        for(User user: object.getActors()) {
            actors.add(new JsonPrimitive(user.getId()));
        }
        
        json.add("actors", actors);
        
        return json.toString();
    }

    @Override
    public Picture fromJson(String json) throws ParseException {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        
        byte[] decoded = Base64.getDecoder().decode(object.get("data").getAsString());
        Set<User> actors = new HashSet<>();
        
        try{
            for (JsonElement element : object.get("actors").getAsJsonArray()) {
                actors.add(new User(element.getAsLong()));
            }
            
            Picture picture = new Picture(
                    decoded,
                    DateConverter.toDate(object.get("date").getAsString()),
                    object.get("likes").getAsLong(),
                    object.get("dislikes").getAsLong(),
                    new BigInteger(object.get("id").getAsString()),
                    new User(object.get("owner").getAsLong()),
                    actors,
                    object.get("expired").getAsBoolean()
            );

            return picture;
        }catch(Exception e) {
            throw new ParseException(e.getMessage(), 0);
        }
    }
    
}
