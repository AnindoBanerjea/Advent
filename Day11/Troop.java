import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Troop {

    private final List<Monkey> monkeys = new ArrayList<>();
    public Troop(String filename, int part) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        List<Integer> items = null;
        char operator = '\0';
        int operand=0, divisor=0, truetarget=0, falsetarget=0;
        for (String line : lines) {
            String[] parts = line.trim().split(":");
            String[] secondpart = (parts.length > 1) ? parts[1].trim().split(" +") : null;
            switch (parts[0]) {
                case "Starting items":
                    items = new ArrayList<>();
                    for (int i = 0; i < secondpart.length; i++) {
                        String nextitem = secondpart[i].replaceAll(",$", "");
                        int next = Integer.parseInt(nextitem);
                        items.add(next);
                    }
                    break;
                case "Operation":
                    operator = secondpart[3].charAt(0);
                    if (secondpart[4].equals("old")) {
                        operator = '^';
                        operand = 2;
                     } else {
                        operand = Integer.parseInt(secondpart[4]);
                     }
                    break;
                case "Test":
                    divisor = Integer.parseInt(secondpart[2]);
                    break;
                case "If true":
                    truetarget = Integer.parseInt(secondpart[3]);
                    break;
                case "If false":
                    falsetarget = Integer.parseInt(secondpart[3]);
                    monkeys.add(new Monkey(items, operator, operand, ((part == 1) ? 3 : 1), divisor, truetarget, falsetarget));
                    break;
                default:
                    break;
            }
        }
    }

    public void executeRound() {
        for (Monkey m : monkeys) {
            executeRound(m);
        }
    }

    public void executeRound(Monkey m) {
        while (!m.getItems().isEmpty()) {
            int i = (Integer)m.getItems().remove(0);
            i = m.operate(i);
            i = m.decay(i);
            if (m.test(i)) {
                monkeys.get(m.getTrueTarget()).appendItem(i);
            } else {
                monkeys.get(m.getFalseTarget()).appendItem(i);
            }
        }
    }

    public long monkeyBusiness () {
        long hi1 = 0;
        long hi2 = 0;
        for (Monkey m : monkeys) {
            int i = m.getInspectCount();
            if (i >= hi1) {
                hi2 = hi1;
                hi1 = i;
            } else if (i >= hi2) {
                hi2 = i;
            }
        }
        return hi1*hi2;
    }

    public void printInspected() {
        int i=0;
        for (Monkey m : monkeys) {
            System.out.printf("Monkey %d inspected items %d times.\n", i++, m.getInspectCount());
        }
        System.out.println();
    }

    public void printItems() {
        int i=0;
        for (Monkey m : monkeys) {
            System.out.printf("Monkey %d: %s\n", i++, m.getItems());
        }
    }
}
