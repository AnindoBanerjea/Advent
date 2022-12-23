import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
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
        for (int i=min.getCoord()[0]; i<=max.getCoord()[0]; i++) {
            for (int j=min.getCoord()[1]; j<=max.getCoord()[1]; j++) {
                for (int k=min.getCoord()[1]; k<=max.getCoord()[2]; k++) {
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
        int[] minCoord = new int[3], maxCoord = new int[3];
        for (int i=0; i< 3; i++) {
            final int ii = i;
            minCoord[ii] = lava.stream().map(s -> s.getCoord()[ii]).min(Integer::compare).orElse(0);
            // create one unit of extra space for the steam
            minCoord[ii] -= 1;
        }
        for (int i=0; i< 3; i++) {
            final int ii = i;
            maxCoord[ii] = lava.stream().map(s -> s.getCoord()[ii]).max(Integer::compare).orElse(0);
            // create one unit of extra space for the steam
            maxCoord[ii] += 1;
        }
        min = new Cube(minCoord);
        max = new Cube(maxCoord);

    }

    public long countSides() {
        // generate pairs
         return (long) lava.size()*6 - 2*Sets.combinations(lava, 2).
                 stream().
                 map(List::copyOf).
                 filter(l -> l.get(0).adjacent(l.get(1))).
                 count();
    }

}
