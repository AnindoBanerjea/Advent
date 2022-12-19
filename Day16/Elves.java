import java.io.IOException;

public class Elves {
    public static void main(String[] args) throws IOException {
        if (args.length == 0 || !args[0].matches("^\\d+$")) {
            args = new String[]{"2"};
        }
        String filename = "test.txt";
        boolean verbose = true;
        int boom;

        for (String arg : args) {
            int part = Integer.parseInt(arg);
            boom = (part == 1) ? 30 : 26;
            System.out.printf("Solving part %d with input %s\n", part, filename);
            Valves valves = new Valves(filename, boom, part, verbose);
            valves.permuteAndVisit();
            System.out.println(valves.getMaxPressure());
        }
    }
}
