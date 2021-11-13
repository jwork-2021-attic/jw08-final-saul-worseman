package maze;
public class Main {
    // run it
    public static void main(String[] args) {
        MazeGenerator maze = new MazeGenerator(9);
        maze.generate();
        maze.draw();
    }
}