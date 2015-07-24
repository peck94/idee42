package app.parsers;

/**
 * Interface for JSON parsers.
 * @param <T> Type of object to parse
 * @author jonathan
 */
public interface Parser<T> {
    String toJSON(T object);
    T toObject(String json);
}
