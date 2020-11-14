package objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

public class WGraph_DS implements weighted_graph, Serializable
{
	private int edges;
	private int mode_count;
	private HashMap<Integer, node_info> nodes;

	private class NodeInfo implements node_info, Comparable<NodeInfo>, Serializable
	{
		private final int id;
		private double tag;
		private String info;
		private HashMap<Integer, Edge> ni_edges;

		public NodeInfo(int id) 
		{
			this.id = id;
			this.tag = Double.POSITIVE_INFINITY;
			this.info = "id: " + id;
			this.ni_edges = new HashMap<>();
		}

		@Override
		public int getKey()
		{
			return id;
		}

		@Override
		public String getInfo()
		{
			return info;
		}

		@Override
		public void setInfo(String s)
		{
			info = s;
		}

		@Override
		public double getTag() 
		{
			return tag;
		}

		@Override
		public void setTag(double t) 
		{
			tag = t;
		}

		public HashMap<Integer, Edge> getNi_edges() { return ni_edges; }

		@Override
		public int compareTo(NodeInfo ni)
		{
			return (int)(this.tag - ni.getTag());
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			NodeInfo other = (NodeInfo) o;
			return id == other.id &&
					Double.compare(other.tag, tag) == 0 &&
					info.equals(other.info) &&
					ni_edges.equals(other.ni_edges);
		}

		@Override
		public int hashCode() {
			return Objects.hash(id, tag, info, ni_edges);
		}
	}

	
	public WGraph_DS()
	{
		this.edges = 0;
		this.mode_count = 0;
		this.nodes = new HashMap<>();
	}

	@Override
	public node_info getNode(int key)
	{
		return nodes.get(key);
	}

	@Override
	public boolean hasEdge(int node1, int node2)
	{
		NodeInfo n1 = (NodeInfo) nodes.get(node1);
		NodeInfo n2 = (NodeInfo) nodes.get(node2);

		return (n1 != null && n2 != null 
				&& n1.ni_edges.containsKey(node2) 
				&& n2.ni_edges.containsKey(node1));
	}

	@Override
	public double getEdge(int node1, int node2) 
	{
		if (hasEdge(node1, node2))
		{
			NodeInfo n1 = (NodeInfo) nodes.get(node1);

			return n1.ni_edges.get(node2).getWeight();
		}

		return -1;
	}

	@Override
	public void addNode(int key) 
	{
		if (!nodes.containsKey(key))
		{
			nodes.put(key, new NodeInfo(key));	
			this.mode_count++;			
		}
	}

	@Override
	public void connect(int node1, int node2, double w) 
	{
		NodeInfo n1 = (NodeInfo) nodes.get(node1);
		NodeInfo n2 = (NodeInfo) nodes.get(node2);

		if (n1 != null && n2 != null && n1 != n2)
		{
			if (!hasEdge(node1, node2))
			{				
				this.edges++;
			}

			n1.ni_edges.put(node2, new Edge(w, n2));
			n2.ni_edges.put(node1, new Edge(w, n1));
			this.mode_count++;	
		}
	}

	@Override
	public Collection<node_info> getV()
	{
		return nodes.values();
	}

	@Override
	public Collection<node_info> getV(int node_id)
	{		
		NodeInfo n = (NodeInfo) nodes.get(node_id);
		ArrayList<node_info> arr = new ArrayList<>();

		for (Edge e : n.ni_edges.values())
		{
			arr.add(e.getDest());
		}

		return arr;
	}

	@Override
	public node_info removeNode(int key)
	{
		NodeInfo n = (NodeInfo) nodes.get(key);

		if(n != null)
		{
			ArrayList<Edge> n_edges = new ArrayList<>(n.ni_edges.values());

			for (Edge e : n_edges)
			{
				NodeInfo ni = (NodeInfo) e.getDest();
				n.ni_edges.remove(ni.getKey());
				ni.ni_edges.remove(key);
				this.edges--;
			}

			this.mode_count++;			
		}

		return nodes.remove(key);
	}

	@Override
	public void removeEdge(int node1, int node2)
	{
		NodeInfo n1 = (NodeInfo) nodes.get(node1);
		NodeInfo n2 = (NodeInfo) nodes.get(node2);

		if (hasEdge(node1, node2))
		{
			n1.ni_edges.remove(node2);
			n2.ni_edges.remove(node1);

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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		WGraph_DS other = (WGraph_DS) o;
		return edges == other.edges &&
				mode_count == other.mode_count &&
				nodes.equals(other.nodes);

	}

	@Override
	public int hashCode() {
		return Objects.hash(edges, mode_count, nodes);
	}

	@Override
	public String toString() {
		String str = "WGraph_DS {" +
					"\n|V|=" + nodeSize() +
					"\n|E|=" + edges +
					"\nmode_count=" + mode_count + "\n";

		for(node_info n : nodes.values())
		{
			str += "node " + n.getKey() + ": [";
			ArrayList<Edge> arr = new ArrayList<>(((NodeInfo) n).ni_edges.values());

			for(Edge e : arr)
			{
				str += e.toString() + ", ";
			}

			str += "]\n";
		}

		return str + '}';
	}
}
