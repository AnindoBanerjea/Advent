import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class RucksackList {
   
    
    private ArrayList<Rucksack> rs;

    public RucksackList(String filename) {
        this.rs = new ArrayList<Rucksack>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            while (line != null) {
                Rucksack r = new Rucksack(line.substring(0, line.length()/2), line.substring(line.length()/2, line.length()));
                this.rs.add(r);

                // read next line
                line = reader.readLine();

            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Rucksack> getRucksacks() {
        return rs;
    }
}
