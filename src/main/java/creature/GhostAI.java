package creature;

import router.GhostScaredRouter;
import router.Router;
import world.Tile;

public abstract class GhostAI extends CreatureAI {


    private final char runUp = 163;
    private final char runDown = 162;
    private final char runLeft = 160;
    private final char runRight = 161;
    private final int countdown = 25;
    protected char up = 163;
    protected char down = 162;
    protected char left = 160;
    protected char right = 161;
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
        if(Player.getPlayer().getImmortalSteps() > 0){
            this.scared();
            this.changeface();
        }
        if(Player.getPlayer().getImmortalSteps() == 0){
            this.resume();
        }
        if(count == countdown / 5 * 4 && Player.getPlayer().getImmortalSteps() == 0)
            this.scared();
        else if(count == 0 && Player.getPlayer().getImmortalSteps() == 0)
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
            if(Player.getPlayer().getImmortalSteps() == 0) {
                c.setHp(Player.getPlayer().getHp() - 1);
                Player.getPlayer().shuffle();
            }

        }
    }

    public abstract void resume();

    public void scared(){
        router = new GhostScaredRouter(creature);
    }

    public void changeface(){
        up = runUp;
        right = runRight;
        down = runDown;
        left = runLeft;
    }

}
