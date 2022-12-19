import java.util.ArrayList;
import java.util.List;

public class Valve implements Comparable<Valve> {
    private final String name;
    private final int number;
    private final int rate;
    private final List<Valve> children;

    public Valve(String name, int number, int rate) {
        this.name = name;
        this.number = number;
        this.rate = rate;
        this.children = new ArrayList<>();
    }
    @Override public int compareTo(Valve other) {
        return (this.rate - other.rate);
    }

    public void addChild(Valve child) {
        this.children.add(child);
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public int getRate() {
        return rate;
    }

    public List<Valve> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
