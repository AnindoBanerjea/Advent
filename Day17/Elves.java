import java.io.IOException;

public class Elves {
    public static void main(String[] args) throws IOException {
        if (args.length == 0 || !args[0].matches("^\\d+$")) {
            args = new String[]{"1", "2"};
        }
        String filename = "input.txt";

        for (String arg : args) {
            int part = Integer.parseInt(arg);
            System.out.printf("Solving part %d with input %s\n", part, filename);
            Chamber chamber = new Chamber(filename);
            long iterations = (part == 1) ? 2022 : 1000000000000L;
            chamber.simulate(iterations);
            System.out.println(chamber.getChamberHeight());
        }
    }
}
