public class Elves {
    public static void main(String[] args) {
        RucksackList rl = new RucksackList("input.txt");
        int sum = 0;
        for (Rucksack r : rl.getRucksacks()) {
            boolean[] seen = new boolean[52];
            for (char c : r.getCompartment1().toCharArray()) {
                seen[index(c)] = true;
            }
            for (char c : r.getCompartment2().toCharArray()) {
                if (seen[index(c)]) {
                    sum += priority(c);
                    // System.out.println("C1: " + r.getCompartment1() + ", C2:" + r.getCompartment2() + ", common item: " + c + ", priority: " + priority(c));
                    break;
                }
            }
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
