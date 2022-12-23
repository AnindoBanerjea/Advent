import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
Canimport java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
                map(s -> new Cube(
                        Integer.parseInt(s[0]),
                        Integer.parseInt(s[1]),
                        Integer.parseInt(s[2]))).collect(Collectors.toSet());
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
        List<Cube> newLava =
                IntStream.rangeClosed(min.x(), max.x()).boxed().
                        flatMap(i -> IntStream.rangeClosed(max.y(), max.y()).boxed().
                                flatMap(j -> IntStream.rangeClosed(min.z(), max.z()).
                                        mapToObj(k -> new Cube(i, j, k)))).
                        filter(n -> !lava.contains(n) && !steam.contains(n)).
                        toList();
        lava.addAll(newLava);
    }

    public long countExternalSides() {
        steam = new HashSet<>();
        generateSteam();
        convertUnreachableToLava();
        return countSides();
    }

    private void computeMinMax() {
        min = lava.stream().reduce(Cube.MAX_VALUE, Cube::min);
        max = lava.stream().reduce(Cube.MIN_VALUE, Cube::max);
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
