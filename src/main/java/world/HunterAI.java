package world;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class HunterAI extends CreatureAI{


    private Creature prey = Player.getPlayer();
    private int preyX;
    private int preyY;
    private int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    public int nextStep[] = new int[2];
    private boolean visited[][];
    public HunterAI(Creature creature) {
        super(creature);
    }


    private void bfs(){
        Queue<int[]> queue = new LinkedList<int[]>();
        visited = new boolean[creature.world.width()][creature.world.width()];
        for (int i = 0; i < creature.world.width(); i++){
            Arrays.fill(visited[i],false);
        }
        System.out.println(preyX + " " + preyY);
        queue.offer(new int[]{preyX, preyY});
        visited[preyX][preyY] = true;
        while(!queue.isEmpty()){
            int[] tuple = queue.poll();
            for(int i = 0; i < 4; i++){
                int x = tuple[0] + directions[i][0];
                int y = tuple[1] + directions[i][1];
                Tile t = creature.world.tile(x,y);
                if(x == creature.x() && y == creature.y()){
                    nextStep[0] = tuple[0];
                    nextStep[1] = tuple[1];
                    return;
                }
                else if(x < 48 && x > 0 && y < 48 && y > 0 && (t == Tile.PATH || t == Tile.LAVA)){
                    System.out.println(x + " " + y);
                    if(visited[x][y] == false){
                        queue.add(new int[]{x,y});
                        visited[x][y] = true;
                    }
                }
            }
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
    public void route(){
        preyX = prey.x();
        preyY = prey.y();
        bfs();
        creature.moveBy(nextStep[0] - creature.x(), nextStep[1] - creature.y());
        creature.setHp(creature.Hp - 4);
        if(creature.isDead() == true)
            Player.getPlayer().earnCredits(creature);
    }
}
