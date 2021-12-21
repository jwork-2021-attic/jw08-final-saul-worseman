package creature;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import world.Tile;

import static org.junit.jupiter.api.Assertions.*;

class CreatureTest {

    CreatureFactory creatureFactory = new CreatureFactory();
    private static Creature c = new Creature(1, (char)186, 200,"Blinky");
    @BeforeAll
    static void setUp(){
        CreatureFactory creatureFactory = new CreatureFactory();
        c = creatureFactory.newCoin();
    }

    @Test
    void setMaxHp() {
        c.setMaxHp(2);
        assertEquals(2,c.getMaxHp());
        c.setMaxHp(1);
    }

    @Test
    void getHp() {
        c.setMaxHp(1);
        assertEquals(1,c.getMaxHp());
    }

    @Test
    void setX() {
        c.setX(2);
        assertEquals(2,c.getX());
    }


    @Test
    void setY() {
        assertEquals(0,c.getY());
        c.setY(2);
        assertEquals(2,c.getY());
    }

    @Test
    void setGlyph() {
        assertEquals(144,c.getGlyph());
        c.setGlyph((char)145);
        assertEquals(145,c.getGlyph());
        c = creatureFactory.newCoin();
    }

    @Test
    void setTitle() {
        assertTrue("Coin".equals(c.getTitle()));
        c.setTitle("1");
        assertTrue("1".equals(c.getTitle()));
        c = creatureFactory.newCoin();
    }

    @Test
    void getWorld() {
        assertNull(c.getWorld());
    }

    @Test
    void setAI() {
        assertTrue(c.getAI() instanceof CoinAI);
        new BlinkyAI(c);
        assertFalse(c.getAI() instanceof CoinAI);
        assertTrue(c.getAI() instanceof BlinkyAI);
        c = creatureFactory.newCoin();
    }


    @Test
    void setCredits() {
        c = creatureFactory.newCoin();
        assertEquals(1,c.getCredits());
        c = creatureFactory.newBlinky();
        assertEquals(200,c.getCredits());
        c = creatureFactory.newCoin();
    }

    @Test
    void setHp() {
        c.setHp(2);
        assertEquals(2,c.getHp());
        c.setHp(1);
    }

    @Test
    void earnCredits() {
        c.earnCredits(c);
        assertEquals(2,c.getCredits());

    }


    @Test
    void isDead() {
        assertFalse(c.isDead());
        c.setHp(0);
        assertTrue(c.isDead());
    }





}