import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;


public class AssignmentPairList {
   
    
    private List<RangePair> rs;

    public AssignmentPairList(String filename) {
        this.rs = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (String line : lines) {
                String[] parts = line.split("[,-]");
                RangePair rp = new RangePair(Integer.parseInt(parts[0]),
                                             Integer.parseInt(parts[1]),
                                             Integer.parseInt(parts[2]),
                                             Integer.parseInt(parts[3])); 
                this.rs.add(rp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<RangePair> getRangePairs() {
        return rs;
    }
}
