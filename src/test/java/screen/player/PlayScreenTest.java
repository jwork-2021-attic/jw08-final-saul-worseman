package screen.player;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.*;

class PlayScreenTest {

    static PlayScreen playScreen;
    @BeforeAll
    static  void setUp(){
        playScreen = new PlayScreen();
    }
    @Test
    void target() {
        assertEquals(10,playScreen.target());
    }



    @Test
    void nextFrame() {
        assertTrue(playScreen.nextFrame() instanceof PlayScreen);
    }
}