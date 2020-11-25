package directed_weighted_graph;

public class EdgeLocation implements edge_location {

    private edge_data edge;

    public EdgeLocation(edge_data e)
    {
        this.edge = e;
    }

    @Override
    public edge_data getEdge() {
        return edge;
    }

    @Override
    public double getRatio() {
        return 0;
    }
}
