import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Vikno on 2/19/2016.
 */
public class GraphTest {

    /*
    Ініціалізація ребер
     */
    Graph.Edge[] GRAPH = {
                new Graph.Edge("Львів", "Луцьк", 5),
                new Graph.Edge("Львів", "Рівне", 2),
                new Graph.Edge("Рівне", "Луцьк", 2)};

    Graph graph;


    @Before
    public void setUp() throws Exception {
        /*
        Ініціалізація графу
         */
        graph = new Graph(GRAPH);
        graph.dijkstra("Львів");
        graph.printPath("Луцьк");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Test complated succseful!");
    }

    @Test
    public void testPrintPath() throws Exception {
        /*
        Тестування коректності шляху
         */
        assertEquals("Львів Рівне Луцьк ",graph.resPath);
    }

    @Test
    public void testPathLength() throws Exception {
        /*
        Перевірка отримоної відстані
         */
        assertEquals(4,graph.distance);

    }
}