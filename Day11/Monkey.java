import java.util.List;

public class Monkey {
    private final List<Long> items;

    private int inspectcount;
    private final char operator;
    private final long operand;
    private long decayfactor;
    private final long divisor;
    private final int truetarget;
    private final int falsetarget;

    public Monkey(List<Long> items, char operator, long operand, long decayfactor, long divisor, int truetarget, int falsetarget) {
        this.items = items;
        this.operator = operator;
        this.operand = operand;
        this.decayfactor = decayfactor;
        this.divisor = divisor;
        this.truetarget = truetarget;
        this.falsetarget = falsetarget;
        this.inspectcount = 0;
    }

    public void setDecayfactor(long value) {
        decayfactor = value;
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

    public long decay1(long worry) {
        return worry / decayfactor;
    }

    public long decay2(long worry) {
        return worry % decayfactor;
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

    public long getDivisor() {
        return divisor;
    }
}
