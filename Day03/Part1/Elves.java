import java.util.*;
import com.google.common.primitives.Chars;


public class Elves {
    public static void main(String[] args) {
        RucksackList rl = new RucksackList("input.txt");
        int sum = 0;
        for (Rucksack r : rl.getRucksacks()) {
            Set<Character> set1 = new HashSet<>(Chars.asList(r.getCompartment1().toCharArray()));
            Set<Character> set2 = new HashSet<>(Chars.asList(r.getCompartment2().toCharArray()));
            set1.retainAll(set2);
            sum += priority(set1.iterator().next());
        }
        System.out.println(sum);
    }  
    
    private static int index (char c) {
        if (c >= 'a' && c <= 'z') return c - 'a';
        return c - 'A' + 26;
    }

    private static int priority (char c) {
        return index(c) + 1;
    }
}
