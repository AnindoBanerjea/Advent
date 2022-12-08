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
                String[] l1 = line.split(",");
                String[] range1 = l1[0].split("-");
                String[] range2 = l1[1].split("-");
                RangePair rp = new RangePair(Integer.parseInt(range1[0]),
                                             Integer.parseInt(range1[1]),
                                             Integer.parseInt(range2[0]),
                                             Integer.parseInt(range2[1])); 
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
