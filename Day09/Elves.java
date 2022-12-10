import java.io.IOException;


public class Elves {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            args = new String[] { "2", "10" };
        }
        for (String arg : args) {
            int length = Integer.parseInt(arg);
            Moves f = new Moves("input.txt", length);
            System.out.printf("Tail of snake with length %d visits %d places%n", length, f.getVisited().size());
        }
    }
}
