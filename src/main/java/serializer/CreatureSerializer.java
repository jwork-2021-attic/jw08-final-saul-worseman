package serializer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import creature.Creature;

import java.io.IOException;



public class CreatureSerializer extends StdSerializer<Creature> {
    public CreatureSerializer(Class<Creature> t) {
        super(t);
    }

    @Override
    public void serialize(Creature creature, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if(!creature.getTitle().equals("Player")) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("title", creature.getTitle());
            jsonGenerator.writeNumberField("Hp", creature.getHp());
            jsonGenerator.writeNumberField("maxHp", creature.getMaxHp());
            jsonGenerator.writeNumberField("credits", creature.getCredits());
            jsonGenerator.writeNumberField("x", creature.getX());
            jsonGenerator.writeNumberField("y", creature.getY());
            jsonGenerator.writeNumberField("glyph", creature.getGlyph());
            jsonGenerator.writeEndObject();
        }
    }
}
