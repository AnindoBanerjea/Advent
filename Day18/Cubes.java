import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.common.collect.Sets;


public class Cubes {

    // Since we are dealing with 3d cubes here, we are only allowed to call Cube
    // and not NCube directly. This way, we always get checked to see that dimensions
    // is 3
    private final Set<Cube> lava;
    private Set<Cube> steam;

    private Cube min;
    private Cube max;

    public Cubes(String filename) throws IOException {
        lava = Files.readAllLines(Paths.get(filename)).
                stream().
                // Stream of lines
                map(s -> s.split(",")).
                // Steam of String[]
                map(s -> new Cube(
                        Integer.parseInt(s[0]),
                        Integer.parseInt(s[1]),
                        Integer.parseInt(s[2]))).
                // Stream of Cube
                collect(Collectors.toSet());
        computeMinMax();
    }

    private void generateSteam() {
        Deque<Cube> pending = new ArrayDeque<>();
        // start from min
        pending.add(min);
        steam.add(min);
        Stream.generate(pending::poll).
                takeWhile(Objects::nonNull).
                // steam spreads in all directions
                flatMap(current -> Arrays.stream(Cube.directions).
                        // apply each direction to current
                        map(current::apply)).
                        // unless going outside the box or already lava or already steam
                        filter(next ->next.inBox(min, max) &&
                                !lava.contains(next) &&
                                !steam.contains(next)).
                // Add the cubes that pass the filter to steam, but also to pending to keep the BFS going
                forEach(next -> {steam.add(next); pending.add(next); });

    }

    private void convertUnreachableToLava() {
        // convert all unreachable cubes to lava
        List<Cube> newLava =
                IntStream.rangeClosed(min.x(), max.x()).boxed().
                        flatMap(i -> IntStream.rangeClosed(min.y(), max.y()).boxed().
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
        min = // convert NCube to Cube
                new Cube(// Find the smallest number along each dimension x, y and z
                        lava.stream().reduce(Cube.MAX_VALUE, Cube::min).
                                // subtract 1 along each dimension to give space for steam to expand
                                apply(Cube.MINUS_ONE));
        max = // Convert NCube to cube
                new Cube(// find the largest number along each dimension
                        lava.stream().reduce(Cube.MIN_VALUE, Cube::max).
                                // add 1 on each dimension to give space for steam to expand
                                apply(Cube.PLUS_ONE));
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
