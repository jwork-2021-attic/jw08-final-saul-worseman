package maze;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnionFindTest {

    private static UnionFind unionFind = new UnionFind(10);
    @BeforeAll
    public static void setUp(){
        unionFind.unite(1,2);
        unionFind.unite(1,3);
    }
    @Test
    void find() {
        assertEquals(2,unionFind.find(3));
        assertEquals(4,unionFind.find(4));

    }


    @Test
    void connected() {
        assert(unionFind.connected(1,2));
        assert(unionFind.connected(2,3));
        assert(!unionFind.connected(1,4));
    }

    @Test
    void clusterSize() {
        assertEquals(3,unionFind.clusterSize(1));
    }
}