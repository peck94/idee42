package app.parsers;

import java.util.Collection;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.List;

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
    public String toJsonArray(Collection<T> objects) {
        JsonParser parser = new JsonParser();
        JsonArray results = new JsonArray();
        for(T object: objects) {
            JsonElement element = parser.parse(toJson(object));
            results.add(element);
        }
        
        return results.toString();
    }
    
    /**
     * Convert a JSON array to objects.
     * @param json JSON array
     * @return Collection of objects
     */
    public Collection<T> fromJsonArray(String json) {
        List<T> results = new ArrayList<>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for(JsonElement element: array) {
            T object = fromJson(element.toString());
            results.add(object);
        }
        
        return results;
    }
}
