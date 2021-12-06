package world;

public class BlinkyAI extends CreatureAI{

    private char up = 187;
    private char down = 186;
    private char left = 184;
    private char right = 185;
    private Router router;
    public BlinkyAI(Creature creature) {
        super(creature);
        router = new BlinkyChaseRouter(creature);
    }


}
