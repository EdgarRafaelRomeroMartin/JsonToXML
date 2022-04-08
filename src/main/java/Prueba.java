import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class Prueba {

    public static final String CLASS_NAME = Prueba.class.getSimpleName();
    private static final Logger LOG = Logger.getLogger(CLASS_NAME);
    public static void main(String[] args) throws FileNotFoundException {
        FileReader fileReader = new FileReader("output.json");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        try {
            dBuilder = dbFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            LOG.severe(ex.getMessage());
        }
        Document doc = null;


        JsonReader reader = new JsonReader(fileReader);
        try {
            //handleObject(reader);
            doc = dBuilder.parse(handleArray(reader));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }

    private static void handleObject(JsonReader reader) throws IOException
    {
        reader.beginObject();
        while (reader.hasNext()) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.BEGIN_ARRAY))
                handleArray(reader);
            else if (token.equals(JsonToken.END_OBJECT)) {
                reader.endObject();
                return;
            } else
                handleNonArrayToken(reader, token);
        }

    }

    public static String handleArray(JsonReader reader) throws IOException
    {
        reader.beginArray();
        while (true) {
            JsonToken token = reader.peek();
            if (token.equals(JsonToken.END_ARRAY)) {
                reader.endArray();
                break;
            } else if (token.equals(JsonToken.BEGIN_OBJECT)) {
                handleObject(reader);
            } else if (token.equals(JsonToken.END_OBJECT)) {
                reader.endObject();
            } else
                handleNonArrayToken(reader, token);
        }
        return null;
    }

    public static void handleNonArrayToken(JsonReader reader, JsonToken token) throws IOException
    {


        if ( token.equals(JsonToken.NAME) ) {
            System.out.println( reader.nextName() );
        }
        else if (token.equals(JsonToken.STRING)) {
            System.out.println( reader.nextString() );
        }
        else if (token.equals(JsonToken.NUMBER)) {
            System.out.println(reader.nextDouble());
        }
        else

            reader.skipValue();
    }

}


