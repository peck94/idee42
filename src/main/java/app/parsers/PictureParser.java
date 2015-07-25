package app.parsers;

import app.domain.pictures.Picture;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.math.BigInteger;
import java.util.Base64;
import java.util.Date;

/**
 * Parser for Picture objects.
 * @author jonathan
 */
public class PictureParser extends Parser<Picture> {

    @Override
    public String toJson(Picture object) {
        byte[] encoded = Base64.getEncoder().encode(object.getImage());
        
        JsonObject json = new JsonObject();
        json.addProperty("id", object.getId());
        json.addProperty("data", new String(encoded));
        json.addProperty("likes", object.getLikes());
        json.addProperty("dislikes", object.getDislikes());
        json.addProperty("date", object.getDate().toString());
        
        return json.toString();
    }

    @Override
    public Picture fromJson(String json) {
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        Picture picture = new Picture(
                object.get("data").getAsString().getBytes(),
                new Date(object.get("date").getAsString()),
                object.get("likes").getAsLong(),
                object.get("dislikes").getAsLong(),
                new BigInteger(object.get("id").getAsString())
        );
        
        return picture;
    }
    
}
