package undirected_unweighted_graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Graph_DS implements graph
{
	private int edges;
	private int mode_count;
	private HashMap<Integer, node_data> nodes;


	public Graph_DS() 
	{
		this.edges = 0;
		this.mode_count = 0;
		this.nodes = new HashMap<>();
	}


	public Graph_DS(int edges, int mode_count, HashMap<Integer, node_data> nodes)
	{
		this.edges = edges;
		this.mode_count = mode_count;
		this.nodes = nodes;
	}


	@Override
	public node_data getNode(int key)
	{
		return nodes.get(key);
	}

	@Override
	public boolean hasEdge(int node1, int node2) 
	{
		node_data n1 = nodes.get(node1);
		node_data n2 = nodes.get(node2);

		return (n1 != null && n2 != null 
				&& n1.hasNi(node2) && n2.hasNi(node1));
	}

	@Override
	public void addNode(node_data n) 
	{
		nodes.put(n.getKey(), n);
		this.mode_count++;
	}

	@Override
	public void connect(int node1, int node2) 
	{
		node_data n1 = nodes.get(node1);
		node_data n2 = nodes.get(node2);
		
		if (n1 != null && n2 != null 
			&& n1 != n2 && !n1.hasNi(node2))
		{
			n1.addNi(n2);
			n2.addNi(n1);

			this.edges++;
			this.mode_count++;	
		}
	}

	@Override
	public Collection<node_data> getV()	
	{
		return nodes.values();
	}

	@Override
	public Collection<node_data> getV(int node_id) 
	{
		return nodes.get(node_id).getNi();
	}

	@Override
	public node_data removeNode(int key) 
	{
		node_data n = nodes.get(key);

		if(n != null)
		{
			ArrayList<node_data> neighbors = new ArrayList<node_data>(n.getNi());

			for (node_data ni : neighbors)
			{
				n.removeNode(ni);
				ni.removeNode(n);
				this.edges--;
			}

			this.mode_count++;			
		}

		return nodes.remove(key);
	}

	@Override
	public void removeEdge(int node1, int node2) 
	{
		node_data n1 = nodes.get(node1);
		node_data n2 = nodes.get(node2);

		if (n1 != null && n2 != null 
			&& n1.hasNi(node2) && n2.hasNi(node1))
		{
			n1.removeNode(n2);
			n2.removeNode(n1);

			this.edges--;
			this.mode_count++;
		}
	}

	@Override
	public int nodeSize() 
	{
		return nodes.size();
	}

	@Override
	public int edgeSize() 
	{
		return this.edges;
	}

	@Override
	public int getMC() 
	{
		return this.mode_count;
	}
	
	@Override
	public String toString() 
	{
		String str = "edges: " + edges + "\nmode_count: " 
						+ mode_count + "\nnodes("+nodes.size()+"):\n";
		
		for(node_data n : nodes.values())
		{
			str += n.getKey() + " neighbors: ";
			
			for(node_data ni : n.getNi())
			{
				str += ni.getKey() + ", ";
			}
			
			str += "\n";
		}
		
		return str;
	}
}
