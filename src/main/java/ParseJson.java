import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ParseJson {
    public static void main(String[] args)
    {

        FileReader fileReader = null;
        try {
             fileReader = new FileReader("sales_array.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        JsonReader reader = new JsonReader(fileReader);

        try {
            //handleObject(reader);
            handleArray(reader);
        } catch (IOException e) {
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

    public static void handleArray(JsonReader reader) throws IOException
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
    }

    public static void handleNonArrayToken(JsonReader reader, JsonToken token) throws IOException
    {
        if ( token.equals(JsonToken.NAME) )
            System.out.println( reader.nextName() );
        else if (token.equals(JsonToken.STRING))
            System.out.println( reader.nextString() );
        else if (token.equals(JsonToken.NUMBER))
            System.out.println( reader.nextDouble() );
        else
            reader.skipValue();
    }


}
