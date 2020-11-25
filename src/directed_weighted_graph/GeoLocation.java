package directed_weighted_graph;

import java.util.Objects;

public class GeoLocation implements geo_location {

    private double x;
    private double y;
    private double z;

    public GeoLocation()
    {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    @Override
    public double x() {
        return x;
    }

    @Override
    public double y() {
        return y;
    }

    @Override
    public double z() {
        return z;
    }

    /* d = ((x2 - x1)^2 + (y2 - y1)^2 + (z2 - z1)^2)^1/2 */
    @Override
    public double distance(geo_location g) {
        double x_dist = Math.pow(x-g.x(), 2);
        double y_dist = Math.pow(y-g.y(), 2);
        double z_dist = Math.pow(z-g.z(), 2);

        return Math.pow((x_dist + y_dist + z_dist), 0.5);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoLocation that = (GeoLocation) o;
        return Double.compare(that.x, x) == 0 &&
                Double.compare(that.y, y) == 0 &&
                Double.compare(that.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
