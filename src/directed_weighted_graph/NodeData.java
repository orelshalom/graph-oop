package directed_weighted_graph;


import com.google.gson.InstanceCreator;

import java.lang.reflect.Type;
import java.util.Objects;

public class NodeData implements node_data {

    private static int keys;
    private int id;
    private geo_location location;
    private String info;
    private int tag;
    private double weight;

    public NodeData()
    {
        this.id = keys++;
        this.location = new GeoLocation();
        this.info = "id: " + id;
        this.tag = 0;
        this.weight = Double.POSITIVE_INFINITY;
    }

    public NodeData(node_data other)
    {
        this.id = other.getKey();
        this.location = other.getLocation();
        this.info = "id: " + id;
        this.tag = other.getTag();
        this.weight = other.getWeight();
    }

    @Override
    public int getKey() {
        return id;
    }

    @Override
    public geo_location getLocation() {
        return location;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        return info;
    }

    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    @Override
    public void setLocation(geo_location p) {
        this.location = p;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeData nodeData = (NodeData) o;
        return id == nodeData.id &&
                tag == nodeData.tag &&
                Double.compare(nodeData.weight, weight) == 0 &&
                location.equals(nodeData.location) &&
                info.equals(nodeData.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, location, info, tag, weight);
    }
}
