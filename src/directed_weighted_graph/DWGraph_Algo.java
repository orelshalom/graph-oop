package directed_weighted_graph;

import com.google.gson.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class DWGraph_Algo implements dw_graph_algorithms {

    private directed_weighted_graph dwg;
    private HashMap<Integer, node_data> parents;

    public DWGraph_Algo() {
        this.dwg = new DWGraph_DS();
        this.parents = new HashMap<>();
    }

    @Override
    public void init(directed_weighted_graph g) {
        this.dwg = g;
        this.parents = new HashMap<>();

        for (node_data n : g.getV())
        {
            parents.put(n.getKey(), null);
            n.setWeight(Double.POSITIVE_INFINITY);
            n.setTag(0);
        }
    }

    @Override
    public directed_weighted_graph getGraph() {
        return dwg;
    }


    private void copyToGraph(directed_weighted_graph cloned_wg, ArrayList<node_data> wg_nodes) {
        Queue<node_data> queue = new LinkedList<>();

        node_data src = wg_nodes.get(0);
        cloned_wg.addNode(new NodeData(src));
        queue.add(src);

        while (!queue.isEmpty())
        {
            node_data q_node = queue.remove();
            ArrayList<edge_data> neighbors = new ArrayList<>(dwg.getE(q_node.getKey()));

            for (edge_data e : neighbors)
            {
                node_data ni = dwg.getNode(e.getDest());

                if (cloned_wg.getNode(ni.getKey()) == null)
                {
                    cloned_wg.addNode(new NodeData(ni));
                    queue.add(ni);
                }

                wg_nodes.remove(ni);

                // if there is not an edge between them - connect
                if (!cloned_wg.getE(q_node.getKey()).contains(e)) {
                    cloned_wg.connect(q_node.getKey(), ni.getKey(),
                            dwg.getEdge(q_node.getKey(), ni.getKey()).getWeight());
                }
            }

            wg_nodes.remove(q_node);
        }
    }

    @Override
    public directed_weighted_graph copy() {
        ArrayList<node_data> wg_nodes = new ArrayList<>(dwg.getV());
        directed_weighted_graph cloned_wg = new DWGraph_DS();

        // for each connection component do dijkstra copy
        while (!wg_nodes.isEmpty())
        {
            copyToGraph(cloned_wg, wg_nodes);
        }

        return cloned_wg;
    }


    private void BFS(directed_weighted_graph g, node_data src) {
        Queue<node_data> q = new LinkedList<>();

        q.add(src);
        src.setTag(1);

        while (!q.isEmpty())
        {
            node_data node = q.poll();
            ArrayList<edge_data> neighbors = new ArrayList<>(g.getE(node.getKey()));

            for (edge_data e : neighbors)
            {
                node_data ni = g.getNode(e.getDest());

                if (ni.getTag() == 0)
                {
                    ni.setTag(1);
                    q.add(ni);
                }
            }

            node.setTag(2);
        }
    }


    private directed_weighted_graph CreateReversedGraph() {
        directed_weighted_graph rev_g = new DWGraph_DS();

        for (node_data n : dwg.getV()) {
            rev_g.addNode(n);

            for (edge_data e : dwg.getE(n.getKey())) {
                rev_g.addNode(dwg.getNode(e.getDest()));
                rev_g.connect(e.getDest(), n.getKey(),
                        dwg.getEdge(n.getKey(), e.getDest()).getWeight());
            }
        }

        return rev_g;
    }

    @Override
    public boolean isConnected() {
        ArrayList<node_data> nodes = new ArrayList<>(dwg.getV());
        if (nodes.isEmpty()) return true;

        node_data src = nodes.get(0);
        init(dwg);
        BFS(dwg, src);

        for (node_data n : dwg.getV())
        {
            if (n.getTag() == 0) return false;
        }

        init(dwg);
        directed_weighted_graph rev_g = CreateReversedGraph();
        BFS(rev_g, src);

        for (node_data n : rev_g.getV())
        {
            if (n.getTag() == 0) return false;
        }

        return true;
    }


    private void dijkstra(node_data src) {
        PriorityQueue<node_data> pq = new PriorityQueue<>(
                (o1, o2) -> (int) (o1.getWeight() - o2.getWeight()));
        pq.add(src);
        src.setWeight(0);

        while (!pq.isEmpty())
        {
            node_data node = pq.poll();
            ArrayList<edge_data> neighbors = new ArrayList<>(dwg.getE(node.getKey()));

            for (edge_data e : neighbors)
            {
                node_data ni = dwg.getNode(e.getDest());
                double node_dist = node.getWeight() + dwg.getEdge(node.getKey(), ni.getKey()).getWeight();

                if (ni.getTag() == 0 && ni.getWeight() > node_dist)
                {
                    ni.setWeight(node_dist);
                    parents.put(ni.getKey(), node);
                    pq.remove(ni);
                    pq.add(ni);
                }
            }

            node.setTag(1);
        }
    }

    @Override
    public double shortestPathDist(int src, int dest) {
        node_data src_n = dwg.getNode(src);
        node_data dest_n = dwg.getNode(dest);

        if(src_n != null && dest_n != null)
        {
            init(dwg);
            dijkstra(src_n);

            double destWeight = dest_n.getWeight();
            if (destWeight != Double.POSITIVE_INFINITY) { return destWeight; }
        }

        return -1;
    }

    @Override
    public List<node_data> shortestPath(int src, int dest) {
        node_data src_n = dwg.getNode(src);
        node_data dest_n = dwg.getNode(dest);

        if (src_n == null || dest_n == null) return null;

        init(dwg);
        dijkstra(src_n);

        List<node_data> nodes_path = new ArrayList<>();

        while (dest_n != src_n && dest_n != null)
        {
            nodes_path.add(0, dest_n);
            dest_n = parents.get(dest_n.getKey());
        }

        nodes_path.add(0, dest_n);

        return (dest_n == null) ? null : nodes_path;
    }

    @Override
    public boolean save(String file) {
        FileWriter fw = null;
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(node_data.class, new InterfaceAdapter())
                .registerTypeAdapter(geo_location.class, new InterfaceAdapter())
                .registerTypeAdapter(edge_data.class, new InterfaceAdapter())
                .serializeSpecialFloatingPointValues().create();

        try {
            fw = new FileWriter(file);
            gson.toJson(dwg, fw);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean load(String file) {
        FileReader fr = null;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(node_data.class, new InterfaceAdapter())
                .registerTypeAdapter(geo_location.class, new InterfaceAdapter())
                .registerTypeAdapter(edge_data.class, new InterfaceAdapter()).create();

        try {
            fr = new FileReader(file);
            this.dwg = gson.fromJson(fr, DWGraph_DS.class);

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }
}
