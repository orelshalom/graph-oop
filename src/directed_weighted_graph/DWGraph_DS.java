package directed_weighted_graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class DWGraph_DS implements directed_weighted_graph {

    private HashMap<Integer, node_data> nodes;
    private HashMap<Integer, HashMap<Integer, edge_data>> edges; // <src, <dest, edge>>
    private int modeCount, edgeCount;

    public DWGraph_DS()
    {
        this.nodes = new HashMap<>();
        this.edges = new HashMap<>();
        this.modeCount = 0;
        this.edgeCount = 0;
    }


    @Override
    public node_data getNode(int key) {
        return nodes.get(key);
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        node_data n = getNode(src);

        return (n != null) ? edges.get(src).get(dest) : null;
    }

    @Override
    public void connect(int src, int dest, double w) {

        if (getNode(src) != null && getNode(dest) != null && src != dest && w >= 0)
        {
            if (!edges.get(src).containsKey(dest))
            {
                this.edgeCount++;
            }

            edges.get(src).put(dest, new EdgeData(src, dest, w));
            this.modeCount++;
        }
    }

    @Override
    public Collection<node_data> getV() {
        return nodes.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        if (!nodes.containsKey(node_id)) return null;

        return edges.get(node_id).values();
    }

    @Override
    public node_data removeNode(int key) {
        // TODO: remove n from its in-degree nodes
        node_data n = nodes.get(key);

        if(n != null)
        {
            ArrayList<Integer> neighbors = new ArrayList<>(edges.get(key).keySet());

            edges.remove(key);
            this.edgeCount -= neighbors.size();

            for (Integer ni_key : neighbors)
            {
                edges.get(ni_key).remove(key);
                this.edgeCount--;
            }

            this.modeCount++;
        }

        return nodes.remove(key);
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        if (getNode(src) != null)
        {
            edge_data edge = edges.get(src).remove(dest);

            if (edge != null)
            {
                this.edgeCount--;
                this.modeCount++;
            }
        }

        return null;
    }

    @Override
    public int nodeSize() {
        return nodes.size();
    }

    @Override
    public int edgeSize() {
        return edgeCount;
    }

    @Override
    public int getMC() {
        return modeCount;
    }

    @Override
    public void addNode(node_data n) {
        if (!nodes.containsKey(n.getKey())) {
            nodes.put(n.getKey(), n);
            edges.put(n.getKey(), new HashMap<>());
            this.modeCount++;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DWGraph_DS that = (DWGraph_DS) o;
        return edgeCount == that.edgeCount &&
                nodes.equals(that.nodes) &&
                edges.equals(that.edges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodes, edges, modeCount, edgeCount);
    }
}
