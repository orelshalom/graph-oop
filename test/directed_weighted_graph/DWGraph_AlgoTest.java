package directed_weighted_graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DWGraph_AlgoTest {

    private directed_weighted_graph g0 = new DWGraph_DS();
    private node_data n1 = new NodeData();
    private node_data n2 = new NodeData();
    private node_data n3 = new NodeData();
    private node_data n4 = new NodeData();

    @BeforeEach
    private void initG(){
        g0.addNode(n1);//0
        g0.addNode(n2);//1
        g0.addNode(n3);//2
        g0.addNode(n4);//3
    }

    @Test
    void copy() {
        dw_graph_algorithms ga = new DWGraph_Algo();
        initG();
        ga.init(g0);
        directed_weighted_graph g1 = ga.copy();

        g1.connect(0, 1, 5);
        g1.connect(0, 2, 1);
        g1.connect(0, 3, 10);
        g1.connect(1, 2, 0);

        ga.init(g1);
        directed_weighted_graph g2 = ga.copy();
        assertEquals(g1, g2);
    }

    @Test
    void isConnected() {
        dw_graph_algorithms ga = new DWGraph_Algo();
        initG();
        ga.init(g0);
        directed_weighted_graph g1 = ga.copy();

        g1.connect(0, 1, 12);
        g1.connect(0, 2, 12);
        g1.connect(0, 3, 12);
        System.out.println(g1.nodeSize());

        ga.init(g1);
        assertFalse(ga.isConnected());

        g1.connect(1, 2, 12);
        g1.connect(2, 3, 12);
        assertFalse(ga.isConnected());

        g1.removeEdge(0, 3);
        g1.connect(3, 0, 12);
        assertTrue(ga.isConnected());
    }

    @Test
    void shortestPathDist() {
        dw_graph_algorithms ga = new DWGraph_Algo();
        initG();
        ga.init(g0);
        directed_weighted_graph g1 = ga.copy();

        g1.connect(0, 1, 5);
        g1.connect(0, 2, 1);
        g1.connect(0, 3, 10);
        g1.connect(1, 2, 0);

        ga.init(g1);
        assertEquals(-1, ga.shortestPathDist(0, 4));
        assertEquals(-1, ga.shortestPathDist(1, 0));
        assertEquals(-1, ga.shortestPathDist(2, 3));

        g1.connect(2, 3, 3);
        assertEquals(4, ga.shortestPathDist(0, 3));
    }

    @Test
    void shortestPath() {
        dw_graph_algorithms ga = new DWGraph_Algo();
        initG();
        ga.init(g0);
        directed_weighted_graph g1 = ga.copy();

        g1.connect(0, 1, 5);
        g1.connect(0, 2, 1);
        g1.connect(0, 3, 10);
        g1.connect(1, 2, 0);

        ga.init(g1);
        assertNull(ga.shortestPath(0, 4));
        assertNull(ga.shortestPath(1, 0));
        assertNull(ga.shortestPath(2, 3));

        g1.connect(2, 3, 3);
        List<node_data> list = new ArrayList<>();
        list.add(g1.getNode(0));
        list.add(g1.getNode(2));
        list.add(g1.getNode(3));
        assertEquals(list, ga.shortestPath(0, 3));
    }

    @Test
    void saveAndLoad() {
        dw_graph_algorithms ga = new DWGraph_Algo();
        initG();
        ga.init(g0);
        directed_weighted_graph g1 = ga.copy();

        g1.connect(0, 1, 12);
        g1.connect(0, 2, 12);
        g1.connect(0, 3, 12);

        ga.init(g1);
        assertTrue(ga.save("dwg.json"));

        directed_weighted_graph g2 = new DWGraph_DS();

        g2.addNode(new NodeData());
        g2.addNode(new NodeData());
        g2.addNode(new NodeData());

        g2.connect(4, 5, 5);
        g2.connect(5, 6, 5);

        ga.init(g2);
        assertTrue(ga.load("dwg.json"));
        assertEquals(g1, ga.getGraph());
    }
}