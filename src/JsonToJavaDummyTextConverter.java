import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

//import static java.util.logging.Logger.getLogger;

//import org.apache.logging.log4j.core.Logger;

//import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;

/*
 * Java program to convert JSON String into Java object using Jackson library.
 * Jackson is very easy to use and require just two lines of code to create a Java object
 * from JSON String format.
 *
 * @author http://javarevisited.blogspot.com
 */
public class JsonToJavaDummyTextConverter {

  // private static final Logger LOGGER = LoggerFactory.getLogger(JsonToJavaDummyTextConverter.class); //getLoggerFactory();

    private  static final Logger LOGGER = Logger.getLogger(JsonToJavaDummyTextConverter.class.getName());

    public static void jsonToJavaDummyTextConverter(String dummyText) throws JsonParseException, IOException {
       // JsonToJavaDummyTextConverter converter = new JsonToJavaDummyTextConverter();
        //converting JSON String to Java object
        GenDummyText  dummyTextPara =   JsonToJavaDummyTextConverter.fromJson(dummyText);
    }


    public static GenDummyText fromJson(String jsonString) throws JsonParseException, IOException {

      //  System.setProperty(org.slf4j.impl.SimpleLogger.DEFAULT_LOG_LEVEL_KEY, "INFO");

        GenDummyText dummyTextParagraphs = new ObjectMapper().readValue(jsonString, GenDummyText.class);
  //      final Logger LOGGER = LoggerFactory.getLogger(JsonToJavaDummyTextConverter.class); //getLoggerFactory();

        LOGGER.info("Java Object created from JSON String ");
        LOGGER.info("JSON String : " + jsonString);
        LOGGER.info("Java Object : " + dummyTextParagraphs);
        LOGGER.log( Level.FINEST, "I'm here logging ");
        LOGGER.log( Level.FINEST, jsonString);

        return dummyTextParagraphs;
    }

}
