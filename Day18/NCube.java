import java.util.Arrays;

// Generic n-dimensional cube class. Should be able to handle 2d cubes (squares) or 3d cubes or
// 4d, 5d, 6d cubes

public class NCube {

    private final int[] coordinates;

    public NCube(int[] coordinates) {
        this.coordinates = Arrays.copyOf(coordinates, coordinates.length);
    }
    public NCube(NCube other) {
        this(other.coordinates);
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public NCube apply(NCube other) {
        NCube copy = new NCube(this);
        for (int i=0; i<coordinates.length; i++) {
            copy.coordinates[i] += other.coordinates[i];
        }
        return copy;
    }

    public boolean inBox(NCube min, NCube max) {
        for (int dim=0; dim < coordinates.length; dim++) {
            if (    this.coordinates[dim] < min.coordinates[dim]    ||
                    this.coordinates[dim] > max.coordinates[dim]    ) {
                return false;
            }
        }
        return true;
    }


    public boolean adjacent(NCube b) {
        // two cubes are adjacent if they are same on two dimensions and off by one on the third
        int match = 0;
        int offByOne = 0;
        for (int dim=0; dim < coordinates.length; dim++) {
            if (this.coordinates[dim] == b.coordinates[dim]) {
                match++;
            } else if (Math.abs(this.coordinates[dim]-b.coordinates[dim]) <= 1) {
                offByOne++;
            }
        }
        return (match == 2 && offByOne == 1);
    }

    public NCube min (NCube other) {
        NCube copy = new NCube(this);
        for (int i=0; i<coordinates.length; i++) {
            copy.coordinates[i] = Math.min(copy.coordinates[i],other.coordinates[i]);
        }
        return copy;
    }
    public NCube max (NCube other) {
        NCube copy = new NCube(this);
        for (int i=0; i<coordinates.length; i++) {
            copy.coordinates[i] = Math.max(copy.coordinates[i],other.coordinates[i]);
        }
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NCube cube = (NCube) o;

        return Arrays.equals(coordinates, cube.coordinates);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(coordinates);
    }

    @Override
    public String toString() {
        return "" + coordinates.length + "Cube" + Arrays.toString(coordinates);
    }
}
