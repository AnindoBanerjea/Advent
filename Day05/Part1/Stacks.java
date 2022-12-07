import java.util.ArrayList;
import java.util.*;

public class Stacks {
    private ArrayList<Stack<Character>> s;

    public Stacks(ArrayList<String> lines){
        // Read how many stacks there are
        String[] parts = lines.get(lines.size()-1).trim().split("\\s+");
        int numStacks = parts.length;
        s = new ArrayList<Stack<Character>>(numStacks);
        for (int j = 0; j < numStacks; j++){
            s.add(j, new Stack<Character>());
        }
        for (int i=lines.size()-2; i>=0; i--){
            for (int j = 0; j < numStacks; j++){
                char c = lines.get(i).charAt(j*4+1);
                if (c != ' ') {
                    s.get(j).push(c);
                }
            }
        }
    }

    public Stack<Character> getStack(int index) {
        return s.get(index);
    }

    public void move(Move m) {
        for (int i = 0; i < m.getNum(); i++) {
            char c = getStack(m.getFrom()-1).pop();
            getStack(m.getTo()-1).push(c);
        }
    }

    public String topOfStacks() {
        StringBuilder sb = new StringBuilder();
        for (Stack<Character> sc : s) {
            sb.append(sc.peek());
        }
        return sb.toString();
    }
    
}
