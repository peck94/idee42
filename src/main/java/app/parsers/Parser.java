package app.parsers;

import java.util.Collection;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * Interface for parsers.
 * @param <T> Type to parse
 * @author jonathan
 */
public abstract class Parser<T> {
    /**
     * Convert object to JSON string
     * @param object Object to be converted
     * @return JSON string
     */
    public abstract String toJson(T object);
    
    /**
     * Convert JSON string into object
     * @param json JSON string
     * @return Object
     */
    public abstract T fromJson(String json);
    
    /**
     * Convert a collection of objects to JSON
     * @param objects Collection to convert
     * @return JSON encoding of collection
     */
    public String toJson(Collection<T> objects) {
        JsonParser parser = new JsonParser();
        JsonArray results = new JsonArray();
        for(T object: objects) {
            JsonElement element = parser.parse(toJson(object));
            results.add(element);
        }
        
        return results.getAsString();
    }
}
