package serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import creature.Creature;

import java.io.IOException;


public class CreatureDeserializer extends StdDeserializer<Creature> {
    public CreatureDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Creature deserialize(JsonParser parser, DeserializationContext deserializer) throws IOException, JsonProcessingException {
        Creature creature = new Creature();
        while(creature.getGlyph() == 0){
            //this will consume the end_array tag,so the loop can not end correctly
            JsonToken jsonToken = parser.nextToken();
            if(JsonToken.FIELD_NAME.equals(jsonToken)){
                String filedName = parser.getCurrentName();
                //System.out.println(filedName);
                jsonToken = parser.nextToken();
                if("title".equals(filedName)){
                    creature.setTitle(parser.getValueAsString());
                }
                else if("Hp".equals(filedName)){
                    creature.setHp(parser.getValueAsInt());
                }
                else if("maxHp".equals(filedName)){
                    creature.setMaxHp(parser.getValueAsInt());
                }
                else if("credits".equals(filedName)){
                    creature.setCredits(parser.getValueAsInt());
                }
                else if("x".equals(filedName)){
                    creature.setX(parser.getValueAsInt());
                }
                else if("y".equals(filedName)){
                    creature.setY(parser.getValueAsInt());
                }
                else if("glyph".equals(filedName)){
                    creature.setGlyph((char) parser.getValueAsInt());
                    jsonToken = parser.nextToken();
                }
            }
        }
        return creature;
    }
}
