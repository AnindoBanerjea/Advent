import java.io.IOException;

public class Elves {
    public static void main(String[] args) throws IOException {
        if (args.length == 0 || !args[0].matches("^\\d+$")) {
            args = new String[]{"1", "2"};
        }
        String filename = "input.txt";
        int row;
        Position max;
        if (filename.equals("test.txt")) {
            row = 10;
            max = new Position(20, 20);
        } else {
            row = 2000000;
            max = new Position(4000000, 4000000);
        }

        boolean verbose = true;

        for (String arg : args) {
            int part = Integer.parseInt(arg);
            System.out.printf("Solving part %d with input %s\n", part, filename);
            Sensors sensors = new Sensors(filename, part, verbose);
            if (part == 1) {
                SparseRange coverage = sensors.coverage(row);
                if (verbose) System.out.println(row + " " + coverage);
                System.out.println(sensors.coverageSize(coverage));
            } else {
                Position hidden = sensors.findSensor(max);
                System.out.printf("Found sensor at (%d, %d). Tuning frequency=%d\n",
                        hidden.getX(),
                        hidden.getY(),
                        (long)(hidden.getX())*4000000 + hidden.getY());

            }
        }
    }
}
