package algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import objects.WGraph_DS;
import objects.node_info;
import objects.weighted_graph;


public class WGraph_Algo implements weighted_graph_algorithms
{
	private weighted_graph wg;
	private HashMap<Integer, Double> dist;
	private HashMap<Integer, node_info> parents;
	
	
	public WGraph_Algo() 
	{
		this.dist = new HashMap<Integer, Double>();
		this.parents = new HashMap<Integer, node_info>();
	}
	
	
	@Override
	public void init(weighted_graph g) 
	{
		this.wg = g;
		this.dist = new HashMap<Integer, Double>();
		this.parents = new HashMap<Integer, node_info>();

		for (node_info node : g.getV())
		{
			dist.put(node.getKey(), Double.POSITIVE_INFINITY);
			parents.put(node.getKey(), null);
			node.setTag(0);
		}
	}

	
	@Override
	public weighted_graph getGraph() 
	{
		return wg;
	}

	
	private void copyToHashMap(weighted_graph cloned_wg, ArrayList<node_info> wg_nodes)
	{
		Queue<node_info> queue = new LinkedList<node_info>();
		
		node_info src = wg_nodes.get(0);
		queue.add(src);
		cloned_wg.addNode(src.getKey());//

		while (!queue.isEmpty())
		{
			node_info q_node = queue.remove();
			ArrayList<node_info> neighbors = new ArrayList<node_info>(wg.getV(q_node.getKey()));
			
			for (node_info ni : neighbors) 
			{
				if (cloned_wg.getNode(ni.getKey()) == null)
				{ 
					cloned_wg.addNode(ni.getKey());
					queue.add(ni); 
				}

				wg_nodes.remove(ni);
				cloned_wg.connect(q_node.getKey(), ni.getKey(), 
									wg.getEdge(q_node.getKey(), ni.getKey())); 
			} 

			wg_nodes.remove(q_node);
		}
	}
	
	
	@Override
	public weighted_graph copy() 
	{
		ArrayList<node_info> wg_nodes = new ArrayList<node_info>(wg.getV());
		weighted_graph cloned_wg = new WGraph_DS(wg.edgeSize(), wg.getMC(), 
													new HashMap<Integer, node_info>());

		// for each connection component do dijkstra copy
		while (!wg_nodes.isEmpty())
		{
			copyToHashMap(cloned_wg, wg_nodes);
		}

		return cloned_wg;
	}

	
	private void dijkstra(node_info src) // O(|E|*log|V|)
	{
		PriorityQueue<node_info> pq = new PriorityQueue<node_info>();
		
		dist.put(src.getKey(), 0.0);
		pq.add(src);
		
		while (!pq.isEmpty()) // O(|E|)
		{
			node_info node = pq.poll(); // O(log |V|)
			ArrayList<node_info> neighbors = new ArrayList<node_info>(wg.getV(node.getKey()));
			
			for (node_info ni : neighbors)
			{
				if (ni.getTag() == 0 
					&& dist.get(ni.getKey()) > dist.get(node.getKey()) 
												+ wg.getEdge(node.getKey(), ni.getKey())) 
				{
					dist.put(ni.getKey(), dist.get(node.getKey()));
					parents.put(ni.getKey(), node);
					pq.remove(ni);
					pq.add(ni);
				}
			}
			
			node.setTag(1);
		}
	}
	
	
	@Override
	public boolean isConnected() 
	{
		ArrayList<node_info> g_nodes = new ArrayList<node_info>(wg.getV());
		
		if (!g_nodes.isEmpty())
		{
			init(wg);
			dijkstra(g_nodes.get(0));			
		}

		return !dist.containsValue(Double.POSITIVE_INFINITY);
	}

	
	@Override
	public double shortestPathDist(int src, int dest) 
	{
		node_info src_n = wg.getNode(src);
		node_info dest_n = wg.getNode(dest);
		
		if(src_n != null)
		{
			init(wg);
			dijkstra(src_n);

			return (dest_n == null || dist.get(dest) == Double.POSITIVE_INFINITY) ? (-1) : (dist.get(dest));
		}
		
		return -1;
	}

	
	@Override
	public List<node_info> shortestPath(int src, int dest) 
	{
		node_info src_n = wg.getNode(src);
		node_info dest_n = wg.getNode(dest);
		List<node_info> nodes_path = new ArrayList<node_info>();

		if (src_n == null || dest_n == null)
		{
			return null;
		}
		
		init(wg);
		dijkstra(dest_n);

		while (src_n != dest_n)
		{
			nodes_path.add(src_n);
			src_n = parents.get(src_n.getKey());			
		}
		
		nodes_path.add(src_n);

		return nodes_path;
	}

	
	@Override
	public boolean save(String file) 
	{
		return false;
	}

	@Override
	public boolean load(String file) 
	{
		return false;
	}

}
