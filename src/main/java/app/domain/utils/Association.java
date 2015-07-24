package app.domain.utils;

/**
 * Represents an association between two objects.
 * @author jonathan
 * @param <S> Source type
 * @param <T> Target type
 */
public class Association<S, T> {
    // store source and target
    private final S source;
    private final T target;
    
    /**
     * Create a new association
     * @param source Source object
     * @param target Target object
     */
    public Association(S source, T target) {
        this.source = source;
        this.target = target;
    }
    
    public S getSource() {
        return source;
    }
    
    public T getTarget() {
        return target;
    }
}
