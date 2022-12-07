import java.util.*;
import java.util.stream.*;


public class Elves {
    public static void main(String[] args) {
        RucksackList rl = new RucksackList("input.txt");
        int sum = 0;
        for (Rucksack r : rl.getRucksacks()) {
            Set<Character> set1 = r.getCompartment1().chars().mapToObj(e->(char)e).collect(Collectors.toSet());
            Set<Character> set2 = r.getCompartment2().chars().mapToObj(e->(char)e).collect(Collectors.toSet());
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
