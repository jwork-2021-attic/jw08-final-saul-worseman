package creature;

import router.GhostScaredRouter;
import router.Router;
import world.Tile;

public abstract class GhostAI extends CreatureAI {

    private final int countdown = 25;
    protected char up = 187;
    protected char down = 186;
    protected char left = 184;
    protected char right = 185;
    protected Router router;
    // private Creature creature;
    private int count;
    public GhostAI(Creature creature) {
        super(creature);
        this.creature = creature;
        count = 0;
    }

    @Override
    public void route(){
        count = (count + 1) % countdown;
        if(count == countdown / 5 * 4)
            this.scared();
        if(count == 0)
            this.resume();
        int[] nextSteps = router.nextSteps();
        creature.moveBy(nextSteps[0],nextSteps[1]);
        if(nextSteps[0] == -1){
            creature.setGlyph(left);
        }
        else if(nextSteps[0] == 1){
            creature.setGlyph(right);
        }
        else if(nextSteps[1] == -1){
            creature.setGlyph(up);
        }
        else{
            creature.setGlyph(down);
        }
    }

    @Override
    public void onEnter(int x, int y, Tile tile) {
        if (tile.isGround()) {
            creature.setX(x);
            creature.setY(y);
        }
    }

    @Override
    public void attack(Creature c){
        if(c instanceof Player){
            c.setHp(Player.getPlayer().hp() - 1);
            if(Player.getPlayer().getImmortal() > 0)
                Player.getPlayer().shuffle();
        }
    }

    public abstract void resume();

    public void scared(){
        router = new GhostScaredRouter(creature);
    }

}
