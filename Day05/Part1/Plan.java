import java.util.*;

public class Plan {
    private ArrayList<Move> p;

    public Plan(ArrayList<String> lines) {
        p = new ArrayList<Move>();
        for (String line : lines) {
            String[] parts = line.split(" ");
            int num = Integer.parseInt(parts[1]);
            int from = Integer.parseInt(parts[3]);
            int to = Integer.parseInt(parts[5]);
            p.add(new Move(num, from, to));
        }
    }

    public ArrayList<Move> getPlan() {
        return p;
    }
}