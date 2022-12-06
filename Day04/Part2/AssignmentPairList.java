import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class AssignmentPairList {
   
    
    private ArrayList<RangePair> rs;

    public AssignmentPairList(String filename) {
        this.rs = new ArrayList<RangePair>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            while (line != null) {
                String[] l1 = line.split(",");
                String[] range1 = l1[0].split("-");
                String[] range2 = l1[1].split("-");
                RangePair rp = new RangePair(Integer.parseInt(range1[0]),
                                             Integer.parseInt(range1[1]),
                                             Integer.parseInt(range2[0]),
                                             Integer.parseInt(range2[1])); 
                this.rs.add(rp);

                // read next line
                line = reader.readLine();

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<RangePair> getRangePairs() {
        return rs;
    }
}
