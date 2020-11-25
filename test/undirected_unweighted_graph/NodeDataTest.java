package undirected_unweighted_graph;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeDataTest {

    @Test
    void getNi() {
        node_data n1 = new NodeData();
        node_data n2 = new NodeData();
        node_data n3 = new NodeData();

        n1.addNi(n2);
        n1.addNi(n3);
        n2.addNi(n1);
        n3.addNi(n1);

        assertEquals(2, n1.getNi().size());
        assertEquals(1, n2.getNi().size());
        n1.removeNode(n3);
        n3.removeNode(n1);
        assertEquals(0, n3.getNi().size());
    }

    @Test
    void hasNi() {
        node_data n1 = new NodeData();
        node_data n2 = new NodeData();

        assertFalse(n1.hasNi(n2.getKey()));
        n1.addNi(n2);
        assertTrue(n1.hasNi(n2.getKey()));
    }

    @Test
    void addNi() {
        node_data n1 = new NodeData();
        node_data n2 = new NodeData();

        n1.addNi(n2);
        n2.addNi(n1);

        assertTrue(n1.hasNi(n2.getKey()));
    }

    @Test
    void removeNode() {
        node_data n1 = new NodeData();
        node_data n2 = new NodeData();

        n1.addNi(n2);
        n2.addNi(n1);
        n1.removeNode(n2);
        assertFalse(n1.hasNi(n2.getKey()));
    }
}