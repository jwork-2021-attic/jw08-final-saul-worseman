package creature;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import world.Tile;
import world.World;

import static org.junit.jupiter.api.Assertions.*;

class PlayerAITest {

    static Player player = Player.getPlayer();

    @BeforeAll
    static void setUp(){
        new PlayerAI(player);
    }

    @Test
    void route() {
        player.route();
    }

    @Test
    void setDirection() {
        player.setDirection(0);
        assertEquals(0,player.getDirection());
    }



    @Test
    void onEnter(){
        Tile t = Tile.PATH;
        player.getAI().onEnter(1,1,t);
        assertEquals(1,player.getX());
        assertEquals(1,player.getY());
    }
}