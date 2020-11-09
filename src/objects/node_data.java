package objects;

import java.util.Collection;

/**
 * This interface represents the set of operations applicable on a 
 * node (vertex) in an undirected unweighted graph.
 * @author boaz.benmoshe
 */
public interface node_data {
	
	/**
	 * Return the key (id) associated with this node.
	 * Each node_data should have a unique key.
	 * @return the unique id of this node.
	 */
	public int getKey();

    /**
	 * This method returns a collection with all the Neighbors of this node_data
	 */
	public Collection<node_data> getNi();

    /**
     * return true iff this<==>key are adjacent, as an edge between them.
	 * @param key
     * @return
     */
	public boolean hasNi(int key);
	
	/** This method adds the node_data (t) to this node_data.*/
	public void addNi(node_data t);

    /**
     * Removes the edge between this and node,
	 * @param node
	 */
	public void removeNode(node_data node);
	
	/**
	 * return the remark (meta data) associated with this node.
	 * @return
	 */
	public String getInfo();
	
	/**
	 * Allows changing the remark (meta data) associated with this node.
	 * @param s
	 */
	public void setInfo(String s);
	
	/**
	 * Temporal data (aka color: e,g, white, gray, black) 
	 * which can be used be algorithms 
	 * @return
	 */
	public int getTag();
	
	/** 
	 * Allow setting the "tag" value for temporal marking an node - common 
	 * practice for marking by algorithms.
	 * @param t - the new value of the tag
	 */
	public void setTag(int t);
}