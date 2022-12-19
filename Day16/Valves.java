import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Valves {


    private final boolean verbose;
    private final int part;

    private final Map<String, Valve> valves;
    private final List<Valve> byNumber;

    private final int boom;

    // List of non zero valves
    private final List<Valve> nzv;


    private Deque<Minute> stepstomax;


    public int getMaxPressure() {
        return maxpressure;
    }

    private int maxpressure = 0;

    private final int[][] dist;
    private final int[][] next;

    public Deque<Minute> getStepsToMax() {
        return stepstomax;
    }

    public Valves(String filename, int boom, int part, boolean verbose) throws IOException {
        this.verbose = verbose;
        this.part = part;
        this.boom = boom;
        valves = new HashMap<>();
        nzv = new ArrayList<>();
        byNumber = new ArrayList<>();
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
            byNumber.add(newvalve);
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

    public Valve nextOnShortestPath(Valve s, Valve d) {
         return byNumber.get(next[s.getNumber()][d.getNumber()]);
    }
    public List<Valve> shortestPath(Valve s, Valve d) {
        Valve cur = s;
        List<Valve> result = new ArrayList<>();
        while (cur != d) {
            cur = byNumber.get(next[cur.getNumber()][d.getNumber()]);
            result.add(cur);
        }
        return result;
    }

    public void permuteAndVisit() {
        Valve currentElephant = (part == 2) ? valves.get("AA") : null;
        // Start at Node AA
        Deque<Minute> history = new LinkedList<>();
        history.push(new Minute(valves.get("AA"), null, currentElephant, null, new HashSet<>(), 0, new HashSet<>(), 1));
        permuteAndVisit(history);

    }

    public void doubleLoop(Deque<Minute> history, List<Valve> toVisit) {
        // Double loop over both your destination and the elephant's destination
        // Both currentTarget and currentTargetElephant were null
        Minute top = history.peek();
        Valve current = top.getCurrent();
        Valve currentElephant = top.getCurrentElephant();

        for (Valve dest : toVisit) {
            Valve yourOpen = null;
            int yourPressure = 0;
            Valve elephantOpen = null;
            int elephantPressure = 0;
            Valve currentTarget;
            if (current == dest) {
                // we are already there, we don't need to look up the path
                // double check its not already open in the meantime
                if (!top.getOpen().contains(current)) {
                    top.setDescription("You open valve " + current.getName() + ".\n");
                    yourOpen = current;
                    yourPressure = current.getRate();
                }
                // currentTarget was already null so we don't need to set it
                currentTarget = null;
            } else {
                Valve nextToVisit = nextOnShortestPath(current, dest);
                top.setDescription("You move to valve " + nextToVisit.getName() + ".\n");
                current = nextToVisit;
                currentTarget = dest;
            }
            if (currentElephant != null) {
                // We have a trained elephant (i.e. part2). The elephant also does not have a target, so
                // loop inside loop to find all possible combinations of targets for both.
                List<Valve> elephantToVisit = new ArrayList<>(toVisit);
                elephantToVisit.remove(dest);
                for (Valve elephantDest : elephantToVisit) {
                    Valve currentTargetElephant;
                    if (currentElephant == elephantDest) {
                        // Elephant is already there, we don't need to look up the path
                        // double check not already open
                        if (!top.getOpen().contains(elephantDest) &&
                                (yourOpen == null || !yourOpen.equals(currentElephant))) {
                            top.setDescriptionElephant("Elephant opens valve " +
                                    currentElephant.getName() + ".\n");
                            elephantOpen = currentElephant;
                            elephantPressure = currentElephant.getRate();
                        }
                        // set currentTargetElephant to null, so we find another
                        // target in the next round.
                        currentTargetElephant = null;
                    } else {
                        Valve nextToVisit = nextOnShortestPath(currentElephant, elephantDest);
                        top.setDescriptionElephant("The elephant moves to valve " +
                                nextToVisit.getName() + ".\n");
                        currentElephant = nextToVisit;
                        currentTargetElephant = elephantDest;
                    }
                    Set<Valve> tempOpen = new HashSet<>(top.getOpen());
                    if (yourOpen != null) tempOpen.add(yourOpen);
                    if (elephantOpen != null) tempOpen.add(elephantOpen);
                    Minute newminute = new Minute(current, currentTarget, currentElephant,
                            currentTargetElephant, tempOpen,
                            top.getPressure() + yourPressure + elephantPressure,
                            top.getVisited(), history.size() + 1);
                    history.push(newminute);
                    permuteAndVisit(history);
                    history.pop();
                    // We have to set currentElephant because we just undid what
                    // it were doing and now we will try something else
                    top = history.peek();
                    currentElephant = top.getCurrentElephant();
                    // But we don't reset current because we want to stay there
                    // we only reset current when we hit the dest loop
                }
            } else {
                Set<Valve> tempOpen = new HashSet<>(top.getOpen());
                if (yourOpen != null) tempOpen.add(yourOpen);
                // No trained elephant so just process for you
                Minute newminute = new Minute(current, currentTarget, null,
                        null, tempOpen,
                        top.getPressure() + yourPressure,
                        top.getVisited(), history.size() + 1);
                history.push(newminute);
                permuteAndVisit(history);
                history.pop();
            }
            // We have to set current and currentElephant because we just undid what
            // they were doing and now we will try something else
            top = history.peek();
            current = top.getCurrent();
            currentElephant = top.getCurrentElephant();
            // No need to reset currentTarget or currentElephantTarget because they are local variables
        }
    }

    public void yourLoop(Deque<Minute> history, List<Valve> toVisit) {
        // single loop only for your destination. Elephant continues his path
        Minute top = history.peek();
        Valve current = top.getCurrent();
        Valve currentElephant = top.getCurrentElephant();

        for (Valve dest : toVisit) {
            // Make currentElephant target a loop variable because when we roll back to try another
            // destination for you, we should keep the elephant going to the same one it was going before
            Valve currentTargetElephant = top.getCurrentTargetElephant();
            assert currentTargetElephant != null;
            Valve currentTarget;
            Valve yourOpen = null;
            int yourPressure = 0;
            Valve elephantOpen = null;
            int elephantPressure = 0;
            if (current == dest) {
                // we are already there, we don't need to look up the path
                // double check its not already open in the meantime
                if (!top.getOpen().contains(current)) {
                    top.setDescription("You open valve " + current.getName() + ".\n");
                    yourOpen = current;
                    yourPressure = current.getRate();
                }
                // set currentTarget to null, so we find another target in the next round
                currentTarget = null;
            } else {
                Valve nextToVisit = nextOnShortestPath(current, dest);
                top.setDescription("You move to valve " + nextToVisit.getName() + ".\n");
                current = nextToVisit;
                currentTarget = dest;
            }
            if (currentElephant != null) {
                // We have a trained elephant (i.e. part2). the elephant was happily moving
                // along a shortest path, so just move him along.
                if (currentTargetElephant != currentElephant) {
                    // Elephant is proceeding along a shortest path so just keep doing it
                    Valve next = nextOnShortestPath(currentElephant, currentTargetElephant);

                    top.setDescriptionElephant("Elephant moves to valve " + next.getName() + ".\n");
                    currentElephant = next;

                } else {
                    // Elephant is at dest so open it
                    // double check not already open
                    if (!top.getOpen().contains(currentElephant) &&
                            (yourOpen == null || !yourOpen.equals(currentElephant))) {
                        top.setDescriptionElephant("Elephant opens valve " +
                                currentElephant.getName() + ".\n");
                        elephantOpen = currentElephant;
                        elephantPressure = currentElephant.getRate();
                    }
                    // set currentTargetElephant to null, so we find another target
                    // in the next round
                    currentTargetElephant = null;
                }
                Set<Valve> tempOpen = new HashSet<>(top.getOpen());
                if (yourOpen != null) tempOpen.add(yourOpen);
                if (elephantOpen != null) tempOpen.add(elephantOpen);
                Minute newminute = new Minute(current, currentTarget, currentElephant,
                        currentTargetElephant, tempOpen,
                        top.getPressure() + yourPressure + elephantPressure,
                        top.getVisited(), history.size() + 1);
                history.push(newminute);
            } else {
                // There is no trained elephant (i.e., part 1)
                Set<Valve> tempOpen = new HashSet<>(top.getOpen());
                if (yourOpen != null) tempOpen.add(yourOpen);
                // No trained elephant so just process for you
                Minute newminute = new Minute(current, currentTarget, currentElephant,
                        currentTargetElephant, tempOpen,
                        top.getPressure() + yourPressure,
                        top.getVisited(), history.size() + 1);
                history.push(newminute);
            }
            permuteAndVisit(history);
            history.pop();
            // We have to set current and currentElephant because we just undid what
            // they were doing and now we will try something else
            top = history.peek();
            current = top.getCurrent();
            currentElephant = top.getCurrentElephant();
            // No need to reset currentTarget or currentTargetElephant because they are local loop variables
        }
    }
    public void elephantLoop(Deque<Minute> history, List<Valve> toVisit) {
        // currentTarget != null && currentTargetElephant == null
        // Single loop only for the elephant. You continue your path.
        // Only reset currentTargetElephant after backtracking
        Minute top = history.peek();
        Valve current = top.getCurrent();
        Valve currentElephant = top.getCurrentElephant();
        //Valve currentTargetElephant = top.getCurrentTargetElephant();
        //assert currentTargetElephant == null;

        // currentTargetElephant == null, so we must iterate over all elephant targets
        // but in this case currentTarget is already set, so we don't iterate over your targets
        List<Valve> elephantToVisit = new ArrayList<>(toVisit);
        Valve yourOpen = null;
        int yourPressure = 0;
        // this is the case where you were happily moving along the shortest path
        // so we need to move you along

        // moved this out of the loop as because every time we backtrack and go to
        // a new elephant, we want to go to the same current target
        Valve currentTarget = top.getCurrentTarget();
        assert currentTarget != null;
        if (currentTarget != current) {
            // You are proceeding along a shortest path so just keep doing it
            Valve next = nextOnShortestPath(current, currentTarget);
            top.setDescription("You move to valve " + next.getName() + ".\n");
            current = next;
        } else {
            // You are at dest so open it
            // double check not already open
            if (!top.getOpen().contains(current)) {
                top.setDescription("You open valve " + current.getName() + ".\n");
                yourOpen = current;
                yourPressure = current.getRate();
            }
            // set currentTarget to null, so we find another target in the next round
            currentTarget = null;
        }

        Valve elephantOpen = null;
        int elephantPressure = 0;
        for (Valve elephantDest : elephantToVisit) {
            Valve currentTargetElephant;
            if (currentElephant == elephantDest) {
                // Elephant is already there, we don't need to look up the path
                // double check not already open
                if (!top.getOpen().contains(currentElephant) &&
                        (yourOpen == null || !yourOpen.equals(currentElephant))) {
                    top.setDescriptionElephant("Elephant opens valve " +
                            currentElephant.getName() + ".\n");
                    elephantOpen = currentElephant;
                    elephantPressure = currentElephant.getRate();
                }
                // set currentTargetElephant to null, so we find another target in the next round
                currentTargetElephant = null;
            } else {
                Valve nextToVisit = nextOnShortestPath(currentElephant, elephantDest);
                top.setDescriptionElephant("The elephant moves to valve " +
                        nextToVisit.getName() + ".\n");
                currentElephant = nextToVisit;
                currentTargetElephant = elephantDest;
            }
            Set<Valve> tempOpen = new HashSet<>(top.getOpen());
            if (yourOpen != null) tempOpen.add(yourOpen);
            if (elephantOpen != null) tempOpen.add(elephantOpen);
            Minute newminute = new Minute(current, currentTarget, currentElephant,
                    currentTargetElephant, tempOpen,
                    top.getPressure() + yourPressure + elephantPressure,
                    top.getVisited(), history.size() + 1);
            history.push(newminute);
            permuteAndVisit(history);
            history.pop();
            // We have to set currentElephant because we just undid what it was doing and now we will try
            // something else. But current is fixed and will not change
            top = history.peek();
            currentElephant = top.getCurrentElephant();
            // No need to reset currentTarget or currentTargetElephant, they are local variables
        }
    }
    public void noLoop(Deque<Minute> history) {
        // Single loop only for the elephant. You continue your path.
        // Only reset currentTargetElephant after backtracking
        Minute top = history.peek();
        Valve current = top.getCurrent();
        Valve currentElephant = top.getCurrentElephant();

        // No loops so no worries about resetting
        Valve currentTarget = top.getCurrentTarget();
        // if currentTarget is null, this means the end when we only have the elephant to move
        Valve currentTargetElephant = top.getCurrentTargetElephant();
        // if currentTargetElephant is null, this means either part 1 (no elephant or the end
        // when we only have you to move

        // both you and the elephant have targets you are moving towards
        Valve yourOpen = null;
        int yourPressure = 0;
        Valve elephantOpen = null;
        int elephantPressure = 0;
        if (currentTarget != null) {
            if (currentTarget != current) {
                // You are proceeding along a shortest path so just keep doing it
                Valve next = nextOnShortestPath(current, currentTarget);
                top.setDescription("You move to valve " + next.getName() + ".\n");
                current = next;
            } else {
                // You are at dest so open it
                // double check not already open
                if (!top.getOpen().contains(current)) {
                    top.setDescription("You open valve " + current.getName() + ".\n");
                    yourOpen = current;
                    yourPressure = current.getRate();
                }
                // set currentTarget to null, so we find another target in the next round
                currentTarget = null;
            }
        }
        if (currentElephant != null && currentTargetElephant != null) {
            if (currentTargetElephant != currentElephant) {
                // Elephant is proceeding along a shortest path so just keep doing it
                Valve next = nextOnShortestPath(currentElephant, currentTargetElephant);

                top.setDescriptionElephant("Elephant moves to valve " + next.getName() + ".\n");
                currentElephant = next;

            } else {
                // Elephant is dest so open it
                // double check not already open
                if (!top.getOpen().contains(currentElephant) &&
                        (yourOpen == null || !yourOpen.equals(currentElephant))) {
                    top.setDescriptionElephant("Elephant opens valve " + currentElephant.getName() + ".\n");
                    elephantOpen = currentElephant;
                    elephantPressure = currentElephant.getRate();
                }
                // set currentTargetElephant to null, so we find another target in the next round
                currentTargetElephant = null;
            }
        }
        Set<Valve> tempOpen = new HashSet<>(top.getOpen());
        if (yourOpen != null) tempOpen.add(yourOpen);
        if (elephantOpen != null) tempOpen.add(elephantOpen);

        Minute newminute = new Minute(current, currentTarget, currentElephant, currentTargetElephant,
                tempOpen, top.getPressure() + yourPressure + elephantPressure,
                top.getVisited(), history.size() + 1);
        history.push(newminute);
        permuteAndVisit(history);
        history.pop();
    }

    public void permuteAndVisit(Deque<Minute> history) {
        int remainingTime = this.boom - history.size();
        if (remainingTime == 0) {
            int totalpressure = 0;
            for (Minute m : history) {
                totalpressure += m.getPressure();
            }
            if (totalpressure > maxpressure) {
                // this is the best answer so far
                maxpressure = totalpressure;
                stepstomax = new LinkedList<>(history);
                if (verbose) {
                    Minute.printHistory(stepstomax);
                    System.out.printf("Found new max %d\n", maxpressure);
                }
            }
        } else {

            Minute top = history.peek();
            Valve current = top.getCurrent();
            Valve currentTarget = top.getCurrentTarget();
            Valve currentElephant = top.getCurrentElephant();
            Valve currentTargetElephant = top.getCurrentTargetElephant();
            if (currentTarget == null || (currentElephant != null && currentTargetElephant == null)) {
                // If you (or the elephant if there is an elephant) don't have a target, we have to pick
                // the next target, i.e. an open valve that is near enough (or do nothing if all valves
                // are open or too far)
                List<Valve> toVisit = new ArrayList<>(nzv);
                toVisit.removeAll(top.getVisited());
                List<Valve> tooFarValves = new ArrayList<>();
                for (Valve dest : toVisit) {
                    if (dist[current.getNumber()][dest.getNumber()] >= remainingTime &&
                        (currentElephant == null ||
                         dist[currentElephant.getNumber()][dest.getNumber()] >= remainingTime)) {
                        tooFarValves.add(dest);
                    }
                }
                toVisit.removeAll(tooFarValves);

                if (toVisit.isEmpty()) {
                    if (currentTargetElephant != null || currentTarget != null) {
                        // elephant or you still have one target to get
                        noLoop(history);
                    } else {
                        // once all the valves are open (or too far away) just keep adding minutes until time is up
                        // every thing is open so do nothing, just continue to minute 30

                        Minute newminute = new Minute(current, currentTarget, currentElephant, currentTargetElephant,
                                top.getOpen(), top.getPressure(), top.getVisited(), history.size() + 1);
                        history.push(newminute);
                        permuteAndVisit(history);
                        history.pop();
                    }
                } else {
                    // There are open valves close enough to visit. So depending on if current target is null
                    // we iterate over to visit and visit them all then backtrack and continue consuming the rest
                    if (currentTarget == null && currentTargetElephant == null) {
                        // Double loop with resets of both variables on the outer loop and
                        // reset of only currentTargetElephant on the inner loop
                        doubleLoop(history, toVisit);
                    } else if (currentTarget == null /* && currentTargetElephant != null */) {
                        // single loop only for your destination. Elephant continues his path
                        // Only reset currentTarget after backtracking
                        yourLoop(history, toVisit);
                    } else /* if (currentTarget != null && currentTargetElephant == null) */ {
                        // Single loop only for the elephant. You continue your path.
                        // Only reset currentTargetElephant after backtracking
                        elephantLoop(history, toVisit);
                    }
                }
            } else {
                noLoop(history);
            }
        }
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
        if (false) System.out.println(Arrays.deepToString(dist));
        if (false) System.out.println(Arrays.deepToString(next));
    }
}