import java.io.IOException;

public class Elves {
    public static void main(String[] args) throws IOException {
        if (args.length == 0 || !args[0].matches("^\\d+$")) {
            args = new String[]{"2", "10"};
        }

        for (String arg : args) {           
            int length = Integer.parseInt(arg);
            System.out.println("Solving for snake length " + length + ".");
            Moves f = new Moves("input.txt", length);
            System.out.println(f.getVisited().size());
        }
    }
}
