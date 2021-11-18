package world;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class HunterAI extends CreatureAI{


    private Creature preyer = Player.getPlayer();
    private int preyerX;
    private int preyerY;
    private int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    public int nextStep[] = new int[2];
    private boolean visited[][];
    public HunterAI(Creature creature) {
        super(creature);
        visited = new boolean[50][50];
    }

    Queue<int[]> queue = new LinkedList<int[]>();
    private void bfs(){
        for (int i = 0; i < creature.world.width(); i++){
            Arrays.fill(visited[i],false);
        }
        System.out.println(preyerX + " " + preyerY);
        queue.offer(new int[]{preyerX,preyerY});
        visited[preyerX][preyerY] = true;
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
        preyerX = preyer.x();
        preyerY = preyer.y();
        bfs();
        creature.moveBy(nextStep[0] - creature.x(), nextStep[1] - creature.y());
        System.out.println(1);
    }
}
