package algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import objects.NodeData;
import objects.graph;
import objects.node_data;
import objects.Graph_DS;

public class Graph_Algo implements graph_algorithms
{
	private graph g;
	private HashMap<Integer, Integer> dist;
	private HashMap<Integer, node_data> parents;


	public Graph_Algo()
	{
		this.dist = new HashMap<Integer, Integer>();
		this.parents = new HashMap<Integer, node_data>();
	}


	@Override
	public void init(graph g)
	{
		this.g = g;
		this.dist = new HashMap<Integer, Integer>();
		this.parents = new HashMap<Integer, node_data>();

		for (node_data node : g.getV())
		{
			dist.put(node.getKey(), -1);
			parents.put(node.getKey(), null);
			node.setTag(NodeData.WHITE);
		}
	}

	
	private void copyToHashMap(HashMap<Integer, node_data> cloned_hm, ArrayList<node_data> g_nodes)
	{
		Queue<node_data> queue = new LinkedList<node_data>();
		node_data src = g_nodes.get(0);
		queue.add(src);
		cloned_hm.put(src.getKey(), new NodeData(src.getKey(), src.getTag(), 
													new HashMap<Integer, node_data>()));

		while (!queue.isEmpty())
		{
			node_data q_node = queue.remove();

			for (node_data ni : q_node.getNi()) 
			{
				node_data cloned_node = cloned_hm.get(ni.getKey()); 

				if (cloned_node == null)
				{ 
					cloned_node = new NodeData(ni.getKey(), ni.getTag(), 
												new HashMap<Integer, node_data>()); 
					cloned_hm.put(cloned_node.getKey(),cloned_node); 
					queue.add(ni); 
				}

				g_nodes.remove(ni);
				cloned_hm.get(q_node.getKey()).addNi(cloned_node); 
			} 

			g_nodes.remove(q_node);
		}
	}


	@Override
	public graph copy()
	{
		ArrayList<node_data> g_nodes = new ArrayList<node_data>(g.getV());
		HashMap<Integer, node_data> cloned_hm = new HashMap<Integer, node_data>();

		// for each connection component do BFS copy
		while (!g_nodes.isEmpty())
		{
			copyToHashMap(cloned_hm, g_nodes);
		}

		return (new Graph_DS(g.edgeSize(), g.getMC(), cloned_hm));
	}


	private void BFS(node_data src)
	{
		Queue<node_data> queue = new LinkedList<node_data>();

		src.setTag(NodeData.GRAY);
		dist.put(src.getKey(), 0);
		queue.add(src);

		while (!queue.isEmpty())
		{
			node_data q_node = queue.remove();
			ArrayList<node_data> q_node_ni = new ArrayList<node_data>(q_node.getNi());

			for (node_data ni : q_node_ni) 
			{
				if (ni.getTag() == NodeData.WHITE)
				{ 
					ni.setTag(NodeData.GRAY);
					dist.replace(ni.getKey(), (dist.get(q_node.getKey()) + 1));
					parents.replace(ni.getKey(), q_node);
					queue.add(ni); 					
				}
			}
			
			q_node.setTag(NodeData.BLACK); 
		}
	}


	@Override
	public boolean isConnected()
	{
		ArrayList<node_data> g_nodes = new ArrayList<node_data>(g.getV());
		
		init(g);

		if (!g_nodes.isEmpty())
		{
			BFS(g_nodes.get(0));			
		}

		return !dist.containsValue(-1);
	}


	@Override
	public int shortestPathDist(int src, int dest)
	{	
		node_data src_n = g.getNode(src);

		if(src_n != null)
		{
			init(g);
			BFS(src_n);
		}
		
		return dist.get(dest);
	}


	@Override
	public List<node_data> shortestPath(int src, int dest)
	{
		node_data src_n = g.getNode(src);
		node_data dest_n = g.getNode(dest);
		List<node_data> nodes_path = new ArrayList<node_data>();

		if (src_n == null || dest_n == null)
		{
			return null;
		}
		
		init(g);
		BFS(dest_n);

		while (src_n != dest_n)
		{
			nodes_path.add(src_n);
			src_n = parents.get(src_n.getKey());			
		}
		
		nodes_path.add(src_n);

		return nodes_path;
	}
}
