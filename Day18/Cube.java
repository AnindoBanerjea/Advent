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
    private final int[] coord;


    public Cube(int[] other) {
        assert other.length == dimensions;
        this.coord = Arrays.copyOf(other, dimensions);
    }
    public Cube(Cube other) {
        assert other.coord.length == dimensions;
        this.coord = Arrays.copyOf(other.coord, dimensions);
    }

    public Cube apply(Cube other) {
        assert coord.length == dimensions;
        Cube copy = new Cube(this);
        for (int i=0; i<dimensions; i++) {
            copy.coord[i] += other.coord[i];
        }
        return copy;
    }

    public int[] getCoord() {
        return coord;
    }

    public boolean inBox(Cube min, Cube max) {
        return (this.coord[0] >= min.coord[0] &&
            this.coord[1] >= min.coord[1] &&
            this.coord[2] >= min.coord[2] &&
            this.coord[0] <= max.coord[0] &&
            this.coord[1] <= max.coord[1] &&
            this.coord[2] <= max.coord[2]);
    }


    public boolean adjacent(Cube b) {
        // two cubes are adjacent if they are same on two dimensions and off by one on the third
        int match = 0;
        int offbyone = 0;
        for (int dim=0; dim < dimensions; dim++) {
            if (this.coord[dim] == b.coord[dim]) {
                match++;
            } else if (Math.abs(this.coord[dim]-b.coord[dim]) <= 1) {
                offbyone++;
            }
        }
        return (match == 2 && offbyone == 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cube cube = (Cube) o;

        return Arrays.equals(getCoord(), cube.getCoord());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getCoord());
    }

    @Override
    public String toString() {
        return "Cube" + Arrays.toString(coord);
    }
}
