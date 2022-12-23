public class Cube extends NCube {

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

    public Cube(Cube other) {
        super(other);
    }

    public Cube apply(Cube other) {
        return (Cube) super.apply(other);
    }

    public Cube min(Cube other) {
        return (Cube) super.min(other);
    }
    public Cube max(Cube other) {
        return (Cube) super.max(other);
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