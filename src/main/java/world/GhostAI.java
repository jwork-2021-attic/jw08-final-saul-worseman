package world;

public class GhostAI extends CreatureAI{

    protected char up = 187;
    protected char down = 186;
    protected char left = 184;
    protected char right = 185;
    protected Router router;
    public GhostAI(Creature creature) {
        super(creature);
    }

    @Override
    public void route(){
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
        }
        else{
            this.creature.setX(c.x());
            this.creature.setY(c.y());
        }
    }
}
