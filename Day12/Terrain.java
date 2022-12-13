import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Terrain {

    Map<Position, Integer> visited;
    List<Position> pending;

    private final char[][] map;
    private Position target;
    private Position start;

    public Terrain(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        map = new char[lines.size()][];
        int linecount = 0;
        for (String line : lines) {
            map[linecount++] = line.toCharArray();
        }
        for (int i=0; i< map.length; i++) {
            for (int j=0; j< map[i].length; j++) {
                if (map[i][j] == 'S') {
                    start = new Position(i, j);
                    map[i][j] = 'a';
                } else if (map[i][j] == 'E') {
                    target = new Position(i, j);
                    map[i][j] = 'z';
                }
            }
        }
        visited = new HashMap<>();
        pending = new LinkedList<>();
    }
    public Position walk(Position p, int part) {
        visited.put(p, p.getSteps());
        pending.add(p);
        return walk(part);
    }

    private boolean valid(Position from, Position to){
        if (to == null) return false;
        if (to.getX() < 0 || to.getX() >= map.length) return false;
        if (to.getY() < 0 || to.getY() >= map[0].length) return false;
        return map[to.getX()][to.getY()] - map[from.getX()][from.getY()] >= -1;
    }
    public Position walk(int part) {
        while (!pending.isEmpty()) {
            Position p = pending.remove(0);
            // plot(p);
            for (Move m : Position.lookup.values()) {
                Position next = Position.copyOf(p);
                next.apply(m);
                if (valid(p, next) && !visited.containsKey(next)) {
                    visited.put(next, next.getSteps());
                    pending.add(next);
                    if ((part == 1 && next.equals(start)) ||
                        (part == 2) && map[next.getX()][next.getY()] == 'a') {
                        return next;
                    }
                }
            }
        }
        return null;
    }

     public Position getTarget() {
        return target;
    }


    public void plot(Position p) {
        for (int i=0; i< map.length; i++){
            for (int j=0; j<map[i].length; j++) {
                if (p.getX() == i && p.getY() == j) {
                    System.out.print(".");
                } else {
                    System.out.print(map[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] ca : map) {
            sb.append(ca).append("\n");
        }
        return sb.toString();
    }

}
