package maze;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    // run it
    public static void main(String[] args) throws IOException {
        MazeGenerator maze = new MazeGenerator(9);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(new FileOutputStream("src/main/resources/maze.json"),maze);
        maze.draw();
    }
}