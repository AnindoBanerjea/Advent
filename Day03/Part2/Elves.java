import java.util.*;
import java.util.stream.*;

public class Elves {
    public static void main(String[] args) {
        RucksackList rl = new RucksackList("input.txt");
        int sum = 0;
        for (int i=0; i<rl.getRucksacks().size(); i=i+3) {

            System.out.println("Rucksack: " + rl.getRucksacks().get(i));
            System.out.println("Rucksack: " + rl.getRucksacks().get(i+1));
            System.out.println("Rucksack: " + rl.getRucksacks().get(i+2));
            Set<Character> set1 = rl.getRucksacks().get(i).chars().mapToObj(e->(char)e).collect(Collectors.toSet());
            Set<Character> set2 = rl.getRucksacks().get(i+1).chars().mapToObj(e->(char)e).collect(Collectors.toSet());
            Set<Character> set3 = rl.getRucksacks().get(i+2).chars().mapToObj(e->(char)e).collect(Collectors.toSet());
            set1.retainAll(set2);
            set1.retainAll(set3);
            sum += priority(set1.iterator().next());
        }
        System.out.println(sum);
    }  
    
    private static int index (char c) {
        if (c >= 'a' && c <= 'z') return (int)(c - 'a');
        return (int)(c - 'A' + 26);
    }

    private static int priority (char c) {
        return index(c) + 1;
    }
}
