package app.domain.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for converting dates to strings and vice versa.
 * @author jonathan
 */
public class DateConverter {
    private static final SimpleDateFormat format;
    
    static {
        format = new SimpleDateFormat("d/M/y H:m:s:S Z");
    }
    
    /**
     * Convert a string to a date.
     * @param input Input string
     * @return Date
     * @throws ParseException 
     */
    public static Date toDate(String input) throws ParseException {
        return format.parse(input);
    }
    
    /**
     * Convert a date to a string
     * @param input Input date
     * @return String
     */
    public static String fromDate(Date input) {
        return format.format(input);
    }
}
