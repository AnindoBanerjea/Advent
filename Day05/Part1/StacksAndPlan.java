import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class StacksAndPlan {
   
    
    private Stacks stacks;
    private Plan plan;

    public StacksAndPlan(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            ArrayList<String> lines = new ArrayList<String>();
            while (!line.trim().isEmpty()) {
                lines.add(line);

                // read next line
                line = reader.readLine();
            }
            stacks = new Stacks(lines);
            lines = new ArrayList<String>();
            while (line != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }

                // read next line
                line = reader.readLine();
            }
            plan = new Plan(lines);

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stacks getStacks() {
        return stacks;
    }

    public Plan getPlan() {
        return plan;
    }

}
