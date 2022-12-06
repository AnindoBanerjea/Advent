public class Elves {
    public static void main(String[] args) {
        RucksackList rl = new RucksackList("input.txt");
        int sum = 0;
        for (int i=0; i<rl.getRucksacks().size(); i=i+3) {
            boolean[] seen1 = new boolean[52];
            boolean[] seen2 = new boolean[52];
            System.out.println("Rucksack: " + rl.getRucksacks().get(i));
            System.out.println("Rucksack: " + rl.getRucksacks().get(i+1));
            System.out.println("Rucksack: " + rl.getRucksacks().get(i+2));
            for (char c : rl.getRucksacks().get(i).toCharArray()) {
                seen1[index(c)] = true;
            }
            for (char c : rl.getRucksacks().get(i+1).toCharArray()) {
                seen2[index(c)] = true;
            }
            for (char c : rl.getRucksacks().get(i+2).toCharArray()) {
                if (seen1[index(c)] && seen2[index(c)]) {
                    sum += priority(c);
                    System.out.println("Badge: " + c + ", priority: " + priority(c));
                    System.out.println("------------------------------------------------------");
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
