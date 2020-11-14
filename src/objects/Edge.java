package objects;

import java.io.Serializable;
import java.util.Objects;

public class Edge implements Serializable
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Edge other = (Edge) o;
		return Double.compare(other.weight, weight) == 0 &&
				dest.getKey() == other.dest.getKey();
	}

	@Override
	public int hashCode() {
		return Objects.hash(weight, dest);
	}

	@Override
	public String toString() {
		return "Edge{" +
				"neighbor=" + dest.getKey() +
				", weight=" + weight +
				'}';
	}
}
