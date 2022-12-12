import java.io.IOException;

public class Elves {
    public static void main(String[] args) throws IOException {
        if (args.length == 0 || !args[0].matches("^\\d+$")) {
            args = new String[]{"1", "2"};
        }

        for (String arg : args) {
            String filename = "test.txt";
            int part = Integer.parseInt(arg);
            System.out.printf("Solving part %d with input %s\n", part, filename);
            Troop troop = new Troop(filename, part);
            for (int i=1; i<=((part == 1) ? 20 : 10000); i++) {
                troop.executeRound();
                if (part ==1) {
                    if ( i <=10 ||  i % 5 == 0) {
                        System.out.printf("After round %d, the monkeys are holding items with these worry levels:\n", i);
                        troop.printItems();
                    }

                }
                if ( i==1 || i == 20 || i % 1000 == 0) {
                    System.out.printf("== After round %d ==\n", i);
                    troop.printInspected();
                }
            }
            System.out.println(troop.monkeyBusiness());
        }
    }
}
