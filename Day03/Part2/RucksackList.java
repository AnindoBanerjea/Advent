import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;


public class RucksackList {
   
    
    private ArrayList<String> rs;

    public RucksackList(String filename) {
        this.rs = new ArrayList<String>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (String line : lines) {
                this.rs.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getRucksacks() {
        return rs;
    }
}
