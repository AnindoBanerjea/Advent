import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;


public class StacksAndPlan {
   
    
    private Stacks stacks;
    private Plan plan;

    public StacksAndPlan(String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));

            // find the blank line
            int i;
            for (i = 0; i < lines.size(); i++) {
                if ( lines.get(i).trim().isEmpty()) break;
            }

            stacks = new Stacks(lines.subList(0, i));
            plan = new Plan(lines.subList(i+1, lines.size()));
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
