package serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import creature.Player;

import java.io.IOException;

public class PlayerSerializer extends StdSerializer<Player> {
    public PlayerSerializer(Class<Player> t) {
        super(t);
    }

    @Override
    public void serialize(Player player, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("title", player.getTitle());
        jsonGenerator.writeNumberField("Hp", player.getHp());
        jsonGenerator.writeNumberField("maxHp", player.getMaxHp());
        jsonGenerator.writeNumberField("credits", player.getCredits());
        jsonGenerator.writeNumberField("x", player.getX());
        jsonGenerator.writeNumberField("y", player.getY());
        jsonGenerator.writeNumberField("glyph", player.getGlyph());
        jsonGenerator.writeBooleanField("cheat",player.getCheat());
        jsonGenerator.writeNumberField("immortalSteps",player.getImmortalSteps());
        jsonGenerator.writeEndObject();
    }
}
