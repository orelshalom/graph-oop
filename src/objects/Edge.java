package objects;

public class Edge 
{	
	private double weight;
	private node_info dest;

	public Edge(double w, node_info dest)
	{
		this.weight = w;
		this.dest = dest;
	}

	public double getWeight() 
	{
		return weight;
	}
	
	public void setWeight(double weight) 
	{
		this.weight = weight;
	}
	
	public node_info getDest() 
	{
		return dest;
	}
}
