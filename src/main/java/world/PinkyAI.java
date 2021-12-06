package world;

public class PinkyAI extends GhostAI{
    private char up = 167;
    private char down = 166;
    private char left = 164;
    private char right = 165;
    private Router router;

    public PinkyAI(Creature creature) {
        super(creature);
        up = 167;
        down = 166;
        left = 164;
        right = 165;
        router = new PinkyChaseRouter(creature);
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
