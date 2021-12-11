package router;

import creature.Creature;
import creature.Player;
import world.Tile;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class PinkyChaseRouter implements Router {
    private Creature prey = Player.getPlayer();
    private int preyX;
    private int preyY;
    private int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
    public int nextPos[] = new int[2];
    private boolean visited[][];
    private Creature creature;


    public PinkyChaseRouter(Creature creature){
        this.creature = creature;
    }

    private void bfs(){
        Queue<int[]> queue = new LinkedList<int[]>();
        visited = new boolean[creature.getWorld().getWidth()][creature.getWorld().getWidth()];
        for (int i = 0; i < creature.getWorld().getWidth(); i++){
            Arrays.fill(visited[i],false);
        }
        //System.out.println(preyX + " " + preyY);
        queue.offer(new int[]{preyX, preyY});
        visited[preyX][preyY] = true;
        while(!queue.isEmpty()){
            int[] tuple = queue.poll();
            for(int i = 0; i < 4; i++){
                int x = tuple[0] + directions[i][0];
                int y = tuple[1] + directions[i][1];
                Tile t = creature.getWorld().tile(x,y);
                if(x == creature.x() && y == creature.y()){
                    nextPos[0] = tuple[0];
                    nextPos[1] = tuple[1];
                    return;
                }
                else if(x < 48 && x > 0 && y < 48 && y > 0 && (t == Tile.PATH || t == Tile.LAVA)){
                    if(visited[x][y] == false){
                        queue.add(new int[]{x,y});
                        visited[x][y] = true;
                    }
                }
            }
        }
    }

    public int[] nextSteps(){
        preyX = prey.x();
        preyY = prey.y();
        if(Player.getPlayer().getDirection() == 0){
            preyX = preyX - 4 > 1 ? preyX - 4: preyX;
        }
        if(Player.getPlayer().getDirection() == 1){
            preyX = preyX + 4 < 48 ? preyX + 4 : preyX;
        }
        if(Player.getPlayer().getDirection() == 2){
            preyY = preyY - 4 > 1 ? preyY - 4: preyY;
        }
        else{
            preyY = preyY + 4 < 48 ? preyY + 4 : preyY;
        }
        bfs();
        return new int[]{nextPos[0] - creature.x(),nextPos[1] - creature.y()};
    }
}
