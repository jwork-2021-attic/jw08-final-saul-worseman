package creature;

public class CoinAI extends CreatureAI {
    public final char[] CoinState = {144,145,146,147,148,149,150,151};
    int index;
    public CoinAI(Creature creature) {
        super(creature);
        index = 0;
    }

    @Override
    public void route(){
        index = (index + 1) % CoinState.length;
        creature.setGlyph(CoinState[index]);
    }
    
}
