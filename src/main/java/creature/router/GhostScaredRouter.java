package creature.router;

import creature.Creature;

public class GhostScaredRouter implements Router{
    Router router;

    public GhostScaredRouter(Creature creature){
        router = new ClydeChaseRouter(creature);
    }
    @Override
    public int[] nextSteps() {
        return router.nextSteps();
    }
}
