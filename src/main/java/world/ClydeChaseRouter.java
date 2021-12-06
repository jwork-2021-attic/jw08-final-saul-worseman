package world;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class ClydeChaseRouter implements Router{
    private Creature prey = Player.getPlayer();
    private int preyX;
    private int preyY;
    private int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    public int nextPos[] = new int[2];
    private boolean visited[][];
    private Creature creature;

    public ClydeChaseRouter(Creature creature){
        this.creature = creature;
    }

    @Override
    public  int[] nextSteps(){
        int x;
        int y;
        Tile t;
        int dice;
        do{
            int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
            dice = (int)(Math.random() * 4);
            x = creature.x() + directions[dice][0];
            y = creature.y() + directions[dice][1];
            t = creature.world.tile(x,y);
        } while(!(x < 48 && x > 0 && y < 48 && y > 0 && (t == Tile.PATH || t == Tile.LAVA)));
        return new int[]{x - creature.x(), y - creature.y()};
    }

}
