package undirected_weighted_graph;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class WGraph_Algo implements weighted_graph_algorithms
{
	private weighted_graph wg;
	private HashMap<Integer, Integer> visited;
	private HashMap<Integer, node_info> parents;

	public WGraph_Algo() 
	{
		this.wg = new WGraph_DS();
		this.visited = new HashMap<>();
		this.parents = new HashMap<>();
	}
	
	
	@Override
	public void init(weighted_graph g) 
	{
		this.wg = g;
		this.visited = new HashMap<>();
		this.parents = new HashMap<>();

		for (node_info node : g.getV())
		{
			visited.put(node.getKey(), 0);
			parents.put(node.getKey(), null);
			node.setTag(Double.POSITIVE_INFINITY);
		}
	}

	
	@Override
	public weighted_graph getGraph() 
	{
		return wg;
	}


	private void addCopyOfNode(weighted_graph cloned_wg, node_info node)
	{
		cloned_wg.addNode(node.getKey());
		node_info newNode = cloned_wg.getNode(node.getKey());
		newNode.setInfo(node.getInfo());
		newNode.setTag(node.getTag());
	}

	private void copyToHashMap(weighted_graph cloned_wg, ArrayList<node_info> wg_nodes)
	{
		Queue<node_info> queue = new LinkedList<>();
		
		node_info src = wg_nodes.get(0);
		addCopyOfNode(cloned_wg, src);
		queue.add(src);

		while (!queue.isEmpty())
		{
			node_info q_node = queue.remove();
			ArrayList<node_info> neighbors = new ArrayList<>(wg.getV(q_node.getKey()));
			
			for (node_info ni : neighbors) 
			{
				if (cloned_wg.getNode(ni.getKey()) == null)
				{ 
					addCopyOfNode(cloned_wg, ni);
					queue.add(ni);
				}

				wg_nodes.remove(ni);

				if (!cloned_wg.hasEdge(q_node.getKey(), ni.getKey())) {
					cloned_wg.connect(q_node.getKey(), ni.getKey(),
							wg.getEdge(q_node.getKey(), ni.getKey()));
				}
			}

			wg_nodes.remove(q_node);
		}
	}
	
	
	@Override
	public weighted_graph copy() 
	{
		ArrayList<node_info> wg_nodes = new ArrayList<>(wg.getV());
		weighted_graph cloned_wg = new WGraph_DS();

		// for each connection component do dijkstra copy
		while (!wg_nodes.isEmpty())
		{
			copyToHashMap(cloned_wg, wg_nodes);
		}

		return cloned_wg;
	}

	
	private void dijkstra(node_info src) // O(|E|*log|V|)
	{
		PriorityQueue<node_info> pq = new PriorityQueue<>((o1, o2) -> (int) (o1.getTag() - o2.getTag()));

		pq.add(src);
		src.setTag(0);

		while (!pq.isEmpty()) // O(|E|)
		{
			node_info node = pq.poll(); // O(log |V|)
			ArrayList<node_info> neighbors = new ArrayList<>(wg.getV(node.getKey()));
			
			for (node_info ni : neighbors)
			{
				double node_dist = node.getTag() + wg.getEdge(node.getKey(), ni.getKey());

				if (visited.get(ni.getKey()) == 0 && ni.getTag() > node_dist)
				{
					ni.setTag(node_dist);
					parents.put(ni.getKey(), node);
					pq.remove(ni);
					pq.add(ni);
				}
			}
			
			visited.put(node.getKey(), 1);
		}
	}
	
	
	@Override
	public boolean isConnected() 
	{
		ArrayList<node_info> g_nodes = new ArrayList<>(wg.getV());
		
		if (!g_nodes.isEmpty())
		{
			init(wg);
			dijkstra(g_nodes.get(0));			
		}

		return !visited.containsValue(0);
	}

	
	@Override
	public double shortestPathDist(int src, int dest) 
	{
		node_info src_n = wg.getNode(src);
		node_info dest_n = wg.getNode(dest);

		if(src_n != null && dest_n != null)
		{
			init(wg);
			dijkstra(src_n);

			double d_tag = dest_n.getTag();
			if (d_tag != Double.POSITIVE_INFINITY) { return d_tag; }
		}

		return -1;
	}

	
	@Override
	public List<node_info> shortestPath(int src, int dest) 
	{
		node_info src_n = wg.getNode(src);
		node_info dest_n = wg.getNode(dest);

		if (src_n == null || dest_n == null)
		{
			return null;
		}

		init(wg);
		dijkstra(dest_n);

		List<node_info> nodes_path = new ArrayList<>();

		while (src_n != dest_n && src_n != null)
		{
			nodes_path.add(src_n);
			src_n = parents.get(src_n.getKey());			
		}
		
		nodes_path.add(src_n);

		return (src_n == null) ? null : nodes_path;
	}

	
	@Override
	public boolean save(String file_path) // file_name.ser
	{
		try {
			FileOutputStream fos = new FileOutputStream(file_path);
			ObjectOutputStream oos = new ObjectOutputStream(fos);

			oos.writeObject(wg);

			fos.close();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	@Override
	public boolean load(String file_path)
	{
		try {
			FileInputStream fis = new FileInputStream(file_path);
			ObjectInputStream ois = new ObjectInputStream(fis);

			this.wg = (weighted_graph) ois.readObject();

			fis.close();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			return false;
		}

		return true;
	}

}
