import java.util.List;

public class Monkey {
    private final List<Long> items;

    private int inspectcount;
    private final char operator;
    private final int operand;
    private final int decayfactor;
    private final int divisor;
    private int divisorproduct;
    private final int truetarget;
    private final int falsetarget;

    public Monkey(List<Long> items, char operator, int operand, int decayfactor, int divisor, int truetarget, int falsetarget) {
        this.items = items;
        this.operator = operator;
        this.operand = operand;
        this.decayfactor = decayfactor;
        this.divisor = divisor;
        this.divisorproduct = divisor;
        this.truetarget = truetarget;
        this.falsetarget = falsetarget;
        this.inspectcount = 0;
    }

    public long operate(long worry) {
        long result = -1;
        switch (operator) {
            case '+' -> result = worry + operand;
            case '*' -> result = worry * operand;
            case '^' -> result = worry * worry;
            default -> {}
        }
        inspectcount++;
        return result;
    }

    public void setDivisorproduct(int divisorproduct) {
        this.divisorproduct = divisorproduct;
    }

    public int getDivisor() {
        return divisor;
    }

    public long decay(long worry) {
        return (worry / decayfactor) % divisorproduct;
    }

    public boolean test(long worry){
        return ((worry % divisor) == 0);
    }



    public void appendItem(long i){
        items.add(i);
    }

    public int getTrueTarget() {
        return truetarget;
    }

    public int getFalseTarget() {
        return falsetarget;
    }

    public List<Long> getItems() {
        return this.items;
    }

    public int getInspectCount() {
        return inspectcount;
    }

}
