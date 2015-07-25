package app.parsers;

import app.domain.pictures.Picture;
import app.domain.utils.DateConverter;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jonathan
 */
public class PictureParserTest {
    
    public PictureParserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of toJson method, of class PictureParser.
     */
    @Test
    public void testToJson() throws ParseException {
        PictureParser parser = new PictureParser();
        
        String data = "yolo";
        String encoded = new String(Base64.getEncoder().encode(data.getBytes()));
        Picture picture = new Picture(data.getBytes(), new Date(), 10, 5, BigInteger.ONE);
        String json = parser.toJson(picture);
        
        JsonObject object = new JsonParser().parse(json).getAsJsonObject();
        assertEquals(new BigInteger(object.get("id").getAsString()), picture.getId());
        assertEquals(object.get("data").getAsString(), encoded);
        assertEquals(object.get("likes").getAsLong(), picture.getLikes());
        assertEquals(object.get("dislikes").getAsLong(), picture.getDislikes());
        assertEquals(object.get("date").getAsString(), DateConverter.fromDate(picture.getDate()));
    }

    /**
     * Test of fromJson method, of class PictureParser.
     */
    @Test
    public void testFromJson() throws ParseException {
        BigInteger id = BigInteger.ZERO;
        long likes = 10;
        long dislikes = 5;
        Date date = new Date();
        String data = "yolo";
        String encoded = new String(Base64.getEncoder().encode(data.getBytes()));
        
        JsonObject object = new JsonObject();
        object.addProperty("id", id);
        object.addProperty("data", encoded);
        object.addProperty("likes", likes);
        object.addProperty("dislikes", dislikes);
        object.addProperty("date", DateConverter.fromDate(date));
        
        PictureParser parser = new PictureParser();
        Picture picture = parser.fromJson(object.toString());
        
        assertEquals(picture.getId(), id);
        assertEquals(picture.getLikes(), likes);
        assertEquals(picture.getDislikes(), dislikes);
        assertEquals(DateConverter.fromDate(picture.getDate()), DateConverter.fromDate(date));
        assertEquals(new String(picture.getImage()), data);
    }
    
}
