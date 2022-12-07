public class Move {
    private int num;
    private int from;
    private int to;

    public Move(int n, int f, int t){
        num = n;
        from = f;
        to = t;
    }

    public int getNum() {
        return num;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}
