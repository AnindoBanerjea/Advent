public class Cube extends NCube {

    // Specialized class that only handles 3d cubes. Inherits all methods from the generic
    // nd cube object, and then adds the 3d constructor, min_value, directions, xyz getters, etc.
    // all objects in Cubes should be of type Cube, to ensure dimensions are always 3.

    public static final Cube MIN_VALUE = new Cube(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    public static final Cube MAX_VALUE = new Cube(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    public static final Cube[] directions = {
            new Cube( 1, 0, 0),
            new Cube(-1, 0, 0),
            new Cube( 0, 1, 0),
            new Cube( 0,-1, 0),
            new Cube( 0, 0, 1),
            new Cube( 0, 0,-1)
    };


    public Cube(int x, int y, int z) {
        super(new int[] {x, y, z});
    }

    public Cube(NCube other) {
        super(other);
        assert other.getCoordinates().length == 3;
    }

    public int x() {
        return super.getCoordinates()[0];
    }
    public int y() {
        return super.getCoordinates()[1];
    }
    public int z() {
        return super.getCoordinates()[2];
    }

}
