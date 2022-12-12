import java.util.List;

public class Monkey {
    private final List<Integer> items;

    private int inspectcount;
    private final char operator;
    private final int operand;
    private final int decayfactor;
    private final int divisor;
    private final int truetarget;
    private final int falsetarget;

    public Monkey(List<Integer> items, char operator, int operand, int decayfactor, int divisor, int truetarget, int falsetarget) {
        this.items = items;
        this.operator = operator;
        this.operand = operand;
        this.decayfactor = decayfactor;
        this.divisor = divisor;
        this.truetarget = truetarget;
        this.falsetarget = falsetarget;
        this.inspectcount = 0;
    }

    public int operate(int worry) {
        int result = -1;
        switch (operator) {
            case '+' -> result = worry + operand;
            case '*' -> result = worry * operand;
            case '^' -> result = worry * worry;
            default -> {}
        }
        inspectcount++;
        return result;
    }

    public int decay(int worry) {
        return worry / decayfactor;
    }

    public boolean test(int worry){
        return ((worry % divisor) == 0);
    }



    public void appendItem(int i){
        items.add(i);
    }

    public int getTrueTarget() {
        return truetarget;
    }

    public int getFalseTarget() {
        return falsetarget;
    }

    public List<Integer> getItems() {
        return this.items;
    }

    public int getInspectCount() {
        return inspectcount;
    }
}
