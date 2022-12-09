import java.util.*;
import com.google.common.primitives.Chars;

public class Elves {
    public static void main(String[] args) {
        RucksackList rl = new RucksackList("input.txt");
        int sum = 0;
        for (int i=0; i<rl.getRucksacks().size(); i=i+3) {

            System.out.println("Rucksack: " + rl.getRucksacks().get(i));
            System.out.println("Rucksack: " + rl.getRucksacks().get(i+1));
            System.out.println("Rucksack: " + rl.getRucksacks().get(i+2));
            Set<Character> set1 = new HashSet<>(Chars.asList(rl.getRucksacks().get(i).toCharArray()));
            Set<Character> set2 = new HashSet<>(Chars.asList(rl.getRucksacks().get(i+1).toCharArray()));
            Set<Character> set3 = new HashSet<>(Chars.asList(rl.getRucksacks().get(i+2).toCharArray()));
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
