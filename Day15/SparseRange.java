import java.util.LinkedList;
import java.util.List;

public class SparseRange {
    private final List<Move> range;

    public SparseRange(int low, int hi) {
        range = new LinkedList<>();
        range.add(new Move(low, hi));
    }

    public void addIntoRange(int index) {
        for (int i=0; i< range.size(); i++) {
            Move move = range.get(i);
            if (move.x <= index && move.y >= index) {
                // its already in the range
                return;
            } else if (move.y+1 == index) {
                move.y++;
            } else if (move.x-1 == index) {
                move.x--;
            } else if (move.x > index) {
                // found a range larger than i and more than one away, so cannot combine
                range.add(i, new Move(index, index));
                break;
            }
        }

    }

    public void removeFromRange(int index) {
        for (int i=0; i< range.size(); i++) {
            Move move = range.get(i);
            if (move.x == index) {
                move.x++;
                if (move.x > move.y) range.remove(move);
            } else if (move.y == index) {
                move.y--;
                if (move.x > move.y) range.remove(move);
            } else if (move.x < index && index < move.y) {
                // split the range
                int temp = move.y;
                move.y = index -1;
                Move newMove = new Move(index+1, temp);
                range.add(i+1, newMove);
            }
        }
    }

    public void removeFromRange(int low, int hi) {
        for (int i=0; i< range.size(); i++) {
            Move move = range.get(i);
            if (move.x >= low && move.y <= hi) {
                // move range completely within hi and low
                range.remove(move);
                // to avoid skipping over next element when we increment i
                i--;
            } else if (low <= move.x && hi >= move.x && hi < move.y) {
                // overlapping with the lower part of move range
                move.x = hi+1;
            } else if (low > move.x && low <= move.y && hi >= move.y) {
                // overlapping with the higher part of move range
                move.y = low-1;
            } else if (move.x < low && hi < move.y) {
                // hi low completely within move range, so split the range
                int temp = move.y;
                move.y = low -1;
                Move newMove = new Move(hi+1, temp);
                range.add(i+1, newMove);
            }
            // else non-overlapping ranges, do nothing
        }
    }

    public int size(){
        int count = 0;
        for (Move move : range) {
            count += move.y - move.x + 1;
        }
        return count;
    }


    public int first() {
        if (range.isEmpty()) return Integer.MIN_VALUE;
        return range.get(0).x;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i< range.size(); i++) {
            Move move = range.get(i);
            if (i>0) sb.append("->");
            sb.append('(').append(move.x).append("..").append(move.y).append(')');
        }
        return sb.toString();
    }

}
