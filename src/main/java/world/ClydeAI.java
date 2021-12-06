package world;

public class ClydeAI extends GhostAI{
    public ClydeAI(Creature creature) {
        super(creature);
        up = 183;
        down = 182;
        left = 180;
        right = 181;
        router = new ClydeChaseRouter(creature);
    }
}
