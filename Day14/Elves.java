import java.io.IOException;

public class Elves {
    public static void main(String[] args) throws IOException {
        if (args.length == 0 || !args[0].matches("^\\d+$")) {
            args = new String[]{"1", "2"};
        }
        String filename = "input.txt";
        boolean verbose = false;

        for (String arg : args) {
            int part = Integer.parseInt(arg);
            System.out.printf("Solving part %d with input %s\n", part, filename);
            Cave cave = new Cave(filename, part);
            System.out.println(cave);
            while (cave.drop()) {
                if (verbose) cave.plot();
            }
            cave.plot();
            System.out.println(cave.getCount());
        }
    }
}
