import java.io.IOException;

public class Elves {

    public static void main(String[] args) throws IOException {
        if (args.length == 0 || !args[0].matches("^\\d+$")) {
            args = new String[]{"1"};
        }

        String filename = "input.txt";
        boolean verbose = true;

        for (String arg : args) {
            int part = Integer.parseInt(arg);
            System.out.printf("Solving part %d with input %s\n", part, filename);
            Cubes cubes = new Cubes(filename, part, verbose);
            if (part == 1) {
                System.out.printf("Count of sides = %d\n", cubes.countSides());
            } else {
                System.out.print("Not yet implemented.\n");
            }
        }
    }
}
