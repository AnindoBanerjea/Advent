import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            Signal signal = new Signal(filename, part);
            if (verbose) System.out.println(signal);
            if (part == 1) {
                System.out.println(signal.sumIndicesRightOrder(verbose));
            }  else {
                List<Packet> dividers = new ArrayList<>();
                Packet d1 = new Packet("[[2]]");
                dividers.add(d1);
                Packet d2 = new Packet("[[6]]");
                dividers.add(d2);
                signal.addDividerPackets(dividers);
                signal.sort();
                if (verbose) System.out.println(signal);
                System.out.println(signal.decoderKey(dividers, verbose));
            }
        }
    }
}
