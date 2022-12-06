import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Expedition {
    private ArrayList<ArrayList<Integer>> elves;

    public Expedition(String filename) {
        this.elves = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> currentElf = new ArrayList<Integer>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            while (line != null) {
                if (line.trim().isEmpty()) {
                    this.elves.add(currentElf);
                    currentElf = new ArrayList<Integer>();
                } else {
                    currentElf.add(Integer.valueOf(line));
                }
                // read next line
                line = reader.readLine();
            }
            this.elves.add(currentElf);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<ArrayList<Integer>> getElves() {
        return elves;
    }
}

