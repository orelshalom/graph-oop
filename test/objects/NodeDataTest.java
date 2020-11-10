package objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeDataTest {

    @Test
    void getKey() {
        node_data n1 = new NodeData();
        node_data n2 = new NodeData();

        assertNotEquals(n1.getKey(), n2.getKey());
    }

    @Test
    void hasNi() {
        node_data n1 = new NodeData();
        node_data n2 = new NodeData();

        n1.addNi(n2);
        n2.addNi(n1);

        assertTrue(n1.hasNi(n2.getKey()) && n2.hasNi(n1.getKey()));

        n1.removeNode(n2);
        n2.removeNode(n1);
        assertFalse(n1.hasNi(n2.getKey()));
        assertFalse(n2.hasNi(n1.getKey()));
    }

    @Test
    void addNi() {
        node_data n1 = new NodeData();
        node_data n2 = new NodeData();

        n1.addNi(n2);
        n2.addNi(n1);

        assertTrue(n1.getNi().contains(n2));
        assertTrue(n2.getNi().contains(n1));
    }

    @Test
    void removeNode() {
        node_data n1 = new NodeData();
        node_data n2 = new NodeData();

        n1.addNi(n2);
        n2.addNi(n1);

        n1.removeNode(n2);
        assertFalse(n1.hasNi(n2.getKey()));

        n1.removeNode(n2);
        assertFalse(n1.hasNi(n2.getKey()));
    }
}