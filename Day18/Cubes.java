import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import com.google.common.collect.Sets;


public class Cubes {

    private final Set<int[]> cubes;
    private final boolean verbose;
    private final int part;

    public Cubes(String filename, int part, boolean verbose) throws IOException {
        this.verbose = verbose;
        this.part = part;
        cubes = new HashSet<>();
        List<String> lines = Files.readAllLines(Paths.get(filename));
        for (String line : lines) {
            String[] cube = line.split(",");
            assert cube.length == 3;
            cubes.add(new int[] {
                    Integer.parseInt(cube[0]),
                    Integer.parseInt(cube[1]),
                    Integer.parseInt(cube[2])
            });
        }
    }

    private boolean adjacent(int[] a, int [] b) {
        // two cubes are adjacent if they are same on two dimensions and off by one on the third
        int match = 0;
        int offbyone = 0;
        for (int dim=0; dim < 3; dim++) {
            if (a[dim] == b[dim]) {
                match++;
            } else if (Math.abs(a[dim]-b[dim]) <= 1) {
                offbyone++;
            }
        }
        return (match == 2 && offbyone == 1);
    }
    public long countSides() {
        // generate pairs
         return (long)cubes.size()*6 - 2*Sets.combinations(cubes, 2).
                 stream().
                 map(List::copyOf).
                 filter(l -> adjacent(l.get(0), l.get(1))).
                 count();
    }
}
