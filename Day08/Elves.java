import java.io.IOException;
import java.util.Arrays;


public class Elves {
    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            args = new String[] { "1", "2" };
        }
        for (int part : Arrays.stream(args).mapToInt(Integer::parseInt).toArray()) {
            System.out.println("Executing part " + part + ".");
            Forest f = new Forest("input.txt");
            System.out.println(part == 1 ? f.countVisible() : f.topViewingScore());
        }
    }
}
