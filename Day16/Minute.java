import java.util.*;

public class Minute {
    private final Valve current;
    private final Valve currentTarget;
    private final Valve currentElephant;
    private final Valve currentTargetElephant;
    private final Set<Valve> open;
    private final int pressure;
    private String description;
    private String descriptionElephant;



    // We use visited to keep track of which nodes we have visited. For brute force, since opening last
    // valve to keep track of if we are looping meaninglessly without doing anything useful. For
    // permurte and visit, we keep track of all visited nodes since AA
    private final Set<Valve> visited;

    private final int num;


    public Minute(Valve current, Valve currentTarget, Valve currentElephant, Valve currentTargetElephant, Set<Valve> open, int pressure, Set<Valve> visited, int num) {
        this.current = current;
        this.currentTarget = currentTarget;
        this.currentElephant = currentElephant;
        this.currentTargetElephant = currentTargetElephant;
        this.visited = new HashSet<>(visited);
        if (currentTarget != null) {
                this.visited.add(currentTarget);
        }
        if (currentTargetElephant != null) {
            this.visited.add(currentTargetElephant);
        }
        this.open = new HashSet<>(open);
        this.pressure = pressure;
        this.num = num;
    }

    public Valve getCurrentElephant() {
        return currentElephant;
    }

    public Valve getCurrentTargetElephant() {
        return currentTargetElephant;
    }

    public Valve getCurrentTarget() {
        return currentTarget;
    }

    public Set<Valve> getOpen() {
        return open;
    }

    public int getPressure() {
        return pressure;
    }

    public Set<Valve> getVisited() {
        return visited;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public void setDescriptionElephant(String descriptionElephant) {
        this.descriptionElephant = descriptionElephant;
    }

    public Valve getCurrent() {
        return current;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("== Minute ").append(num).append(" ==\n");
        if (this.getOpen().isEmpty()) {
            result.append("No valves are open\n");
        } else {
            result.append("Valves ").append(this.getOpen().toString())
                    .append(" are open, releasing ").append(this.getPressure()).append( " pressure.\n");

        }
        if (description != null) {
            result.append(description);
        }
        if (descriptionElephant != null) {
            result.append(descriptionElephant);
        }
        return result.toString();
    }

    public static void printHistory(Deque<Minute> history) {
        List<Minute> steps =  new LinkedList<>(history);
        Collections.reverse(steps);
        for (Minute m : steps) {
            System.out.println(m.toString());
        }
    }

}
