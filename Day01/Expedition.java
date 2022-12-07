import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Expedition {
    private ArrayList<ArrayList<Integer>> elves;

    public Expedition(String filename) {
        this.elves = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> currentElf = new ArrayList<Integer>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (String line : lines) {
                if (line.trim().isEmpty()) {
                    this.elves.add(currentElf);
                    currentElf = new ArrayList<Integer>();
                } else {
                    currentElf.add(Integer.valueOf(line));
                }
            }
            this.elves.add(currentElf);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Integer>> getElves() {
        return elves;
    }
}

