import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Troop {

    private final List<Monkey> monkeys = new ArrayList<>();
    private final int part;

    public Troop(String filename, int part) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        this.part = part;
        List<Long> items = null;
        char operator = '\0';
        long operand=0;
        long divisor=0;
        int truetarget=0;
        int falsetarget=0;
        for (String line : lines) {
            String[] parts = line.trim().split(":");
            String[] secondpart = (parts.length > 1) ? parts[1].trim().split(" +") : null;
            switch (parts[0]) {
                case "Starting items":
                    items = new ArrayList<>();
                    for (int i = 0; i < secondpart.length; i++) {
                        String nextitem = secondpart[i].replaceAll(",$", "");
                        long next = Long.parseLong(nextitem);
                        items.add(next);
                    }
                    break;
                case "Operation":
                    operator = secondpart[3].charAt(0);
                    if (secondpart[4].equals("old")) {
                        operator = '^';
                        operand = 2L;
                     } else {
                        operand = Long.parseLong(secondpart[4]);
                     }
                    break;
                case "Test":
                    divisor = Long.parseLong(secondpart[2]);
                    break;
                case "If true":
                    truetarget = Integer.parseInt(secondpart[3]);
                    break;
                case "If false":
                    falsetarget = Integer.parseInt(secondpart[3]);
                    monkeys.add(new Monkey(items, operator, operand, 3, divisor, truetarget, falsetarget));
                    break;
                default:
                    break;
            }
        }
        if (part == 2) {
            long factor = 1;
            for (Monkey monkey: monkeys) {
                factor = factor * monkey.getDivisor();
            }
            for (Monkey monkey: monkeys) {
                monkey.setDecayfactor(factor);
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
            long i = m.getItems().remove(0);
            i = m.operate(i);
            i = part == 1 ? m.decay1(i) : m.decay2(i);
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
