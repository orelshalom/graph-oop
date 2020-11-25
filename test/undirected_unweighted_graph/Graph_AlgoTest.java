package undirected_unweighted_graph;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Graph_AlgoTest {

    @Test
    void copy() {
        Graph_Algo ga = new Graph_Algo();
        Graph_DS g0 = new Graph_DS();

        g0.addNode(new NodeData());
        g0.addNode(new NodeData());
        g0.addNode(new NodeData());
        g0.connect(0, 1);

        ga.init(g0);
        graph g1 = ga.copy();

        assertNotSame(g1.getNode(0), g0.getNode(0));
        assertNotSame(g1.getNode(2), g0.getNode(2));
        assertTrue(g1.hasEdge(0, 1));
    }

    @Test
    void isConnected() {
        Graph_Algo ga = new Graph_Algo();
        Graph_DS g0 = new Graph_DS();

        g0.addNode(new NodeData());
        g0.addNode(new NodeData());
        g0.addNode(new NodeData());
        g0.connect(0, 1);
        g0.connect(1, 2);

        ga.init(g0);
        assertTrue(ga.isConnected());
        g0.removeEdge(0, 1);
        assertFalse(ga.isConnected());
    }

    @Test
    void shortestPathDist() {
        Graph_Algo ga = new Graph_Algo();
        Graph_DS g0 = new Graph_DS();

        g0.addNode(new NodeData());
        g0.addNode(new NodeData());
        g0.addNode(new NodeData());
        g0.connect(0, 1);
        g0.connect(1, 2);

        ga.init(g0);
        assertEquals(2, ga.shortestPathDist(0,2));
        g0.connect(0, 2);
        assertEquals(1, ga.shortestPathDist(0,2));
    }

    @Test
    void shortestPath() {
        Graph_Algo ga = new Graph_Algo();
        Graph_DS g0 = new Graph_DS();

        g0.addNode(new NodeData());
        g0.addNode(new NodeData());
        g0.addNode(new NodeData());
        g0.addNode(new NodeData());
        g0.connect(0, 1);
        g0.connect(1, 2);
        g0.connect(2, 3);
        g0.connect(1, 3);

        ga.init(g0);

        ArrayList<node_data> path = new ArrayList<>();
        path.add(g0.getNode(1));
        assertEquals(path, ga.shortestPath(1, 1));

        path.remove(0);
        path.add(g0.getNode(0));
        path.add(g0.getNode(1));
        path.add(g0.getNode(3));

        assertEquals(path, ga.shortestPath(0, 3));
        assertNull(ga.shortestPath(0, 4));
    }
}