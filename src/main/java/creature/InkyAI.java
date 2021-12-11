package creature;

import router.InkyRouter;

public class InkyAI extends GhostAI{

    public InkyAI(Creature creature){
        super(creature);
        up = 179;
        down = 178;
        left = 176;
        right = 177;
        router = new InkyRouter(creature);
    }

    @Override
    public void resume() {
        up = 179;
        down = 178;
        left = 176;
        right = 177;
        router = new InkyRouter(creature);
    }
}
