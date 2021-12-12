package serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import creature.Player;

import java.io.IOException;

public class PlayerDeserializer extends StdDeserializer<Player> {
    public PlayerDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Player deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Player player = Player.getPlayer();
        while(!parser.isClosed()){
            JsonToken jsonToken = parser.nextToken();
            if(JsonToken.FIELD_NAME.equals(jsonToken)){
                String filedName = parser.getCurrentName();
                //System.out.println(filedName);
                jsonToken = parser.nextToken();
                if("title".equals(filedName)){
                    player.setTitle(parser.getValueAsString());
                }
                else if("Hp".equals(filedName)){
                    player.setHp(parser.getValueAsInt());
                }
                else if("maxHp".equals(filedName)){
                    player.setMaxHp(parser.getValueAsInt());
                }
                else if("credits".equals(filedName)){
                    player.setCredits(parser.getValueAsInt());
                }
                else if("x".equals(filedName)){
                    player.setX(parser.getValueAsInt());
                }
                else if("y".equals(filedName)){
                    player.setY(parser.getValueAsInt());
                }
                else if("glyph".equals(filedName)){
                    player.setGlyph((char) parser.getValueAsInt());
                }
                else if("cheat".equals(filedName)){
                    player.setCheat(parser.getValueAsBoolean());
                }
                else if("immortalSteps".equals(filedName)){
                    player.setImmortalSteps(parser.getValueAsInt());
                }
            }
        }
        return player;
    }
}
