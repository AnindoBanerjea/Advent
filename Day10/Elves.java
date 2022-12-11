import java.io.IOException;

public class Elves {
    public static void main(String[] args) throws IOException {
        if (args.length == 0 || !args[0].matches("^\\d+$")) {
            args = new String[]{"1", "2"};
        }

        for (String arg : args) {           
            int part = Integer.parseInt(arg);
            System.out.println("Solving part " + part + ".");
            Instructions ins = new Instructions("input.txt");
            if (part == 1) {
                System.out.println(ins.sumSignal());
            } else {
                ins.draw();
            }
        }
    }
}
