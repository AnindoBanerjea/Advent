import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.google.common.collect.Sets;

public class Valves {


    private final int part;

    private final Map<String, Valve> valves;

    // List of non zero valves
    private final List<Valve> nzv;


    private final int[][] dist;
    private final int[][] next;

    public Valves(String filename, int part) throws IOException {
        this.part = part;
        valves = new HashMap<>();
        nzv = new ArrayList<>();
        Map<String, String> children = new HashMap<>();
        List<String> lines = Files.readAllLines(Paths.get(filename));
        int number = 0;
        for (String line : lines) {
            Pattern pattern = Pattern.compile("Valve ([A-Z]+) has flow rate=(\\d+); tunnels? leads? to valves? (.+)");
            Matcher matcher = pattern.matcher(line);
            if (!matcher.find()) {
                System.out.printf("Match failed for Line \"%s\"\n", line);
            }
            // don't look at match(0) which matches the whole thing
            String name = matcher.group(1);
            int rate = Integer.parseInt(matcher.group(2));
            Valve newvalve = new Valve(name, number++, rate);
            valves.put(name, newvalve);
            if (rate > 0) {
                nzv.add(newvalve);
            }
            children.put(name, matcher.group(3));
        }
        for (String name : valves.keySet()) {
            Valve v = valves.get(name);
            String childrenstring = children.get(name);
            String[] childstring = childrenstring.split(", ");
            for (String child : childstring) {
                v.addChild(valves.get(child));
            }
        }
        dist = new int[valves.size()][valves.size()];
        next = new int[valves.size()][valves.size()];
        allShortestPaths();
    }
    public List<Valve> stillReachable(Set<Valve> target, Valve current, int remainingTime) {
        List<Valve> toVisit = new LinkedList<>(target);
        toVisit.removeIf(dest -> dist[current.getNumber()][dest.getNumber()] >= remainingTime);
        return toVisit;
    }

    public int maximizePressure() {
        int maxPressure = 0;
        if (part == 1) {
            return maximizePressure(new HashSet<>(nzv), valves.get("AA"), 30, 0);
        } else {
            Set<Set<Valve>> pset = Sets.powerSet(new HashSet<>(nzv));
            for (Set<Valve> mine : pset) {
                Set<Valve> elephants = new HashSet<>(nzv);
                elephants.removeAll(mine);
                int pressure =  maximizePressure(mine, valves.get("AA"), 26, 0) +
                                maximizePressure(elephants, valves.get("AA"), 26, 0);
                maxPressure = Math.max(maxPressure, pressure);
            }
        }
        return maxPressure;
    }

    public int maximizePressure(Set<Valve> targets, Valve current, int remainingTime, int maxPressure) {
        int maxSoFar = maxPressure;

       // Compute list of still reachable unvisited nodes
        List<Valve> reachable = stillReachable(targets, current, remainingTime);

        // if empty return max pressure
        if (reachable.isEmpty()) {
            return maxPressure;
        }

        // else iterate over remaining reachable nodes
        for (Valve next : reachable) {
            int distance = dist[current.getNumber()][next.getNumber()];
            int newRemainingTime = remainingTime - distance - 1;
            int newMaxPressure = maxPressure + next.getRate() * newRemainingTime;
            Set<Valve> newTargets = new HashSet<>(targets);
            newTargets.remove(next);

            // call maximize pressure recursively
            maxSoFar = Math.max(maxSoFar,
                    maximizePressure(newTargets, next, newRemainingTime, newMaxPressure));
        }
        return maxSoFar;
    }


    public void allShortestPaths() {
        for (int i = 0; i< valves.size(); i++) {
            for (int j=0; j< valves.size(); j++) {
                if (i == j) {
                    dist[i][j] = 0;
                    next[i][j] = -1;
                } else {
                    dist[i][j] = Integer.MAX_VALUE;
                    next[i][j] = j;
                }
            }
        }
        for (Valve v : valves.values()) {
            for (Valve c : v.getChildren()) {
                dist[v.getNumber()][c.getNumber()] = 1;
            }
        }
        for (int k = 0; k < valves.size(); k++) {
            // Pick all vertices as source one by one
            for (int i = 0; i < valves.size(); i++) {
                // Pick all vertices as destination for the
                // above picked source
                for (int j = 0; j < valves.size(); j++) {
                    // If vertex k is on the shortest path
                    // from i to j, then update the value of
                    // dist[i][j]
                    if (dist[i][k] != Integer.MAX_VALUE &&
                        dist[k][j] != Integer.MAX_VALUE &&
                        dist[i][k] + dist[k][j] < dist[i][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[i][k];
                    }
                }
            }
        }
    }
}
