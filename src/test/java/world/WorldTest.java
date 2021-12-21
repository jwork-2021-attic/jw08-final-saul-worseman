package world;

import com.fasterxml.jackson.databind.ser.impl.WritableObjectId;
import creature.Creature;
import creature.CreatureFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WorldTest {

    World world;
    @BeforeEach
    void setUp() {
        WorldBuilder worldBuilder = new WorldBuilder(11);
        world = worldBuilder.setTiles().build();
    }

    @Test
    void setCreatures() {
        ArrayList<Creature> temp = new ArrayList<>();
        world.register(new CreatureFactory().newCoin());
        world.setCreatures(temp);

    }

    @Test
    void getTiles() {
        assertFalse(world.getTiles()[0][0].isGround());
        world.setTile(0,0,Tile.PATH);
        assertTrue(world.getTiles()[0][0].isGround());
    }

    @Test
    void setTiles() {
        world.setTiles(world.getTiles());
        world.setTile(0,0,Tile.WALL);
        assertFalse(world.tile(0,0).isGround());
    }


    @Test
    void glyph() {
        assertEquals(Tile.WALL,world.tile(0,0));
    }


    @Test
    void setWidth() {
        world.setWidth(12);
        assertEquals(12,world.getWidth());
        world.setWidth(11);
        assertEquals(11,world.getWidth());
    }

    @Test
    void setHeight() {
        assertEquals(11,world.getHeight());
        world.setHeight(10);
        assertEquals(10,world.getHeight());
        world.setHeight(11);
    }

    @Test
    void addAtEmptyLocation() {
        Creature c = new Creature();
        world.addAtEmptyLocation(c);
        assertEquals(1,world.getCreatures().size());
        world.unlockWorld();
    }



}