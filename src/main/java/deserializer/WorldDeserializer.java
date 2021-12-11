package deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import world.World;

import javax.swing.*;
import java.io.IOException;

public class WorldDeserializer extends StdDeserializer<World> {
    protected WorldDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public World deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException, JsonProcessingException {
        World world = new World();
        while(!parser.isClosed()){
            JsonToken jsonToken = parser.nextToken();
            if(JsonToken.FIELD_NAME.equals(jsonToken)){
                String filedName = parser.getCurrentName();
                System.out.println(filedName);
            }
        }
        return world;
    }
}
