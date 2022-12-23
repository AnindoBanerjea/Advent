import java.util.Arrays;

public class Cube {

    public static final int dimensions = 3;
    public static final Cube[] directions = {
            new Cube(new int[]{ 1, 0, 0}),
            new Cube(new int[]{-1, 0, 0}),
            new Cube(new int[]{ 0, 1, 0}),
            new Cube(new int[]{ 0,-1, 0}),
            new Cube(new int[]{ 0, 0, 1}),
            new Cube(new int[]{ 0, 0,-1})
    };
    private final int[] coordinates;


    public Cube(int[] other) {
        assert other.length == dimensions;
        this.coordinates = Arrays.copyOf(other, dimensions);
    }
    public Cube(Cube other) {
        assert other.coordinates.length == dimensions;
        this.coordinates = Arrays.copyOf(other.coordinates, dimensions);
    }

    public Cube apply(Cube other) {
        assert other.coordinates.length == dimensions;
        Cube copy = new Cube(this);
        for (int i=0; i<dimensions; i++) {
            copy.coordinates[i] += other.coordinates[i];
        }
        return copy;
    }

    public int[] getCoordinates() {
        return coordinates;
    }

    public boolean inBox(Cube min, Cube max) {
        return (this.coordinates[0] >= min.coordinates[0] &&
            this.coordinates[1] >= min.coordinates[1] &&
            this.coordinates[2] >= min.coordinates[2] &&
            this.coordinates[0] <= max.coordinates[0] &&
            this.coordinates[1] <= max.coordinates[1] &&
            this.coordinates[2] <= max.coordinates[2]);
    }


    public boolean adjacent(Cube b) {
        // two cubes are adjacent if they are same on two dimensions and off by one on the third
        int match = 0;
        int offByOne = 0;
        for (int dim=0; dim < dimensions; dim++) {
            if (this.coordinates[dim] == b.coordinates[dim]) {
                match++;
            } else if (Math.abs(this.coordinates[dim]-b.coordinates[dim]) <= 1) {
                offByOne++;
            }
        }
        return (match == 2 && offByOne == 1);
    }

    public Cube min (Cube other) {
        assert other.coordinates.length == dimensions;
        Cube copy = new Cube(this);
        for (int i=0; i<dimensions; i++) {
            copy.coordinates[i] = Math.min(copy.coordinates[i],other.coordinates[i]);
        }
        return copy;
    }
    public Cube max (Cube other) {
        assert other.coordinates.length == dimensions;
        Cube copy = new Cube(this);
        for (int i=0; i<dimensions; i++) {
            copy.coordinates[i] = Math.max(copy.coordinates[i],other.coordinates[i]);
        }
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cube cube = (Cube) o;

        return Arrays.equals(getCoordinates(), cube.getCoordinates());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getCoordinates());
    }

    @Override
    public String toString() {
        return "Cube" + Arrays.toString(coordinates);
    }
}
