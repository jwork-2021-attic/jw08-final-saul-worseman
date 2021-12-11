package router;

import creature.Creature;
import creature.InkyAI;
import creature.Player;
import world.Tile;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class InkyRouter implements Router{
    private Creature prey = Player.getPlayer();
    private Creature creature;
    private final int mhtDistance = 24;
    private Router router;

    public InkyRouter(Creature creature) {
        this.creature = creature;
        router = new BlinkyChaseRouter(creature);
    }


    @Override
    public int[] nextSteps() {
        int dis = Math.abs(prey.x() - creature.x()) + Math.abs(prey.y() - creature.y());
        if(dis >= mhtDistance){
            router = new BlinkyChaseRouter(creature);
        }
        if(dis < mhtDistance / 3){
            router = new GhostScaredRouter(creature);
        }
        return router.nextSteps();
    }
}
