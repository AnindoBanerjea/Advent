import java.util.ArrayList;
import java.util.List;

public class Packet implements Comparable<Packet> {
    private final List<Object> data;

    public Packet(String s) {
        data = new ArrayList<>();
        // drop the first [ and last ]
        String trimmed = s.substring(1, s.length()-1);
        while (!trimmed.isEmpty()) {
            if (trimmed.matches("\\d+,.*")){
                // next element is an integer
                String[] part = trimmed.split(",", 2);
                trimmed = part[1];
                data.add(Integer.parseInt(part[0]));
            } else if (trimmed.matches("\\d+")) {
                data.add(Integer.parseInt(trimmed));
                trimmed = "";
            } else {
                // next element is a list
                // find matching brackets
                int count = 0;
                for (int i=0; i< trimmed.length(); i++) {
                    char c = trimmed.charAt(i);
                    if (c == '[') count++;
                    else if (c == ']') count--;
                    if (count == 0) {
                        data.add(new Packet(trimmed.substring(0,i+1)));
                        if (i+2 < trimmed.length()) {
                            trimmed = trimmed.substring(i + 2);
                        } else {
                            trimmed = "";
                        }
                        break;
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i=0; i<data.size(); i++) {
            Object o = data.get(i);
            if (i > 0) sb.append(',');
            if (o.getClass() == Integer.class) {
                int n = (Integer)o;
                sb.append(n);
            } else {
                // must be a Packet
                Packet p = (Packet)o;
                sb.append(p);
            }
        }
        return sb.append(']').toString();
    }

    public int compareTo(Packet other) {
        return compareTo(other, false, "");
    }
    public int compareTo(Packet other, boolean verbose, String indent) {
        String mixedindent = "";
        if (verbose) System.out.printf("%s- Compare %s vs %s\n", indent, this, other);
        if (this.data.size() == 0 && other.data.size() == 0) return 0;
        if (this.data.size() == 0) {
            if (verbose) System.out.printf("%s  - Left side ran out of items, so inputs are in the right order\n", indent);
            return -1;
        }
        if (other.data.size() == 0) {
            if (verbose) System.out.printf("%s  - Right side ran out of items, so inputs are NOT in the right order\n", indent);
            return 1;
        }
        int i = 0;
        while (i < this.data.size() && i < other.data.size()) {
            Object thisNext = this.data.get(i);
            Object otherNext = other.data.get(i);
            i++;
            if (thisNext.getClass() == Integer.class && otherNext.getClass() == Integer.class) {
                if (verbose) System.out.printf("%s  - Compare %s vs %s\n", indent, thisNext, otherNext);
                if ((Integer) thisNext < (Integer) otherNext) {
                    if (verbose) System.out.printf("%s    - Left side is smaller, so inputs are in the right order\n", indent);
                    return -1;
                }
                if ((Integer) thisNext > (Integer) otherNext) {
                    if (verbose) System.out.printf("%s    - Right side is smaller, so inputs are NOT in the right order\n", indent);
                    return 1;
                }
            } else {
                if (verbose) {
                    if (thisNext.getClass() == Integer.class) {
                        System.out.printf("%s  - Compare %s vs %s\n", indent, thisNext, otherNext);
                        System.out.printf("%s    - Mixed types, convert left to [%d] and retry comparison\n", indent, thisNext);
                        mixedindent = "  ";                    }
                    if (otherNext.getClass() == Integer.class) {
                        System.out.printf("%s  - Compare %s vs %s\n", indent, thisNext, otherNext);
                        System.out.printf("%s    - Mixed types, convert right to [%d] and retry comparison\n", indent, otherNext);
                        mixedindent = "  ";
                    }
                }
                Packet thisNextP = (thisNext.getClass() == Integer.class) ?
                        new Packet("[" + thisNext + "]") :
                        (Packet) thisNext;
                Packet otherNextP = (otherNext.getClass() == Integer.class) ?
                        new Packet("[" + otherNext + "]") :
                        (Packet) otherNext;
                int compare = thisNextP.compareTo(otherNextP, verbose, indent + "  " + mixedindent);
                if (compare != 0) return compare;
            }
        }
        if (this.data.size() > i) {
            if (verbose) System.out.printf("%s  - Right side ran out of items, so inputs are NOT in the right order\n", indent);
            return 1;
        }
        if (other.data.size() > i) {
            if (verbose) System.out.printf("%s  - Left side ran out of items, so inputs are in the right order\n", indent);
            return -1;
        }
        return 0;
    }


}
