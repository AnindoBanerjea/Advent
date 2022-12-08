import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;


public class StacksAndPlan {
   
    
    private Stacks stacks;
    private final Plan plan;

    public StacksAndPlan(String filename) {
        List<String> lines = null;

        try {
            lines = Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (lines != null) {
            // find the blank line
            int i;
            for (i = 0; i < lines.size(); i++) {
                if ( lines.get(i).trim().isEmpty()) break;
            }

            stacks = new Stacks(lines.subList(0, i));
            plan = new Plan(lines.subList(i+1, lines.size()));
        } else {
            stacks = new Stacks(new ArrayList<>());
            plan = new Plan(new ArrayList<>());
        }
    }

    public Stacks getStacks() {
        return stacks;
    }

    public Plan getPlan() {
        return plan;
    }

}
