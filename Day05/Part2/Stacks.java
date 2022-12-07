import java.util.ArrayList;
import java.util.*;

public class Stacks {
    private List<ArrayDeque<Character>> s;

    public Stacks(List<String> lines){
        // Read how many stacks there are
        String[] parts = lines.get(lines.size()-1).trim().split("\\s+");
        int numStacks = parts.length;
        s = new ArrayList<>(numStacks);
        for (int j = 0; j < numStacks; j++){
            s.add(j, new ArrayDeque<Character>());
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

    public Deque<Character> getStack(int index) {
        return s.get(index);
    }

    public void move(Move m) {
        Deque<Character> temp = new ArrayDeque<>();
        for (int i = 0; i < m.getNum(); i++) {
            char c = getStack(m.getFrom()-1).pop();
            temp.push(c);
        }
        while (!temp.isEmpty()) {
            char c = temp.pop();
            getStack(m.getTo()-1).push(c);
        }
    }

    public String topOfStacks() {
        StringBuilder sb = new StringBuilder();
        for (Deque<Character> sc : s) {
            sb.append(sc.peek());
        }
        return sb.toString();
    }
    
}
