import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Instructions {

    List<Integer> xvalue = new ArrayList<>();
    List<Integer> signal = new ArrayList<>();
    public Instructions(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));

        int cycle = 1;
        int X = 1;
        for (String line : lines) {
            String[] part = line.split(" +");
            String instruction = part[0];
            if (instruction.equals("addx")) {
                int argument = Integer.parseInt(part[1]);
                xvalue.add(X);
                signal.add(cycle*X);
                xvalue.add(X);
                signal.add((cycle+1)*X);
                X += argument;
                cycle += 2;
            } else {
                // assume noop according to puzzle instructions
                xvalue.add(X);
                signal.add(cycle*X);
                cycle++;
            }
        }
    }

    public int sumSignal() {
        int result = 0;
        for (int i=19; i<220 && i < signal.size(); i+=40) {
            result += signal.get(i);
        }
        return result;
    }

    public void draw() {
        for (int i=0; i<240; i++){
            int hpos = i % 40;
            if (Math.abs(hpos - xvalue.get(i)) <= 1) {
                System.out.print('#');
            } else {
                System.out.print('.');
            }
            if (hpos == 39) {
                System.out.println();
            }
        }
    }
}
