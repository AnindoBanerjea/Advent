import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import com.google.common.collect.Sets;


public class Cubes {


    private final Set<Cube> lava;
    private Set<Cube> steam;

    private Cube min;
    private Cube max;

    public Cubes(String filename) throws IOException {
        lava = Files.readAllLines(Paths.get(filename)).
                stream().
                map(s -> s.split(",")).
                map(s -> new Cube(new int[] {
                        Integer.parseInt(s[0]),
                        Integer.parseInt(s[1]),
                        Integer.parseInt(s[2])})).
                collect(Collectors.toSet());
        computeMinMax();
    }

    private void generateSteam() {
        Deque<Cube> pending = new ArrayDeque<>();
        // start from min
        Cube minCopy = new Cube(min);
        pending.add(minCopy);
        steam.add(minCopy);
        while (!pending.isEmpty()) {
            Cube current = pending.poll();
            // steam spreads in all directions
            for (Cube dir : Cube.directions) {
                Cube next = current.apply(dir);
                // unless going outside the box or already lava or already steam
                if (next.inBox(min, max) && !lava.contains(next) && !steam.contains(next)) {
                    steam.add(next);
                    pending.add(next);
                }
            }
        }
    }

    private void convertUnreachableToLava() {
        // convert all unreachable cubes to lava
        for (int i = min.getCoordinates()[0]; i<=max.getCoordinates()[0]; i++) {
            for (int j = min.getCoordinates()[1]; j<=max.getCoordinates()[1]; j++) {
                for (int k = min.getCoordinates()[1]; k<=max.getCoordinates()[2]; k++) {
                    Cube next = new Cube(new int[]{ i, j, k});
                    if (!lava.contains(next) && !steam.contains(next)) {
                        lava.add(next);
                    }
                }
            }
        }
    }

    public long countExternalSides() {
        steam = new HashSet<>();
        generateSteam();
        convertUnreachableToLava();
        return countSides();
    }

    private void computeMinMax() {
        min = lava.stream().reduce(Cube::min).
                orElse(new Cube(new int [] {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE}));
        max = lava.stream().reduce(Cube::max).
                orElse(new Cube(new int [] {Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE}));
    }

    public long countSides() {
        //  we have 6 * number of cubes sides - 2 * number of cubes that touch along a face
         return (long) lava.size()*6 - 2*
                 // generate pairs as sets of two
                 Sets.combinations(lava, 2).
                         // turn them into a stream
                         stream().
                         // turn the set of two into a list (so we can get to 0 and 1 elements)
                         map(List::copyOf).
                         // filter to keep only the pairs that are adjacent
                         filter(l -> l.get(0).adjacent(l.get(1))).
                         // count them
                         count();
    }

}
