package creature;

import creature.router.PinkyChaseRouter;

public class PinkyAI extends GhostAI {
    public PinkyAI(Creature creature) {
        super(creature);
        up = 167;
        down = 166;
        left = 164;
        right = 165;
        router = new PinkyChaseRouter(creature);
    }

    @Override
    public void resume() {
        up = 167;
        down = 166;
        left = 164;
        right = 165;
        router = new PinkyChaseRouter(creature);
    }
}
