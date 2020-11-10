package objects;

import java.util.Collection;
import java.util.HashMap;

public class NodeData implements node_data 
{	
	private static int keys;
	private final int id;
	private int color;
	private String info;
	private HashMap<Integer, node_data> neighbors;
	public static final int WHITE = 0, GRAY = 1, BLACK = 2;
    
	
	public NodeData()
	{
		this.id = keys++;
		this.color = WHITE;
		this.neighbors = new HashMap<>();
		this.info = "id: " + id + ", neighbors: " + neighbors.size(); 
	}

	
	public NodeData(int key, int tag, HashMap<Integer, node_data> hm)
	{
		this.id = key;
		this.color = tag;
		this.neighbors = hm;
	}


	@Override
	public int getKey() 
	{
		return id;
	}

	@Override
	public Collection<node_data> getNi() 
	{
		return neighbors.values();
	}

	@Override
	public boolean hasNi(int key) 
	{
		return neighbors.containsKey(key);
	}

	@Override
	public void addNi(node_data t) 
	{
		neighbors.put(t.getKey(), t);
	}

	@Override
	public void removeNode(node_data node) 
	{
		neighbors.remove(node.getKey());
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
	public int getTag() 
	{
		return this.color;
	}

	@Override
	public void setTag(int t) 
	{
		this.color = t;
	}
}
