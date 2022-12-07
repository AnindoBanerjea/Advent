import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;


public class RucksackList {
   
    
    private ArrayList<Rucksack> rs;

    public RucksackList(String filename) {
        this.rs = new ArrayList<Rucksack>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (String line : lines) {
                Rucksack r = new Rucksack(line.substring(0, line.length()/2), line.substring(line.length()/2, line.length()));
                this.rs.add(r);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Rucksack> getRucksacks() {
        return rs;
    }
}
