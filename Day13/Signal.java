import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class Signal {

    private final List<Packet> data;
    private final int part;
    private boolean verbose;

    public Signal(String filename, int part, boolean verbose) throws IOException {
        this.part = part;
        this.verbose = verbose;
        data = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filename));
        for (int i=0; i<lines.size(); i+=3) {
            data.add(new Packet(lines.get(i), verbose));
            data.add(new Packet(lines.get(i+1), verbose));
        }
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (Packet pp : data) {
                if (part ==1 && index > 0 && index %2 == 0) sb.append("\n");
                sb.append(pp).append("\n");
                index++;
        }
        return sb.toString();
    }

    public int sumIndicesRightOrder() {
        int sumIndices = 0;
        for (int index = 1; index * 2 <= data.size(); index++) {
            if (verbose) System.out.printf("== Pair %d ==\n", index);
            Packet p1 = data.get((index-1)*2);
            Packet p2 = data.get((index-1)*2 + 1);
            p1.setIndent("");
            if (p1.compareTo(p2) < 0) {
                sumIndices += index;
            }
            if (verbose) System.out.println();
        }
        return sumIndices;
    }

    public void sort(){
        Collections.sort(data);
    }

    public void addDividerPackets(List<Packet> dividers) {
        data.addAll(dividers);
    }

    public int decoderKey(List<Packet> dividers) {
        int result = 1;
        for (Packet divider : dividers) {
            for (int i=0; i<data.size(); i++) {
                if (divider.equals(data.get(i))) {
                    if (verbose) System.out.printf("Key %s is at index %d\n", divider, i+1);
                    result *= (i+1);
                }
            }
        }
        return result;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
        for (Packet p : data) {
            p.setVerbose(verbose);
        }
    }
}
