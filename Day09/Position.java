import java.util.Map;

import com.google.common.collect.ImmutableMap;


public class Position extends Move {
    public static final Map<Character, Move> lookup = ImmutableMap.of(
        'L', new Move(-1, 0),
        'R', new Move(1, 0),
        'U', new Move(0, 1),
        'D', new Move(0, -1)
    );

    public void apply(Move move) {
        x += move.x;
        y += move.y;
    }

    // this is the tail or body of the node following head
    public void follow(Position head) {
        // Difference in positions for X axis
        int deltaX = head.x - x;

        // Difference in positions for Y axis
        int deltaY = head.y - y;

        // Now do we move at all? We move if any of the dimensions is off by more than 1
        if (Math.abs(deltaX) > 1 || Math.abs(deltaY) > 1) {
            // if we move at all, we move on both axes, even if we are off by only 1
            x += Integer.signum(deltaX);
            y += Integer.signum(deltaY);
        }
    }

    public Position(int x, int y) {
        super(x, y);
    }

    public Position(Position other) {
        super(other.x, other.y);
    }
}
