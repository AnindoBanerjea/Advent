import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class RucksackList {
   
    
    private ArrayList<String> rs;

    public RucksackList(String filename) {
        this.rs = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            while (line != null) {
                this.rs.add(line);

                // read next line
                line = reader.readLine();

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getRucksacks() {
        return rs;
    }
}
