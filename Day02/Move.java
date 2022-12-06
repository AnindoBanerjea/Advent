public class Move implements Comparable<Move> {
    private int m;
    public Move(int n) {
        m = n;
    }

    @Override public int compareTo(Move other) {
        // m is either 0, 1, or 2.
        // So this.m - other.m ranges from -2 to + 2, but wins are 1 or -2, losses are -1 or 2, and draws are 0
        // adding 4 takes the range from 2 to 6, but wins are 5 or 2, losses are 3 or 6, and draws are 4
        // % 3 reduces it to the range 0 to 2, but wins are 2, losses are 0, draws are 1
        // subtracting 1 shifts the range to -1 to 1, and wins are 1, losses are -1 and draws are 0
        // which fits the definition of comparable
        return (this.m - other.m + 4) % 3 - 1;
    }

    public int score(Move other) {
        // this.compareTo(other) + 1) * 3 is the win score. the results of compareTo (-1, 0, 1), with one added to it (0, 1, 2) and multipled by 3 (0, 3, 6) fits the description
        // this.m + 1 is the move score. 0 is rock, and gives you a score of 1; 1 is paper, and gives you a score of 2; 2 is scissors, and gives you a score of 3 
        return (this.compareTo(other) + 1) * 3 + this.m + 1;
    }
    
    @Override public String toString() {
        return Integer.toString(this.m);
    }

    public Move predict(int target) {
        // Predicts what move you should play in response to this move, if you want the outcome to be target
        // target = -1, you want to lose
        // target = 0, you want to draw
        // target = 1, you want to win
        return new Move((this.m + target + 3) % 3);
    }
}
