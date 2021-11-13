package maze;
import java.util.*;

public class MazeGenerator {
    int dim;
    int[][] grid;
    UnionFind union;
    int coreDim;
    List<int[]> walls = new LinkedList<>();
    int[][] dimensions = new int[][]{{-1,0},{1,0},{0,-1},{0,1}};
    public MazeGenerator(int dim){
        this.dim = dim;
        coreDim = (dim - 1) / 2;
        union = new UnionFind(dim * dim);
        grid = new int[dim][dim];
        for(int i = 0; i < dim; i++){
            Arrays.fill(grid[i], 1);
        }
        for(int i = 1; i < dim; i+=2){
            for(int j = 1; j < dim; j+=2){
                grid[i][j] = 0;
            }
        }
    }

    void generate(){
        walls.add(new int[]{1,2});
        walls.add(new int[]{2,1});
        while(union.clusterSize(dim + 1) != (dim - 1) / 2 * (dim - 1) / 2 && !walls.isEmpty()){
            int dice = (int)(Math.random() * walls.size());
            int[] wall = walls.get(dice);
            walls.remove(dice);
            int index = wall[0] * dim + wall[1];
            if(wall[0] % 2 == 1){
                if(!union.connected(index - 1, index + 1)){
                    grid[wall[0]][wall[1]] = 0;
                    union.unite(index - 1, index + 1);
                    for(int i = 0; i < 4; i++){
                        int x = wall[0] + dimensions[i][0];
                        int y = wall[1] + dimensions[i][1] + 1;
                        if(grid[x][y] == 0)
                            y -= 2;
                        if(x > 0 && y > 0 && x < dim - 1 && y < dim - 1 && grid[x][y] == 1){
                            walls.add(new int[]{x,y});
                        }
                    }
                }
            }
            else{
                if(!union.connected(index - dim, index + dim)){
                    grid[wall[0]][wall[1]] = 0;
                    union.unite(index - dim, index + dim);
                    for(int i = 0; i < 4; i++){
                        int x = wall[0] + dimensions[i][0] + 1;
                        int y = wall[1] + dimensions[i][1];
                        if(grid[x][y] == 0)
                            x -= 2;
                        if(x > 0 && y > 0 && x < dim - 1 && y < dim - 1 && grid[x][y] == 1){
                            walls.add(new int[]{x,y});
                        }
                    }
                }
            }

        }
    }
    public ArrayList<int[]> getRawMaze(){
        generate();
        ArrayList<int[]> maze = new ArrayList<>();
        for(int i = 0; i < grid.length; i++)
            maze.add(grid[i]);
        return maze;
    }
    public void draw(){

        for(int i = 0; i < dim; i++){
            for(int j = 0; j < dim; j++)
                System.out.print(grid[i][j] + " ");
            System.out.println();
        }
    }
}