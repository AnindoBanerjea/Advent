import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Integer.MAX_VALUE;


public class Cave {

    int[] max = new int[]{0,0};
    int[] min = new int[]{MAX_VALUE, 0};
    private final char[][] matrix;

    private int count;

    List<List<int[]>> input;

    public Cave(String filename, int part) throws IOException {
        this.count = 0;
        input = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filename));
        for (String line : lines) {
            String[] pieces = line.split("(,| -> )");
            List<int[]> structure = new ArrayList<>();
            for (int i=0; i < pieces.length; i+=2) {
                int[] pair = new int[2];
                pair[0] = Integer.parseInt(pieces[i]);
                pair[1] = Integer.parseInt(pieces[i+1]);
                structure.add(pair);
                for (int j=0; j<2; j++) max[j] = Math.max(pair[j], max[j]);
                for (int j=0; j<2; j++) min[j] = Math.min(pair[j], min[j]);
            }
            input.add(structure);
        }
        if (part == 2) {
            List<int[]> floor = new ArrayList<>();
            min[0] = 0;
            max[0] *= 2;
            max[1] +=2;
            floor.add(new int[]{min[0],max[1]});
            floor.add(new int[]{max[0], max[1]});
            input.add(floor);
        }
        matrix = new char[max[1]+1][max[0]+1];
        for (List<int[]> structure : input) {
            int[] prevpair = null;
            for (int[] pair : structure) {
                if (prevpair != null) {
                    if (prevpair[0] == pair[0]) {
                        // x is the same, so iterate over y and fill it
                        int x = pair[0];
                        int lessery = Math.min(prevpair[1], pair[1]);
                        int greatery = Math.max(prevpair[1], pair[1]);
                        for (int y = lessery; y <= greatery; y++) {
                            matrix[y][x] = '#';
                        }
                    } else if (prevpair[1] == pair[1]) {
                        // y is the same, so iterate over x and fill it
                        int y = pair[1];
                        int lesserx = Math.min(prevpair[0], pair[0]);
                        int greaterx = Math.max(prevpair[0], pair[0]);
                        for (int x = lesserx; x <= greaterx; x++) {
                            matrix[y][x] = '#';
                        }
                    } else {
                        System.out.println("Cannot handle diagonal lines (x and y are both different).");
                        System.exit(-1);
                    }
                }
                prevpair = pair;
            }
        }
        matrix[0][500] = '+';
    }

    public boolean drop() {
        int[] current = new int[]{500,0};
        while (true) {
            // if we are dropping off the bottom
            if (current[1]+1 > max[1]) {
                // there can be nothing below us, so we can just terminate the similation
                break;
            }
            // look immediately below
            else if (matrix[current[1]+1][current[0]] == '\u0000') {
                current[1]++;
            }
            //look left
            else if (current[0]-1 < min[0]) {
                        // we are dropping off the left edge so stop simulation
                break;
            }
            // look below and to the left
            else if (matrix[current[1]+1][current[0]-1] == '\u0000') {
                current[1]++;
                current[0]--;
            }
            // look right
            else if (current[0]+1 > max[0]) {
                // we are dropping off the left edge so stop simulation
                break;
            }
            //look below and to the right
            else if (matrix[current[1]+1][current[0]+1] == '\u0000') {
                current[1]++;
                current[0]++;
            } else{
                // sand stays there because no place to go

                matrix[current[1]][current[0]] = 'o';
                count++;
                if (current[1] == 0 && current[0] == 500) {
                    // source is blocked so we can stop simulating
                    break;
                }
                return true;
            }
        }
        // we have fallen off the edge or blocked the source so we can stop simulating
        return false;
    }
    public void plot() {
        for (int y=min[1]; y<=max[1]; y++) {
            System.out.println(Arrays.copyOfRange(matrix[y], min[0], max[0]+1));
        }
    }

    public int getCount() {
        return count;
    }

}
