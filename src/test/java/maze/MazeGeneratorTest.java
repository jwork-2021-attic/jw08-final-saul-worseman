package maze;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MazeGeneratorTest {


    @Test
    void getRawMaze() {
        MazeGenerator mazeGenerator = new MazeGenerator(11);
        List<int[]> list = mazeGenerator.getRawMaze();
        assertEquals(0,list.get(9)[9]);
    }
}