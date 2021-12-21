package creature;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import router.BlinkyChaseRouter;
import router.GhostScaredRouter;

import static org.junit.jupiter.api.Assertions.*;

class BlinkyAITest {
    private static Creature c;
    @BeforeAll
    public static void setUp(){
        c = new Creature();
        new BlinkyAI(c);
    }

    @Test
    public void resumeTest(){
       BlinkyAI ai = ((BlinkyAI) ((GhostAI) c.getAI()));
       ai.resume();
       assertEquals(ai.up,187);
   }

   public void scaredTest(){
        BlinkyAI ai = (BlinkyAI) c.getAI();
        ai.scared();
        assertTrue(ai.getRouter() instanceof GhostScaredRouter);
        ai.resume();
        assertTrue(ai.getRouter() instanceof BlinkyChaseRouter);

   }



}